package net.ScyllaMc.Matan.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.ScyllaMc.Matan.BendingClass.Korra;

public class EventBlockSign implements Listener{

	
	
	
	
	
	
	

	    
	    @EventHandler(priority=EventPriority.HIGHEST)
	    public void onSignChange(SignChangeEvent event){
	    	
	    	
	      String CHAR = "&";
	   
	      char[] ch = CHAR.toCharArray();
	      if (event.getPlayer().hasPermission("server.master")) {
	        for (int i = 0; i <= 3; i++)
	        {
	          String line = event.getLine(i);
	          line = ChatColor.translateAlternateColorCodes(ch[0], line);
	          event.setLine(i, line);
	        }
	      }
	    }
	
	 
	 
	 
	 @EventHandler
	 public void signClick(PlayerInteractEvent e) {
	   if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	   if(e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
	   Sign sign = (Sign) e.getClickedBlock().getState();
	    
	   
	   
	   if(sign.getLine(0).equalsIgnoreCase(ChatColor.RED + ChatColor.BOLD.toString() + "Bind Fire")) {Korra.chooseDefault(e.getPlayer(), "fire");}
	   if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "Bind Earth")) {Korra.chooseDefault(e.getPlayer(), "earth");}
	   if(sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + ChatColor.BOLD.toString() + "Bind Water")) {Korra.chooseDefault(e.getPlayer(), "water");}
	   if(sign.getLine(0).equalsIgnoreCase(ChatColor.WHITE + ChatColor.BOLD.toString() + "Bind Air")) {Korra.chooseDefault(e.getPlayer(), "air");}

	   
	     
	   }
	   }
	 }





}








