package net.ScyllaMc.Matan.NPC;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import com.google.common.collect.Sets;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;


public class NPCVillager extends EntityVillager{
	
	
public NPCVillager(World world){

super(world);

try {
	 
	 Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
	 bField.setAccessible(true);
	 Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
	 cField.setAccessible(true);
	 bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
	 bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
	 cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
	 cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());	
	 
	 goalSelector.a(0, new PathfinderGoalFloat(this));
     goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
	 goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
	 
	 this.persistent = true;
	 
	 
	 } catch (Exception exc) {exc.printStackTrace();}
    

}


@Override
public void move(double d0, double d1, double d2) {

}

@Override
public void collide(Entity entity) {
}

@Override
public boolean damageEntity(DamageSource damagesource, float f) {
    return false;
}

@Override
public void g(double d0, double d1, double d2) {
}



}