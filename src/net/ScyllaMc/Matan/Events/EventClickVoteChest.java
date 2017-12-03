package net.ScyllaMc.Matan.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class EventClickVoteChest implements Listener {


     public static ArrayList<String> DONTCLICK = new ArrayList<String>();
	 
     public void donotclickplz(final Player p){
	 new BukkitRunnable(){
	 @Override
	 public void run(){     
	 DONTCLICK.remove(p.getUniqueId().toString());
	 }}.runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), 320);}


	@EventHandler
    public void PlayerRightClick(PlayerInteractEvent event) {
	final Player p = event.getPlayer();
	final MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(p);

	if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
	if(p.isSneaking()){return;}	
	if(!event.getClickedBlock().getLocation().equals(MelonCore.voteloc)){return;}
	
	event.setCancelled(true);
	
	if(DONTCLICK.contains(p.getUniqueId().toString())){p.sendMessage(MelonCore.prefix + ChatColor.RED.toString() + "Please wait before opening another chest!");return;}
		        
	if(mp.votetokens <= 0){p.sendMessage(MelonCore.prefix + ChatColor.RED.toString() + "You do not have any keys! Use /Vote to get more!");return;}
	
	DONTCLICK.add(p.getUniqueId().toString());
	donotclickplz(p);
	MelonCore.VoteChest.playOpening(mp);
	return;	}		
				
			
		
		}
		       
			

				

  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	

