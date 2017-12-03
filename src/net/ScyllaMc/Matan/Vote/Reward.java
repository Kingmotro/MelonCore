package net.ScyllaMc.Matan.Vote;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Util.ItemCreator;
import net.ScyllaMc.Matan.Vote.MysteryItems.Level;
import net.ScyllaMc.Matan.Vote.MysteryItems.Type;

public class Reward {
	
	private Type type;
	private Level level;
	private Object o;
	private Integer weight;
	private String range;
	
	public Reward(Type type, Level level , Object o ,Integer weight){
		
		this.type = type;
		this.level = level;
		this.o = o;
		this.weight = weight;

	}
	
	public Reward(Type type, Level level , ItemStack is ,Integer weight, String range){
		
		this.type = type;
		this.level = level;
		this.o = is;
		this.weight = weight;
		this.range = range;

	}
	
	public Reward(Level level , Integer weight, String range){
		
		this.type = Type.COINS;
		this.level = level;
		this.o = -1;
		this.weight = weight;
		this.range = range;

	}
	
	public String giveToPlayer(MelonPlayer p){
		
	String tag = this.level.getColor() + MelonCore.firstLetterCaps(this.level.toString()) + ChatColor.GRAY + " - ";

		if(type == Type.ITEMSTACK && o instanceof ItemStack){
			
			ItemStack item = (ItemStack) o;
			ItemMeta meta = item.getItemMeta();
			
			int a = ItemCreator.stramount(this.range);
			item.setAmount(a);
			
			ArrayList<String> lore = new ArrayList<String>();
		    lore.add("");
		    lore.add(ChatColor.GRAY + "Voting Item");
		    meta.setLore(lore);
		    
		    item.setItemMeta(meta);
		    
		    if(p != null){
		    	p.getOnlinePlayer().getInventory().addItem(item);
		    }
		    
			String name = this.level.getColor() + MelonCore.firstLetterCaps(item.getType().toString().replaceAll("_", " "));
			String amount = "";
			
			if(item.getAmount() > 1){
				amount = ChatColor.GRAY + " X " + item.getAmount();
			}
			
			return tag + name + amount;	
		}
		
		if(type == Type.COINS){
			
			int coins = (int) ItemCreator.stramount(this.range);
			
			String name = this.level.getColor() + "" + coins + " coins!";
			
			if(p != null){
				p.addCoins(coins, true);
			}
			
			return tag + name;
		}
		
		return o.toString();		
	}
	
	public int getWeight(){
		return this.weight;
	}
	
	public Level getLevel(){
		return this.level;
	}
	
}
