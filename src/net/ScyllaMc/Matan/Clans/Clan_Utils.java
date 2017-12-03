package net.ScyllaMc.Matan.Clans;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.ScyllaMc.Matan.Clans.Clan_Member.Clan_Rank;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class Clan_Utils {

	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	private static final Type TT_mapStringString = new TypeToken<Map<String, String>>() {
	}.getType();

	public static Map<String, String> stringToMap(String json) {
		Map<String, String> ret = new HashMap<String, String>();
		if (json == null || json.isEmpty())
			return ret;
		return gson.fromJson(json, TT_mapStringString);
	}

	public static String mapToString(Map<String, String> map) {
		if (map == null)
			map = new HashMap<String, String>();
		return gson.toJson(map);
	}

	public static void leaveClan(final MelonPlayer p) {

		if (p == null) {
			return;
		}

		if (!p.inClan()) {
			p.sendMessage(Message.CLAN_NOTIN);
			return;
		}

		if (p.getClan().getClanMember(p) != null && p.getClan().getClanMember(p).getClanRank().equals(Clan_Rank.LEADER)) {
			p.sendMessage(Message.CLAN_LEADERCANTLEAVE);
			return;
		}

		final Clan clan = p.getClan();

		if (p.getClan().leave(p.getUniqueId())) {

			p.sendMessage(Message.CLAN_YOU_LEFT, new String[] { clan.getColor() + clan.getName() });
			clan.broadcast(Message.CLAN_MEMBER_LEFT, new String[] { p.rank.getTagClosed() + p.getName() });
			
			return;
		}
	}

	public static void changeName(final MelonPlayer p, String newname) {

		if (!p.inClan()) {
			p.sendMessage(Message.CLAN_NOTIN);
			return;
		}

		if (!checkClanName(p, newname)) {
			return;
		}

		if (!p.getClan().getClanMember(p).getClanRank().equals(Clan_Rank.LEADER)) {
			p.sendMessage(Message.GLOBAL_NOPERM);
			return;
		}

		p.getClan().updateName(newname);

		p.getClan().broadcast(Message.CLAN_CHANGENAME, new String[] { newname });
	}

	public static void joinClan(final String name, final MelonPlayer p) {
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			Clan clan = Clan.clanByName(name);

			@Override
			public void run() {		

				if (clan == null) {
					p.sendMessage(Message.CLAN_DOESNOTEXISTS);
					return;
				}

				if (p == null) {
					return;
				}
				if (p.inClan()) {
					p.sendMessage(Message.CLAN_ALREADYIN);
					return;
				}

				if (!clan.canJoin(p, clan)) {
					p.sendMessage(Message.CLAN_INVITE_NOT);
					return;
				}

				clan.join(p);

				clan.broadcast(p.rank.getTagClosed() + p.getName() + ChatColor.GREEN + " has joined the clan!");

			}
			
		}, 10);

	}

	public static boolean isAlpha(String name) {
		if (name.matches("[a-zA-Z]+")) {
			return true;
		}

		return false;

	}

	public static boolean checkClanName(MelonPlayer p, String name) {

		if (!isAlpha(name)) {
			p.sendMessage(Message.CLAN_AZ);
			return false;
		}

		if (name.length() > 10) {
			p.sendMessage(Message.CLAN_NAMETOOLONG);
			return false;
		}

		if (Clan.clanByName(name) != null) {
			p.sendMessage(Message.CLAN_NAMETAKEN);
			return false;
		}

		return true;
	}

	public static void getStringInfo(MelonPlayer p, Clan c) {

		
		if (c != null) {
			
			String leader = "";

			if (c.getLeader() != null) {
				leader = c.getLeader().getName();
			}

			p.sendMessage(Msg.clan + ChatColor.WHITE.toString() + ChatColor.BOLD + c.getName() + "'s clan stats");
			p.sendMessage(Msg.clan + ChatColor.GRAY.toString() + "Leader: " + ChatColor.LIGHT_PURPLE + leader);
			p.sendMessage(Msg.clan + ChatColor.GRAY.toString() + "Points: " + ChatColor.LIGHT_PURPLE + c.getPoints());
			p.sendMessage(Msg.clan + ChatColor.GRAY.toString() + "Members (" + c.getMembers().size() + "): ");

			String list = "";

			for (Clan_Member member : c.getMembers()) {

				list = list + member.getClanRank().getTag() + member.getName() + ChatColor.GRAY + ", ";

			}

			p.sendMessage(list);
			
			return;
		}
		
		p.sendMessage(Message.CLAN_DOESNOTEXISTS);
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * public static void clanInfoByClan(final MelonPlayer p, final String
	 * name){
	 * 
	 * Clan.pullClanFromName(name);
	 * 
	 * 
	 * ProxyServer.getInstance().getScheduler().schedule(ProxyServer.getInstance
	 * ().getPluginManager().getPlugin("MelonBungee"), new Runnable() { public
	 * void run() {
	 * 
	 * final Clan clan = Clan.pulledclans.get(name.toUpperCase());
	 * 
	 * if(clan == null){ p.sendMessage(Main.clanprefix + ChatColor.RED +
	 * " The clan " + ChatColor.GRAY + name + ChatColor.RED + " does not exist!"
	 * ); return; }
	 * 
	 * 
	 * 
	 * final MelonPlayer leader = clan.getLeader();
	 * 
	 * p.sendMessage(Main.clanprefix + ChatColor.GRAY + "-------==[" +
	 * ChatColor.DARK_GREEN + name + ChatColor.GRAY + "]==-------");
	 * p.sendMessage(ChatColor.DARK_AQUA + " Clan: " + clan.getColor() +
	 * clan.getName()); p.sendMessage(ChatColor.DARK_AQUA + " Leader: " +
	 * ChatColor.GRAY + leader.rank.getTagClosed() + leader.getName());
	 * p.sendMessage(ChatColor.DARK_AQUA + " Status: " + ChatColor.GRAY +
	 * clan.status.toString()); p.sendMessage(ChatColor.DARK_AQUA + " Points: "
	 * + ChatColor.GRAY + clan.POINTS);
	 * 
	 * 
	 * if(clan.getOnlineMembersClanMember().size() > 0){ String list = "";
	 * for(Clan_Member member : clan.getOnlineMembersClanMember()){
	 * 
	 * list = list + member.getClanRank().getTag() + member.getName() +
	 * ChatColor.GRAY + ", ";
	 * 
	 * } p.sendMessage(ChatColor.DARK_GREEN + " Online Members: " + list);
	 * 
	 * }
	 * 
	 * if(clan.getOfflineMembersClanMember().size() > 0){
	 * 
	 * String list = ""; for(Clan_Member member :
	 * clan.getOfflineMembersClanMember()){
	 * 
	 * list = list + member.getClanRank().getTag() + member.getName() +
	 * ChatColor.GRAY + ", ";
	 * 
	 * } p.sendMessage(ChatColor.RED + " Offline Members: " + list);
	 * 
	 * }
	 * 
	 * 
	 * p.sendMessage(Main.clanprefix + ChatColor.GRAY +
	 * "-------------------------------------------------");
	 * 
	 * } }, 500, TimeUnit.MILLISECONDS);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 **/

}
