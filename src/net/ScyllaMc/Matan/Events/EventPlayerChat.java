package net.ScyllaMc.Matan.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.unon1100.TellrawAPI.TellrawAPI;
import com.unon1100.TellrawAPI.TellrawText;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Rank.Rank;

@SuppressWarnings("deprecation")
public class EventPlayerChat implements Listener {

	public static HashMap<String, Integer> cooldownTime = new HashMap<String, Integer>();
	public static HashMap<String, BukkitRunnable> cooldownTask = new HashMap<String, BukkitRunnable>();

	public static void addCoolDown(final Player p, int time) {
		cooldownTime.put(p.getUniqueId().toString(), time);
		cooldownTask.put(p.getUniqueId().toString(), new BukkitRunnable() {
			@Override
			public void run() {
				cooldownTime.put(p.getUniqueId().toString(), cooldownTime.get(p.getUniqueId().toString()) - 1);
				if (cooldownTime.get(p.getUniqueId().toString()) == 0) {
					cooldownTime.remove(p.getUniqueId().toString());
					cooldownTask.remove(p.getUniqueId().toString());
					cancel();
				}
			}
		});
		cooldownTask.get(p.getUniqueId().toString()).runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 10, 10);
	}

	public static ArrayList<UUID> hidewarn = new ArrayList<UUID>();

	@EventHandler
	public void chatFormat(PlayerChatEvent event) {
		Player p = event.getPlayer();

		MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(p);

		if (mp.hidden && !hidewarn.contains(mp.getUniqueId())) {
			p.sendMessage(MelonCore.prefix + ChatColor.RED + "YOU ARE HIDDEN! You shouldnt chat! you have been warned.");
			event.setCancelled(true);
			hidewarn.add(mp.getUniqueId());
			return;
		}

		if (mp.rank.equals(Rank.OBAMA) || mp.rank.equals(Rank.USELESS)) {
			event.getMessage();

			Random random = new Random();
			int Chance = random.nextInt(6);
			if (Chance == 5) {
				event.setMessage("I like to suck ****");
			}
		}

		if (cooldownTime.containsKey(p.getUniqueId().toString())) {
			p.sendMessage(MelonCore.prefix + ChatColor.RED + "Please dont spam Chat!");
			event.setCancelled(true);
			return;
		}

		if (!p.isOp()) {

			if (mp.lastMessage.equalsIgnoreCase(event.getMessage())) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Do not repeat yourself, be original! ");
				event.setCancelled(true);
				return;
			}

			mp.setLastMessage(event.getMessage().toLowerCase());
			// addCoolDown(p, 1);
		}

		TellrawText name = new TellrawText(mp.rank.getColor() + p.getName());
		name.setHover(ChatColor.DARK_AQUA + "Rank: " + ChatColor.AQUA + mp.rank.getTag() + ChatColor.GRAY + " || " + ChatColor.DARK_AQUA + "Kills: " + ChatColor.AQUA + mp.kills + ChatColor.GRAY + " || " + ChatColor.DARK_AQUA + "Deaths: " + ChatColor.AQUA + mp.deaths);

		String clantag = " ";
		if (mp.inClan()) {
			clantag = mp.clan.getTag();
		}

		TellrawText rank = new TellrawText(mp.rank.getTag());
		TellrawText clan = new TellrawText(" " + clantag);
		TellrawText bar = new TellrawText(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "⏐ ");

		if (mp.rank.getName().equals("Default")) {
			rank = new TellrawText(ChatColor.DARK_GRAY.toString());
		}

		TellrawText level = null;

		if (mp.level < 10) {
			level = new TellrawText(ChatColor.GRAY + "" + mp.level + ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + " ⏐ ");
		} else {
			level = new TellrawText(ChatColor.GRAY + "" + mp.level + ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + " ⏐ ");
		}

		TellrawText messageprefix = new TellrawText(ChatColor.DARK_GRAY + " >> " + mp.rank.getColor());

		String msg = event.getMessage();
		String newmsg = "";

		for (int i = 0; i < msg.length(); i++) {
			newmsg = newmsg + mp.rank.getChatColor().toString() + msg.charAt(i);
		}

		TellrawText message = new TellrawText(newmsg);

		StringBuilder sb = null;
		if (mp.rank.equals(Rank.DEFAULT) && !mp.inClan()) {
			sb = TellrawAPI.finalize(level, name, messageprefix, message);
		} else {
			sb = TellrawAPI.finalize(level, rank, clan, bar, name, messageprefix, message);

		}
		TellrawAPI.sendToAll(sb);
		event.setCancelled(true);

	}

}
