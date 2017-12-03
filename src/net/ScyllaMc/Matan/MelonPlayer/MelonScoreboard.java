package net.ScyllaMc.Matan.MelonPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.ScyllaMc.Matan.Items.Item;
import net.ScyllaMc.Matan.Items.Item.ModifierType;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonCore.MelonCore.ServerType;
import net.ScyllaMc.Matan.MelonPlayer.Buff.BuffType;
import net.ScyllaMc.Matan.Mobs.Bending.BendingManager;
import net.ScyllaMc.Matan.Util.ActionBar;

public class MelonScoreboard implements Listener {

	public void showActionBar(MelonPlayer p) {

		String g = "" + ChatColor.GRAY;
		String gl = "" + ChatColor.GOLD;
		String i = ChatColor.GRAY + " || ";
		String i2 = ChatColor.GRAY + "  *  ";

		String c = NumberFormat.getNumberInstance(Locale.US).format(p.coins);
		double d = p.getDefenceBonus();
		DecimalFormat format = new DecimalFormat("#.##");
		double d2 = Double.valueOf(format.format(d));
		double damage = p.getDamageBonus() * 100 + 100;

		String coins = g + Msg.Message.GLOBAL_COINS.getTransMsg(p.lan) + " " + gl + c;
		String keys = g + Msg.Message.GLOBAL_KEYS.getTransMsg(p.lan) + " " + gl + p.votetokens;
		String defence = ChatColor.DARK_AQUA + "" + d2 + g + p.getBonusString(p.getBuffs(ModifierType.DEFENCE, BuffType.DEFENCE)) + g + " " + Msg.Message.GLOBAL_DEFENCE.getTransMsg(p.lan);
		String attack = ChatColor.DARK_AQUA + "" + damage + g + p.getBonusString(p.getBuffs(ModifierType.DAMAGE, BuffType.DAMAGE)) + g + " " + Msg.Message.GLOBAL_ATTACK.getTransMsg(p.lan);

		String p1 = coins + i2 + keys + i;
		String p2 = i + defence + i2 + attack;

		new ActionBar(padToMatch(p1, getCurrentAbility(p), p2), p.getOnlinePlayer());
		
		sendTabTitle(p.getOnlinePlayer(), "", ChatColor.DARK_AQUA + "Buy ranks at: " + ChatColor.LIGHT_PURPLE + "Scylla" + ChatColor.AQUA + "Mc" + ChatColor.GRAY + ".Net");

	}

	public String getLongerString(String s1, String s2) {

		if (s2.length() > s1.length()) {
			return s2;
		}

		return s1;
	}

	public String getShorterString(String s1, String s2) {

		if (s2.length() <= s1.length()) {
			return s2;
		}

		return s1;
	}

	public String padToMatch(String s1, String c, String s2) {

		String fin = c;

		String l = getLongerString(s1, s2);

		String s1f = fixedLengthString(s1, l.length());
		String s2f = fixedLengthString(s2, l.length());

		fin = s1f + c + s2f;

		return fin;
	}

	public static String fixedLengthString(String string, int length) {
		return String.format("%1$" + length + "s", string);
	}

	public String getCurrentAbility(MelonPlayer p) {

		String middle = "Unknown Realm";

		if (Item.isItem(p.getOnlinePlayer().getItemInHand()) && Item.fromItemStack(p.getOnlinePlayer().getItemInHand()).getLevel() > p.level) {

			return ChatColor.RED + "Item out of lvl";
		}

		if (MelonCore.server.equals(ServerType.BEND)) {
			com.projectkorra.projectkorra.BendingPlayer bp = com.projectkorra.projectkorra.BendingPlayer.getBendingPlayer(p.getName());
			int slot = p.getOnlinePlayer().getInventory().getHeldItemSlot() + 1;
			String a = "null";

			if (bp != null) {
				if (bp.getAbilities() != null) {
					if (bp.getAbilities().get(slot) != null) {
						a = bp.getAbilities().get(slot);
					}
				}
			}

			String ca = BendingManager.getAbilityColor(a) + a;
			if (ca.contains("null")) {
				ca = ChatColor.GRAY + "Empty Bind";
			}
			String bindb = ca;

			String bindb2 = StringUtils.center(bindb, 19);
			middle = bindb2;
		}

		return middle;
	}

	public void showScoreboard(MelonPlayer p) {

		p.setScoreboard(MelonCore.mainScoreboard);
		updateAllRanks(p);
		showActionBar(p);
	}

	@SuppressWarnings("deprecation")
	public void updateAllRanks(MelonPlayer mp) {
		Scoreboard mainScoreboard = MelonCore.mainScoreboard;

		String prefix = mp.rank.getTagClosed();
		String suffix = "";

		if (mp.hidden) {
			suffix = suffix + ChatColor.GRAY + " [Hidden]";
		} else if (Duel.inDuel(mp)) {
			suffix = suffix + ChatColor.RED + " [In Battle]";
		} else if (MelonCore.server.equals(ServerType.BEND)) {
			suffix = suffix + ChatColor.GRAY + " [lvl " + mp.level + "]";
		}

		try {
			for (Team t : mainScoreboard.getTeams()) {
				if (t.getName().equalsIgnoreCase(mp.getName())) {
					t.setPrefix(prefix);
					t.setSuffix(suffix);
					return;
				}
			}

			Team team = mainScoreboard.registerNewTeam(mp.getName());
			team.setPrefix(prefix);
			team.setSuffix(suffix);
			team.addPlayer(mp.getOnlinePlayer());

			mp.setScoreboard(mainScoreboard);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public static int SECONDS_IN_A_DAY = 24 * 60 * 60;

	public static String checkWinterSale() {

		Calendar thatDay = Calendar.getInstance();
		thatDay.setTime(new Date(0));

		thatDay.set(Calendar.DAY_OF_MONTH, 7);
		thatDay.set(Calendar.MONTH, 5);
		thatDay.set(Calendar.YEAR, 2017);

		long diff = thatDay.getTimeInMillis() - new Date().getTime();
		long diffSec = diff / 1000;

		long days = diffSec / SECONDS_IN_A_DAY;
		long secondsDay = diffSec % SECONDS_IN_A_DAY;
		long seconds = secondsDay % 60;
		long minutes = (secondsDay / 60) % 60;
		long hours = (secondsDay / 3600);

		if (days > 0) {
			return String.format(ChatColor.DARK_AQUA + "%d" + ChatColor.GRAY + " Days & " + ChatColor.DARK_AQUA + "%d" + ChatColor.GRAY + " Hours until " + ChatColor.GOLD.toString() + ChatColor.BOLD + " Third off Sale " + ChatColor.GRAY + "ends!", days, hours);
		} else if (days == 0 && hours > 0 && minutes > 0) {
			return String.format(ChatColor.DARK_AQUA + "%d" + ChatColor.RED + " Hours & " + ChatColor.DARK_AQUA + "%d" + ChatColor.RED + " Minutes until " + ChatColor.GOLD.toString() + ChatColor.BOLD + "Third off Sale  " + ChatColor.RED + "ends!", hours, minutes);
		} else if (hours == 0 && seconds > 0 && minutes > 0) {
			return String.format(ChatColor.DARK_AQUA + "%d" + ChatColor.DARK_RED + " Minutes & " + ChatColor.DARK_AQUA + "%d" + ChatColor.RED + " Seconds until " + ChatColor.GOLD.toString() + ChatColor.BOLD + "Third off Sale  " + ChatColor.GRAY + "ends!", minutes, seconds);
		} else if (minutes == 0 && seconds > 0) {
			return String.format(ChatColor.DARK_AQUA + "%d" + ChatColor.DARK_RED + " Seconds left until " + ChatColor.GOLD.toString() + ChatColor.BOLD + "Third off Sale  " + ChatColor.GRAY + "ends!", seconds);
		} else if (days < 0 || hours < 0 || minutes < 0 || seconds < 0) {
			return String.format(ChatColor.RED + "The 30% sale has ended ):");
		}

		return (ChatColor.RED + "The 30% off Reopening sale has ended");

	}

	public static void sendTabTitle(Player player, String header, String footer) {
		if (header == null)
			header = "";
		header = ChatColor.translateAlternateColorCodes('&', header);

		if (footer == null)
			footer = "";
		footer = ChatColor.translateAlternateColorCodes('&', footer);

		header = header.replaceAll("%player%", player.getDisplayName());
		footer = footer.replaceAll("%player%", player.getDisplayName());

		try {
			Object tabHeader = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
			Object tabFooter = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
			Constructor<?> titleConstructor = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(getNMSClass("IChatBaseComponent"));
			Object packet = titleConstructor.newInstance(tabHeader);
			Field field = packet.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(packet, tabFooter);
			sendPacket(player, packet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

}
