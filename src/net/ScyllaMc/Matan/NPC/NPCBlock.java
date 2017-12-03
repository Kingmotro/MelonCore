package net.ScyllaMc.Matan.NPC;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class NPCBlock {
	
	public static HashMap<Block, NPCBlock> list = new HashMap<Block, NPCBlock>();
	Block b;
	String name;
	Hologram holo;
	int level = 1;
	//Quest q;


	@SuppressWarnings("deprecation")
	public NPCBlock(Material m, Location loc,String name,int level){
	loc.getWorld().getBlockAt(loc).setTypeId(m.getId());
	Block b = loc.getBlock();
	this.b = b;
	//this.q = q;
	this.level = level;
	this.name = name;
	
	this.holo = HologramsAPI.createHologram(Bukkit.getPluginManager().getPlugin("MelonCore"), loc.add(0,1.7,0));
	holo.appendTextLine(ChatColor.DARK_GREEN + this.name);
	holo.appendTextLine(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Quest Item");
    list.put(this.b, this);}
	
	

	
	public static NPCBlock fromBlock(Block b){
	if(list.containsKey(b)){return list.get(b);}
	return null;}
	
	
	
	
	public void interact(MelonPlayer p){
	new NPC(this).interact(p);}
	
	

	public String getName() {
	return name;
	}
	
	
	
	

	
	
}
