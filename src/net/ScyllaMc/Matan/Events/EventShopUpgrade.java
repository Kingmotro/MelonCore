package net.ScyllaMc.Matan.Events;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.Mcbender.Matan.MelonCore.ParticleEffect;
import net.Mcbender.Matan.MelonCore.UpgradeInfo;
import net.Mcbender.Matan.MelonCore.UpgradeInv;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.MelonCore.ServerType;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class EventShopUpgrade implements Listener {

	public static ParticleEffect Effect = new ParticleEffect(
			net.Mcbender.Matan.MelonCore.ParticleEffect.ParticleType.CLOUD, 0.02, 5, 0.0001);

	@EventHandler
	public void PlayerRightClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();

		if (entity instanceof Villager) {
			event.setCancelled(true);
			player.sendMessage(
					MelonCore.prefix + ChatColor.RED + "Villager trading is disabled, to trade use /market");
			return;
		}

		if (entity.getCustomName() == null) {
			return;
		}
		if (!(entity instanceof Player)) {
			return;
		}
		if (!entity.getCustomName().contains(ChatColor.BOLD.toString())) {
			return;
		}

		if (entity.getCustomName().equalsIgnoreCase(ChatColor.GREEN + ChatColor.BOLD.toString() + "Upgrade Shop")) {
			event.setCancelled(true);
			UpgradeInv.CreateManu(MelonPlayer.getInstanceOfPlayer(player), player);
		}

	}

	public static void MorganUpgrade(Player p) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);

		p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);

		final Location l = new Location(p.getWorld(), 13, 9, 2);
		partical(l);

		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {
				partical(l);
			}
		}, 20l);

		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {
				partical(l);
			}
		}, 40l);

		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {
				partical(l);
			}
		}, 60l);
	}

	public static void partical(final Location loc) {
		Location l = loc;
		FireworkEffect.builder().flicker(false).withColor(Color.GREEN).withFade(Color.GRAY).with(Type.BURST).trail(true)
				.build();
		Firework fw = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		new Random();
		Type type1 = Type.BURST;
		FireworkEffect effect1 = FireworkEffect.builder().flicker(false).withColor(Color.GREEN).withFade(Color.GRAY)
				.with(type1).trail(true).build();
		fwm.addEffect(effect1);
		fwm.setPower(0);
		fw.setFireworkMeta(fwm);

		new BukkitRunnable() {
			double t = 0;
			double r = 0.5;

			@Override
			public void run() {
				t = t + Math.PI / 8;
				double x = r * Math.cos(t);
				double y = t;
				double z = r * Math.sin(t);
				loc.add(x, y, z);
				Effect.sendToLocation(loc);
				loc.subtract(x, y, z);
				if (t > Math.PI * 4) {
					this.cancel();
				}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0, 1);
	}

	String prefix = MelonCore.prefix;

	@EventHandler
	public void PlayerUpgradeAttack(InventoryClickEvent e) {
		ItemStack clicked = e.getCurrentItem();
		Inventory inventory = e.getInventory();
		if (!e.getSlotType().equals(SlotType.CONTAINER)) {
			return;
		}
		if (inventory.getName().equals("Morgan's Upgrades Shop")) {
			Player p2 = (Player) e.getWhoClicked();
			MelonPlayer p = MelonPlayer.getInstanceOfPlayer(p2);
			e.setCancelled(true);
			Bukkit.getPluginManager().getPlugin("MelonCore").reloadConfig();

			if (clicked.getItemMeta().getDisplayName().contains("Click to unlock")) {
				int nextlevel = p.damagelevel + 1;
				int price = UpgradeInfo.getDamageUpgradePrice(nextlevel);

				if (p.coins >= price) {
					p.sendMessage(prefix + ChatColor.GREEN.toString()
							+ " You have Upgraded your Attack Damage to level " + nextlevel + "!");
					p.addCoins(-price, false);
					
					p.setDamageLevel(nextlevel);
					
					p2.closeInventory();
					MorganUpgrade(p2);
					
					return;
				} else {
					p.sendMessage(prefix + ChatColor.RED.toString() + " You do not have enough coins to unlock this!");
				}
			}
		}
	}

}
