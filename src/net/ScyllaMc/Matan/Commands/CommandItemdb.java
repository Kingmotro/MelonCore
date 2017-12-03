package net.ScyllaMc.Matan.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.Material;

public class CommandItemdb{
 
   
  @SuppressWarnings("deprecation")
public void run(CommandSender sender, String[] args) throws Exception{
  if(!(sender instanceof Player)){return;}
  
  Player p = (Player) sender;
 
  if (!p.hasPermission("server.itemdb")){p.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have the permission to use this command.");return;}
 
  if( p.getItemInHand().getType().equals(Material.AIR) || p.getItemInHand() != null){
  ItemStack item = p.getItemInHand();
  String type = ChatColor.DARK_AQUA + "Item ID: " + ChatColor.GRAY +  item.getTypeId()  + ":" + Integer.toString(item.getDurability());
  p.sendMessage(MelonCore.prefix + type);
  return;}
  
  p.sendMessage(MelonCore.prefix + ChatColor.RED + "You need to hold an item in hand for this command to work.");
  
  }
  
  
}
