package net.ScyllaMc.Matan.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.md_5.bungee.api.ChatColor;

public class CommandGuide{
 
	  
	  public void run(CommandSender sender, String[] args) throws Exception{
		  if(!(sender instanceof Player)){return;}
		  
		  String prefix = MelonCore.prefix;
		  Player player = (Player) sender;
		 
		  if(args.length == 0){  
		  player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		  player.sendMessage(prefix+ChatColor.DARK_GREEN + "Guides: ");
		      player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Guide Commands " +ChatColor.GRAY + "Helpfull commands to get you started");
		      player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Guide Bending");
		      player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Guide Level");
			  player.sendMessage(prefix +ChatColor.DARK_AQUA + "/Guide Upgrades");
		      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		      return;}

			  
			  
	     if(args[0].equalsIgnoreCase("Commands")){    
		     player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		     player.sendMessage(prefix +ChatColor.LIGHT_PURPLE + "/Spawn " +  ChatColor.GRAY.toString() + " Teleports you back to the main spawn (some servers require you to do it twice) ");
		     player.sendMessage(prefix +ChatColor.LIGHT_PURPLE + "/Hub " +  ChatColor.GRAY.toString() + " Instantly teleports you back to the main spawn ");
		     player.sendMessage(prefix +ChatColor.LIGHT_PURPLE + "/Rules " +  ChatColor.GRAY.toString() + " Shows you the server's rules");
		     player.sendMessage(prefix +ChatColor.LIGHT_PURPLE + "/Vote " +  ChatColor.GRAY.toString() + " Gives you the voting links, vote to get free items");
		     player.sendMessage(prefix +ChatColor.LIGHT_PURPLE + "/Site " +  ChatColor.GRAY.toString() + " Gives you the link to our site ");
		     player.sendMessage(prefix +ChatColor.LIGHT_PURPLE + "/Buy " +  ChatColor.GRAY.toString() + " Get a link to purchuse a rank");
		     player.sendMessage(prefix +ChatColor.LIGHT_PURPLE + "/Coins " +  ChatColor.GRAY.toString() + " Check and send coins to othe players ");
		     player.sendMessage(prefix +ChatColor.LIGHT_PURPLE + "/Stats (player) " +  ChatColor.GRAY.toString() + " Check Another player's Stats!");
		     player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		     return;}
		  
		  if(args[0].equalsIgnoreCase("Bending")){    
		      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		      player.sendMessage(prefix + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Bending Guide: ");
		      player.sendMessage("Gives you a bending preset to get you started" + ChatColor.LIGHT_PURPLE + "/autobind (element)");
		      player.sendMessage("to start choose your element with " + ChatColor.LIGHT_PURPLE + "/b choose (element)");
		      player.sendMessage("to see a full list of abilitys for an element use " + ChatColor.LIGHT_PURPLE + "/b d (element)");
		      player.sendMessage("to use the abilitys bind then to number key slots, ");
		      player.sendMessage("to bind abilits use " + ChatColor.LIGHT_PURPLE + "/b b (ability)");
		      player.sendMessage("to get information about an ability use " + ChatColor.LIGHT_PURPLE + "/b help (ability)");
		      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		    return;}
		  
		  
		  if(args[0].equalsIgnoreCase("Leveling") || args[0].equalsIgnoreCase("RPG")  || args[0].equalsIgnoreCase("level")){    
      	      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
      	      player.sendMessage(prefix + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Leveling Guide: ");
      	      player.sendMessage("Levels determine your defence strenght and coins earned from mobs.");
      	      player.sendMessage("To level up you need to kill mobs or players, the higher level the mob");
      	      player.sendMessage("you kill the more xp you will get.");
      	      player.sendMessage("The Xp Needed to leveling up increases with the level");
      	      player.sendMessage("Armor, Ecnhanting items is disabled due to the custom defence and level system");
      	      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
      	   return;}
		  
		  
		  if(args[0].equalsIgnoreCase("Spawn") || args[0].equalsIgnoreCase("Lobby") ){    
      	      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
      	      player.sendMessage(prefix + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Spawn Guide: ");
      	      player.sendMessage("Spawn is the the heart of the server,");
      	      player.sendMessage("In spawn you will find portals to all of our servers");
      	      player.sendMessage("and merchants with items for sale or traders that will");
      	      player.sendMessage("buy metarials from you!");
      	      player.sendMessage("To get to the spawn use the /spawn command (Some servers require you to do it twice)");
      	      player.sendMessage("Or the /hub command that will instantly teleport you to the spawn!");
      	      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
      	   return;}  
		  
		 
		  if(args[0].equalsIgnoreCase("Shops") || args[0].equalsIgnoreCase("Market") ){    
      	      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
      	      player.sendMessage(prefix + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Shops Guide: ");
      	      player.sendMessage("Many diffrent shops and merchants are placed around spawn");
      	      player.sendMessage("you can buy or trade goods with them for coins.");
      	      player.sendMessage("Every merchant will have diffrent items and prices.");
      	      player.sendMessage("Some merchants are traders and will allow you to sell them materials too");
      	      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
      	   return;}  
		  
		
		  if(args[0].equalsIgnoreCase("Upgrades")){    
      	      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
      	      player.sendMessage(prefix + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Upgrades Guide: ");
      	      player.sendMessage("To incrase your damage you will need to upgrade youself,");
      	      player.sendMessage("to do so you will need to find morgan freeman at the main spawn.");
      	      player.sendMessage("He is located infront of the church and for the right price he will magicly upgrade you!");
      	      player.sendMessage("There are 21 diffrent damage upgrades, and prices go up the more you levle up your damage.");
      	      player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
      	   return;}
		  
		  
		   if(args[0].equalsIgnoreCase("Creative")){    
		       player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		       player.sendMessage(prefix + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Creative Guide: ");
		       player.sendMessage("To start claim your own plot with " + ChatColor.LIGHT_PURPLE + "/plot auto");
		       player.sendMessage("To add friends to your plot use " + ChatColor.LIGHT_PURPLE + "/plot trust (player)");
		       player.sendMessage("To teleport to your home plot " + ChatColor.LIGHT_PURPLE + "/plot home");
		       player.sendMessage("To view all your plots  " + ChatColor.LIGHT_PURPLE + "/plot list (mine, shared)");
		       player.sendMessage("To to visit other plots " + ChatColor.LIGHT_PURPLE + "/plot visit (plot id)");
		       player.sendMessage("To get fancy heads / banners" + ChatColor.LIGHT_PURPLE + "/decor");
		       player.sendMessage("In your plot you may build anything that does not break the rules! ");
		       player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
		     return;}  

		      	  
		  
		   
		  if(args[0].equalsIgnoreCase("Arena")){    
  		      	 player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
  		      	 player.sendMessage(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Arena help: ");
  		      	 player.sendMessage("Welcome to the " + ChatColor.LIGHT_PURPLE + "Scylla Arena");
  		      	 player.sendMessage("This arena is unranked and levels do not make you stronger here!"); 
  		      	 player.sendMessage("Fight a battle to the death here!"); 
  		      	 player.sendMessage("To reselect your element go to /spawn");     		      	   
  		      	 player.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
  		       return;}       
  		      	      
		   
		     
		   
		   player.sendMessage(prefix + ChatColor.GRAY + args[0] + ChatColor.RED + " Is not a guide topic. use /guide to view the available topics");
		   
		  return;
		  
		  
		  
		  }
		
		
		
  
  
}
