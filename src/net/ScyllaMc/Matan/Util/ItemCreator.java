package net.ScyllaMc.Matan.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemCreator {
			
	
	public static ItemStack armor(Material m){
		ItemStack item = new ItemStack(m);
		return item;	
	}
	
	
	public static ItemStack item(Material m, String amount){
		ItemStack item = new ItemStack(m, stramount(amount));
		return item;	
	}
	
	public static ItemStack item(Material m, String amount, String name){
		ItemStack item = new ItemStack(m, stramount(amount));
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		
		return item;	
	}
	
	public ItemStack coloredArmor(Material m,Color c){
	ItemStack item = new ItemStack(m);
	LeatherArmorMeta metachest = (LeatherArmorMeta) item.getItemMeta();
	metachest.setColor(c);
	item.setItemMeta(metachest);
	return item;	
	}
		
	
	public ItemStack skull(String name){
	ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(short) 3);
	SkullMeta meta = (SkullMeta) item.getItemMeta();
	meta.setOwner(name);
	item.setItemMeta(meta);
	
	return item;	
	}

	
	
	
	public static int  stramount(String a){
	
		int itemnumber = 1;
		
		if (a != null && a.contains("-")) {
		      try
		      {
		        String[] strArray = a.split("-");
		        List<Integer> intList = new ArrayList<>();
		        String[] arrayOfString1;
		        int j = (arrayOfString1 = strArray).length;
		        for (int i = 0; i < j; i++)
		        {
		          String strInt = arrayOfString1[i];
		          intList.add(Integer.valueOf(Integer.parseInt(strInt)));
		        }
		        Random r = new Random();
		        int Low = ((Integer)intList.get(0)).intValue();
		        int High = ((Integer)intList.get(1)).intValue();
		        int Result = r.nextInt(High - Low) + Low;
		        itemnumber = Result;
		      }
		      catch (Exception e)
		      {
		        e.printStackTrace();
		      }
		    }
		
		return itemnumber;
		
	}
}
