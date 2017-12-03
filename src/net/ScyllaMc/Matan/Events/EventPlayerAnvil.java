package net.ScyllaMc.Matan.Events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class EventPlayerAnvil implements Listener {

	@EventHandler
	public void PlayerRightClick(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) {
			return;
		}
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(e.getPlayer());

		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock().getType().equals(Material.ANVIL)) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Enchantment tables are disabled due the custom leveling system.");
				e.setCancelled(true);
			}
		}

		return;
	}

}
