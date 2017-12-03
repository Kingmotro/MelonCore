package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandCoins {

	@SuppressWarnings("deprecation")
	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player player = (Player) sender;
		Player p = player;

		if (args.length == 0) {
			p.sendMessage(ChatColor.GRAY + "-------------");
			p.sendMessage(MelonCore.prefix + ChatColor.RED + "Wrong use of command.");
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "/coins send (Player) (Amount)");
			p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "/coins (Player)");
			p.sendMessage(ChatColor.GRAY + "-------------");
			return;
		}

		if (args[0].equalsIgnoreCase("Send")) {

			if (args.length < 3 || !CommandManager.isInt(args[2])) {
				p.sendMessage(ChatColor.GRAY + "-------------");
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Wrong use of command.");
				p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "/coins send (Player) (Amount)");
				p.sendMessage(ChatColor.GRAY + "-------------");
				return;
			}

			if (Bukkit.getPlayer(args[1]) == null) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Player not found");
				return;
			}

			Integer amount = Integer.valueOf(args[2]);
			MelonPlayer coinsender = MelonPlayer.getInstanceOfPlayer(player);
			MelonPlayer sent = MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(args[1]));

			if (amount < 50) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You cannot send less then 50 coins!");
				return;
			}
			if (coinsender.coins < amount) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have enough coins.");
				return;
			}

			int minusamount = amount * (-1);
			coinsender.addCoins(minusamount, false);
			sent.addCoins(amount, false);

			player.sendMessage(MelonCore.prefix + ChatColor.GREEN + " You have sent " + ChatColor.AQUA + amount + ChatColor.GREEN + " Coins to " + ChatColor.AQUA + sent.getName());
			sent.sendMessage(MelonCore.prefix + ChatColor.GREEN + " You have received " + ChatColor.AQUA + amount + ChatColor.GREEN + " Coins from " + ChatColor.AQUA + player.getName());

			return;
		}

		if (args.length == 1) {
			if (Bukkit.getOfflinePlayer(args[0]) == null) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Player not found");
			} else {
				OfflinePlayer s = Bukkit.getOfflinePlayer(args[0]);
				player.performCommand("stats " + s.getName());
			}
			return;
		}

		p.sendMessage(MelonCore.prefix + ChatColor.RED + "Wrong use of command.");
		return;

	}
}
