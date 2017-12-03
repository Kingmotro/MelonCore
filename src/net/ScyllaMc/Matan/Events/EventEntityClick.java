package net.ScyllaMc.Matan.Events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;

import net.ScyllaMc.Matan.BendingClass.Korra;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.MelonCore.ServerType;

public class EventEntityClick implements Listener {

	
	
	
	
	
	
	
	
	
	@EventHandler
    public void PlayerRightClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		  Entity entity = event.getRightClicked(); 
		  
		  if(!MelonCore.server.equals(ServerType.BEND) && entity instanceof Villager){event.setCancelled(true); player.sendMessage(MelonCore.prefix + ChatColor.RED + "Villager trading is disabled, to trade go to spawn!"); return; }
		    
		    if (cooldownTime.containsKey(player.getUniqueId().toString())) {
		        player.sendMessage(MelonCore.prefix + ChatColor.RED + "Please do not spam click the npc.");
		        return;}				
			
		    
		    
		  if(entity.getCustomName() == null){return;}
		  if(!(entity instanceof Player)){return;}
		  if(!entity.getCustomName().contains(ChatColor.BOLD.toString())){return;}
			  
			  
			  addCoolDown(player, 5);

	      
             if(entity.getCustomName().equalsIgnoreCase(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "Earth")){
            	 player.performCommand("bending choose earth");
            	 Korra.chooseDefault(player, "earth");
            	  event.setCancelled(true);
             }    
             
             

             if(entity.getCustomName().equalsIgnoreCase(ChatColor.BLUE + ChatColor.BOLD.toString() + "Water")){
            	 player.performCommand("bending choose water");
            	  Korra.chooseDefault(player, "water");
            	  event.setCancelled(true);
             }    
             
             

             if(entity.getCustomName().equalsIgnoreCase(ChatColor.RED + ChatColor.BOLD.toString() + "Fire")){
            	 player.performCommand("bending choose fire");
            	  Korra.chooseDefault(player, "fire");
            	  event.setCancelled(true);}    
             

             if(entity.getCustomName().equalsIgnoreCase(ChatColor.GRAY + ChatColor.BOLD.toString() + "Air")){
            	 player.performCommand("bending choose air");
            	  Korra.chooseDefault(player, "air");
            	  event.setCancelled(true);}  
             
             
             if(entity.getCustomName().equalsIgnoreCase(ChatColor.GOLD + ChatColor.BOLD.toString() + "Chi")){
            	 player.performCommand("bending choose chi");
            	  event.setCancelled(true);}  
             
             
             if(entity.getCustomName().equalsIgnoreCase(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Avatar")){
            	 if(player.hasPermission("melon.hero")){
            		  BendingPlayer b = BendingPlayer.getBendingPlayer(player);
            			b.addElement(Element.AIR);
            			b.addElement(Element.EARTH);
            			b.addElement(Element.WATER);
            			 b.addElement(Element.FIRE);
            			b.addElement(Element.CHI);
            			player.sendMessage(MelonCore.prefix + ChatColor.GREEN + "Your now an avatar!");
            	 }else{
            		player.sendMessage(MelonCore.prefix + ChatColor.RED + "You need to be" + ChatColor.GOLD + " [Gold] Rank " + ChatColor.RED +   "or above to choose this!"); 
            	 }
            	  event.setCancelled(true);}

          }
	
	
	
	
	
	
	
	
	public static HashMap<String, Integer> cooldownTime = new HashMap<String, Integer>();
	public static  HashMap<String, BukkitRunnable>  cooldownTask = new HashMap<String, BukkitRunnable>();


	public static void addCoolDown(final Player p, int time){
	    cooldownTime.put(p.getUniqueId().toString(), time);
	    cooldownTask.put(p.getUniqueId().toString(), new BukkitRunnable() {
	    @Override
		public void run() {
	    cooldownTime.put(p.getUniqueId().toString(), cooldownTime.get(p.getUniqueId().toString()) - 1);
	    if (cooldownTime.get(p.getUniqueId().toString()) == 0) {
	    cooldownTime.remove(p.getUniqueId().toString());
	    cooldownTask.remove(p.getUniqueId().toString());
	    cancel();
	    }}});
	    cooldownTask.get(p.getUniqueId().toString()).runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 20, 20);}
	          
	
	
	
	
	
	
	
	
	
	
	
}
