package net.ScyllaMc.Matan.Mobs.Bending;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.projectkorra.projectkorra.GeneralMethods;



public class BendingManager{

		
	

	 //entity.setMetadata("element", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("MelonCore"), Integer.valueOf(element.ordinal())));
	
	 //  return Element.getType((entity.getMetadata("element").get(0)).asInt());

	  
	  
	  
	 public static List<Player> getPlayersAroundPoint(Location location, double radius){
	  List<Player> list = location.getWorld().getPlayers();
	    
	  for (Player entity : location.getWorld().getPlayers()) {
	  if (entity.getWorld() != location.getWorld() || entity.getGameMode().equals(GameMode.SPECTATOR) || entity.getLocation().distance(location) > radius) {
	  list.remove(entity);
	  }}
	  
	  return list;
	  }
	  
 
	  
	  
	  
	  
	 public Block getRandomSourceBlock(Location location, int radius){
	    List<Integer> checked = new ArrayList<Integer>();
	    List<Block> blocks = GeneralMethods.getBlocksAroundPoint(location, radius);
	 
	    Random rand = new Random();
	    for (int i = 0; i < blocks.size(); i++){
	    int index = rand.nextInt(blocks.size());
	    while (checked.contains(Integer.valueOf(index))) {
	    index = rand.nextInt(blocks.size());}
	    
	    
	      checked.add(Integer.valueOf(index));
	      Block block = blocks.get(index);
	      if ((block != null) && (block.getLocation().distance(location) >= 2.0D)) {

	          //  if ((EarthMethods.isEarthbendable(block)) && (isTransparent(block.getRelative(BlockFace.UP)))) {
	              return block;
	            //}

	      }}
	    return null;
	  }

	public static ArrayList<Material> trans = new ArrayList<Material>();

		
	public static void fillTransArray(){
		
		trans.add(Material.AIR);
		trans.add(Material.GRASS);
		trans.add(Material.FLOWER_POT);
		trans.add(Material.GRASS);
		trans.add(Material.LONG_GRASS);
		trans.add(Material.RED_ROSE);
		trans.add(Material.YELLOW_FLOWER);
		trans.add(Material.REDSTONE);
		trans.add(Material.DOUBLE_PLANT);

		
	}
	 
	public static boolean isTransparent(Block block){
		ArrayList<Material> trans = new ArrayList<Material>();
		
		if(trans.isEmpty()){
			fillTransArray();
		}
		
		if(block == null  || trans.contains(block.getType())){
			return true;
		}
			
	    return false;
	  }

	 
	 
	 
	 public static ChatColor getAbilityColor(String ability){
	    if (com.projectkorra.projectkorra.ability.ChiAbility.getAbilities().contains(ability)) {return ChatColor.GOLD;}
	    if (com.projectkorra.projectkorra.ability.AirAbility.getAbilities().contains(ability)) {return ChatColor.GRAY;}
	    if (com.projectkorra.projectkorra.ability.FireAbility.getAbilities().contains(ability)) {return ChatColor.RED;}
	    if (com.projectkorra.projectkorra.ability.EarthAbility.getAbilities().contains(ability)) {return ChatColor.DARK_GREEN;}
	    if (com.projectkorra.projectkorra.ability.AvatarAbility.getAbilities().contains(ability)) {return ChatColor.LIGHT_PURPLE;}

	    
	
	    return ChatColor.GRAY;
	  }
	  
	 
	 
	 
	 
}
