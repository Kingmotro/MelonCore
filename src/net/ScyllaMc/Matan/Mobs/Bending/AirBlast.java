package net.ScyllaMc.Matan.Mobs.Bending;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class AirBlast implements Ability{
	
  private static double damage = 5;
  private static UUID ID = UUID.randomUUID();


  private BendingManager BendingManager = new BendingManager();
  private LivingEntity entity;
  private Location origin;
  private Location head;
  private Vector dir;
  
  
  @SuppressWarnings("static-access")
@Override
public boolean progress(){
    if (this.entity == null) {
      return false;
    }
    if (this.entity.getWorld() != this.head.getWorld()) {
      return false;
    }
    if (this.origin.distance(this.head) > 20.0D) {
      return false;
    }
    if ((!BendingManager.isTransparent(this.head.getBlock())) || (this.head.getBlock().getType().equals(Material.WATER))) {
      return false;
    }
    
    this.head.add(this.dir.multiply(1));
    ParticleEffect.CLOUD.display(this.head, 0.275F, 0.275F, 0.275F, 0.0F, 6);
    entity.getWorld().playSound(entity.getLocation(), Sound.SILVERFISH_WALK , 1.0F, 17.0F);

    for (Player entity : net.ScyllaMc.Matan.Mobs.Bending.BendingManager.getPlayersAroundPoint(this.head, 4.0D)) {
      if (((entity instanceof LivingEntity)) && (entity.getEntityId() != this.entity.getEntityId())) {
       
    	if (entity instanceof Player){
    	((LivingEntity)entity).damage(damage, this.entity);
        entity.setLastDamageCause(new EntityDamageByEntityEvent(this.entity, entity, EntityDamageByEntityEvent.DamageCause.CUSTOM, damage));
        entity.setVelocity(entity.getLocation().getDirection().setY(0.3));
        return false;}
    	
      }
    }
    
    return true;
  }
  
  @Override
public void start(LivingEntity entity, LivingEntity target,Double damage){
    this.entity = entity;
    this.origin = entity.getEyeLocation();
    this.head = entity.getEyeLocation();
    this.dir = GeneralMethods.getDirection(entity.getLocation(), target.getLocation()).normalize();
    AirBlast.damage = damage;
    AbilityRunnable.instances.put(ID, this);
  
    BendingManager.attackCooldown(entity,target,2);

  }
  

}
