package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.Events.EventPlayerChat;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Util.Title;
import net.md_5.bungee.api.ChatColor;

public class CommandHide {

	@SuppressWarnings("static-access")
	public void run(final CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		MelonPlayer p = MelonPlayer.getInstanceOfPlayer((Player) sender);

		if (!p.hasPermission("server.hide")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}

		if (p.hidden) {
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "You are no longer hidden.");
			Bukkit.broadcastMessage(MelonCore.prefix + p.rank.getTagClosed() + p.getName() + ChatColor.DARK_GREEN + " Joined the server!");

			for (Player ap : Bukkit.getOnlinePlayers()) {
				new Title().sendTitle(MelonPlayer.getInstanceOfPlayer(ap), 20, 50, 50, "", p.rank.getTagClosed() + p.getName() + ChatColor.DARK_GREEN + " Joined the server!");

			}

			p.hidden = false;
		} else {
			Bukkit.broadcastMessage(MelonCore.prefix + p.rank.getTagClosed() + p.getName() + ChatColor.RED + " has left the server!");
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "You are now hidden.");
			p.hidden = true;

			new BukkitRunnable() {
				final MelonPlayer mpf = MelonPlayer.getInstanceOfPlayer((Player) sender);

				@Override
				public void run() {
					MelonCore.MelonScoreboard.updateAllRanks(mpf);

					if (!mpf.isOnline() || mpf == null) {
						this.cancel();
						return;
					}

					if (!mpf.hidden || !mpf.getOnlinePlayer().hasPermission("server.hide")) {
						showAll(mpf.getOnlinePlayer());
						this.cancel();
						if (EventPlayerChat.hidewarn.contains(mpf.getUniqueId())) {
							EventPlayerChat.hidewarn.remove(mpf.getUniqueId());
						}
						return;
					}

					hideAll(mpf.getOnlinePlayer());
				}
			}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 10, 10);

		}
		
		
		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);

	}

	public void showAll(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.showPlayer(p);
		}
	}

	public void hideAll(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.hasPermission("server.hide")) {
				player.hidePlayer(p);
			} else {
				player.showPlayer(p);
			}
		}
	}

}
