package net.Mcbender.Matan.MelonCore;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
public class UpgradeInv {








public static void CreateManu(MelonPlayer p, Player sender){
p.getOnlinePlayer().playSound(p.getOnlinePlayer().getLocation(), Sound.ENDERMAN_TELEPORT ,1, 1);

Inventory Customize = Bukkit.createInventory(null, 27 ,"Morgan's Upgrades Shop");

ItemStack b = new ItemStack(Material.STAINED_GLASS_PANE , 1 ,(short) 15);{
ItemMeta meta = b.getItemMeta();
meta.setDisplayName(" ");
b.setItemMeta(meta);}




ItemStack Attack = new ItemStack(Material.EMPTY_MAP);{
ItemMeta meta = Attack.getItemMeta();
meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Attack Upgrades");
ArrayList<String> lore = new ArrayList<String>();
lore.add(ChatColor.GRAY + "-----[ Info: ] -----");
lore.add(ChatColor.GRAY + "Upgrade your attack damage to");
lore.add(ChatColor.GRAY + "increase the damage you deal");
lore.add(ChatColor.GRAY + "while bending and fighting!");
lore.add(ChatColor.GRAY + "At max level it increases damage by 300%");
lore.add(ChatColor.GRAY + "-------------------------");
meta.setLore(lore);
Attack.setItemMeta(meta);}

ItemStack Luck = new ItemStack(Material.EMPTY_MAP);{
ItemMeta meta = Luck.getItemMeta();
meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Luck Upgrades");
ArrayList<String> lore = new ArrayList<String>();
lore.add(ChatColor.GRAY + "-----[ Info: ] -----");
lore.add(ChatColor.GRAY + "Upgrade your luck to");
lore.add(ChatColor.GRAY + "increase your chance of getting");
lore.add(ChatColor.GRAY + "extra blocks while mining and");
lore.add(ChatColor.GRAY + "XP / Coins while killing mobs");
lore.add(ChatColor.GRAY + "-------------------------");
meta.setLore(lore);
Luck.setItemMeta(meta);}


Customize.setItem(0,Attack);
Customize.setItem(1,b);
Customize.setItem(2,damage(p, 1));
Customize.setItem(3,damage(p, 2));
Customize.setItem(4,damage(p, 3));
Customize.setItem(5,damage(p, 4));
Customize.setItem(6,damage(p, 5));
Customize.setItem(7,damage(p, 6));
Customize.setItem(8,damage(p, 7));

Customize.setItem(10, b);
Customize.setItem(11,damage(p, 8));
Customize.setItem(12,damage(p, 9));
Customize.setItem(13,damage(p, 10));
Customize.setItem(14,damage(p, 11));
Customize.setItem(15,damage(p, 12));
Customize.setItem(16,damage(p, 13));
Customize.setItem(17,damage(p, 14));

Customize.setItem(19, b);
Customize.setItem(20,damage(p, 15));
Customize.setItem(21,damage(p, 16));
Customize.setItem(22,damage(p, 17));
Customize.setItem(23,damage(p, 18));
Customize.setItem(24,damage(p, 19));
Customize.setItem(25,damage(p, 20));
Customize.setItem(26,damage(p, 21));

/**
Customize.setItem(27,Luck);
Customize.setItem(28,b);
Customize.setItem(29,luck(p, 1));
Customize.setItem(30,luck(p, 2));
Customize.setItem(31,luck(p, 3));
Customize.setItem(32,luck(p, 4));
Customize.setItem(33,luck(p, 5));
Customize.setItem(34,luck(p, 6));
Customize.setItem(35,luck(p, 7));

Customize.setItem(37, b);
Customize.setItem(38,luck(p, 8));
Customize.setItem(39,luck(p, 9));
Customize.setItem(40,luck(p, 10));
Customize.setItem(41,luck(p, 11));
Customize.setItem(42,luck(p, 12));
Customize.setItem(43,luck(p, 13));
Customize.setItem(44,luck(p, 14));

Customize.setItem(46, b);
Customize.setItem(47,luck(p, 15));
Customize.setItem(48,luck(p, 16));
Customize.setItem(49,luck(p, 17));
Customize.setItem(50,luck(p, 18));
Customize.setItem(51,luck(p, 19));
Customize.setItem(52,luck(p, 20));
Customize.setItem(53,luck(p, 21));

**/

sender.openInventory(Customize);
}


	





public static ItemStack damage(final MelonPlayer p ,int level){


	ItemStack item = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
	ItemMeta meta = item.getItemMeta();
	ArrayList<String> lore = new ArrayList<String>();
	meta.setDisplayName(ChatColor.DARK_AQUA + "Damage Upgrade");

	int nextdamage = (int) (UpgradeInfo.getBonusDamage(level) * 100) + 100;
	
	int price = UpgradeInfo.getDamageUpgradePrice(level);
	
	UpgradeInfo.getBonusDamage(p.damagelevel);
	if (p.damagelevel + 2 <= level){	
	ItemStack l = new ItemStack(Material.STAINED_GLASS,1 , (short) 15);	
	ItemMeta metal = l.getItemMeta();
	metal.setDisplayName(ChatColor.RED + "Locked");
	ArrayList<String> lorel = new ArrayList<String>();
	lorel.add(ChatColor.GRAY + "This upgrade is locked.");
	lorel.add(ChatColor.GRAY + "Unlock the previous upgrade to unlock");
	metal.setLore(lorel);
	l.setItemMeta(metal);
	return l;
	}
	

	if(level == 1 || p.damagelevel >= level){
	lore.add(ChatColor.GRAY + "-----[" + ChatColor.GREEN + "level" + ChatColor.DARK_GREEN + " " + level + ChatColor.GRAY + "] -----");
	lore.add(ChatColor.DARK_GRAY + "Damage:" + ChatColor.GRAY +  " " + nextdamage + "%");
	lore.add("");	
	lore.add(ChatColor.GREEN + "Unlocked");	

	
	}else{
		
		ChatColor color = ChatColor.RED;int d = 14;
	    if(p.coins >= price){color = ChatColor.GREEN;d = 5;}
	    

		ItemStack l = new ItemStack(Material.STAINED_GLASS,1 , (short) d);	
		ItemMeta metal = l.getItemMeta();
		metal.setDisplayName(ChatColor.DARK_AQUA + "Damage Upgrade " + ChatColor.GREEN + "Click to unlock");
		ArrayList<String> lorel = new ArrayList<String>();
		lorel.add(ChatColor.GRAY + "-----[ " + ChatColor.RED + "level" + ChatColor.DARK_RED + " " + level + ChatColor.GRAY + " ] -----");
		lorel.add(ChatColor.DARK_AQUA + "Damage To Others: " + ChatColor.AQUA + nextdamage + "%" );
		lorel.add(ChatColor.DARK_AQUA + "Price: " + color + price +  " Coins");	
		lorel.add(ChatColor.GRAY + "----------------------");
		
		lore.add("");	
		lore.add(ChatColor.RED + "Locked");	
		metal.setLore(lorel);
		l.setItemMeta(metal);
		return l;
	
	
	
	}
	meta.setLore(lore);
	item.setItemMeta(meta);	
	return item;}







	public static ItemStack luck(final MelonPlayer p ,int level){


	ItemStack item = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
	ItemMeta meta = item.getItemMeta();
	ArrayList<String> lore = new ArrayList<String>();
	meta.setDisplayName(ChatColor.DARK_AQUA + "Luck Upgrade");

	int nextluck = (int) level + 1;
	
	int price = UpgradeInfo.getLuckUpgradePrice(level);
	
	if (p.luck + 2 <= level){	
	ItemStack l = new ItemStack(Material.STAINED_GLASS,1 , (short) 15);	
	ItemMeta metal = l.getItemMeta();
	metal.setDisplayName(ChatColor.RED + "Locked");
	ArrayList<String> lorel = new ArrayList<String>();
	lorel.add(ChatColor.GRAY + "This upgrade is locked.");
	lorel.add(ChatColor.GRAY + "Unlock the previous upgrade to unlock");
	metal.setLore(lorel);
	l.setItemMeta(metal);
	return l;
	}
	

	if(level == 1 || p.luck >= level){
	lore.add(ChatColor.GRAY + "-----[" + ChatColor.GREEN + "level" + ChatColor.DARK_GREEN + " " + level + ChatColor.GRAY + "] -----");
	lore.add(ChatColor.DARK_GRAY + "Luck:" + ChatColor.GRAY +  " " + nextluck + "%");
	lore.add("");	
	lore.add(ChatColor.GREEN + "Unlocked");	

	
	}else{
		
		ChatColor color = ChatColor.RED;int d = 14;
	    if(p.coins >= price){color = ChatColor.GREEN;d = 5;}
	    

		ItemStack l = new ItemStack(Material.STAINED_GLASS,1 , (short) d);	
		ItemMeta metal = l.getItemMeta();
		metal.setDisplayName(ChatColor.DARK_AQUA + "Luck Upgrade " + ChatColor.GREEN + "Click to unlock");
		ArrayList<String> lorel = new ArrayList<String>();
		lorel.add(ChatColor.GRAY + "-----[ " + ChatColor.RED + "level" + ChatColor.DARK_RED + " " + level + ChatColor.GRAY + " ] -----");
		lorel.add(ChatColor.DARK_AQUA + "Luck: " + ChatColor.AQUA + nextluck + "%" );
		lorel.add(ChatColor.DARK_AQUA + "Price: " + color + price +  " Coins");	
		lorel.add(ChatColor.GRAY + "----------------------");
		
		lore.add("");	
		lore.add(ChatColor.RED + "Locked");	
		metal.setLore(lorel);
		l.setItemMeta(metal);
		return l;
	
	
	
	}
	meta.setLore(lore);
	item.setItemMeta(meta);	
	return item;}








}
