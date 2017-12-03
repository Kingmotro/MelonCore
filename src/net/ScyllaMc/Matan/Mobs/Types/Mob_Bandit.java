package net.ScyllaMc.Matan.Mobs.Types;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.Mobs.Mob;
import net.ScyllaMc.Matan.Mobs.Bending.Ability_EarthBlast;
import net.ScyllaMc.Matan.Mobs.Bending.Ability_EarthShockwave;
import net.ScyllaMc.Matan.Mobs.Bending.Ability_EarthSnake;
import net.ScyllaMc.Matan.Util.ItemCreator;
import net.ScyllaMc.Matan.Util.Skull;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;

public class Mob_Bandit implements MobType {

	@Override
	public int getViewRange() {
		return 35;
	}

	@Override
	public double getSpeed() {
		return 0.21;
	}

	@Override
	public double getBaseHealth() {
		return 10;
	}

	@Override
	public double getBaseDamage() {
		return 0.14;
	}

	@Override
	public double getLevelModifier() {
		return 0.15;
	}

	@Override
	public String getTypeName(Language lan) {
		
		return ChatColor.DARK_GREEN + Message.MOB_EARTHBANDIT.getTransMsg(lan);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void assign(Mob m) {

		EntityCreature c = m.getCreature();

		c.goalSelector.a(0, new PathfinderGoalFloat(c));
		c.goalSelector.a(8, new PathfinderGoalRandomLookaround(c));

		c.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(c, 5.0D));
		c.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(c, 1.0D, false));
		c.goalSelector.a(5, new PathfinderGoalMoveTowardsTarget(c, 2.0D, 10));
		c.goalSelector.a(8, new PathfinderGoalLookAtPlayer(c, EntityHuman.class, 8.0F));
		c.goalSelector.a(2, new PathfinderGoalMeleeAttack(c, 1.0, true));
		c.targetSelector.a(1, new PathfinderGoalHurtByTarget(c, false));
		c.goalSelector.a(5, new PathfinderGoalLeapAtTarget(c, 1));
		c.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(c, EntityHuman.class, 0, true, false, null));

		
		if(m.getEntity().getEquipment().getArmorContents()[1].getType().equals(Material.AIR)){
			ItemStack[] armor = new ItemStack[] { null, null, null, null };
	
			Random r = new Random();
			int rn = r.nextInt(2);
			if (rn == 1) {
				armor[3] = Skull.getSkull(Skull.bandit1);
			}
			if (rn == 2) {
				armor[3] = Skull.getSkull(Skull.bandit2);
			}
			if (rn == 3) {
				armor[3] = Skull.getSkull(Skull.bandit3);
			}
	
			armor[2] = ItemCreator.armor(Material.IRON_CHESTPLATE);
			armor[1] = ItemCreator.armor(Material.LEATHER_LEGGINGS);
			armor[0] = ItemCreator.armor(Material.LEATHER_BOOTS);
	
			m.getEntity().getEquipment().setArmorContents(armor);
		}
	}

	@Override
	public ItemStack[] getDrops(Mob m) {
		return new ItemStack[] {};
	}

	@Override
	public void attack(Mob m, LivingEntity t) {

		double dis = m.getEntity().getLocation().distance(t.getLocation());


		if (!m.inCooldown()) {
		
			if (dis < 4) {

				m.setCooldown(12);
				new Ability_EarthShockwave().start(m, t);

			} else if (dis > 3 && dis < 12) {

				m.setCooldown(8);
				new Ability_EarthSnake().start(m, t);

			} else {

				m.setCooldown(7);
				new Ability_EarthBlast().start(m, t);

			}
		}

	}

	@Override
	public boolean shouldTarget(Mob m, LivingEntity t) {

		return true;

	}

}
