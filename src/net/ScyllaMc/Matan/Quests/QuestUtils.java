package net.ScyllaMc.Matan.Quests;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Util.Title;
import net.md_5.bungee.api.ChatColor;

public class QuestUtils {

	public static ArrayList<UUID> talking = new ArrayList<UUID>();

	public void talk(final String name, final MelonPlayer p, final List<String> list, final String quest,
			final String nextact, final String questname) {
		if (list == null || p == null || name == null) {
			return;
		}
		if (talking.contains(p.getUniqueId())) {
			return;
		}

		talking.add(p.getUniqueId());
		new BukkitRunnable() {
			int number = 0;
			final int max = list.size();;

			@SuppressWarnings("unchecked")
			@Override
			public void run() {

				if (number >= max) {
					p.quests.put(quest, nextact);

					if (talking.contains(p.getUniqueId())) {
						talking.remove(p.getUniqueId());
					}

					if (nextact.contains("final") || nextact.contains("-")) {
						String[] split = nextact.split("-");
						int xp = Integer.parseInt(split[1]);
						p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + " Quest Finished: " + ChatColor.GREEN
								+ questname + ChatColor.DARK_GREEN + "  Rewards: " + ChatColor.GREEN + "+" + xp + "XP");
						Title.sendTitle(p, 100, 100, 100,
								MelonCore.prefix + ChatColor.DARK_GREEN + " Quest Finished: " + ChatColor.GREEN
										+ questname,
								ChatColor.DARK_GREEN + "  Rewards: " + ChatColor.GREEN + "+ " + xp + "XP");
						p.addXp(xp);
					}

					this.cancel();
					return;
				}

				p.sendMessage(
						ChatColor.DARK_GREEN + name + ChatColor.GRAY + " >> " + ChatColor.DARK_AQUA + list.get(number));
				if (p.isOnline()) {
					p.getOnlinePlayer().playSound(p.getOnlinePlayer().getLocation(), Sound.VILLAGER_HAGGLE, 1, 1);
				}
				number++;

			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0L, 40l);

	}

}
