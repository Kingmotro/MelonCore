package net.ScyllaMc.Matan.Mobs.Bending;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;


public class EventAbility implements Listener{

	
	
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event){
	
		if (event.getEntity() instanceof FallingBlock){
	    

		   
		   if(event.getBlock().getMetadata("bending") != null){
			  Location loc =  event.getBlock().getLocation();
              loc.getWorld().playEffect(loc.add(0,0,0), Effect.EXPLOSION_LARGE,1);
              loc.getWorld().playEffect(loc.add(0,0,0), Effect.STEP_SOUND,1);
              loc.getWorld().playSound(loc, Sound.EXPLODE , 1.0F, 17.0F);

              
   	       			for (Player player : net.ScyllaMc.Matan.Mobs.Bending.BendingManager.getPlayersAroundPoint(event.getEntity().getLocation(), 4.0D)) {
   	    	   
   	                 ((LivingEntity)player).damage(3);
   	                  player.setVelocity(player.getLocation().getDirection().setY(0.4));           
   	                }
   	       
   	       			event.getEntity().remove();
   	       			event.setCancelled(true);
		   }
		   
          
	    }
	}
	
	
	
}
