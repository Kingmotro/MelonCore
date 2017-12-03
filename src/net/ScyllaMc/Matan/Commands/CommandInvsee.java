package net.ScyllaMc.Matan.Commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandInvsee {

	public static HashMap<UUID, UUID> battleinvites = new HashMap<UUID, UUID>();

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}
		Player player = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(player);

		if (!player.hasPermission("server.mod")) {

			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);

			return;
		}

		if (args.length == 0) {
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_AQUA + "Invsee Commands:");
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + "/Invsee (Player) " + ChatColor.GRAY
					+ "- Watch the inventory of a player");
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + "/Invsee Ender (Player) " + ChatColor.GRAY
					+ "- Watch the inventory of a player");
			return;
		}

		if (args.length == 1) {
			if (Bukkit.getPlayer(args[0]) == null) {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}
			Player o = Bukkit.getPlayer(args[0]);

			player.openInventory(o.getInventory());
			return;
		}

		if (args.length == 2 || args[0].equalsIgnoreCase("ender")) {
			if (Bukkit.getPlayer(args[1]) == null) {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}
			Player o = Bukkit.getPlayer(args[1]);
			player.openInventory(o.getEnderChest());
			return;
		}

		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);

	}

}
