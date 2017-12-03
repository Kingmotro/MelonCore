package net.ScyllaMc.Matan.Clans;

import java.util.Map;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.ScyllaMc.Matan.Clans.Clan_Member.Clan_Rank;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class Clan_Mysql_Utils {






	public static void setRank(final MelonPlayer leader, MelonPlayer promoted, Clan_Rank rank) {

		if (leader == null || promoted == null) {
			return;
		}
		
		if (!leader.inClan()) {
			leader.sendMessage(Msg.clan + ChatColor.RED + "You are not a member of any clan!");
			return;
		}
		
		if (!promoted.inClan()) {
			leader.sendMessage(Msg.clan + promoted.rank.getTagClosed() + promoted.getName() + ChatColor.RED
					+ " Isnt a member of any clan!");
			return;
		}
		if (!leader.getClan().getMembersUUID().contains(promoted.getUniqueId())) {
			leader.sendMessage(Msg.clan + promoted.rank.getTagClosed() + promoted.getName() + ChatColor.RED + " Isnt in the same clan as you!");
			return;
		}
		
		if (rank.equals(Clan_Rank.LEADER)) {
			leader.sendMessage(Msg.clan + ChatColor.RED + "There can only be one leader!");
			return;
		}

		Clan clan = leader.getClan();
		Map<String, String> members = Clan_Utils.stringToMap(clan.getMembersJson().getAsJsonObject().toString());

		members.remove(promoted.getUniqueId().toString());
		members.put(promoted.getUniqueId().toString(), rank.toString());

		JsonParser parser = new JsonParser();
		clan.updateMembers((JsonObject) parser.parse(Clan_Utils.mapToString(members).toString()));
		Clan_Mysql.setClanString(clan, "MEMBERS", Clan_Utils.mapToString(members));

		
		clan.broadcast(Msg.clan + promoted.rank.getTagClosed() + promoted.getName() + ChatColor.DARK_GREEN + "'s clan rank has been set to " + rank.toString());

		leader.sendMessage(Msg.clan + ChatColor.WHITE + "You have set " + promoted.rank.getTagClosed() + promoted.getName() + ChatColor.WHITE + "'s clan rank to " + rank.toString());
		promoted.sendMessage(Msg.clan + ChatColor.WHITE + "You clan rank has been set to " + rank.toString() + " by " + leader.rank.getTagClosed() + leader.getName());

	}

	
	
	

	
}
