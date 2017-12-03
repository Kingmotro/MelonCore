package net.ScyllaMc.Matan.Events;



import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;


public class EventEntitySunlightDamage implements Listener{
  
	

	
	
	
	@EventHandler
	private void onCombust(EntityCombustEvent event) {
    event.setCancelled(true);
	}
	

	
	
	
	
	

	


	
	
	
	
	
	

	
}