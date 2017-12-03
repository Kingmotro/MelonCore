package net.ScyllaMc.Matan.Mobs.Types;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.Mobs.Mob;
import net.ScyllaMc.Matan.Util.ItemCreator;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;

public class Mob_Sheep implements MobType{

	
	
	@Override
	public int getViewRange() {
		return 0;
	}

	@Override
	public double getSpeed() {
		return 0.24;
	}

	
	@Override
	public double getBaseHealth() {
		return 9;
	}

	@Override
	public double getBaseDamage() {
		return 0.0;
	}

	@Override
	public double getLevelModifier() {
		return 0.1;
	}

	@Override
	public ItemStack[] getDrops(Mob m) {
		
		return new ItemStack[] { 
				ItemCreator.item(Material.MUTTON, "1-3" ,ChatColor.ITALIC + "Mutton"),  
				ItemCreator.item(Material.BONE, "1-2")
		};
		
	}

	
	@Override
	public String getTypeName(Language lan) {

		return ChatColor.GRAY + Message.MOB_SHEEP.getTransMsg(lan);
		
	}

	@Override
	public void assign(Mob m) {
		EntityCreature c = m.getCreature();
		
		Sheep s = (Sheep) m.getEntity();
		s.setColor(DyeColor.WHITE);
				
		c.goalSelector.a(0, new PathfinderGoalFloat(c));
		c.goalSelector.a(8, new PathfinderGoalRandomLookaround(c));

		c.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(c, 5.0D));
		c.goalSelector.a(5, new PathfinderGoalLeapAtTarget(c, 1));
		c.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(c, 1.0D, false));
	}
	
	
	
	@Override
	public void attack(Mob m, LivingEntity t) {
		
		
	}

	
	@Override
	public boolean shouldTarget(Mob m, LivingEntity t) {
		
		return false;
		
	}







	
	
	
	
}
