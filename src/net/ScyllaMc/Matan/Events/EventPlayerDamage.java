package net.ScyllaMc.Matan.Events;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.MelonCore.ServerType;
import net.ScyllaMc.Matan.MelonPlayer.Duel;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class EventPlayerDamage implements Listener {

	@EventHandler
	public void Damage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer((OfflinePlayer) e.getEntity());

		if (p.inGodmode) {
			e.setCancelled(true);
			return;
		}
		if (e.getCause().equals(DamageCause.FALL)) {
			e.setDamage(e.getDamage() / 8);
		}

		double dam = e.getDamage();
		double finaldam = dam - (dam * ((double) p.getDefenceBonus() / 100));

		e.setDamage(finaldam);

	}

	@EventHandler
	public void Damaged(EntityDamageByEntityEvent e) {

		if (e.getDamager() instanceof Player) {

			if (e.getEntity() instanceof Player && MelonPlayer.getInstanceOfPlayer((Player) e.getEntity()).hidden) {
				e.setCancelled(true);
				((Player) e.getEntity()).teleport(getBlockDirectlyBehindPlayer((Player) e.getDamager()).getLocation());
				return;
			}

			if ((e.getDamager() instanceof Player) && !(e.getEntity() instanceof Player)) {
				final MelonPlayer damager = MelonPlayer.getInstanceOfPlayer((Player) e.getDamager());
				double d = e.getDamage() + e.getDamage() * damager.getDamageBonus();
				e.setDamage(d);
				return;
			}

			if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) {
				return;
			}

			
			final MelonPlayer p = MelonPlayer.getInstanceOfPlayer((Player) e.getEntity());
			final MelonPlayer damager = MelonPlayer.getInstanceOfPlayer((Player) e.getDamager());

			if (damager.inGodmode) {
				damager.sendMessage(MelonCore.prefix + ChatColor.RED + "You cannot fight other players while you are in god mode.");
				e.setCancelled(true);
				return;
			}

			if (!Duel.canHurt(damager, p)) {

				damager.sendMessage(MelonCore.prefix + ChatColor.RED + "To fight a player use /duel (player)");
				e.setCancelled(true);

			} else {
				
				return;
			}
		}

	}

	Block getBlockDirectlyBehindPlayer(Player player) {

		String playerDirection;
		float yaw = player.getLocation().getYaw();
		World w = player.getWorld();
		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();

		if (yaw < 0) {
			yaw = yaw + 360;
		}

		if ((yaw >= 315) && (yaw <= 360)) {
			playerDirection = "south";
		} else if ((yaw >= 0) && (yaw <= 45)) {
			playerDirection = "south";
		} else if ((yaw >= 45) && (yaw <= 135)) {
			playerDirection = "west";
		} else if ((yaw >= 135) && (yaw <= 180)) {
			playerDirection = "north";
		} else if ((yaw >= 180) && (yaw <= 225)) {
			playerDirection = "north";
		} else if ((yaw >= 225) && (yaw <= 315)) {
			playerDirection = "east";
		} else {
			playerDirection = "east";
		}

		if (playerDirection == "north") {
			z = z + 1;
		} else if (playerDirection == "east") {
			x = x - 1;
		} else if (playerDirection == "south") {
			z = z - 1;
		} else if (playerDirection == "west") {
			x = x + 1;
		}

		Block b = w.getBlockAt(x, y, z);

		return b;

	}

}
