package net.ScyllaMc.Matan.Rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class PermissionManager {

	public static HashMap<UUID,PermissionAttachment> attachments = new HashMap<UUID,PermissionAttachment>();
	
	public static void addPlayer(Player p){
		try{
		MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(p);
		mp.rank.getName();
		PermissionAttachment attachment = p.addAttachment(Bukkit.getPluginManager().getPlugin("MelonCore"));	
		attachments.put(p.getUniqueId(), attachment);
	
		for(String perm : mp.rank.getPermissions()){
		attachment.setPermission(perm,true);} 
		
		if(mp.rank.getPermissions().contains("server.master")){
		mp.luck = mp.luck + 10;}
		
		if(mp.rank.getPermissions().contains("server.champion")){
		mp.luck = mp.luck + 10;}
		
		
		if(mp.isOnline() && mp.rank.getName().equalsIgnoreCase("obama")){
		mp.getOnlinePlayer().setHealthScale(5);	
		mp.getOnlinePlayer().setHealthScaled(true);	
		p.addPotionEffect( new PotionEffect(PotionEffectType.SLOW,20000,2));
		p.addPotionEffect( new PotionEffect(PotionEffectType.SLOW_DIGGING,20000,10));
		p.addPotionEffect( new PotionEffect(PotionEffectType.HUNGER,20000,1));
		p.addPotionEffect( new PotionEffect(PotionEffectType.WEAKNESS,2000,2));
		mp.getOnlinePlayer().setHealth(mp.getOnlinePlayer().getMaxHealth());
		mp.getOnlinePlayer().setGameMode(GameMode.ADVENTURE);}
		
		
		}catch (Exception e){e.printStackTrace();}}
	
	
	public static ArrayList<String> default_rank  = new ArrayList<String>();
	public static  ArrayList<String> obama_rank  = new ArrayList<String>();
	public static  ArrayList<String> gold_rank  = new ArrayList<String>();
	public static  ArrayList<String> hero_rank  = new ArrayList<String>();
	public static  ArrayList<String> master_rank  = new ArrayList<String>();
	public static  ArrayList<String> champion_rank  = new ArrayList<String>();
	public static  ArrayList<String> vip_rank  = new ArrayList<String>();
	public static  ArrayList<String> jrmod_rank  = new ArrayList<String>();
	public static  ArrayList<String> mod_rank  = new ArrayList<String>();
	public static  ArrayList<String> srmod_rank  = new ArrayList<String>();
	public static  ArrayList<String> admin_rank  = new ArrayList<String>();
	
	

	
	
	public void removePlayer(Player p){
	attachments.remove(p.getUniqueId());}
	
	
	
	
	
	
	
	
}
