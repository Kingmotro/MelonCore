package net.ScyllaMc.Matan.Mobs;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;


public class EventEntityTarget implements Listener{


	
	  
	  @EventHandler
	  public void onTarget(EntityTargetEvent event){
		  		 
	  if (event.getTarget() == null) {return;}
	  if (!(event.getEntity() instanceof LivingEntity)){return;}
	  if (!(event.getTarget() instanceof LivingEntity)){return;}

	  LivingEntity e = (LivingEntity) event.getEntity();
	  LivingEntity t  = (LivingEntity) event.getTarget();
	  
	  
	  
	  if(Mob.isMob(e)){
		  
		  Mob mob = Mob.fromLivingEntity(e);
		 
		  if(mob.shouldTarget(e)){	
			  mob.attack(t);
		  }else{
			  event.setCancelled(true);
		  }

		  
		  
		  return;
	  }
	  
	  
	  }
	 



}
