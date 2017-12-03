package net.ScyllaMc.Matan.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Rank.Rank;


public class EventPlayerInteract implements Listener {

	@EventHandler
	public void onBlockInteract(PlayerInteractEvent event) {
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(event.getPlayer());

		if (p.rank.equals(Rank.USELESS) || p.rank.equals(Rank.OBAMA)) {
			p.sendMessage(ChatColor.RED + "You are useless, you cannot do this!");
			p.getOnlinePlayer().playSound(p.getLocation(), Sound.FIZZ, 1, 1);
			event.setCancelled(true);
			return;
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onClick(InventoryClickEvent event) {
		if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
			((Player) event.getWhoClicked()).updateInventory();
			event.getCurrentItem().setType(Material.AIR);
			event.getWhoClicked().sendMessage(MelonCore.prefix + ChatColor.RED + "Armor is disabled, if tou want to increase protection level-up!");
			event.setCancelled(true);
		}
	}

}
