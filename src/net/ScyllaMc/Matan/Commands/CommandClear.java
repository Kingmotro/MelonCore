package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandClear {

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player op = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);

		if (!p.hasPermission("server.clear")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}

		if (args.length == 0) {
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Inventory cleared.");
			p.getOnlinePlayer().getInventory().clear();
			return;
		}

		if (args.length == 1) {
			if (!p.hasPermission("server.mod")) {
				p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
				return;
			}
			if (Bukkit.getPlayer(args[0]) == null) {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}

			if (!CommandManager.isInt(args[0])) {
				Player gp = Bukkit.getPlayer(args[0]);
				p.sendMessage(MelonCore.prefix + ChatColor.GRAY + gp.getName() + "'s inventory has been cleared.");
				gp.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Inventory cleared by " + p.getName());
				gp.getInventory().clear();
				return;
			}
		}

		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);
		return;

	}

}
