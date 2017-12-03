package net.ScyllaMc.Matan.Mobs;


import org.bukkit.event.Listener;


public class EntityEvents implements Listener{
  
	
	
	/**

	
	
	@EventHandler
	private void onDamage(EntityDamageByEntityEvent event) {
	if(event.getDamager() instanceof Player){
	Mob mob = Mob.getInstanceOfEntity((LivingEntity) event.getEntity(), null);
	if(mob.type == MobType.AIR_MONK){
	mob.addTarget((Player)event.getDamager());
	}}}
	
	
	
	
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event){
	if (event.getEntity() instanceof FallingBlock){
	    
		   event.setCancelled(true);
		   
		   if(BendingManager.getElement(event.getEntity()) != null && BendingManager.getElement(event.getEntity()).equals(Element.Earth)){
			   Location loc =  event.getEntity().getLocation();
              loc.getWorld().playEffect(loc.add(0,0,0), Effect.EXPLOSION_LARGE,1);
              loc.getWorld().playEffect(loc.add(0,0,0), Effect.STEP_SOUND,1);
              loc.getWorld().playSound(loc, Sound.EXPLODE , 1.0F, 17.0F);

              
   	       for (Player player : net.ScyllaMc.Matan.Mobs.Bending.BendingManager.getPlayersAroundPoint(event.getEntity().getLocation(), 4.0D)) {
   	    	   
   	                 ((LivingEntity)player).damage(3);
   	                  player.setVelocity(player.getLocation().getDirection().setY(0.4));           
   	                }
   	       
   	       
		   }
           event.getEntity().remove();
	    }
	}
	
	


	@EventHandler
	private void chunckload(ChunkLoadEvent event) {
		
	Chunk c = event.getChunk();
	for(Entity er : c.getEntities()){
	
	if(er instanceof Pig){
	final Mob mob = Mob.getInstanceOfEntity((LivingEntity )er, null);
	if(mob.type != MobType.WILD_BOAR){
	mob.setType(MobType.WILD_BOAR);
	mob.assign();
	}}
		
	if(er instanceof Zombie){
	final Mob mob = Mob.getInstanceOfEntity((LivingEntity )er, null);
	if(mob.type == MobType.HOSTILE){
	mob.setType(EventEntitySpawn.mobTypeByLocation(er.getLocation()));
	mob.assign();
	}}
		
		
	}
		
	
		
	}
	
	
	

	**/


	
	
	
	
	
	

	
}