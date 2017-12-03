package net.ScyllaMc.Matan.BendingClass;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BendingInventory {

	
	
	
	
	 public void open(Player p) {
	 Inventory element = Bukkit.createInventory(null, 9, ChatColor.GRAY + "Select your Element");
			
		
			
	ItemStack air = new ItemStack(Material.CLAY, 1);{
    ItemMeta meta = air.getItemMeta();
	meta.setDisplayName(ChatColor.GRAY + "Air Bending");
	ArrayList<String> lore = new ArrayList<String>();
	lore.add(ChatColor.GRAY  + "---------");
	lore.add(ChatColor.DARK_GREEN  +  "Pros:");
    lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Fast and Agile");
	lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Fast Attacks");
	lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " No fall damage");
	lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Cheap Weapons");
	lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Easy to master");
	lore.add(ChatColor.RED  +  "Cons:");
    lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Low defence");
	lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Weak Attacks");
	lore.add("");
	lore.add(ChatColor.GREEN + " Click to select!");
	meta.setLore(lore);
	air.setItemMeta(meta);}
		
	ItemStack fire = new ItemStack(Material.HARD_CLAY, 1,(short) 14);{
	   ItemMeta meta = fire.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Fire Bending");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY  + "---------");
		lore.add(ChatColor.DARK_GREEN  +  "Pros:");
	    lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Agile");
		lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Strong Attacks");
		lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Weapons are sharp");
		lore.add(ChatColor.RED  +  "Cons:");
	    lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Expensive weapons");
		lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Moves could reflect back");
		lore.add("");
		lore.add(ChatColor.GREEN + " Click to select!");
		meta.setLore(lore);
		fire.setItemMeta(meta);}		
	
	ItemStack earth = new ItemStack(Material.HARD_CLAY, 1,(short) 12);{
		   ItemMeta meta = earth.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_GREEN + "Earth Bending");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY  + "---------");
			lore.add(ChatColor.DARK_GREEN  +  "Pros:");
		    lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Great defence");
			lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Very Strong Attacks");
			lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Weapons are also tools");
			lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Can lava bend");
			lore.add(ChatColor.RED  +  "Cons:");
		    lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Slow and easy to hit");
			lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Hard to master");
			lore.add("");
			lore.add(ChatColor.GREEN + " Click to select!");
			meta.setLore(lore);
			earth.setItemMeta(meta);}	
	
	ItemStack water = new ItemStack(Material.HARD_CLAY, 1,(short) 12);{
		   ItemMeta meta = water.getItemMeta();
			meta.setDisplayName(ChatColor.DARK_AQUA + "Water Bending");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY  + "---------");
			lore.add(ChatColor.DARK_GREEN  +  "Pros:");
			lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Medium Defence");
		    lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Can swim fast");
			lore.add(ChatColor.GRAY + "-" + ChatColor.DARK_GREEN + " Can ice bend");
			lore.add(ChatColor.RED  +  "Cons:");
		    lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Slow and easy to hit");
			lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " Very weak attacks");
			lore.add(ChatColor.GRAY + "-" + ChatColor.RED + " No Weapons");
			lore.add("");
			lore.add(ChatColor.GREEN + " Click to select!");
			meta.setLore(lore);
			water.setItemMeta(meta);}	
	
	
	
		element.setItem(1, air);
		element.setItem(2, water);

		element.setItem(6, fire);
		   element.setItem(7, earth);
		   
		   p.openInventory(element);
		
		    }
	 
	 
	 
	 
	 
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
