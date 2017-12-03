package net.ScyllaMc.Matan.MelonCore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class Msg {

	public static String prefix = ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "[!] ";
	public static String prefixb = ChatColor.RED + ChatColor.BOLD.toString() + "[!] ";
	public static String debugprefix = ChatColor.RED + ChatColor.BOLD.toString() + "[DEBUG] ";
	public static String clan = ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "[!] ";

	public static HashMap<Language, FileConfiguration> language_files = new HashMap<Language, FileConfiguration>();

	@SuppressWarnings("rawtypes")
	public static void debug(String message, Class c) {

		for (Player op : Bukkit.getOnlinePlayers()) {

			MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);

			if (p.inDebug()) {

				p.sendMessage(debugprefix + ChatColor.DARK_GRAY + "| " + c.getSimpleName() + " | " + ChatColor.GRAY + message);

			}

		}

	}

	public static enum Language {
		ENGLISH, DUTCH;

		public static Language fromString(String text) {
			if (text == null) {
				return ENGLISH;
			}
			Language[] arrayOfLanguage;
			int j = (arrayOfLanguage = values()).length;
			for (int i = 0; i < j; i++) {
				Language lan = arrayOfLanguage[i];
				if (text.toUpperCase().equalsIgnoreCase(lan.toString().toUpperCase())) {
					return lan;
				}
			}
			return ENGLISH;
		}

	}

	public static void writeDefaultEnglish() {

		File file = new File(Bukkit.getPluginManager().getPlugin("MelonCore").getDataFolder(), "base_english.yml");
		FileConfiguration lanfile = (FileConfiguration) YamlConfiguration.loadConfiguration(file);

		for (Message m : Message.values()) {
			lanfile.set(m.toString(), m.msg);

		}
		
	
		
		try {
			lanfile.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static enum Message {

		GLOBAL_COMMAND_NOTHERE("&cYou cannot use this command in this server!"),
			GLOBAL_COMMAND_PLAYERNOTFOUND("&cThat player was not found, please try again"),
			GLOBAL_COMMAND_INVALIDUSE("&CInvalid use of command, use /guide for help"),
			GLOBAL_COMMAND_NOPERM("&CYou are not permitted to use this command!"),
			GLOBAL_COMMAND_CHANGELAN("&aYour language has been set to [?]"),

			GLOBAL_COINS("Coins"),
			GLOBAL_KEYS("Keys"),
			GLOBAL_DEFENCE("Defence"),
			GLOBAL_ATTACK("Attack"),

			MOB_EARTHBANDIT("Bandit"),
			MOB_BOAR("Wild Boar"),
			MOB_SHEEP("Sheep"),
			MOB_COW("Cow"),
			MOB_DUCK("Duck"),
			MOB_RAM("Ram"),

			BATTLE_INVITE_FROM("[?] &3Has offered to duel you! "),
			BATTLE_INVITE_TO("&3You invited [?] &3to a duel!"),
			BATTLE_INVITE_TIMEOUT_TO("&cDuel request to [?] &3Timed out!"),
			BATTLE_INVITE_TIMEOUT_FROM("&cDuel request from [?] &3Timed out!"),
			BATTLE_INVITE_ALREADYINVITED("&cYou have already invited that player to a duel!"),
			BATTLE_INVITE_SELF("&cYou cannot duel yourself!"),
			BATTLE_INVITE_NOTINVITED("&c That player did not invite you for a duel!"),
			BATTLE_INVITE_TOOFAR("&cYou need to stand closer to other player to duel!"),
			BATTLE_INVITE_ALREADYIN_YOU("&cYou are already in a duel!"),
			BATTLE_INVITE_ALREADYIN("&cThat player is already in a duel!"),

			BATTLE_STARTING("&3Duel countdown Starting...."),
			BATTLE_COUNTDOWN("&3Duel Starting in [?] &3seconds!"),
			BATTLE_STARTED("&3The duel has &astarted!"),
			BATTLE_FORCEEND_TIME("&3The duel has ended because it was too long!"),
			BATTLE_WINNER("[?] &awon the duel!"),
			BATTLE_REMOVE_QUIT("[?] &c has been kicked from the duel because he went offline"),
			BATTLE_REMOVE_DISTANCE("[?] &chas been removed from the duel because he ran away too far"),
			BATTLE_DIED("[?] &chad died and lost the duel!"),

			MYSTERY_BOX("&d&lMystery Box"),
			MYSTERY_BOX_HOLO_GETMOREKEYS("&2Use keys to open the box, /Vote to get more"),
			MYSTERY_BOX_HOLO_KEYS("&2You Have &a[?] &2Keys"),
			MYSTERY_BOX_OPEN("&d&lOpening the box..."),
			MYSTERY_BOX_WAIT("&cPlease wait before opening another box!"),
			MYSTERY_BOX_NOTENOUGH("&cYou do not have any keys! /Vote to get more"),
			
			TABLE_START("&5Creating a rune"),
			TABLE_ADD_RUNE("&5You have added a shard, and increased the power by &d[?]"),
			TABLE_CRAFT("&5You have crafted a [?] &5run!"),
			TABLE_ERR_NOT_A_SHARD("&cYou can only add shards to the table"),
			TABLE_ERR_NO_POWER("&cYou need to add shard to craft a run, you can find shards in the mystery box!"),
			TABLE_HOLO("&5&lRune Crafting Table"),
			TABLE_HOLO_TOP("&dCurrect power: &8[?]"),
			TABLE_HOLO_RUNE("&5Runs &dare artifacts that empower you"),
			TABLE_HOLO_RIGHT("&5RIGHT CLICK &dwith shards to add thier power"),
			TABLE_HOLO_LEFT("&5LEFT-CLICK &dto craft a powerful rune"),

			GLOBAL_NOPERM("&cYou are not permitted to use this command!"),
			GLOBAL_INVALIDUSE("&CInvaild use of command!"),
			
			CLAN_CREATING("&fCreating clan...."),
			CLAN_CHANGENAME("&fThe clan name has been changed to [?]"),
			CLAN_NOWLEADER("&fYou are now the leader of the clan [?]"),
			
			CLAN_DOESNOTEXISTS("&cThat clan does not exists"),
			CLAN_DIABANDED("&cThe clan has been disbanded!"),
			CLAN_NAMETAKEN("&cA clan by that name already exists!"),
			CLAN_ALREADYIN("&cYou are not a member of any clan!"),
			CLAN_NOTIN("&cYou are not a member of any clan!"),
			CLAN_LEADERCANTLEAVE("&cYou are the leader of the clan and cannot leave it!"),
			CLAN_YOU_LEFT("&cYou are no longer a member [?]"),
			CLAN_MEMBER_LEFT("[?] &chas left the clan"),
			CLAN_MEMBER_KICKED("[?] &chas been kicked from the clan by [?]"),
			CLAN_MEMBER_ALREADYIN("[?] &c is already in the clan [?]"),
			
			CLAN_AZ("&cA Clan name can only contain latters from A-Z!"),
			CLAN_NAMETOOLONG("&cClan Names cannot be longer then 10 charecters"), 
		
			CLAN_INVITED_YOU("&f[?] has invited you to join the clan [?]"),
			CLAN_INVITED_MEMBER("&f[?] has been invited to join the clan by [?]"),
			CLAN_INVITE_NOT("&cYou haven't been invited to join this clan!"),
			
			CLAN_JOINED_YOU("&fYou are now a memeber of [?]"),
			CLAN_JOINED_MEMBER("&fYou are now a memeber of [?]"),
		;

		String msg = "";

		private Message(String msg) {
			this.msg = msg;
		}

		
		
		public String getTransMsg(Msg.Language lan) {

			String fin = this.msg;

			
			if ((lan != null) && (lan != Msg.Language.ENGLISH)) {
				if (!Msg.language_files.containsKey(lan)) {
					File file = new File(Bukkit.getPluginManager().getPlugin("MelonCore").getDataFolder(), lan.toString() + ".yml");
					Msg.language_files.put(lan, YamlConfiguration.loadConfiguration(file));
				}
				FileConfiguration lanfile = (FileConfiguration) Msg.language_files.get(lan);
				if (lanfile.contains(toString())) {
					fin = lanfile.getString(toString());
				}
			}

			return ChatColor.translateAlternateColorCodes('&', fin);
		}

		public String getTransMsg(Msg.Language lan, String[] fill) {
			String s = getTransMsg(lan);
			for (int r = 0; r < fill.length; r++) {
				s = s.replaceFirst("[?]", fill[r].toString());
			}
			s = s.replaceAll("[\\[\\]]", "");

			return s;
		}

		public void send(MelonPlayer p) {
			if (p.isOnline()) {
				p.getOnlinePlayer().sendMessage(Msg.prefix + getTransMsg(p.lan));
			}
		}

		public void send(MelonPlayer p, String[] fill) {
			if (p.isOnline()) {
				p.getOnlinePlayer().sendMessage(Msg.prefix + getTransMsg(p.lan, fill));
			}
		}
	}

}
