package net.ScyllaMc.Matan.Mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.Mobs.Mob.MobTypes;

public class Spawner {
	public static Map<Location, Spawner> spawners = new HashMap<Location, Spawner>();
	public static Map<Entity, Spawner> mobsspawner = new HashMap<Entity, Spawner>();

	private EntityType type;
	private MobTypes mtype;
	private int mobLimit;
	private int min;
	private int level;
	private int max;
	private Location loc;
	private ArrayList<Entity> mobs = new ArrayList<Entity>();

	public Spawner(EntityType e, Location location, int interval, int level, MobTypes mtype, int limit, int rad_min, int rad_max) {
		this.type = e;
		this.level = level;
		this.mtype = mtype;
		this.mobLimit = limit;
		this.min = rad_min;
		this.max = rad_max;
		this.loc = location;

		if (spawners.containsKey(location)) {
			spawners.remove(location);
			spawners.put(location, this);
		} else {
			spawners.put(location, this);
		}

		Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			
		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		
		@Override
			public void run() {

				mobs.remove(toRemove);
				toRemove.clear();
				
				for (Entity e : mobs) {
					if (e == null || e.isDead()) {
						toRemove.add(e);
					}
				}

				if (loc.getChunk().isLoaded()) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().distance(loc) <= 100) {
							spawn();
							spawn();
						}
					}
				}

			}
		}, 0, interval);

	}

	public Location getRandomLocation() {
		World world = loc.getWorld();

		int Xminimum = loc.getBlockX() - min;
		int Xmaximum = loc.getBlockX() + max;
		int Zminimum = loc.getBlockZ() - min;
		int Zmaximum = loc.getBlockZ() + max;

		int randomX = 0;
		int randomZ = 0;
		double x = 0.0D;
		double z = 0.0D;
		randomX = Xminimum + (int) (Math.random() * (Xmaximum - Xminimum + 1));
		randomZ = Zminimum + (int) (Math.random() * (Zmaximum - Zminimum + 1));
		x = Double.parseDouble(Integer.toString(randomX));
		z = Double.parseDouble(Integer.toString(randomZ));
		x = x + 0.5;
		z = z + 0.5;
		Location n = new Location(world, x, world.getHighestBlockYAt(new Location(world, x, loc.getY(), z)), z);
		return n;
	}

	public void spawn() {

		if (loc.getChunk().getEntities().length > 20) {
			return;
		}
		if (mobs.size() < mobLimit) {
			Mob mob = new Mob((LivingEntity) loc.getWorld().spawnEntity(getRandomLocation(), type), mtype, level);
			mobs.add(mob.getEntity());
			mobsspawner.put(mob.getEntity(), this);
			return;
		}
	}

	public void removeEntity(Entity e) {
		mobs.remove(e);
	}

}