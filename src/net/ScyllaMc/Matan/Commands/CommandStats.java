package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.unon1100.TellrawAPI.TellrawAPI;
import com.unon1100.TellrawAPI.TellrawText;
import com.unon1100.TellrawAPI.TellrawURL;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandStats{
 
  
  
  @SuppressWarnings("deprecation")
public void run(CommandSender sender, String[] args) throws Exception{
  if(!(sender instanceof Player)){return;}
  
  Player player = (Player) sender;
  Player p = player;

  MelonPlayer mp2;
  final Player player2 = player;
  if(args.length == 0){
  mp2 = MelonPlayer.getInstanceOfPlayer(p);
  }else{
  mp2 = MelonPlayer.getInstanceOfPlayer(Bukkit.getOfflinePlayer(args[0]));
  mp2.dowloadData();}

  final MelonPlayer mp = mp2;
	Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {				 
	@Override
	public void run() {
		
	player2.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
	player2.sendMessage(ChatColor.GRAY + mp.rank.getTagClosed() + mp.getName() + "'s Stats");
	player2.sendMessage(ChatColor.AQUA + "Level: " + ChatColor.GRAY + mp.level);
	player2.sendMessage(ChatColor.DARK_AQUA + "Coins: " + ChatColor.GRAY + mp.coins);
	player2.sendMessage(ChatColor.DARK_AQUA + "Kills: " + ChatColor.GRAY + mp.kills);
	player2.sendMessage(ChatColor.DARK_AQUA + "Deaths: " + ChatColor.GRAY + mp.deaths);

	TellrawURL url = new TellrawURL(ChatColor.LIGHT_PURPLE + "Click Here", "http://scyllamc.net/stats.php?player=" + mp.getName());  
	TellrawText text = new TellrawText(ChatColor.DARK_AQUA + " to view full stats!");  
  StringBuilder sb = TellrawAPI.finalize(url,text);
	TellrawAPI.sendTo(sb,player2);  
	player2.sendMessage(ChatColor.GRAY + "-------------------------------------------------");
  }},7l);
	
	
  }
  
  
  
}
