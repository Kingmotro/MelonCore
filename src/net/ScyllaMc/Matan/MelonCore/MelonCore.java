package net.ScyllaMc.Matan.MelonCore;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.Scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import net.ScyllaMc.Matan.BendingClass.Korra;
import net.ScyllaMc.Matan.Clans.Clan;
import net.ScyllaMc.Matan.Clans.Clan_Command;
import net.ScyllaMc.Matan.Commands.AdminCommands;
import net.ScyllaMc.Matan.Commands.CommandManager;
import net.ScyllaMc.Matan.Commands.DonatorCommands;
import net.ScyllaMc.Matan.Events.EventBlockSign;
import net.ScyllaMc.Matan.Events.EventEntityClick;
import net.ScyllaMc.Matan.Events.EventEntityMount;
import net.ScyllaMc.Matan.Events.EventEntityNPC;
import net.ScyllaMc.Matan.Events.EventEntitySunlightDamage;
import net.ScyllaMc.Matan.Events.EventMerchentInventory;
import net.ScyllaMc.Matan.Events.EventPlayerAnvil;
import net.ScyllaMc.Matan.Events.EventPlayerChat;
import net.ScyllaMc.Matan.Events.EventPlayerDamage;
import net.ScyllaMc.Matan.Events.EventPlayerDeath;
import net.ScyllaMc.Matan.Events.EventPlayerInteract;
import net.ScyllaMc.Matan.Events.EventPlayerInventory;
import net.ScyllaMc.Matan.Events.EventPlayerJoin;
import net.ScyllaMc.Matan.Events.EventPlayerLeave;
import net.ScyllaMc.Matan.Events.EventPlayerPortal;
import net.ScyllaMc.Matan.Events.EventShopUpgrade;
import net.ScyllaMc.Matan.Items.EventEnchantInventory;
import net.ScyllaMc.Matan.Items.Item;
import net.ScyllaMc.Matan.MelonPlayer.LeaderboardMaker;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.MelonPlayer.MelonScoreboard;
import net.ScyllaMc.Matan.Mobs.EventEntityDeath;
import net.ScyllaMc.Matan.Mobs.EventEntitySpawn;
import net.ScyllaMc.Matan.Mobs.EventEntityTarget;
import net.ScyllaMc.Matan.Mobs.Mob;
import net.ScyllaMc.Matan.Mobs.Mob.MobTypes;
import net.ScyllaMc.Matan.Mobs.Mount;
import net.ScyllaMc.Matan.Mobs.Spawner;
import net.ScyllaMc.Matan.Mobs.Bending.AbilityRunnable;
import net.ScyllaMc.Matan.Mobs.Bending.EventAbility;
import net.ScyllaMc.Matan.NPC.NPCManager;
import net.ScyllaMc.Matan.Rank.PermissionManager;
import net.ScyllaMc.Matan.Rank.loadPerms;
import net.ScyllaMc.Matan.Vote.EventClickVoteChest;
import net.ScyllaMc.Matan.Vote.MysteryItems;
import net.ScyllaMc.Matan.Vote.VoteChest;
import net.ScyllaMc.Matan.Vote.VoteListener;
import net.minecraft.server.v1_8_R3.DataWatcher.WatchableObject;

public class MelonCore extends JavaPlugin implements Listener {

	public static String name = ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "[!]";
	public static String prefix = name + ChatColor.GRAY + " ";
	public static boolean Shuttingdown = false;

	public static enum ServerType {
		BEND, RPG;
	}

	public static ServerType server;

	public static Mysql Mysql;
	public static VoteChest VoteChest;
	public static MelonScoreboard MelonScoreboard;
	public static NPCManager NPCManager;
	public static PermissionManager PermissionManager;

	public static World world;

	public static Location spawn;
	public static Location firstspawn;
	public static Location voteloc;
	public static Location votelocholo;
	public static Location table;
	public static Location portal;

	public static Scoreboard mainScoreboard;
	public static java.sql.Connection connection;

	public static String firstLetterCaps(String data) {
		String firstLetter = data.substring(0, 1).toUpperCase();
		String restLetters = data.substring(1).toLowerCase();
		return firstLetter + restLetters;
	}

	public static WorldGuardPlugin getWorldGaurd() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		return (WorldGuardPlugin) plugin;
	}

	@Override
	public void onEnable() {

		world = Bukkit.getWorlds().get(0);

		if (world.getName().equalsIgnoreCase("lobby")) {
			//server = ServerType.HUB;
		} else if (world.getName().equalsIgnoreCase("rpg")) {
			server = ServerType.RPG;
		} else {
			server = ServerType.BEND;
		}

		Mysql = new Mysql();
		VoteChest = new VoteChest();
		MelonScoreboard = new MelonScoreboard();
		NPCManager = new NPCManager();
		PermissionManager = new PermissionManager();
		loadPerms.loadRankPerms();
		MysteryItems.loadAll();

		Mysql.openConnection();

		mainScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		mainScoreboard.registerNewTeam("PLACEHOLDER");

		getServer().getScheduler().scheduleSyncRepeatingTask(this, new AbilityRunnable(), 0L, 1L);

		Bukkit.getPluginManager().registerEvents(new EventPlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerLeave(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerChat(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerAnvil(), this);
		Bukkit.getPluginManager().registerEvents(new EventEntityDeath(), this);
		Bukkit.getPluginManager().registerEvents(new EventEntityMount(), this);
		Bukkit.getPluginManager().registerEvents(new EventMerchentInventory(), this);
		Bukkit.getPluginManager().registerEvents(new VoteListener(), this);
		Bukkit.getPluginManager().registerEvents(new EventBlockSign(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerDamage(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerInteract(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerInventory(), this);
		Bukkit.getPluginManager().registerEvents(new EventEntitySpawn(), this);
		Bukkit.getPluginManager().registerEvents(new EventAbility(), this);
		Bukkit.getPluginManager().registerEvents(new EventEnchantInventory(), this);
		Bukkit.getPluginManager().registerEvents(new net.ScyllaMc.Matan.Mobs.EntityEvents(), this);
		Bukkit.getPluginManager().registerEvents(new EventShopUpgrade(), this);
		Bukkit.getPluginManager().registerEvents(new EventEntityNPC(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerDeath(), this);
		Bukkit.getPluginManager().registerEvents(new EventEntityTarget(), this);
		Bukkit.getPluginManager().registerEvents(new EventEntityClick(), this);
		Bukkit.getPluginManager().registerEvents(new EventEntitySunlightDamage(), this);
		Bukkit.getPluginManager().registerEvents(new EventClickVoteChest(), this);
		Bukkit.getPluginManager().registerEvents(new EventPlayerPortal(), this);

		getCommand("clan").setExecutor(new Clan_Command());

		getCommand("speed").setExecutor(new CommandManager());
		getCommand("gamemode").setExecutor(new CommandManager());
		getCommand("heal").setExecutor(new CommandManager());
		getCommand("god").setExecutor(new CommandManager());
		getCommand("coins").setExecutor(new CommandManager());
		getCommand("stats").setExecutor(new CommandManager());
		getCommand("give").setExecutor(new CommandManager());
		getCommand("clear").setExecutor(new CommandManager());
		getCommand("teleport").setExecutor(new CommandManager());
		getCommand("itemdb").setExecutor(new CommandManager());
		getCommand("hide").setExecutor(new CommandManager());
		getCommand("fly").setExecutor(new CommandManager());
		getCommand("battle").setExecutor(new CommandManager());
		getCommand("invsee").setExecutor(new CommandManager());
		getCommand("nick").setExecutor(new CommandManager());
		getCommand("tpa").setExecutor(new CommandManager());
		getCommand("home").setExecutor(new CommandManager());
		getCommand("repair").setExecutor(new CommandManager());
		getCommand("rename").setExecutor(new CommandManager());
		getCommand("autobind").setExecutor(new CommandManager());
		getCommand("market").setExecutor(new CommandManager());
		getCommand("plugins").setExecutor(new CommandManager());
		getCommand("shutdown").setExecutor(new CommandManager());
		getCommand("language").setExecutor(new CommandManager());
		getCommand("lobby").setExecutor(new CommandManager());

		Korra.registerAllDefaults();

		if (MelonCore.server.equals(ServerType.BEND)) {

			spawn = new Location(world, 1182, 99, 1671);
			firstspawn = spawn;

			voteloc = new Location(world, 1186, 100, 1670);
			votelocholo = new Location(world, 1186, 101.7, 1670);

			portal = new Location(world, 1181, 94, 1689);

			table = new Location(world, 2357, 65, 733);

			//Location lead =  new Location(world, 1187, 101, 1673);
			//new LeaderboardMaker(lead, "kills", BlockFace.WEST, ChatColor.RED, 10);

			//Spawner(EntityType e, Location location, int interval, int level, MobTypes mtype, int limit, int rad_min, int rad_max)
			//Location s1 =  new Location(world, 1182, 99, 1671);
			//new Spawner(EntityType.SHEEP, s1, 1200 ,  2, MobTypes.SHEEP, 7, 1, 4);
		}

		/**
		 * if (MelonCore.server.equals(ServerType.HUB)) {
		 * 
		 * world.setDifficulty(Difficulty.EASY);
		 * 
		 * spawn = new Location(world, 220, 112, 1353, (float) -90, (float) -9);
		 * firstspawn = spawn;
		 * 
		 * voteloc = new Location(world, 238, 114, 1337); votelocholo = new
		 * Location(world, 238.5, 115.7, 1337);
		 * 
		 * table = new Location(world, 238, 114, 1369);
		 * 
		 * }
		 * 
		 * if (server.equals(ServerType.HUB)) {
		 * 
		 * loadAllChunks(world); for (Entity e : world.getEntities()) { if ((e
		 * instanceof LivingEntity)) { e.remove(); } }
		 * 
		 * // NPCRegister nms = new NPCRegister(); //
		 * nms.registerEntity("Witch", 66, EntityWitch.class, //
		 * NPCWitch.class); // nms.registerEntity("Villager", 120,
		 * EntityVillager.class, // NPCVillager.class);
		 * 
		 * }
		 **/

		try {
			NPCManager npc = new NPCManager();
			npc.registerAll();
		} catch (IOException e) {
			((IOException) e).printStackTrace();
		}

		getCommand("addcoins").setExecutor(new AdminCommands());
		getCommand("setrank").setExecutor(new AdminCommands());
		getCommand("test").setExecutor(new AdminCommands());
		getCommand("vote").setExecutor(new CommandManager());
		getCommand("help").setExecutor(new CommandManager());
		getCommand("spawn").setExecutor(new CommandManager());
		getCommand("help").setExecutor(new CommandManager());
		getCommand("buy").setExecutor(new CommandManager());
		getCommand("site").setExecutor(new CommandManager());
		getCommand("rules").setExecutor(new CommandManager());
		getCommand("avatar").setExecutor(new DonatorCommands());
		getCommand("setavatar").setExecutor(new DonatorCommands());

		for (Player p : Bukkit.getOnlinePlayers()) {
			MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(p);
			mp.dowloadData();
			VoteChest.display(mp);
		}

		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers()) {
					MelonCore.MelonScoreboard.showActionBar(MelonPlayer.getInstanceOfPlayer(p));
				}

				world.setWeatherDuration(0);
				world.setThunderDuration(0);
				world.setStorm(false);
				world.setThundering(false);

			}
		}, 0, 10l);

		Tasks.runAll();

		if (server.equals(ServerType.RPG)) {

			ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Server.SET_SLOT) {
				@Override

				public void onPacketSending(PacketEvent event) {
					Msg.debug("SET SLOT PACKET SENT", this.getClass());

					Player p = event.getPlayer();
					if (p.getGameMode().equals(GameMode.CREATIVE)) {
						return;
					}

					MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(p);
					PacketContainer packet = event.getPacket().deepClone();
					StructureModifier<ItemStack> sm = packet.getItemModifier();

					for (int j = 0; j < sm.size(); j++) {
						if (sm.getValues().get(j) != null) {
							ItemStack item = sm.getValues().get(j);
							ItemMeta itemMeta = item.getItemMeta();

							if (itemMeta != null && itemMeta.hasDisplayName()) {

								if (Item.isItem(item)) {
									Item i = Item.fromItemStack(item);
									itemMeta.setDisplayName(i.getName(mp));
									item.setItemMeta(itemMeta);
								}
							}

						}
					}
					event.setPacket(packet);
				}
			});

			ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Server.WINDOW_ITEMS) {

				@Override
				public void onPacketSending(PacketEvent event) {
					Msg.debug("WINDOT ITEMS PACKET SENT", this.getClass());

					Player p = event.getPlayer();
					if (p.getGameMode().equals(GameMode.CREATIVE)) {
						return;
					}

					MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(p);
					PacketContainer packet = event.getPacket().deepClone();
					StructureModifier<ItemStack[]> sm = packet.getItemArrayModifier();

					for (int j = 0; j < sm.size(); j++) {

						ItemStack[] itema = sm.getValues().get(j);
						for (int i = 0; i < itema.length; i++) {
							if (itema[i] != null && itema[i].getItemMeta() != null && !itema[i].getType().equals(Material.AIR)) {
								ItemStack item = itema[i];
								ItemMeta itemMeta = item.getItemMeta();

								if (itemMeta != null && itemMeta.hasDisplayName()) {

									if (Item.isItem(item)) {
										Item itm = Item.fromItemStack(item);
										itemMeta.setDisplayName(itm.getName(mp));
										item.setItemMeta(itemMeta);
									}
								}
							}
						}

						event.setPacket(packet);
					}
				}
			});
		}

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
			@Override
			public void onPacketSending(PacketEvent event) {
				String soundName = event.getPacket().getStrings().read(0);

				if (soundName.startsWith("mob.zombie.hurt")) {
					event.getPacket().getStrings().write(0, "game.player.hurt");
				}
				if (soundName.startsWith("mob.zombie.death")) {
					event.getPacket().getStrings().write(0, "game.player.die");
				}
				if (soundName.startsWith("mob.pig.say")) {
					event.getPacket().getStrings().write(0, "mob.wolf.growl");
				}
				if (soundName.startsWith("mob.zombie.say")) {
					event.setCancelled(true);
				}

			}
		});

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {
			@Override
			public void onPacketSending(PacketEvent event) {
				PacketContainer packet = event.getPacket().deepClone();

				try {
					@SuppressWarnings("unchecked")
					List<WatchableObject> list = (List<WatchableObject>) packet.getModifier().getValues().get(1);

					WatchableObject old = null;

					int loc = -1;

					for (int r = 0; r < list.size(); r++) {

						if (list.get(r).b() instanceof String) {
							old = list.get(r);
							loc = r;
						}

					}

					if (old != null) {
						if (old.b() instanceof String) {

							Mob m = Mob.fromJson((String) old.b());

							if (m != null) {

								int c = old.c();
								int a = old.a();
								String name = m.getName(MelonPlayer.getInstanceOfPlayer(event.getPlayer()).lan);

								WatchableObject wo = new WatchableObject(c, a, name);

								list.remove(loc);

								if (list.size() > loc) {
									list.set(loc, wo);
								} else {
									list.add(wo);
								}
							}
						}
					}

					packet.getModifier().write(1, list);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}

				event.setPacket(packet);

			}
		});

	}

	@Override
	public void onDisable() {

		Shuttingdown = true;

		for (Mount m : Mount.mounts.keySet()) {
			m.despawn();
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(p);

			if (mp.hidden) {
				mp.hidden = false;
				for (Player p2 : Bukkit.getOnlinePlayers()) {
					p2.showPlayer(mp.getOnlinePlayer());
				}
				mp.sendMessage(prefix + ChatColor.RED + " A restart has forced you to become visible");
			}

		}

		Mysql.closeConnection();

		for (Hologram hologram : HologramsAPI.getHolograms(Bukkit.getPluginManager().getPlugin("MelonCore"))) {
			hologram.delete();
		}

	}

	@SuppressWarnings("static-access")
	public void setOnDisableStringValue(final MelonPlayer p, String data, String s, Connection connection) {
		String qry = "UPDATE `network_player_data` SET " + data + "= '" + s + "' WHERE player= '" + p.getUniqueId().toString() + "';";
		try {
			Statement st = Mysql.connection.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void shutDown() {
		Bukkit.broadcastMessage(prefix + ChatColor.GRAY + "--------------------------");
		Bukkit.broadcastMessage(prefix + ChatColor.RED + "Restart in 1 minute");
		Bukkit.broadcastMessage(prefix + ChatColor.GRAY + "--------------------------");

		/**
		 * Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"),
		 * new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 *           for (Player p : Bukkit.getOnlinePlayers()) { MelonPlayer mp
		 *           = MelonPlayer.getInstanceOfPlayer(p); if
		 *           (server.equals(ServerType.HUB)) { mp.sendMessage(prefix +
		 *           ChatColor.RED + "You were moved to bending due to a
		 *           restart"); BungeeUtils.Kick(p, "survival"); } else {
		 *           mp.sendMessage(prefix + ChatColor.RED + "You were moved to
		 *           the lobby due to a restart"); BungeeUtils.Kick(p, "lobby");
		 *           } }
		 * 
		 *           } }, 1000l);
		 **/
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean isJSONValid(String s, Class c) {
		Gson gson = new Gson();

		try {
			gson.fromJson(s, c);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private static void loadAllChunks(World world) {
		final Pattern regionPattern = Pattern.compile("r\\.([0-9-]+)\\.([0-9-]+)\\.mca");

		File worldDir = new File(Bukkit.getWorldContainer(), world.getName());
		File regionDir = new File(worldDir, "region");

		File[] regionFiles = regionDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return regionPattern.matcher(name).matches();
			}
		});
		File[] arrayOfFile1;
		int j = (arrayOfFile1 = regionFiles).length;
		for (int i = 0; i < j; i++) {
			File f = arrayOfFile1[i];

			Matcher matcher = regionPattern.matcher(f.getName());
			if (matcher.matches()) {
				int mcaX = Integer.parseInt(matcher.group(1));
				int mcaZ = Integer.parseInt(matcher.group(2));

				int loadedCount = 0;
				for (int cx = 0; cx < 32; cx++) {
					for (int cz = 0; cz < 32; cz++) {
						boolean didLoad = world.loadChunk((mcaX << 5) + cx, (mcaZ << 5) + cz, false);
						if (didLoad) {
							loadedCount++;
						}
					}
				}
				Bukkit.getLogger().info("Actually loaded " + loadedCount + " from " + f.getName() + ".");
			}
		}
	}

}