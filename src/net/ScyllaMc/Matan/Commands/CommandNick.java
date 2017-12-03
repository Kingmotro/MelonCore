package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandNick {

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player op = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);
		
		if (!p.hasPermission("server.nick")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}

		if (args.length == 1) {
			String nick = args[0];
			if (nick.length() > 16) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Nick names cannot be longer then 16 charecters!");
				return;
			}
			if (!nick.matches("[a-zA-Z0-9]+")) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Nick names can only contains letters and numbers");
				return;
			}

			op.setDisplayName(nick);
			p.sendMessage(MelonCore.prefix + "You have changed your name to: " + nick);
			return;
		}

		if (args.length == 2) {
			if (!p.hasPermission("server.nick.others")) {
				p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
				return;
			}
			if (Bukkit.getPlayer(args[1]) == null) {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}
			Player o = Bukkit.getPlayer(args[1]);

			String nick = args[0];
			if (nick.length() > 16) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Nick names cannot be longer then 16 charecters!");
				return;
			}
			if (!nick.matches("[a-zA-Z0-9]+")) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Nick names can only contains letters and numbers");
				return;
			}

			o.setDisplayName(nick);
			o.sendMessage(MelonCore.prefix + p.getName() + " has set your name to as:" + nick);
			p.sendMessage(MelonCore.prefix + "You have changed " + o.getName() + "'s name to: " + nick);
			return;
		}

		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);
		return;

	}

}
