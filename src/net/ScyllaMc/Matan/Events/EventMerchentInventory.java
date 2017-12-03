package net.ScyllaMc.Matan.Events;


import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Mobs.MountItem;
import net.ScyllaMc.Matan.NPC.Market;
import net.md_5.bungee.api.ChatColor;

public class EventMerchentInventory implements Listener{

	
	
	


	@EventHandler	
	public void intercactEvent(InventoryClickEvent e){
	ItemStack clicked = e.getCurrentItem(); 
	Inventory inventory = e.getInventory(); 
	MelonPlayer p = MelonPlayer.getInstanceOfPlayer((Player) e.getWhoClicked());
	if (!e.getSlotType().equals(SlotType.CONTAINER)) {return;}
	if (!clicked.hasItemMeta()) {return;}

	if (inventory.getName().contains("Market")) {
	e.setCancelled(true);
	
	if(Market.items.containsKey(clicked.getType())){
	p.getOnlinePlayer().closeInventory();

	Market.items.get(clicked.getType()).openMarketMenu(p);
	}
	
	return;
	}

	if (inventory.getName().contains("Block Trader") || inventory.getName().contains("Shop") ) {
	e.setCancelled(true);
	if(clicked.getItemMeta().getDisplayName().contains("Sell")){
	Material m = clicked.getType();
	Integer needed = clicked.getAmount();
	if(getItemCount(p,m) < needed){p.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have enough blocks to sell!"); return;}
	removeItemCount(p,clicked,needed);
    p.getOnlinePlayer().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0F, 17.0F);
	p.sendMessage(MelonCore.prefix + ChatColor.GREEN + "You sold " + clicked.getItemMeta().getDisplayName().replace("Sell ", " "));
    p.addCoins(getPrice(clicked.getItemMeta().getLore().get(1)), false);
    p.saveInv();
	return;
	}
	
	if(clicked.getItemMeta().getDisplayName().contains("Buy")){
	Integer needed = getPrice(clicked.getItemMeta().getLore().get(1));
	if(p.coins < needed){p.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have enough coins to buy this!"); return;}
	
	if(clicked.getType().equals(Material.SADDLE)){

		
	ItemStack item = clicked;
	ItemMeta meta = item.getItemMeta();	
	String name = meta.getDisplayName().replaceAll("BUY ", "");
	String[] split = name.split("Level");
	
	String n1 = ChatColor.stripColor(split[1].replace(" ", ""));
	int level = Integer.parseInt(n1);
	
	p.getOnlinePlayer().getInventory().addItem(new MountItem(level).getItem());	
	
	}else{
		p.getOnlinePlayer().getInventory().addItem(new ItemStack(clicked.getType(),clicked.getAmount(),(short) clicked.getDurability()));
	
		
	}
	
    p.getOnlinePlayer().getWorld().playSound(p.getLocation(), Sound.CLICK, 1.0F, 17.0F);
	p.sendMessage(MelonCore.prefix + ChatColor.GREEN + "You bought" + clicked.getItemMeta().getDisplayName().replace("Buy ", " "));
	p.addCoins(-needed, false);
	p.saveInv();
	return;
		}
	
	}
	
	
        
	}

	
	public int getPrice(String s){
	int price = 100;
	s = ChatColor.stripColor(s);
	String s2 = s.replaceAll(" ", "").replaceAll("[^0-9.]", "");
	price = Integer.parseInt(s2);
	return price;}
	
	
	
	public int getItemCount(MelonPlayer p,Material m){
		PlayerInventory inventory = p.getOnlinePlayer().getInventory();
		ItemStack[] items = inventory.getContents();
		int has = 0;
		for (ItemStack item : items){
		if ((item != null) && (item.getType().equals(m) && (item.getAmount() > 0))){
		has += item.getAmount();}
		}	
			
		        
	return has;
	}
	
	
  public void removeItemCount(MelonPlayer p,ItemStack i,int remove){
        	
	PlayerInventory inventory = p.getOnlinePlayer().getInventory();
	ItemStack[] items = inventory.getContents();
	int has = 0;
	for (ItemStack item : items){
	if ((item != null) && (item.getType().equals(i.getType()) && item.getAmount() > 0)){
	has += item.getAmount();
	inventory.remove(item);}
	}	
		
	int n = has - remove;
	if(n > 0){
	inventory.addItem(new ItemStack(i.getType(),n , (short) i.getDurability()));}
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
