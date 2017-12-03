package net.ScyllaMc.Matan.Mobs.Bending;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import net.ScyllaMc.Matan.Mobs.Mob;

public class Ability_EarthShockwave implements Ability {

	private static UUID ID = UUID.randomUUID();
	private Entity entity;
	private Material material;

	private double damage = 3.0;

	private int counter;

	@Override
	public void start(Mob mob, LivingEntity target) {

		this.entity = mob.getEntity();
		this.damage = damage * mob.getDamage();

		if (!entity.isOnGround()) {
			return;
		}

		AbilityRunnable.instances.put(ID, this);

		if (entity.getWorld().getBlockAt(entity.getLocation().add(0, -1, 0)) != null
				&& entity.getWorld().getBlockAt(entity.getLocation().add(0, -1, 0)).getType() != Material.AIR) {
			material = entity.getWorld().getBlockAt(entity.getLocation().add(0, -1, 0)).getType();
		} else {
			
			if (material == null || !material.equals(Material.STONE) || material.equals(Material.LONG_GRASS)|| !material.equals(Material.GRASS)) {
				material = Material.DIRT;
			}
			if (entity.getLocation().getBlock().getBiome().equals(Biome.DESERT)) {
				material = Material.SAND;
			}
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean progress() {
		if (counter == 10) {
			return false;
		}
		counter++;

		if (counter == 1) {
			for (Location loc : getCircle(entity.getLocation(), 2, 12)) {
				FallingBlock block2 = loc.getWorld().spawnFallingBlock(loc, material, (byte) 0);
				block2.getWorld().playEffect(block2.getLocation().add(0, -1, 0), Effect.STEP_SOUND, material.getId());

				block2.setDropItem(false);
				block2.setVelocity(new Vector(0, 0.2, 0));
			}
		}

		if (counter == 3) {
			for (Location loc : getCircle(entity.getLocation(), 3, 16)) {
				FallingBlock block2 = loc.getWorld().spawnFallingBlock(loc, material, (byte) 0);
				block2.getWorld().playEffect(block2.getLocation().add(0, -1, 0), Effect.STEP_SOUND, material.getId());

				block2.setDropItem(false);
				block2.setVelocity(new Vector(0, 0.3, 0));
			}
		}

		if (counter == 7) {
			for (Location loc : getCircle(entity.getLocation(), 4, 20)) {
				FallingBlock block2 = loc.getWorld().spawnFallingBlock(loc, material, (byte) 0);
				block2.getWorld().playEffect(block2.getLocation().add(0, -1, 0), Effect.STEP_SOUND, material.getId());
				block2.setDropItem(false);
				block2.setVelocity(new Vector(0, 0.4, 0));
			}
		}

		for (Player player : net.ScyllaMc.Matan.Mobs.Bending.BendingManager.getPlayersAroundPoint(entity.getLocation(),
				4.0D)) {

			if (entity.getEntityId() != player.getEntityId()) {
				((LivingEntity) player).damage(damage, this.entity);
				player.setLastDamageCause(new EntityDamageByEntityEvent(this.entity, player,
						EntityDamageByEntityEvent.DamageCause.CUSTOM, damage));
				player.getWorld().playEffect(player.getLocation().add(0, -1, 0), Effect.STEP_SOUND, material.getId());
				player.getWorld().playSound(player.getLocation(), Sound.STEP_GRASS, 1.0F, 17.0F);
				player.setVelocity(entity.getLocation().getDirection().setY(0.4));
			}
		}

		return true;

	}

	public ArrayList<Location> getCircle(Location center, double radius, int amount) {
		World world = center.getWorld();
		double increment = (2 * Math.PI) / amount;
		ArrayList<Location> locations = new ArrayList<Location>();
		for (int i = 0; i < amount; i++) {
			double angle = i * increment;
			double x = center.getX() + (radius * Math.cos(angle));
			double z = center.getZ() + (radius * Math.sin(angle));
			if (world.getBlockAt(new Location(world, x, center.getY(), z)).getType().equals(Material.AIR)) {
				locations.add(new Location(world, x, center.getY(), z));
			}
		}
		return locations;
	}

}
