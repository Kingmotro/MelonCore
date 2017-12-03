package net.ScyllaMc.Matan.Mobs.Bending;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import net.ScyllaMc.Matan.Mobs.Mob;

public class Ability_EarthBlast implements Ability{
	
	   private static UUID ID = UUID.randomUUID();
	   
	   
	   private Mob e;
	   private Material material;
	   private Vector vector;
	  
	    
	    
	    @Override
		public void start(Mob e, LivingEntity target){
				   
		if(!e.getEntity().isOnGround()){return;}

		this.e = e;
		this.vector =  target.getLocation().toVector().subtract(e.getEntity().getLocation().toVector());
			        
			        
			        AbilityRunnable.instances.put(ID, this);
			        
			        if(e.getEntity().getWorld().getBlockAt(e.getEntity().getLocation().add(0,-1,0)) != null && e.getEntity().getWorld().getBlockAt(e.getEntity().getLocation().add(0,-1,0)).getType() != Material.AIR){
				        material = e.getEntity().getWorld().getBlockAt(e.getEntity().getLocation().add(0,-1,0)).getType();	
				        }else{
				        if(material == null || !material.equals(Material.STONE) || !material.equals(Material.STONE)  || !material.equals(Material.GRASS) ){
					    material = Material.DIRT;}
					    if(e.getEntity().getLocation().getBlock().getBiome().equals(Biome.DESERT)){
					    material = Material.SAND;}}
				        
				        
			    }
	   
	    
	    
	    @Override
		public boolean progress(){	    	
	    	
	    	 @SuppressWarnings("deprecation")
			 FallingBlock block = e.getEntity().getLocation().getWorld().spawnFallingBlock(e.getEntity().getLocation(), material, (byte) 0);
	    	 block.setMetadata("bending", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("MelonCore"), 1));			  
	    	 block.setVelocity(vector.multiply(0.05));
		   
		   return false;
	   
		}
		        
	    
	    
	    
}
