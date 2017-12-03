package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandGamemode {

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player op = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);
		
		if (!p.hasPermission("server.gamemode")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}
		if (args.length < 1) {
			p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);
			return;
		}

		if (args.length == 1) {
			op.setGameMode(GameMode.getByValue(Integer.parseInt(args[0])));
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Gamemode set to " + GameMode.getByValue(Integer.parseInt(args[0])).toString().toLowerCase());
			return;
		}

		if (args.length == 2) {
			if (!p.hasPermission("server.admin")) {
				p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
				return;
			}

			GameMode gm = GameMode.getByValue(Integer.parseInt(args[0]));
			if (Bukkit.getPlayer(args[1]) != null) {
				Player gp = Bukkit.getPlayer(args[1]);
				gp.setGameMode(gm);
				p.sendMessage(MelonCore.prefix + ChatColor.GRAY + gp.getName() + "'s gamemode set to " + gm.toString().toLowerCase());
				return;
			} else {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}

		}

	}

}
