package net.ScyllaMc.Matan.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.Material;

public class CommandRepair {

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

		if (op.getItemInHand() == null || op.getItemInHand().getType().equals(Material.AIR) || op.getItemInHand().getDurability() == 0) {
			p.sendMessage(MelonCore.prefix + ChatColor.RED + "This item cannot be fixed.");
			return;
		}

		op.getItemInHand().setDurability((short) 0);
		p.sendMessage(MelonCore.prefix + ChatColor.GREEN + "This item has been fixed");

	}

}
