package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandFly {

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player op = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);

		if (!p.hasPermission("server.fly")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}

		if (args.length == 0) {
			if (op.getAllowFlight()) {
				p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Fly-Mode disabled!");
				op.setAllowFlight(false);
			} else {
				p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Fly-Mode enabled!");
				op.setAllowFlight(true);
			}

			return;
		}

		if (args.length == 1) {
			if (!p.hasPermission("server.fly.others")) {
				p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
				return;
			}

			if (Bukkit.getPlayer(args[0]) != null) {
				Player gp = Bukkit.getPlayer(args[0]);
				MelonPlayer gmp = MelonPlayer.getInstanceOfPlayer(gp);

				if (gmp.getOnlinePlayer().getAllowFlight()) {
					p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Fly-Mode disabled for " + gmp.getName() + "!");
					gmp.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Fly-Mode disabled!");
					gmp.getOnlinePlayer().setAllowFlight(false);
				} else {
					p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Fly-Mode enabled for " + gmp.getName() + "!");
					gmp.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Fly-Mode enabled!");
					gmp.getOnlinePlayer().setAllowFlight(true);
				}

				return;
			} else {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}

		}

		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);

	}

}
