package net.ScyllaMc.Matan.Events;





import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class EventPlayerInventory implements Listener{


	

	
	
	

	@EventHandler
	public void interact(InventoryClickEvent event) {
	MelonPlayer.getInstanceOfPlayer((Player) event.getWhoClicked()).saveInv();

	}

	
	
	@EventHandler
	public void itemdrop(PlayerDropItemEvent event) {
	MelonPlayer.getInstanceOfPlayer(event.getPlayer()).saveInv();

	}
	
	
	@EventHandler
	public void itemdrop(BlockPlaceEvent event) {
	MelonPlayer.getInstanceOfPlayer(event.getPlayer()).saveInv();

	}
	
	
	
	@EventHandler
	public void itempickup(PlayerPickupItemEvent event) {
	MelonPlayer.getInstanceOfPlayer(event.getPlayer()).saveInv();
	}
	

	
	
}
