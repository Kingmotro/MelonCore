package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandTeleport {

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player op = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);

		if (!p.hasPermission("server.teleport")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
			if (!p.hasPermission("server.admin")) {
				p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
				return;
			}

			for (Player o : Bukkit.getOnlinePlayers()) {
				if (!o.getUniqueId().equals(p.getUniqueId())) {
					o.teleport(op);
					o.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Teleported to " + p.getName());
				}
			}

			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Teleported to all players");
			return;
		}

		if (args.length >= 2) {
			if (!CommandManager.isInt(args[0])) {
				if (Bukkit.getPlayer(args[0]) == null) {
					p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
					return;
				}
				if (Bukkit.getPlayer(args[1]) == null) {
					p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
					return;
				}
				Player p1 = Bukkit.getPlayer(args[0]);
				Player p2 = Bukkit.getPlayer(args[1]);

				p1.teleport(p2);
				p1.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Teleported to " + p2.getName());

				return;
			}
		}

		if (!CommandManager.isInt(args[0])) {
			if (Bukkit.getPlayer(args[0]) == null) {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}
			Player gp = Bukkit.getPlayer(args[0]);
			op.teleport(gp);
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Teleported to " + gp.getName());
			return;
		}

		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);
		return;

	}

}
