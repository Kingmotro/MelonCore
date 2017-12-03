package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandGod {

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		MelonPlayer p = MelonPlayer.getInstanceOfPlayer((Player) sender);

		if (!p.hasPermission("server.god")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}

		if (args.length == 0) {
			if (p.inGodmode) {
				p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "God-Mode disabled!");
				p.inGodmode = false;
			} else {
				p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "God-Mode enabled!");
				p.inGodmode = true;
			}

			return;
		}

		if (args.length == 1) {
			if (!p.hasPermission("server.admin")) {
				p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
				return;
			}

			if (Bukkit.getPlayer(args[0]) != null) {
				Player gp = Bukkit.getPlayer(args[0]);
				MelonPlayer gmp = MelonPlayer.getInstanceOfPlayer(gp);

				if (gmp.inGodmode) {
					p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "God-Mode disabled for " + gmp.getName() + "!");
					gmp.sendMessage(MelonCore.prefix + ChatColor.GRAY + "God-Mode disabled!");
					gmp.inGodmode = false;
				} else {
					p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "God-Mode enabled for " + gmp.getName() + "!");
					gmp.sendMessage(MelonCore.prefix + ChatColor.GRAY + "God-Mode enabled!");
					gmp.inGodmode = true;
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
