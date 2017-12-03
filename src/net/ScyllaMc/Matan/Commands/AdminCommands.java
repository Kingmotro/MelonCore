package net.ScyllaMc.Matan.Commands;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.json.simple.JSONObject;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.Mcbender.Matan.MelonCore.UpgradeInv;
import net.ScyllaMc.Matan.Events.EventPlayerJoin;
import net.ScyllaMc.Matan.Items.Enchantments;
import net.ScyllaMc.Matan.Items.Item;
import net.ScyllaMc.Matan.Items.Item.Modifier;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.Buff;
import net.ScyllaMc.Matan.MelonPlayer.Buff.BuffType;
import net.ScyllaMc.Matan.MelonPlayer.LeaderboardMaker;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Mobs.Mount;
import net.ScyllaMc.Matan.Mobs.MountItem;
import net.ScyllaMc.Matan.NPC.NPC;
import net.ScyllaMc.Matan.NPC.NPC.NPCType;
import net.ScyllaMc.Matan.Rank.Rank;

public class AdminCommands implements CommandExecutor {
	net.ScyllaMc.Matan.MelonCore.Mysql Mysql = MelonCore.Mysql;

	@Override
	@SuppressWarnings({ "deprecation", "unused", "unchecked" })
	public boolean onCommand(CommandSender sender, Command command, String alias, final String[] args) {

		if (sender instanceof Player) {
			MelonPlayer p = MelonPlayer.getInstanceOfPlayer(((Player) sender));
			if (!p.rank.equals(Rank.ADMIN) && !p.rank.equals(Rank.OWNER)) {

				sender.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have the permission to use any admin commands.");
				return true;
			}

		}

		if (command.getName().equalsIgnoreCase("setrank")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("melon.*")) {

					Mysql.setStringValue(MelonPlayer.getInstanceOfPlayer(Bukkit.getOfflinePlayer(args[0])), "rank", args[1]);
					Bukkit.broadcastMessage(MelonCore.prefix + ChatColor.LIGHT_PURPLE + args[0] + "'s rank has been set to " + ChatColor.LIGHT_PURPLE + args[1]);
				} else {
					sender.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have the permission to change a player's rank");
				}
			} else {
				Mysql.setStringValue(MelonPlayer.getInstanceOfPlayer(Bukkit.getOfflinePlayer(args[0])), "rank", args[1]);
				Bukkit.broadcastMessage(MelonCore.prefix + ChatColor.LIGHT_PURPLE + args[0] + "'s rank has been set to " + ChatColor.LIGHT_PURPLE + args[1]);
			}
		}

		if (command.getName().equalsIgnoreCase("addcoins")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("melon.*")) {
					MelonPlayer sent = MelonPlayer.getInstanceOfPlayer(Bukkit.getOfflinePlayer(args[0]));
					p.sendMessage(MelonCore.prefix + ChatColor.GREEN + " You have added " + args[1] + " to " + args[0]);
					sent.addCoins(Integer.parseInt(args[1]), false);
					sent.sendMessage(MelonCore.prefix + args[1] + " coins have been added to your account!");
				} else {
					sender.sendMessage(MelonCore.prefix + ChatColor.RED + "You DO NOT have the permission to add coins!");
					return true;
				}

			} else {
				MelonPlayer s = MelonPlayer.getInstanceOfPlayer(Bukkit.getOfflinePlayer(args[0]));
				s.addCoins(Integer.parseInt(args[1]), false);
				s.sendMessage(MelonCore.prefix + ChatColor.GREEN + args[1] + " coins have been added to your account!");
			}
		}

		if (command.getName().equalsIgnoreCase("reload")) {
			Bukkit.broadcastMessage("NOPE, Reload is disabled Mr Admin");
			return true;
		}

		if (sender instanceof Player) {
			final Player player = (Player) sender;
			final Player p = player;

			if (command.getName().equalsIgnoreCase("test")) {
				if (p.hasPermission("melon.*")) {

					MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(p);

					if (args[0].equalsIgnoreCase("perms")) {
						OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
						p.sendMessage(MelonPlayer.getInstanceOfPlayer(op).rank.getPermissions().toString());

						return true;
					}

					if (args[0].equalsIgnoreCase("addbuff")) {

						MelonPlayer.getInstanceOfPlayer(p).buffHandler.addBuff(new Buff(mp.buffHandler, BuffType.DAMAGE, 300, 45));

					}

					if (args[0].equalsIgnoreCase("checkbuffs")) {

						mp.sendMessage(mp.buffHandler.getActiveBuffs());

					}

					if (args[0].equalsIgnoreCase("setlevel")) {
						MelonPlayer mp2 = MelonPlayer.getInstanceOfPlayer(Bukkit.getOfflinePlayer(args[1]));
						mp2.level = Integer.parseInt(args[2]);
						mp2.addXp(1);
						return true;

					}

					if (args[0].equalsIgnoreCase("firstjoin")) {
						EventPlayerJoin.firstJoin(MelonPlayer.getInstanceOfPlayer(Bukkit.getOfflinePlayer(args[1])));
						return true;

					}

					if (args[0].equalsIgnoreCase("item")) {
						net.ScyllaMc.Matan.Items.Item.Items.STAFF.getItem().giveItem(mp);

						net.ScyllaMc.Matan.Items.Item.Items.STAFF_HIGHLVL.getItem().giveItem(mp);

						JSONObject obj = new JSONObject();
						obj.put(Modifier.DAMAGE_I, 150);

						Bukkit.broadcastMessage(obj.toJSONString());
						Item i = new Item(Material.IRON_HOE, obj, ChatColor.RED + "UBER DAMAGE", 3);
						i.giveItem(mp);

						return true;

					}

					if (args[0].equalsIgnoreCase("npc")) {

						NPC npc = new NPC(EntityType.WITCH, p.getLocation(), NPCType.NPC, ChatColor.BLUE + "TEST NPC-", 1, null, null);
						return true;

					}

					if (args[0].equalsIgnoreCase("reloadnpc")) {

						try {
							MelonCore.NPCManager.registerAll();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return true;

					}

					if (args[0].equalsIgnoreCase("writeDefaultEnglish")) {
						Msg.writeDefaultEnglish();
						p.sendMessage("DEFAULT ENGLISH WRITTEN");
						return true;
					}

					if (args[0].equalsIgnoreCase("debug")) {

						if (mp.debug) {
							mp.debug = false;
						} else {
							mp.debug = true;
						}

						mp.sendMessage(MelonCore.prefix + " Debug mode toggled");
						return true;

					}

					if (args[0].equalsIgnoreCase("bungee")) {

						ByteArrayDataOutput out = ByteStreams.newDataOutput();
						out.writeUTF("Melon");
						out.writeUTF("TESTIinggggg");

						// If you don't care about the player
						// Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
						// Else, specify them

						Iterables.getFirst(Bukkit.getOnlinePlayers(), null).sendPluginMessage(Bukkit.getPluginManager().getPlugin("MelonCore"), "BungeeCord", out.toByteArray());

					}

					if (args[0].equalsIgnoreCase("mountitem")) {
						MountItem mi = new MountItem(3);
						p.getInventory().addItem(mi.getItem());

						return true;

					}

					if (args[0].equalsIgnoreCase("mount")) {
						Mount m = new Mount(MelonPlayer.getInstanceOfPlayer(p), Integer.parseInt(args[1]));
						m.spawn();

						return true;

					}

					if (args[0].equalsIgnoreCase("addxp")) {
						MelonPlayer.getInstanceOfPlayer(p).addXp(Integer.parseInt(args[1]));
						return true;

					}

					if (args[0].equalsIgnoreCase("itemlore")) {
						Bukkit.broadcastMessage(p.getItemInHand().getItemMeta().getLore().get(0));
						return true;
					}

					if (args[0].equalsIgnoreCase("itemcount")) {

						PlayerInventory inventory = player.getInventory();
						ItemStack[] items = inventory.getContents();
						int has = 0;
						for (ItemStack item : items) {
							if ((item != null) && (item.getType().equals(Material.COBBLESTONE)) && (item.getAmount() > 0)) {
								has += item.getAmount();
							}
						}

						p.sendMessage("You have: " + has);

						return true;
					}

					if (args[0].equalsIgnoreCase("itemcount")) {

						PlayerInventory inventory = player.getInventory();
						ItemStack[] items = inventory.getContents();
						int has = 0;
						for (ItemStack item : items) {
							if ((item != null) && (item.getType().equals(Material.COBBLESTONE)) && (item.getAmount() > 0)) {
								has += item.getAmount();
							}
						}

						p.sendMessage("You have: " + has);

						return true;
					}

					if (args[0].equalsIgnoreCase("removecount")) {

						int remove = Integer.parseInt(args[1]);

						PlayerInventory inventory = player.getInventory();
						ItemStack[] items = inventory.getContents();
						int has = 0;
						for (ItemStack item : items) {
							if ((item != null) && (item.getType().equals(Material.COBBLESTONE)) && (item.getAmount() > 0)) {
								has += item.getAmount();
								inventory.remove(item);
							}
						}

						int n = has - remove;
						inventory.addItem(new ItemStack(Material.COBBLESTONE, n));
						return true;
					}

					if (args[0].equalsIgnoreCase("shard")) {

						Item.Items.ENCHANT_II.getItem().giveItem(mp);
						Item.Items.ENCHANT_II.getItem().giveItem(mp);
						Item.Items.ENCHANT_II.getItem().giveItem(mp);
						Item.Items.ENCHANT_I.getItem().giveItem(mp);

						return true;
					}

					if (args[0].equalsIgnoreCase("essession")) {
						Item item = new Item(Material.ANVIL, null, "THINGY", 10);

						//ESession.getESession(mp, item);
						return true;
					}

					if (args[0].equalsIgnoreCase("getmods")) {

						int strength = Integer.parseInt(args[1]);

						Item item = new Item(Material.ANVIL, null, "THINGY", 10);
						ItemStack i = item.giveItem(mp);
						Msg.debug(i.getType().toString(), this.getClass());

						ArrayList<Modifier> l = Enchantments.getRandomModifiers(mp, strength, item, i);

						for (Modifier m : l) {
							mp.sendMessage(m.getName());
						}

					}

					if (args[0].equalsIgnoreCase("votechest")) {

						MelonCore.VoteChest.playOpening(MelonPlayer.getInstanceOfPlayer(p));
						return true;
					}

					if (args[0].equalsIgnoreCase("addvote")) {

						MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(args[1])).addVotes(Integer.parseInt(args[2]));

						return true;
					}

					if (args[0].equalsIgnoreCase("ranks")) {
						Bukkit.broadcastMessage("UPDATING RANKS");
						for (Player pl : Bukkit.getOnlinePlayers()) {
							MelonCore.MelonScoreboard.updateAllRanks(MelonPlayer.getInstanceOfPlayer(pl));

						}
						return true;
					}

				}
			}

			if (args[0].equalsIgnoreCase("leaderboard")) {
				new LeaderboardMaker(new Location(MelonCore.world, -12, 50, 16), "kills", BlockFace.NORTH, ChatColor.DARK_GREEN, 10);
				new LeaderboardMaker(new Location(MelonCore.world, -17, 50, 15), "level", BlockFace.EAST, ChatColor.DARK_GREEN, 10);
				new LeaderboardMaker(new Location(MelonCore.world, -17, 50, 9), "deaths", BlockFace.EAST, ChatColor.DARK_GREEN, 10);

			}

			if (alias.equalsIgnoreCase("upgrademenu")) {
				if (p.hasPermission("melon.*")) {
					UpgradeInv.CreateManu(MelonPlayer.getInstanceOfPlayer(player), player);
				}
			}
		}

		return false;
	}

}
