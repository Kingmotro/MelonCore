package net.ScyllaMc.Matan.Commands;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandHome {

	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		MelonPlayer p = MelonPlayer.getInstanceOfPlayer((Player) sender);

		if (args.length == 0) {

			p.sendMessage(MelonCore.prefix + ChatColor.DARK_AQUA + "Home Commands:");
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + "/Home List " + ChatColor.GRAY
					+ "- Shows you a list of all your homes");
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + "/Home tp (Name)" + ChatColor.GRAY
					+ "- Teleports to your home");
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + "/Home set (Name)" + ChatColor.GRAY
					+ "- Adds a new home");
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + "/Home delete (Name)" + ChatColor.GRAY
					+ "- Deletes a home");
			return;
		}

		if (args[0].equalsIgnoreCase("list")) {

			if (p.homes.size() > 0) {
				p.sendMessage(MelonCore.prefix + ChatColor.DARK_AQUA + "Homes:");
				int n = 1;
				for (Object o : p.homes.keySet()) {
					p.sendMessage(MelonCore.prefix + ChatColor.DARK_AQUA + n + ". " + o.toString() + " : "
							+ p.homes.get(o).toString());
					n++;

				}

			} else {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have any homes saved.");
			}

			return;
		}

		if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
			String name = args[1].toLowerCase();
			if (p.getLocation().getWorld() != MelonCore.world) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You cannot set a home in this world.");
				return;
			}
			if (getHomeNumber(p) >= maxHomes(p)) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You have reached your maximun home limit of: "
						+ maxHomes(p));
				return;
			}
			if (p.homes.containsKey(name)) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You already have a home that is named " + name
						+ ", please delete it to create a new one.");
				return;
			}
			// Claim claim =
			// GriefPrevention.instance.dataStore.getClaimAt(p.getLocation(),
			// true, null);
			addHome(p, name, p.getLocation());
			p.sendMessage(MelonCore.prefix + "A new home named '" + name + "' has been set!");
			return;
		}

		if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
			String name = args[1].toLowerCase();

			if (!p.homes.containsKey(name)) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have a home that is named " + name + ".");
				return;
			}
			removeHome(p, name);
			p.sendMessage(MelonCore.prefix + "You have deleted the home: '" + name + "'.");

			return;
		}

		if (args.length == 2 && args[0].equalsIgnoreCase("tp")) {
			String name = args[1].toLowerCase();

			if (!p.homes.containsKey(name)) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have a home named " + name + ".");
				return;
			}

			p.getOnlinePlayer().teleport(locFromString(p.homes.get(name).toString()));
			return;
		}

		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);
		return;

	}

	public int getHomeNumber(MelonPlayer p) {

		if (p.homes != null) {
			return p.homes.size();
		}

		return 0;
	}

	public int maxHomes(MelonPlayer p) {
		int max = 1;

		if (p.hasPermission("server.gold")) {
			max = 2;
		}
		if (p.hasPermission("server.hero")) {
			max = 4;
		}
		if (p.hasPermission("server.master")) {
			max = 6;
		}
		if (p.hasPermission("server.champion")) {
			max = 99999;
		}
		if (p.hasPermission("server.mod")) {
			max = 6;
		}

		return max;

	}

	
	@SuppressWarnings("unchecked")
	public void addHome(MelonPlayer p, String name, Location loc) {

		HashMap<String, String> data = new HashMap<String, String>();
		if (p.homes != null) {
			data = (HashMap<String, String>) p.homes;
			data.put(name.toLowerCase(), locToString(loc));
		}

		p.homes.putAll(data);
		p.saveData();
	}

	public void removeHome(MelonPlayer p, String name) {

		if (p.homes != null) {

			if (p.homes.containsKey(name.toLowerCase())) {
				p.homes.remove(name.toLowerCase());
			}
		}

		p.saveData();
	}

	public String locToString(Location l) {
		String s = l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + "," + l.getPitch() + "," + l.getYaw();
		return s;
	}

	public Location locFromString(String locs) {
		Location l = MelonCore.spawn;

		String[] s = locs.split(",");
		double x = Double.parseDouble(s[0]);
		double y = (Double.parseDouble(s[1]));
		double z = (Double.parseDouble(s[2]));
		float pitch = ((float) Double.parseDouble(s[3]));
		float yaw = (float) Double.parseDouble(s[4]);
		l = new Location(MelonCore.world, x, y, z, pitch, yaw);
		return l;
	}

}
