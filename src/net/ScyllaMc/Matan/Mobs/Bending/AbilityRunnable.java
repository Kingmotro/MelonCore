package net.ScyllaMc.Matan.Mobs.Bending;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class AbilityRunnable implements Runnable{
	

public static ConcurrentHashMap<UUID, Ability> instances = new ConcurrentHashMap<UUID, Ability>();

  
	public void removeAll(){
		instances.clear();
	}
	 
	  
	  
	
	@Override
	public void run(){
		  
	for (UUID id : instances.keySet()){
		if (!instances.get(id).progress()) {
		instances.remove(id);
		}
	}
	
	}

	
   
  
}


