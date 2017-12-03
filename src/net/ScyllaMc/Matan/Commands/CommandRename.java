package net.ScyllaMc.Matan.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.Material;

public class CommandRename {

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}


		Player op = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);
		
		if (!p.hasPermission("server.master")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}

		if (args.length > 0) {

			String s = "";
			for (int i = 0; i < args.length; i++) { // loop threw all the
													// arguments
				String arg = args[i] + " "; // get the argument, and add a space
											// so that the words get spaced out
				s = s + arg; // add the argument to myString
			}

			String nick = s;
			if (op.getItemInHand() == null || op.getItemInHand().getType().equals(Material.AIR) || op.getItemInHand().getItemMeta() == null) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "This item cannot be renamed.");
				return;
			}
			if (nick.length() > 30) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Item names cannot be longer then 30 charecters!");
				return;
			}

			ItemStack i = op.getItemInHand();
			ItemMeta m = i.getItemMeta();
			nick = ChatColor.translateAlternateColorCodes('&', nick);
			m.setDisplayName(nick);
			i.setItemMeta(m);
			p.sendMessage(MelonCore.prefix + "You have changed your item's name to: " + nick);
			return;

		}

		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);
	}

}
