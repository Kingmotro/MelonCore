package net.ScyllaMc.Matan.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.BendingClass.Korra;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.md_5.bungee.api.ChatColor;

public class CommandAutobind{
 
	  
	  public void run(CommandSender sender, String[] args) throws Exception{
		  if(!(sender instanceof Player)){return;}
		  
		  String prefix = MelonCore.prefix;
		  Player player = (Player) sender;
		 
		  if(args.length == 0){  
		  player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		  player.sendMessage(prefix+ChatColor.DARK_GREEN + "Autobind: ");
		      player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Guide Commands " +ChatColor.GRAY + "Lists helpfull commands to get you started");
		      player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Autobind Air");
		      player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Autobind Earth");
		      player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Autobind Fire");
		      player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Autobind Water");
		      player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Autobind Chi");
		      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		      return;}

			  
			  
	     if(args[0].equalsIgnoreCase("Fire")){Korra.chooseDefault(player, "fire");return;}
	     if(args[0].equalsIgnoreCase("Earth")){Korra.chooseDefault(player, "earth");return;}
	     if(args[0].equalsIgnoreCase("Air")){Korra.chooseDefault(player, "air");return;}
	     if(args[0].equalsIgnoreCase("Water")){Korra.chooseDefault(player, "water");return;}
	     if(args[0].equalsIgnoreCase("Chi")){Korra.chooseDefault(player, "chi");return;}


		
		   
		   player.sendMessage(prefix + ChatColor.GRAY + args[0] + ChatColor.RED + " Is not an element. use /autobind to view the available elements for auto binding");
		   
		  return;
		  
		  
		  
		  }
		
		
		
  
  
}
