package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandHeal {

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player op = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);
		
		if (!p.hasPermission("server.heal")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}

		if (args.length == 0) {

			op.setHealth(op.getMaxHealth());
			op.setFoodLevel(20);
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Health set to max");
			return;
		}

		if (args.length == 1) {
			if (!p.hasPermission("server.heal.others")) {
				p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
				return;
			}

			if (Bukkit.getPlayer(args[1]) != null) {
				Player gp = Bukkit.getPlayer(args[1]);
				gp.setHealth(gp.getMaxHealth());
				gp.setFoodLevel(20);
				p.sendMessage(MelonCore.prefix + ChatColor.GRAY + gp.getName() + "'s health set to max");
				return;
			} else {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}

		}
		
		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);

	}

}
