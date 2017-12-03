package net.ScyllaMc.Matan.Mobs.Bending;

import org.bukkit.entity.LivingEntity;

import net.ScyllaMc.Matan.Mobs.Mob;

public interface Ability {

	
		public boolean progress();
		
		public void start(Mob e, LivingEntity t);


		
}
