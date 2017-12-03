package net.ScyllaMc.Matan.Clans;

import java.util.UUID;
import java.util.logging.Logger;

import javax.net.ssl.SSLEngineResult.Status;

import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.ScyllaMc.Matan.Clans.Clan.Clan_Status;
import net.ScyllaMc.Matan.Clans.Clan_Member.Clan_Rank;
import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings({ "unused" })
public class Clan {

	public static Map<UUID, Clan> clans = new HashMap<UUID, Clan>();
	public static HashMap<Clan, ArrayList<UUID>> invites = new HashMap<Clan, ArrayList<UUID>>();

	public static enum Clan_Status {

		CLOSED, OPEN, LOCKDOWN;

		public static Clan_Status statusFromString(String text) {
			if (text == null) {
				return Clan_Status.CLOSED;
			}

			for (Clan_Status status : Clan_Status.values()) {
				if (text.toUpperCase().equalsIgnoreCase(status.toString().toUpperCase()) || status.toString().toUpperCase().contains(text.toUpperCase())) {
					return status;
				}
			}

			return Clan_Status.CLOSED;
		}

	}

	public static Clan clanByName(String name) {

		for (UUID ID : clans.keySet()) {

			Clan c = clans.get(ID);

			if (c.getName().equalsIgnoreCase(name)) {
				return c;
			}

		}

		return Clan_Mysql.getClan(name);
	}

	private Clan clan;

	private UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000000");
	private UUID LEADER_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
	private String NAME = "NO_CLAN";

	private MelonPlayer LEADER = null;
	private JsonObject MEMBERS = null;
	private Clan_Status STATUS = Clan_Status.CLOSED;

	private int POINTS = 0;

	private Boolean downloaded = false;

	public static Clan getClan(UUID id) {

		if (clans.containsKey(id)) {

			return clans.get(id);

		} else {
			Clan c = Clan_Mysql.getClan(id);

			if (c != null) {
				return c;
			}

		}

		return null;

	}

	public Clan(UUID ID, UUID LEADER_UUID, String NAME, JsonObject members, Clan_Status status, Integer POINTS) {
		this.clan = this;

		this.ID = ID;
		this.LEADER_UUID = ID;
		this.NAME = NAME;

		this.LEADER = MelonPlayer.getInstanceOfPlayerFromUUID(LEADER_UUID);
		this.MEMBERS = members;
		this.STATUS = status;
		this.POINTS = POINTS;

		this.downloaded = true;
		this.getMembers();

		clans.put(this.ID, this);
	}

	public UUID getID() {
		return this.ID;
	}

	public String getName() {
		return this.NAME;
	}

	public MelonPlayer getLeader() {
		return this.LEADER;
	}

	public UUID getLeaderUUID() {
		return this.LEADER_UUID;
	}

	public Integer getPoints() {
		return this.POINTS;
	}

	public String getTag() {
		return getColor() + "[" + getName() + "] ";
	}

	public ChatColor getColor() {
		return ChatColor.GRAY;
	}

	public JsonObject getMembersJson() {
		return this.MEMBERS;
	}

	public void updateMembers(JsonObject o) {
		this.MEMBERS = o;
	}

	public Clan_Status getStatus() {
		return this.STATUS;
	}

	public void broadcast(Message msg) {
		broadcast(msg.getTransMsg(Language.ENGLISH));
	}

	public void broadcast(Message msg, String[] args) {
		broadcast(msg.getTransMsg(Language.ENGLISH, args));
	}

	public boolean updateName(String newname) {

		Clan_Mysql.setClanString(clan, "NAME", newname);
		Clan_Mysql.setClanString(clan, "NAME_UPPERCASE", newname.toUpperCase());

		clan.NAME = newname;

		return true;
	}

	public void broadcast(String message) {

		for (MelonPlayer pp : getOnlineMembers()) {

			pp.sendMessage(message);

		}
	}

	public ArrayList<Clan_Member> getMembers() {
		ArrayList<Clan_Member> list = new ArrayList<>();

		for (Entry<String, String> pid : Clan_Utils.stringToMap(this.MEMBERS.toString()).entrySet()) {

			UUID puuid = UUID.fromString(pid.getKey());
			String prank = pid.getValue();

			Clan_Rank crank = Clan_Rank.fromString(prank);

			if (this.LEADER_UUID == puuid) {
				crank = Clan_Rank.LEADER;
			}

			list.add(new Clan_Member(puuid, crank));
		}

		return list;
	}

	public ArrayList<UUID> getMembersUUID() {
		ArrayList<UUID> list = new ArrayList<>();

		for (Object o : this.MEMBERS.entrySet()) {
			String puuids = o.toString().split("=")[0];
			list.add(UUID.fromString(puuids));
		}
		return list;
	}

	public ArrayList<Clan_Member> getOnlineMembersClanMember() {
		ArrayList<Clan_Member> list = new ArrayList<Clan_Member>();

		for (Clan_Member cm : getMembers()) {
			MelonPlayer p = MelonPlayer.getInstanceOfPlayerFromUUID(cm.getUUID());
			if (p.isOnline()) {
				list.add(cm);
			}
		}
		return list;
	}

	public ArrayList<Clan_Member> getOfflineMembersClanMember() {
		ArrayList<Clan_Member> list = new ArrayList<Clan_Member>();

		for (Clan_Member cm : getMembers()) {
			MelonPlayer p = MelonPlayer.getInstanceOfPlayerFromUUID(cm.getUUID());
			if (!p.isOnline()) {
				list.add(cm);
			}
		}
		return list;
	}

	public ArrayList<MelonPlayer> getOnlineMembers() {
		ArrayList<MelonPlayer> list = new ArrayList<MelonPlayer>();

		for (UUID pid : getMembersUUID()) {
			MelonPlayer p = MelonPlayer.getInstanceOfPlayerFromUUID(pid);

			if (p.isOnline()) {
				list.add(p);
			}
		}
		return list;
	}

	public boolean leave(UUID id) {

		Map<String, String> members = Clan_Utils.stringToMap(this.getMembersJson().getAsJsonObject().toString());
		members.remove(id.toString());

		Clan_Mysql.setClanString(clan, "MEMBERS", Clan_Utils.mapToString(members));
	
		JsonParser parser = new JsonParser();
		updateMembers((JsonObject) parser.parse(Clan_Utils.mapToString(members).toString()));

		MelonPlayer.getInstanceOfPlayerFromUUID(id).clearClan();

		return true;
	}

	public boolean delete() {

		for (String ID : Clan_Utils.stringToMap(this.getMembersJson().getAsJsonObject().toString()).keySet()) {

			leave(UUID.fromString(ID));

		}

		Clan_Mysql.updateQuery("DELETE FROM `network_clan_data` WHERE ID=" + this.ID + ";");

		clans.remove(this.getID());

		return true;
	}

	public boolean join(MelonPlayer p) {

		Map<String, String> members = Clan_Utils.stringToMap(MEMBERS.getAsJsonObject().toString());
		members.put(p.getUniqueId().toString(), Clan_Rank.MEMBER.toString());

		Clan_Mysql.setClanString(this, "MEMBERS", Clan_Utils.mapToString(members));
		Clan_Mysql.setString(p.getUniqueId(), "clan", getID().toString());

		JsonParser parser = new JsonParser();
		updateMembers((JsonObject) parser.parse(Clan_Utils.mapToString(members).toString()));
		p.sendMessage(Message.CLAN_JOINED_YOU, new String[] { getColor() + getName() });
		p.setClan(this);

		return true;
	}

	public void invitePlayer(MelonPlayer inviter, MelonPlayer newp) {

		if (newp.inClan()) {
			inviter.sendMessage(Message.CLAN_MEMBER_ALREADYIN, new String[] { newp.rank.getTagClosed() + newp.getName(), newp.getClan().getColor() + newp.getClan().getName() });
			return;
		}

		newp.sendMessage(Message.CLAN_INVITED_YOU, new String[] { inviter.rank.getTagClosed() + inviter.getName(), inviter.getClan().getColor() + inviter.getClan().getName() });
		inviter.getClan().broadcast(Message.CLAN_INVITED_MEMBER, new String[] { newp.rank.getTagClosed() + newp.getName(), inviter.rank.getTagClosed() + inviter.getName() });

		ArrayList<UUID> list = new ArrayList<UUID>();

		if (invites.containsKey(clan)) {
			list = invites.get(clan);
		}

		list.add(newp.getUniqueId());
		invites.put(clan, list);
	}

	public boolean canJoin(MelonPlayer p, Clan clan) {
		if (this.STATUS.equals(Clan_Status.OPEN)) {
			return true;
		}
		if (invites.containsKey(clan) && invites.get(clan).contains(p.getUniqueId())) {
			return true;
		}

		return false;
	}

	public Clan_Member getClanMember(MelonPlayer p) {
		for (Clan_Member member : getMembers()) {
			if (member.getMelonPlayer().equals(p)) {
				return member;
			}
		}
		return null;
	}

	public void addPoints(int amount) {
		POINTS = POINTS + amount;
		Clan_Mysql.addIntegerValue(this, "POINTS", amount);
	}

	public void addClanPointsAndCoins(int coins, int points) {
		this.addPoints(points);

		for (MelonPlayer p : this.getOnlineMembers()) {
			p.addCoins(coins, true);
		}

	}

}
