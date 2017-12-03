package net.ScyllaMc.Matan.Commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
 

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.NPC.Market;

public class CommandMarket{
 
  
  
  public void run(CommandSender sender, String[] args) throws Exception{
  if(!(sender instanceof Player)){return;}
  
  MelonPlayer p = MelonPlayer.getInstanceOfPlayer((OfflinePlayer) sender);
  Market.openGlobalMenu(p);
   
  }
  
  
  
  
}
