package net.ScyllaMc.Matan.Mobs.Bending;

import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import net.ScyllaMc.Matan.Mobs.Mob;

public class Ability_EarthSnake implements Ability {

	private static UUID ID = UUID.randomUUID();
	private BendingManager BendingManager = new BendingManager();
	private Location start;
	private Entity entity;
	private Vector increase;
	private Material material;

	private double damage = 3.0;

	private int counter;

	@Override
	public void start(Mob e, LivingEntity target) {

		this.entity = e.getEntity();

		if (!entity.isOnGround()) {
			return;
		}

		this.start = entity.getLocation().add(0, 0.5, 0);
		Vector increase = start.getDirection().setY(0.9);
		this.increase = increase;
		this.damage = damage * e.getDamage();

		AbilityRunnable.instances.put(ID, this);

		if (entity.getWorld().getBlockAt(entity.getLocation().add(0, -1, 0)) != null
				&& entity.getWorld().getBlockAt(entity.getLocation().add(0, -1, 0)).getType() != Material.AIR) {
			material = entity.getWorld().getBlockAt(entity.getLocation().add(0, -1, 0)).getType();
		} else {
			if (material == null || !material.equals(Material.STONE) || !material.equals(Material.STONE)
					|| !material.equals(Material.GRASS)) {
				material = Material.DIRT;
			}
			if (entity.getLocation().getBlock().getBiome().equals(Biome.DESERT)) {
				material = Material.SAND;
			}
		}

	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public boolean progress() {
		Location point = start.add(increase);

		if (counter == 13) {
			return false;
		}
		if (this.start.distance(point) > 10.0D) {
			return false;
		}
		Location p2 = point;
		
		if ((!BendingManager.isTransparent(p2.getBlock())) || (p2.getBlock().getType().equals(Material.WATER))) {
			return false;
		}

		FallingBlock block = point.getWorld().spawnFallingBlock(point, material, (byte) 0);
		block.setVelocity(new Vector(0, 0.008, 0));
		block.setDropItem(false);

		point.getWorld().playSound(point, Sound.FIREWORK_BLAST, 1.0F, 17.0F);
		point.getWorld().playEffect(point.add(0, -1, 0), Effect.STEP_SOUND, material.getId());

		counter++;

		for (Player player : net.ScyllaMc.Matan.Mobs.Bending.BendingManager.getPlayersAroundPoint(point, 4.0D)) {
			if (player.getEntityId() != this.entity.getEntityId()) {

				if (player instanceof Player) {
					((LivingEntity) player).damage(damage, this.entity);
					player.setLastDamageCause(new EntityDamageByEntityEvent(this.entity, player,
							EntityDamageByEntityEvent.DamageCause.CUSTOM, damage));
					player.getWorld().playEffect(point.add(0, -1, 0), Effect.STEP_SOUND, material.getId());
					player.getWorld().playSound(player.getLocation(), Sound.STEP_GRASS, 1.0F, 17.0F);
					player.getWorld().playEffect(point.add(0, -1, 0), Effect.EXPLOSION_HUGE, 1);
					player.setVelocity(entity.getLocation().getDirection().setY(0.4));

					FallingBlock block2 = point.getWorld().spawnFallingBlock(point.add(0, 2, 0), material, (byte) 0);
					block2.setVelocity(new Vector(0, 0.008, 0));
					block2.setDropItem(false);

					FallingBlock block3 = point.getWorld().spawnFallingBlock(point.add(0, 2, 0), material, (byte) 0);
					block3.setVelocity(new Vector(0, 0.008, 0));
					block3.setDropItem(false);

					return false;
				}
			}
		}

		return true;

	}

}
