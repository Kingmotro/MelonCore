package net.ScyllaMc.Matan.Mobs;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.MelonCore.ServerType;

public class EventEntitySpawn implements Listener {

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		Chunk chunk = event.getChunk();

		for (int r = 0; r < chunk.getEntities().length; r++) {
			Entity e = chunk.getEntities()[r];

			if (e instanceof LivingEntity) {

				if (Mob.isMob((LivingEntity) e)) {
					Mob m = Mob.fromLivingEntity((LivingEntity) e);
					m.reassign();
				}
			}
		}

	}

	@EventHandler
	public void onChunkLoad(ChunkUnloadEvent event) {
		Chunk chunk = event.getChunk();

		for (int r = 0; r < chunk.getEntities().length; r++) {
			Entity e = chunk.getEntities()[r];

			if (e instanceof LivingEntity) {

				if (e instanceof Zombie) {
					e.remove();
				}
			}
		}

	}

	@EventHandler
	public void onSpawn(CreatureSpawnEvent event) {

		if (event.getEntity() instanceof Horse) {

			final LivingEntity e = event.getEntity();
			new BukkitRunnable() {

				@Override
				public void run() {

					if (Mount.getFromEntity(e) == null) {

						e.remove();
						return;
					}

				}
			}.runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), 5);

			return;
		}


		if (event.getEntity() instanceof ExperienceOrb) {

			event.setCancelled(true);

			return;
		}

		if (event.getEntity() instanceof Ageable) {

			Ageable ag = (Ageable) event.getEntity();
			ag.setAdult();

		}

		if (!(event.getEntity() instanceof LivingEntity)) {
			return;
		}

		if (event.getSpawnReason().equals(SpawnReason.BREEDING)) {

			event.setCancelled(true);

			return;
		}

		if (event.getEntity() instanceof Zombie) {

			Zombie z = (Zombie) event.getEntity();
			z.setBaby(false);

			if (MelonCore.spawn.distance(event.getLocation()) < 700) {

				Random rand = new Random();

				if (rand.nextInt(3) == 1) {
					new Mob((LivingEntity) event.getEntity(), Mob.MobTypes.BANDIT, getLevelFromLoc(event.getLocation()));

					return;
				} else {

					event.setCancelled(true);

					return;
				}

			} else {

				Random rand = new Random();

				if (rand.nextInt(2) == 1) {
					new Mob((LivingEntity) event.getEntity(), Mob.MobTypes.BANDIT, getLevelFromLoc(event.getLocation()));

					return;
				} else {

					event.setCancelled(true);

					return;
				}

			}

		}

		if (event.getEntity() instanceof Pig) {

			if (event.getLocation().distance(MelonCore.spawn) > 900) {

				new Mob((LivingEntity) event.getEntity(), Mob.MobTypes.BOAR, getLevelFromLoc(event.getLocation()));

				return;
			}else{
				
				event.setCancelled(true);
				
			}
			
			return;
		}

		if (event.getEntity() instanceof Chicken) {

			new Mob((LivingEntity) event.getEntity(), Mob.MobTypes.DUCK, getLevelFromLoc(event.getLocation()));

			return;
		}

		if (event.getEntity() instanceof Cow) {

			new Mob((LivingEntity) event.getEntity(), Mob.MobTypes.COW, getLevelFromLoc(event.getLocation()));

			return;
		}

		if (event.getEntity() instanceof Sheep) {

			Random r = new Random();

			if (r.nextInt(5) == 1 && event.getLocation().distance(MelonCore.spawn) > 900) {
				new Mob((LivingEntity) event.getEntity(), Mob.MobTypes.RAM, getLevelFromLoc(event.getLocation()));
				return;
			}

			new Mob((LivingEntity) event.getEntity(), Mob.MobTypes.SHEEP, getLevelFromLoc(event.getLocation()));

			return;
		}

		SpawnReason reason = event.getSpawnReason();

		if (reason.equals(CreatureSpawnEvent.SpawnReason.EGG) || reason.equals(CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM) || reason.equals(CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN)) {

			return;
		}

		event.setCancelled(true);

	}

	public int getLevelFromLoc(Location loc) {

		double dis = MelonCore.spawn.distance(loc);

		int baselvl = (int) (1 + Math.round(dis / 200));

		Random r = new Random();
		int Low = 1;
		int High = 5;

		int lvl = baselvl + r.nextInt(High - Low) + Low;

		return lvl;
	}

}