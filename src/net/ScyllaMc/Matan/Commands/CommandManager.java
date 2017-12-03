package net.ScyllaMc.Matan.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Mcbender.Matan.MelonCore.BungeeUtils;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Rank.Rank;

public class CommandManager implements CommandExecutor {

	public static boolean isInt(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

		if (!(sender instanceof Player)) {
			if (command.getName().equalsIgnoreCase("shutdown")) {
				MelonCore.shutDown();
				return true;
			}

		}

		String prefix = MelonCore.prefix;
		Player player = (Player) sender;
		Player p = (Player) sender;

		if (sender instanceof Player && MelonPlayer.getInstanceOfPlayer(p).rank.equals(Rank.USELESS) || MelonPlayer.getInstanceOfPlayer(p).rank.equals(Rank.OBAMA)) {
			p.sendMessage(ChatColor.RED + "You are useless, you cannot do this!");
			p.playSound(p.getLocation(), Sound.FIZZ, 1, 1);
			return true;
		}

		try {
			if (command.getName().equalsIgnoreCase("speed")) {
				new CommandSpeed().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("gamemode")) {
				new CommandGamemode().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("heal")) {
				new CommandHeal().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("coins")) {
				new CommandCoins().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("stats")) {
				new CommandStats().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("god")) {
				new CommandGod().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("give")) {
				new CommandGive().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("clear")) {
				new CommandClear().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("language")) {
				new CommandLanguage().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("teleport")) {
				new CommandTeleport().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("tpa")) {
				new CommandTPA().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("rename")) {
				new CommandRename().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("repair")) {
				new CommandRepair().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("itemdb")) {
				new CommandItemdb().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("fly")) {
				new CommandFly().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("hide")) {
				new CommandHide().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("battle")) {
				new CommandBattle().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("invsee")) {
				new CommandInvsee().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("nick")) {
				new CommandNick().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("home")) {
				new CommandHome().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("help")) {
				new CommandGuide().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("autobind")) {
				new CommandAutobind().run(sender, args);
				return true;
			}

			if (command.getName().equalsIgnoreCase("market")) {
				new CommandMarket().run(sender, args);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (command.getName().equalsIgnoreCase("spawn")) {

			p.teleport(MelonCore.spawn);
			p.sendMessage(prefix + ChatColor.LIGHT_PURPLE + "Teleported to spawn");
			return true;
		}
		
		if (command.getName().equalsIgnoreCase("lobby")) {

			BungeeUtils.Kick(MelonPlayer.getInstanceOfPlayer(p), "lobby");
			return true;
		}

		if (command.getName().equalsIgnoreCase("plugins")) {
			return true;
		}

		if (command.getName().equalsIgnoreCase("Rules")) {

			player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
			player.sendMessage(prefix + ChatColor.RED + "These rules need to be followed.");
			player.sendMessage(prefix + ChatColor.RED + "if you break any of the rules you might be banned from the server!");
			player.sendMessage(prefix + ChatColor.GRAY + "[1] " + ChatColor.RED + " Do not swear ");
			player.sendMessage(prefix + ChatColor.GRAY + "[2] " + ChatColor.RED + " Do not spam ");
			player.sendMessage(prefix + ChatColor.GRAY + "[3] " + ChatColor.RED + " Do not hack ");
			player.sendMessage(prefix + ChatColor.GRAY + "[4] " + ChatColor.RED + " Do not grief ");
			player.sendMessage(prefix + ChatColor.GRAY + "[5] " + ChatColor.RED + " Do not abuse glitches ");
			player.sendMessage(prefix + ChatColor.GRAY + "[6] " + ChatColor.RED + " Do not harass players");
			player.sendMessage(prefix + ChatColor.GRAY + "[7] " + ChatColor.RED + " Do not be racist ");
			player.sendMessage(prefix + ChatColor.GRAY + "[8] " + ChatColor.RED + " Do not build traps ");
			player.sendMessage(prefix + ChatColor.GRAY + "[9] " + ChatColor.RED + " Do not build redstone clocks ");
			player.sendMessage(prefix + ChatColor.GRAY + "[10] " + ChatColor.RED + "Do not fly while in pvp ");
			player.sendMessage(prefix + ChatColor.GRAY + "[12] " + ChatColor.RED + "Do not disrespect players and staff ");
			player.sendMessage(prefix + ChatColor.GRAY + "[13] " + ChatColor.RED + "Do not build any graphic or valgur buildings. ");
			player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");

		}

		if (command.getName().equalsIgnoreCase("site")) {
			player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
			player.sendMessage(prefix + ChatColor.DARK_AQUA + "Site link: http://scyllamc.net");
			player.sendMessage(prefix + ChatColor.DARK_AQUA + "Donation link: http://store.scyllamc.net");
			player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		}

		if (command.getName().equalsIgnoreCase("buy")) {
			player.sendMessage(prefix + ChatColor.DARK_AQUA + "Donation link: http://store.scyllamc.net");
			player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		}

		if (command.getName().equalsIgnoreCase("Vote")) {
			player.sendMessage(MelonCore.prefix + ChatColor.DARK_AQUA + "Click these links to vote: ");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "[1] " + ChatColor.GRAY + "goo.gl/qRAhmL");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "[2] " + ChatColor.GRAY + "goo.gl/zab0Ua");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "[3] " + ChatColor.GRAY + "goo.gl/FkzBHS");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "[4] " + ChatColor.GRAY + "goo.gl/ENF1iL");
			player.sendMessage(ChatColor.LIGHT_PURPLE + "[5] " + ChatColor.GRAY + "goo.gl/ZvGEi0");
			player.sendMessage(MelonCore.prefix + ChatColor.GRAY + "--------");
		}

		return false;
	}

}
