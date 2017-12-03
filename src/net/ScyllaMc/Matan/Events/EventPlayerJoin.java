package net.ScyllaMc.Matan.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.MelonCore.ServerType;
import net.ScyllaMc.Matan.MelonPlayer.Levels;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Mobs.Mob;
import net.ScyllaMc.Matan.Util.RandomTp;
import net.ScyllaMc.Matan.Util.Title;

public class EventPlayerJoin implements Listener {

	@SuppressWarnings({ "static-access", "unused" })
	@EventHandler
	public void PlayerJoinEvent(final PlayerJoinEvent event) {
		final MelonPlayer p = MelonPlayer.getInstanceOfPlayer(event.getPlayer());
		new Title().sendTitle(p, 21, 21, 21, "&cDownloading stats...!", "");

		p.getOnlinePlayer().setGameMode(GameMode.SURVIVAL);

		p.getOnlinePlayer().getInventory().clear();
		p.getOnlinePlayer().setHealthScaled(true);
		p.getOnlinePlayer().setHealthScale(40);
		Player player = p.getOnlinePlayer();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		event.setJoinMessage(null);
		
		Boolean first = false;

		if (!p.getOnlinePlayer().hasPlayedBefore()) {
			first = true;
			Bukkit.getServer().broadcastMessage(MelonCore.prefix + ChatColor.AQUA + p.getName() + ChatColor.DARK_AQUA
					+ " Has Joined for the first time!");
			p.getOnlinePlayer().getWorld().setTime(0);

			for (Player ap : Bukkit.getOnlinePlayers()) {
				new Title().sendTitle(MelonPlayer.getInstanceOfPlayer(ap), 20, 50, 50, "",
						ChatColor.AQUA + p.getName() + ChatColor.DARK_AQUA + " Has Joined for the first time!");

			}

			if (MelonCore.server.equals(ServerType.BEND)) {
				firstJoin(p);
			}

		}

		
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {
				p.dowloadData();

			}
		}, 2L);

		new BukkitRunnable() {

			int counter = 0;

			@Override
			public void run() {

				if(!p.isOnline()){
					this.cancel();
					counter = 0;
					return;
				}
				
				if (counter > 250) {

					p.sendMessage("Downloading timed out");
					this.cancel();
					counter = 0;
					return;
				}

				if (p.dowloaded) {

					this.cancel();

					Bukkit.broadcastMessage(MelonCore.prefix + p.rank.getTagClosed() + p.getName()
							+ ChatColor.DARK_GREEN + " Joined the server!");

					double addhearts = Levels.getAddHearts(p.level);

					double hearts = 40 + addhearts;

					p.getOnlinePlayer().setHealthScale(hearts);
					p.getOnlinePlayer().setHealthScaled(true);
					p.loggedIn = true;
					new Title().sendTitle(p, 21, 21, 21, "&aFinished dowloading.", "&2have fun!");

					if (!p.getOnlinePlayer().hasPlayedBefore() && MelonCore.server.equals(ServerType.BEND)) {
						survivalKit(p.getOnlinePlayer().getInventory());
					}

					for (Player ap : Bukkit.getOnlinePlayers()) {
						new Title().sendTitle(MelonPlayer.getInstanceOfPlayer(ap), 20, 50, 50, "",
								p.rank.getTagClosed() + p.getName() + ChatColor.DARK_GREEN + " Joined the server!");
					}

					return;

				} else {
					counter++;
				}

			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0, 1);

	}

	

	public static void firstJoin(final MelonPlayer p) {

		p.teleport(MelonCore.spawn);
		Title.sendTitle(p, 61, 150, 150, "&aWelcome to ScyllaMc", "&2The Disneyland of minecraft!");

		new BukkitRunnable() {
			int l = 0;

			public void run() {

				if(l == 7){Title.sendTitle(p, 61, 150, 150, "&7Use &d/autobind", "&aTo quickly bind moves");}
				
				if(l == 14){Title.sendTitle(p, 61, 150, 150, "&7Use &d/guide", "&aTo get information");}

				if(l == 21){Title.sendTitle(p, 61, 150, 150, "&7Kill mobs to earn &dxp", "&aEarn xp to level up");}


				l++;
				
				
				if(l > 30){
					this.cancel();
					return;
				}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0, 20);

	}

	public static void survivalKit(Inventory inv) {

		inv.addItem(new ItemStack(Material.IRON_SWORD, 1));
		inv.addItem(new ItemStack(Material.STONE_PICKAXE, 1));
		inv.addItem(new ItemStack(Material.STONE_AXE, 1));

		ItemStack claim = new ItemStack(Material.GOLD_SPADE, 1);
		ItemMeta meta = claim.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Land claming tool");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "---------");
		lore.add(ChatColor.DARK_AQUA + "How to use: ");
		lore.add(ChatColor.DARK_AQUA + "To claim land just right click with");
		lore.add(ChatColor.DARK_AQUA + "this shovel in the two corners of the");
		lore.add(ChatColor.DARK_AQUA + "land section you want to claim.");
		lore.add(ChatColor.DARK_AQUA + "golden blocks and light blocks will");
		lore.add(ChatColor.DARK_AQUA + "be temperorly placed to show you your");
		lore.add(ChatColor.DARK_AQUA + "land! to add playes to your claim");
		lore.add(ChatColor.DARK_AQUA + "use the /trust (player) command");
		meta.setLore(lore);
		claim.setItemMeta(meta);

		inv.addItem(new ItemStack(Material.BREAD, 32));
		inv.addItem(new ItemStack(Material.WOOD, 64));
		inv.addItem(claim);

	}

}
