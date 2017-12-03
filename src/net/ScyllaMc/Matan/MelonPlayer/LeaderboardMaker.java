package net.ScyllaMc.Matan.MelonPlayer;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.Rank.Rank;

public class LeaderboardMaker {

	
	Location origin_loc;
	Location current_loc;
	BlockFace dir;
	String stat;
	ChatColor color;
	int display;
	
	public LeaderboardMaker(Location l,String s, BlockFace d,ChatColor c, int dis){
	this.origin_loc =l;
	this.dir = d;
	this.stat = s;
	this.color = c;
	this.display = dis;
	this.current_loc = this.origin_loc;
	
	HashMap<Integer, String> uuidlist = MelonCore.Mysql.getTop(stat, display);
	HashMap<String, Integer> list = new HashMap<String, Integer>();
	
	int n = 1;
	
	for(Integer position : uuidlist.keySet()){
	String[] split = uuidlist.get(position).split("&");
	String uuid = split[0];
	OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
	int stat_count = Integer.parseInt(split[1]);
	String rank = split[2];

	createSign(n, op.getName(),stat_count,rank);
	n++;
	list.put(op.getName(), stat_count);}	
	
	}
	

	
	public void createSign(int i,String name, int stat_count,String rank){
		Location loc = this.current_loc;	
		
		if(this.dir == BlockFace.NORTH){		
		if(i > 1 && i != 6){loc.add(-1, 0 ,0);}
		if(i == 6){loc = loc.add(4,-2,0);}}
		
		if(this.dir == BlockFace.EAST){		
		if(i > 1 && i != 6){loc.add(0, 0 ,-1);}
		if(i == 6){loc = loc.add(0,-2,4);}}
		
		if(this.dir == BlockFace.SOUTH){		
		if(i > 1 && i != 6){loc.add(1, 0 ,0);}
		if(i == 6){loc = loc.add(-4,-2,0);}}
			
		
		boolean bsign = false;
		if(loc.getBlock().getState() instanceof Sign){bsign = true;}
		
		if(bsign){
		Sign sign = (Sign) loc.getBlock().getState();
		String a = "th";
		if(i == 1){a = "st";}
		if(i == 2){a = "nd";}
		if(i == 3){a = "rd";}
		sign.setLine(0, ChatColor.DARK_AQUA + "" + i + a);
		sign.setLine(1, ChatColor.GRAY + Rank.fromString(rank).getTagClosed());
		sign.setLine(2, Rank.fromString(rank).getColor() + name);
		sign.setLine(3, color + MelonCore.firstLetterCaps(stat) + ": " + stat_count);
		sign.update();
		org.bukkit.material.Sign matSign =  new org.bukkit.material.Sign(Material.WALL_SIGN);
		matSign.setFacingDirection(dir);
		sign.setData(matSign);}
		
		Block b = new Location(loc.getWorld(),loc.getX(),loc.getY() +1 ,loc.getZ()).getBlock();
		if(b.getType() == Material.SKULL){
		    Skull skull = (Skull)b.getState();
		    skull.setSkullType(SkullType.PLAYER);
		    skull.setOwner(name);
		    skull.update(true);
		}
		  
		
		
		
		this.current_loc = loc;
		}
	
	
	
}
