package net.ScyllaMc.Matan.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;

import net.ScyllaMc.Matan.Events.EventPlayerJoin;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class RandomTp {

	public static Location getRandomLocation(Location player, int max, int min) {
		World world = MelonCore.world;
		int randomX = 0;
		int randomZ = 0;

		int Xmaximum = player.getBlockX() + max;
		int Xminimum = player.getBlockX() - min;

		int Zmaximum = player.getBlockZ() + max;
		int Zminimum = player.getBlockZ() - min;

		double x = 0.0D;
		double z = 0.0D;
		randomX = Xminimum + (int) (Math.random() * (Xmaximum - Xminimum + 1));
		randomZ = Zminimum + (int) (Math.random() * (Zmaximum - Zminimum + 1));
		x = Double.parseDouble(Integer.toString(randomX));
		z = Double.parseDouble(Integer.toString(randomZ));
		x = x + 0.5;
		z = z + 0.5;
		Location n = new Location(world, x, world.getHighestBlockYAt(new Location(world, x, player.getY(), z)), z);
		return n;
	}

	public static Location getRandomLocation(Location player, int max, int min, int y) {
		World world = player.getWorld();
		int randomX = 0;
		int randomZ = 0;

		int Xmaximum = player.getBlockX() + max;
		int Xminimum = player.getBlockX() - min;

		int Zmaximum = player.getBlockZ() + max;
		int Zminimum = player.getBlockZ() - min;

		double x = 0.0D;
		double z = 0.0D;
		randomX = Xminimum + (int) (Math.random() * (Xmaximum - Xminimum + 1));
		randomZ = Zminimum + (int) (Math.random() * (Zmaximum - Zminimum + 1));
		x = Double.parseDouble(Integer.toString(randomX));
		z = Double.parseDouble(Integer.toString(randomZ));
		x = x + 0.5;
		z = z + 0.5;
		Location n = new Location(world, x, y, z);
		return n;
	}

	public static Location getRandomBaseLoc() {
		World world = Bukkit.getWorld("bases");
		Location main = new Location(world, 0, 5, 0);
		int max = 800000;
		int min = 3000;

		int randomX = 0;
		int randomZ = 0;

		int Xmaximum = main.getBlockX() + max;
		int Xminimum = main.getBlockX() - min;

		int Zmaximum = main.getBlockZ() + max;
		int Zminimum = main.getBlockZ() - min;

		double x = 0.0D;
		double z = 0.0D;
		randomX = Xminimum + (int) (Math.random() * (Xmaximum - Xminimum + 1));
		randomZ = Zminimum + (int) (Math.random() * (Zmaximum - Zminimum + 1));
		x = Double.parseDouble(Integer.toString(randomX));
		z = Double.parseDouble(Integer.toString(randomZ));
		x = x + 0.5;
		z = z + 0.5;
		Location n = new Location(world, x, 30, z);
		return n;
	}

	public static Location getRandomSafeLocation(final MelonPlayer p, Location center, int mx, int mi) {

		boolean isSafe = false;
		Location loc = MelonCore.spawn;
		loc.setY(MelonCore.world.getHighestBlockYAt(loc));

		int max = mx;
		int min = mi;

		int counter = 0;

		while (!isSafe) {

			counter++;
			loc = RandomTp.getRandomLocation(center, max, min);
			loc.setY(MelonCore.world.getHighestBlockYAt(loc));

			Msg.debug("TRYING SAFE TP AT X=" + loc.getBlockX() + " Z=" + loc.getBlockZ() + " COUNTER: " + counter,
					EventPlayerJoin.class);

			Block blockUnder = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockY());

			if (loc.getY() > 63 && loc.getY() < 90) {
				Msg.debug("TP BLOCK: " + blockUnder.getType().toString(), EventPlayerJoin.class);

				World world = loc.getWorld();
				WorldBorder b = world.getWorldBorder();

				double maxX = b.getCenter().getX() + (b.getSize() / 2);
				double minX = b.getCenter().getX() - (b.getSize() / 2);

				double maxZ = b.getCenter().getZ() + (b.getSize() / 2);
				double minZ = b.getCenter().getZ() - (b.getSize() / 2);

				if (loc.getX() < maxX && loc.getX() > minX) {

					if (loc.getX() < maxZ && loc.getZ() > minZ) {

						if (blockUnder.getType().equals(Material.GRASS) || blockUnder.getType().equals(Material.SAND) || blockUnder.getType().equals(Material.SANDSTONE)) {
							isSafe = true;

						}

					} else {
						max += 5;
						min += 5;
					}
				} else {
					max += 5;
					min += 5;
				}
			}

			if (counter >= 200) {
				isSafe = true;
				loc = MelonCore.spawn;
				loc.setY(MelonCore.world.getHighestBlockYAt(loc));
				p.sendMessage(MelonCore.prefix + ChatColor.RED
						+ "Failed to find valid spawn point, you have been returned to the start point.");
				Msg.debug("FAILED TO FIND SAFE SPACE", EventPlayerJoin.class);

			}

		}

		Msg.debug("FINISHED SAFE TP COUNTER: " + counter, EventPlayerJoin.class);

		return loc;

	}

}
