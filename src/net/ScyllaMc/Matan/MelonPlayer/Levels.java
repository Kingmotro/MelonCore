package net.ScyllaMc.Matan.MelonPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;

public class Levels {

	
	
	
	
	   
    public void calculateLevel(MelonPlayer p, boolean levelup,boolean save){
	    if(p.isOnline()){
	     final Player pl =  p.getOnlinePlayer();
	     pl.setExp(0);
	     pl.setLevel(p.level);
	     int n =  p.currentxp;
	     int v = (p.level * p.level) * 35;
	     if(v == 0){ v = 1; }
	     float percentage = n * 100 / v;
	     double k2 = Math.ceil(percentage)  / 100;
	     pl.setExp((float) k2);
	     pl.setLevel(p.level);
	     
	     if(levelup == true){
	     double addhearts = getAddHearts(p.level);
	     pl.setHealthScale(40 + addhearts);
		 pl.setHealthScaled(true);
		 
		 Bukkit.broadcastMessage(MelonCore.prefix + ChatColor.GREEN + p.rank.getTagClosed() + p.getName() + ChatColor.DARK_GREEN + " Is now level " + p.level);   
		 p.sendMessage(MelonCore.prefix + ChatColor.GREEN + "Leveled up! "  + ChatColor.DARK_AQUA + " You're now level " + p.level);   
		 
		 int addedcoins = p.level * 15;
		 p.addCoins(addedcoins,true);
		 p.sendMessage(ChatColor.LIGHT_PURPLE + "+ " + addedcoins + " Coins");  
		 p.sendMessage(MelonCore.prefix + ChatColor.GRAY + ChatColor.BOLD.toString() + "--------");   

		 new BukkitRunnable(){
		 int number = 0; 
		 @Override
		 public void run(){     
         if(number == 1) {cancel();}
		        
         pl.getWorld().playSound(pl.getLocation(), Sound.LEVEL_UP , 1.0F, 17.0F);
		     
		 final Firework f = pl.getWorld().spawn(pl.getLocation(), Firework.class);
		 FireworkMeta fm = f.getFireworkMeta();
         fm.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(Type.BALL_LARGE).withColor(Color.GREEN).withFade(Color.GRAY).build());
		 fm.setPower(1);
		 f.setFireworkMeta(fm);
		 number++;
		        	
		 
		 }}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0L, 20L);
		 
	    }}
	    
	    if(save != false){
	 
	    p.saveData();
	    MelonCore.Mysql.setIntegerValue(p, "level", p.level);}
	    
		
	}
    
    
   
    public static double getAddHearts(int level){
        
    double addhearts = 0.0;
    
    int d = (level / 5) + 1;
    
    addhearts = d * 0.5;
    
	return addhearts;
    }
	
	
	
	
	
}
