package net.ScyllaMc.Matan.Mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MountItem{
   
  public static HashMap<ItemMeta, MountItem> cacheditems = new HashMap<ItemMeta, MountItem>(); 
	
  public static MountItem getFromItem(ItemStack item){
  if(item == null || item.getItemMeta() == null || item.getItemMeta().getLore() == null || item.getItemMeta().getLore().get(0) == null){return null;}
  
  if(cacheditems.containsKey(item.getItemMeta())){return cacheditems.get(item.getItemMeta());}
  
  String line =  item.getItemMeta().getLore().get(0);
  if(line.contains("Mount: ")){
  String j = line.replace("Mount: ", "");
  try {
  JSONObject data = (JSONObject) new JSONParser().parse(j);
  return new MountItem(Integer.parseInt(data.get("level").toString()));
  
  } catch (ParseException e) {e.printStackTrace();}}
	 
	   
  return null;
  }
 
  private ItemStack i;
  private Integer level;
  private JSONObject data;
  
  
  @SuppressWarnings("unchecked")
  public MountItem(Integer level){
	  
	  this.level = level;
	  
	  i = new ItemStack(Material.SADDLE);
	  data = new JSONObject();
	  data.put("level", level);
	 
	  ItemMeta meta = i.getItemMeta();
	  meta.setDisplayName(ChatColor.DARK_GREEN + "Horse Mount " + ChatColor.GRAY + "- Level " + level);
	  List<String> lore = new ArrayList<String>();
	  lore.add("Mount: " + data.toJSONString());
	  meta.setLore(lore);
	  i.setItemMeta(meta);
  
	  cacheditems.put(meta, this);
	  
  }
  
  
  
  
  
  public List<String> getFormatedLore(){
  ArrayList<String> lore = new ArrayList<String>();
  lore.add(ChatColor.GRAY + "--=====--");
  lore.add(ChatColor.GRAY + "Level " + level);
  return lore;}  

  
  public int getLevel(){
  return level;}
  
  public ItemStack getItem(){
  return i;}
	  
  
}
