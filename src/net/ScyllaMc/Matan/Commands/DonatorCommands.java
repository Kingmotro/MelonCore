package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;

import net.ScyllaMc.Matan.MelonCore.MelonCore;




public  class DonatorCommands implements CommandExecutor{

    @Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
    
 	  
    	 if(command.getName().equalsIgnoreCase("setavatar")){	
    	 if(!(sender instanceof Player)){   
    	 if(!(args.length == 1)){
		 return true;
    	 }else{
        BendingPlayer b =  BendingPlayer.getBendingPlayer(args[0]);
		b.addElement(Element.AIR);
		b.addElement(Element.EARTH);
		b.addElement(Element.WATER);
		 b.addElement(Element.FIRE);
		b.addElement(Element.CHI);
		 Bukkit.getPlayer(args[0]).sendMessage(MelonCore.prefix + ChatColor.GREEN + "Your now an avatar!");

    	 }
    	 
    	 
 	   }else if(sender instanceof Player){  
	  Player p = (Player) sender;
	  if(sender.hasPermission("melon.*")){
	 if(!(args.length == 1)){
	p.sendMessage(MelonCore.prefix + ChatColor.RED + "You need to use /setavatar (player)");
	return true;
	}else{
	
		        BendingPlayer b = BendingPlayer.getBendingPlayer(args[0]);
				b.addElement(Element.AIR);
				b.addElement(Element.EARTH);
				b.addElement(Element.WATER);
				 b.addElement(Element.FIRE);
				b.addElement(Element.CHI);Bukkit.getPlayer(args[0]).sendMessage(MelonCore.prefix + ChatColor.GREEN + "Your now an avatar!");
				}   
 	   }else{p.sendMessage("You cant use this command");}}}
 	   
	   	
    	 
	 if(command.getName().equalsIgnoreCase("avatar")){	
	   if(sender instanceof Player){    
		   Player p = (Player) sender;
		   if(sender.hasPermission("server.gold")){
			   BendingPlayer b =  BendingPlayer.getBendingPlayer(p.getName());
			   b.addElement(Element.AIR);
			   b.addElement(Element.EARTH);
			   b.addElement(Element.WATER);
			   b.addElement(Element.FIRE);
			   b.addElement(Element.CHI);
			   p.sendMessage(MelonCore.prefix + ChatColor.GREEN + "Your now an avatar!");
			   return true;
		       }else{
		       p.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have the permission to be an avatar!");	
		       return true;}}}

	

		return false;}
    
		
		
}
