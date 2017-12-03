package net.ScyllaMc.Matan.Mobs.Types;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
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

public class Mob_Cow implements MobType {

	@Override
	public int getViewRange() {
		return 0;
	}

	@Override
	public double getSpeed() {
		return 0.18;
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

		return new ItemStack[] { ItemCreator.item(Material.RAW_BEEF, "0-6", ChatColor.ITALIC + "Beef"),
				ItemCreator.item(Material.BONE, "1-2") , ItemCreator.item(Material.LEATHER, "0-4") };
		

	}

	@Override
	public String getTypeName(Language lan) {

		return ChatColor.GRAY + Message.MOB_COW.getTransMsg(lan);
		
	}

	@Override
	public void assign(Mob m) {
		EntityCreature c = m.getCreature();

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
