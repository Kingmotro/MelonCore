package net.ScyllaMc.Matan.Mobs.Types;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.Mobs.Mob;
import net.ScyllaMc.Matan.Util.ItemCreator;
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

public class Mob_Boar implements MobType{

	
	
	@Override
	public int getViewRange() {
		return 20;
	}

	@Override
	public double getSpeed() {
		return 0.3;
	}
	
	@Override
	public double getBaseHealth() {
		return 12;
	}

	@Override
	public double getBaseDamage() {
		return 0.04;
	}

	@Override
	public double getLevelModifier() {
		return 0.3;
	}

	@Override
	public ItemStack[] getDrops(Mob m) {
		
		return new ItemStack[] { 
				ItemCreator.item(Material.PORK, "2-4" ,ChatColor.ITALIC + "Pork"),  
				ItemCreator.item(Material.BONE, "0-2")
		};
		
	}
	
	@Override
	public String getTypeName(Language lan) {
		
		return ChatColor.DARK_GREEN + Message.MOB_BOAR.getTransMsg(lan);
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
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void attack(Mob m, LivingEntity t) {
		
		double dis = m.getEntity().getLocation().distance(t.getLocation());
		
		if(!m.inCooldown()){
			
			if(dis < 2.4){
				
				if(t instanceof Player){
					
					Player p = (Player) t;
					
					((LivingEntity) p).damage(m.getDamage(), m.getEntity());
					p.setLastDamageCause(new EntityDamageByEntityEvent( m.getEntity(), p, EntityDamageByEntityEvent.DamageCause.CUSTOM, m.getDamage()));
					
					
				}
			}

		}
	}

	@Override
	public boolean shouldTarget(Mob m, LivingEntity t) {
		
		return true;
		
	}




	
	
	
	
}
