package net.ScyllaMc.Matan.Mobs;


import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class EventEntityDeath implements Listener{
  
	

	@EventHandler
	public void death(EntityDeathEvent event) {  	
		
		event.setDroppedExp(0);
		event.getDrops().clear();
		
		if(!(event.getEntity() instanceof LivingEntity)){ return; }
		if(event.getEntity().getKiller() == null || !(event.getEntity().getKiller() instanceof Player)){ return; }

		LivingEntity e = event.getEntity();
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(event.getEntity().getKiller());
		
		if(e.getPassenger() != null){e.getPassenger().remove();}
    
		if(!Mob.isMob(e)){ return; }
		
	
		Mob mob = Mob.fromLivingEntity(e);
	
		event.getDrops().addAll(Arrays.asList(mob.getDrops()));
		
		
		ArrayList<String> rewards = new ArrayList<String>();
		int coins = (int) Math.round(mob.getDamage() * 3.5);
		int xp = (int) Math.round((mob.getMaxHealth() + (mob.getDamage() * 10)) * 0.6);
		int clancoins = (int) Math.round(coins / 4);
		int clanpoints = (int) Math.round(mob.getDamage() * 1.5);
 
		p.addMobKill(1);

		p.getOnlinePlayer().setFoodLevel(p.getOnlinePlayer().getFoodLevel() + 1);
		
		if(mob.getLevel() - p.level > 20){
			coins = 0;
			xp = 0;
			rewards.add(ChatColor.RED.toString() + ChatColor.BOLD + "Level too high to get rewards");
		}else{
					
			if(coins > 0){
				rewards.add(ChatColor.GOLD.toString() + ChatColor.BOLD + coins + ChatColor.GRAY + " Coins");
				p.addCoins(coins, true);
			}
			
			if(xp > 0){
				rewards.add(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + xp + ChatColor.GRAY + " XP");
				p.addXp(xp);
			}
			
			if(p.inClan()){
				
				if(clancoins > 0){
					rewards.add(ChatColor.DARK_GREEN.toString()  + ChatColor.BOLD + clancoins + ChatColor.GRAY + " Clan Coins");
				}
				
				rewards.add(ChatColor.DARK_GREEN.toString()  + ChatColor.BOLD + "1" + ChatColor.GRAY + " Clan Points");
				p.clan.addClanPointsAndCoins(clancoins, clanpoints);
			}
			
		}
		final Hologram hologram = HologramsAPI.createHologram(Bukkit.getPluginManager().getPlugin("MelonCore"), mob.getEntity().getLocation().add(0.0, 1.8, 0.0));
		hologram.appendTextLine(ChatColor.GRAY + "Killed " + mob.getName());
		hologram.appendTextLine(ChatColor.GREEN.toString() + ChatColor.BOLD + "Rewards: ");
		for(String r : rewards){
			hologram.appendTextLine(r);
		}

		if(rewards.size() == 0){
			hologram.appendTextLine(ChatColor.RED.toString() + ChatColor.BOLD + "No rewards!");
		}
		 
		hologram.getVisibilityManager().setVisibleByDefault(false);
		hologram.getVisibilityManager().showTo(p.getOnlinePlayer());
    
		
		
		
		new BukkitRunnable() {
		@Override
		public void run() {
			hologram.delete();
			cancel();
		}}.runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), 180L);
		
	
	}

	
}