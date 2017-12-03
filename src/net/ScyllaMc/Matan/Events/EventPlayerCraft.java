package net.ScyllaMc.Matan.Events;


import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonCore.MelonCore;

import net.md_5.bungee.api.ChatColor;

public class EventPlayerCraft implements Listener{
  
	

	
    @SuppressWarnings({ "unused", "deprecation" })
	@EventHandler
    public void craftItem(PrepareItemCraftEvent e) {
    Material itemType = e.getRecipe().getResult().getType();
    Byte itemData = e.getRecipe().getResult().getData().getData();
    String name = itemType.toString().toLowerCase().replaceAll("_", "");

    if(itemType==Material.ANVIL||itemType==Material.ENCHANTMENT_TABLE || name.contains("helmet") || name.contains("chestplate")  || name.contains("leggings")  || name.contains("boots") || name.contains("hat") || name.contains("tunic") || name.contains("trousers") ) {
        e.getInventory().setResult(new ItemStack(Material.AIR));
        for(HumanEntity he: e.getViewers()) {
            if(he instanceof Player) {
                ((Player)he).sendMessage(MelonCore.prefix + ChatColor.RED+"This Item is not craftable!");
            }
        }
    }
    }
    
    
	 
	 
	 
	
	
	

	


	
	
	
	
	
	

	
}