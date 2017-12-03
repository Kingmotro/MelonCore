package com.unon1100.TellrawAPI;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;

public class TellrawParent {
	public static String getAchievementID(Achievement a){
		switch(a){
		case ACQUIRE_IRON:
			return "acquireIron";
		case BAKE_CAKE:
			return "bakeCake";
		case BOOKCASE:
			return "bookcase";
		case BREED_COW:
			return "breedCow";
		case BREW_POTION:
			return "potion";
		case BUILD_BETTER_PICKAXE:
			return "buildBetterPickaxe";
		case BUILD_FURNACE:
			return "buildFurnace";
		case BUILD_HOE:
			return "buildHoe";
		case BUILD_PICKAXE:
			return "buildPickaxe";
		case BUILD_SWORD:
			return "buildSword";
		case BUILD_WORKBENCH:
			return "buildWorkBench";
		case COOK_FISH:
			return "cookFish";
		case DIAMONDS_TO_YOU:
			return "diamondsToYou";
		case ENCHANTMENTS:
			return "enchantments";
		case END_PORTAL:
			return "theEnd";
		case EXPLORE_ALL_BIOMES:
			return "exploreAllBiomes";
		case FLY_PIG:
			return "flyPig";
		case FULL_BEACON:
			return "fullBeacon";
		case GET_BLAZE_ROD:
			return "blazeRod";
		case GET_DIAMONDS:
			return "diamonds";
		case GHAST_RETURN:
			return "ghast";
		case KILL_COW:
			return "killCow";
		case KILL_ENEMY:
			return "killEnemy";
		case KILL_WITHER:
			return "killWither";
		case MAKE_BREAD:
			return "makeBread";
		case MINE_WOOD:
			return "mineWood";
		case NETHER_PORTAL:
			return "portal";
		case ON_A_RAIL:
			return "onARail";
		case OPEN_INVENTORY:
			return "openInventory";
		case OVERKILL:
			return "overkill";
		case SNIPE_SKELETON:
			return "snipeSkeleton";
		case SPAWN_WITHER:
			return "spawnWither";
		case THE_END:
			return "theEnd2";
		default:
			return "openInventory";
		}
	}
	public static String getChatColor(ChatColor cc){
		if(cc.isColor()){
			return cc.name().toLowerCase();
		}
		return "white";
	}

	protected StringBuilder msg = new StringBuilder("");

	Gson gson = new Gson();

	boolean alreadyGotten = false;

	public StringBuilder getStringBuilder(){
		if(alreadyGotten) return msg;
		else{
			alreadyGotten = true;
			if(msg.length()>0){
				if(msg.charAt(msg.length()-1) == ','){
					msg.setLength(msg.length()-1);
				}
				msg.append("}");
				return msg;
			}
		}
		return new StringBuilder("{\"text\":\"Something went wrong!\",\"color\":\"red\"}");
	}

	public void setBold(){
		msg.append("\"bold\":true,");
	}

	public void setHover(Achievement a){
		String s = getAchievementID(a);
		msg.append("\"hoverEvent\":{\"action\":\"show_achievement\",\"value\":\"achievement."+s+"\"},");
	}

	public void setHover(Entity e){
		StringBuilder ent = new StringBuilder("{");
		if(e instanceof Player){
			Player p = (Player) e;
			ent.append("name:\\\""+p.getName()+"\\\",");
		}else if(e instanceof LivingEntity){
			LivingEntity el = (LivingEntity) e;
			if(el.getCustomName() != null){
				ent.append("name:\\\""+el.getCustomName()+"\\\",");
			}
			ent.append("id:"+e.getUniqueId().toString()+",");
		}
		if(ent.charAt(ent.length()-1) == ',') ent.deleteCharAt(ent.length()-1);
		ent.append("type:"+WordUtils.capitalizeFully(e.getType().toString()));
		msg.append("\"hoverEvent\":{\"action\":\"show_entity\",\"value\":\""+ent.toString()+"}\"},");
	}

	public void setHover(ItemStack i){
		StringBuilder itemJSON = new StringBuilder();
		itemJSON.append("{id:"+i.getType().getId());
		if(i.getItemMeta().getLore() != null || i.getItemMeta().getDisplayName() != null){
			itemJSON.append(",tag:{display:{");
			if(i.getItemMeta().getLore() != null){
				itemJSON.append("Lore:[");
				for(String s:i.getItemMeta().getLore()){
					itemJSON.append("\\\""+s+"\\\",");
				}
				itemJSON.deleteCharAt(itemJSON.length()-1);
				itemJSON.append("]");
			}
			if(i.getItemMeta().getDisplayName() != null){
				if(itemJSON.charAt(itemJSON.length()-1) == ']') itemJSON.append(',');
				itemJSON.append("Name:\\\""+i.getItemMeta().getDisplayName()+"\\\"");
			}
			itemJSON.append("}}");
		}
		msg.append("\"hoverEvent\":{\"action\":\"show_item\",\"value\":\""+itemJSON+"}\"},");
	}

	public void setHover(String s){
		msg.append("\"hoverEvent\":{\"action\":\"show_text\",\"value\":\""+s+"\"},");
	}

	public void setHover(String...lines){
		ItemStack i = new ItemStack(Material.STONE);
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+lines[0]);
		List<String> lore = new ArrayList<String>(Arrays.asList(lines));
		lore.remove(0);
		for(int count=0; count<lore.size();count++) lore.set(count, ChatColor.RESET + lore.get(count));
		meta.setLore(lore);
		i.setItemMeta(meta);
		setHover(i);
	}

	public void setItalic(){
		msg.append("\"italic\":true,");
	}

	public void setMagic(){
		setObfuscated();
	}
	
	public void setObfuscated(){
		msg.append("\"obfuscated\":true,");

	}
	public void setStrikethrough(){
		msg.append("\"strikethrough\":true,");
	}
	
	public void setUnderlined(){
		msg.append("\"underlined\":true,");
	}
}
