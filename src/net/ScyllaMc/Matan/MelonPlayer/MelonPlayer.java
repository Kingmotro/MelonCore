package net.ScyllaMc.Matan.MelonPlayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import net.ScyllaMc.Matan.Clans.Clan;
import net.ScyllaMc.Matan.Clans.Clan_Mysql;
import net.ScyllaMc.Matan.Items.Item;
import net.ScyllaMc.Matan.Items.Item.Modifier;
import net.ScyllaMc.Matan.Items.Item.ModifierType;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.Buff.BuffType;
import net.ScyllaMc.Matan.Rank.PermissionManager;
import net.ScyllaMc.Matan.Rank.Rank;
import net.md_5.bungee.api.ChatColor;

public class MelonPlayer {

	private static Map<UUID, MelonPlayer> melonplayers = new HashMap<UUID, MelonPlayer>();
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("MelonCore");
	public static HashMap<MelonPlayer, Integer> invQueue = new HashMap<MelonPlayer, Integer>();

	public static MelonPlayer getInstanceOfPlayer(OfflinePlayer g) {
		UUID id = g.getUniqueId();
		if (!melonplayers.containsKey(id)) {
			return new MelonPlayer(g);
		}

		else if (melonplayers.containsKey(g.getUniqueId())) {
			return melonplayers.get(g.getUniqueId());
		} else {
		}
		return null;
	}

	public static MelonPlayer getInstanceOfPlayerFromUUID(UUID id) {
		
		if (melonplayers.containsKey(id)) {
			return melonplayers.get(id);
		} else {	
			return getInstanceOfPlayer(Bukkit.getOfflinePlayer(id));
		}
	}
	
	private OfflinePlayer player;
	private MelonPlayer mp;

	public Rank rank = Rank.DEFAULT;
	public int coins, votetokens, level = 1, currentxp, deaths, kills = 0;

	public int luck = 1;
	public int damagelevel = 1;

	public boolean dowloaded, inGodmode, hidden, invDowloaded, debug, loggedIn = false;
	public Inventory dowloadedInventory;

	net.ScyllaMc.Matan.MelonCore.Mysql mysql = MelonCore.Mysql;

	public JSONObject data = new JSONObject();
	public JSONObject homes = new JSONObject();
	public JSONObject quests = new JSONObject();

	public Levels Levels = new Levels();

	public Hologram voteHolo;
	public Hologram runeHolo;

	public Clan clan;

	public String lastMessage = "___";

	public BuffHandler buffHandler = new BuffHandler(this);

	public Language lan = Language.ENGLISH;

	private MelonPlayer(OfflinePlayer g) {
		this.player = g;
		this.mp = this;
		melonplayers.put(g.getUniqueId(), this);
	}

	public OfflinePlayer getOfflinePlayer() {
		return player;
	}

	public Player getOnlinePlayer() {
		return Bukkit.getPlayer(player.getUniqueId());
	}

	public String getName() {
		return player.getName();
	}

	public UUID getUniqueId() {
		return player.getUniqueId();
	}

	public boolean inDebug() {
		return this.debug;
	}

	public boolean isOnline() {
		if (player.isOnline()) {
			return true;
		}
		return false;
	}

	public Location getLocation() {
		if (isOnline()) {
			return getOnlinePlayer().getLocation();
		}
		return MelonCore.spawn;
	}

	public void setScoreboard(Scoreboard board) {
		if (isOnline()) {
			getOnlinePlayer().setScoreboard(board);
		}
	}

	public void showScoreboard() {
		if (!isOnline()) {
			return;
		}
		MelonCore.MelonScoreboard.showScoreboard(mp);
	}

	public void updateScoreboardRank() {
		if (!isOnline()) {
			return;
		}
		MelonCore.MelonScoreboard.updateAllRanks(mp);
	}

	public void sendMessage(String string) {
		if (isOnline()) {
			getOnlinePlayer().sendMessage(string);
		}
	}

	public void sendMessage(Message msg) {
		msg.send(this);
	}

	public void sendMessage(Message msg, String[] s) {
		msg.send(this, s);
	}

	public void addCoins(final int add, final boolean doub) {
		mysql.updateCoins(this, add);
		coins = coins + (add);
	}

	public void addVotes(int add) {

		this.votetokens = votetokens + (add);

		MelonCore.Mysql.addVotes(this, add);

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				if (player.isOnline()) {
					Bukkit.getPlayer(player.getName());
					MelonCore.VoteChest.display(mp);
				}
			}
		}, 25l);
	}

	public void addDeath(int a) {
		mysql.addIntegerValue(this, "deaths", a);
		deaths = deaths + (a);
	}

	public void addMobKill(int a) {
		mysql.addIntegerValue(this, "kills", a);
		kills = kills + (a);
	}

	public void setLastMessage(String message) {
		this.lastMessage = message;
	}

	@SuppressWarnings("unchecked")
	public void setLuckLevel(int level) {
		luck = level;
		data.put("luck", luck);
		saveData();

	}

	public void setLevel(int level, int xp) {
		this.level = level;
		this.currentxp = 0;
		addXp(xp);
	}

	public boolean hasPermission(String perm) {

		if (rank.equals(Rank.ADMIN) || rank.getPermissions().contains("server.*")){
			return true;
		}

		if (rank.getPermissions().contains(perm)) {
			return true;
		}
		
		return false;
	}

	public void setDamageLevel(int level) {
		damagelevel = level;
		saveData();
	}

	public double getDamageBonus() {
		double damage = ((double) this.damagelevel) / 10;
		damage += getBuffs(ModifierType.DAMAGE, BuffType.DAMAGE);
		return damage;
	}

	public double getDefenceBonus() {
		double defence = this.level - 1;
		defence += getBuffs(ModifierType.DEFENCE, BuffType.DEFENCE);
		return defence;
	}

	public double getBuffs(ModifierType mt, BuffType bt) {
		double add = 0;

		ArrayList<Modifier> pm = new ArrayList<Modifier>();

		for (ItemStack is : getOnlinePlayer().getInventory().getContents()) {

			if (is != null && Item.isItem(is)) {
				Item i = Item.fromItemStack(is);
				Modifier mod = i.getModifier(this, mt);

				if (!pm.contains(mod) && mod != null) {
					add += ((double) mod.getModifier()) / 100;

					pm.add(mod);

				}

			}

		}

		add += (this.buffHandler.getBuffMod(bt) / 100);

		return add;
	}

	public String getBonusString(double mod) {

		if (mod > 0) {
			mod *= 100;
			return ChatColor.GRAY + " (" + ChatColor.LIGHT_PURPLE + "+" + mod + "%" + ChatColor.GRAY + ")";
		}

		return "";
	}

	public Clan getClan() {
		return clan;
	}

	public boolean inClan() {
		if (clan == null || clan.getName().equals("NO_NAME")) {
			return false;
		}
		return true;
	}
	

	public void clearClan() {
		MelonCore.Mysql.setStringValue(this, "clan", "none");
		
		this.clan = null;
	}

	public void createClan(String name) {
		Clan_Mysql.createClan(this, name);
	}

	public void setClan(Clan c) {

		if (c == null) {
			this.clan = null;
		}

		this.clan = c;
	}

	
	@SuppressWarnings("unchecked")
	public void saveData() {
		data.put("xp", this.currentxp);
		data.put("homes", this.homes);
		data.put("damage", this.damagelevel);
		data.put("quests", this.quests);
		data.put("buffs", this.buffHandler.toJson());

		MelonCore.Mysql.setStringValue(this, "data", data.toJSONString());
	}

	public void saveInv() {

		if (!isOnline()) {
			Msg.debug("Inv save error 1, Player not online", this.getClass());
			return;
		}

		if (invQueue.containsKey(mp)) {

			if (invQueue.get(mp) > 0) {
				return;
			}
		}

		invQueue.put(this, 1);

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {

				if (invQueue.containsKey(mp) && invQueue.get(mp) > 0) {

					if (getOnlinePlayer() != null) {
						String s = Univentory.toBase64(getOnlinePlayer().getInventory());

						try {
							if (Univentory.fromBase64(s) != null) {
								data.put("inventory", s);
								saveData();
								invQueue.remove(mp);

							}
						} catch (IOException e) {
							Msg.debug("Inv save error, parse FAILED", this.getClass());
							e.printStackTrace();
						}
					}
				}
			}
		}, 20);

	}

	@SuppressWarnings("static-access")
	public void dowloadData() {

		if (!isOnline()) {
			if (dowloaded) {
				return;
			}
		}

		MelonCore.Mysql.checkifNew(this);
		
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {

				try {
					ResultSet rs = mysql.query("SELECT * FROM `network_player_data` WHERE player= '" + player.getUniqueId().toString() + "'");
					if (!rs.next()) {
						rs.close();
						Msg.debug("MYSQL: Failed to get info for player. player data dose not exist!", MelonPlayer.class);
						return;
					}

					clan = null;
					rank = Rank.fromString(rs.getString("rank"));
					coins = rs.getInt("coins");
					votetokens = rs.getInt("tokens");
					kills = rs.getInt("kills");
					deaths = rs.getInt("deaths");
					level = rs.getInt("level");

					JSONParser parser = new JSONParser();

					try {
						String datas = rs.getString("data");

						if (datas != null && !datas.equals("none") && !datas.equals("")) {
							data = (JSONObject) parser.parse(datas);

							try {
								if (data.containsKey("inventory")) {
									Inventory inv = Univentory.fromBase64(data.get("inventory").toString());
									dowloadedInventory = inv;
									invDowloaded = true;

									if (isOnline()) {
										getOnlinePlayer().getInventory().setContents(inv.getContents());
									}

								}
							} catch (EOFException e) {
								Msg.debug("Download error , Inv parse FAILED", this.getClass());
								e.printStackTrace();
							}

							try {
								if (data.containsKey("xp")) {
									currentxp = Integer.parseInt((String) data.get("xp").toString());
								} else {
									currentxp = 0;
								}
							} catch (Exception e) {
								currentxp = 0;
								Msg.debug("Download error , XP parse FAILED", this.getClass());
								e.printStackTrace();
							}

							try {
								if (data.containsKey("damage")) {
									damagelevel = Integer.parseInt((String) data.get("damage").toString());
								} else {
									damagelevel = 1;
								}
							} catch (Exception e) {
								damagelevel = 1;
								Msg.debug("Download error , Damage parse FAILED", this.getClass());
								e.printStackTrace();
							}

							try {
								if (data.containsKey("luck")) {
									luck = Integer.parseInt((String) data.get("luck").toString());
								} else {
									luck = 1;
								}
							} catch (Exception e) {
								luck = 1;
								Msg.debug("Download error , Luck parse FAILED", this.getClass());
								e.printStackTrace();
							}

							try {
								if (data.containsKey("homes")) {

									String homess = data.get("homes").toString();
									if (homess != null && !homess.equals("none") && !homess.equals("")) {
										homes = (JSONObject) parser.parse(homess);
									}
								}
							} catch (Exception e) {
								Msg.debug("Download error , Home parse FAILED", this.getClass());
								e.printStackTrace();
							}

							try {
								if (data.containsKey("quests")) {

									String questss = data.get("quests").toString();
									if (questss != null && !questss.equals("none") && !questss.equals("")) {
										quests = (JSONObject) parser.parse(questss);
									}
								}
							} catch (Exception e) {
								Msg.debug("Download error , Quest parse FAILED", this.getClass());
								e.printStackTrace();
							}

							try {
								if (data.containsKey("buffs")) {

									String buffs = data.get("buffs").toString();
									if (buffs != null && !buffs.equals("none") && !buffs.equals("")) {
										buffHandler = BuffHandler.fromGson(buffs, mp);
									}

								}
							} catch (Exception e) {

								Msg.debug("Download error , Buffs parse FAILED", this.getClass());
								e.printStackTrace();
							}

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					String clanuuids = rs.getString("clan");

					if (!clanuuids.equals("none") && !clanuuids.equals("") && clanuuids != null) {
						try {
							UUID clanid = UUID.fromString(clanuuids);
							clan = Clan.getClan(clanid);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					MelonCore.Mysql.setStringValue(mp, "name", getName());

					rs.close();
					dowloaded = true;

					Levels.calculateLevel(mp, false, true);

					if (coins < 0) {
						coins = 0;
						mysql.setIntegerValue(mp, "coins", 0);
					}
				} catch (Exception exp) {
					exp.printStackTrace();
				}
			}
		});

		if (isOnline()) {
			Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
				@Override
				public void run() {
					PermissionManager.addPlayer(getOnlinePlayer());
					showScoreboard();

					MelonCore.VoteChest.display(mp);
				}
			}, 20);
		}

	}

	public void addXp(final int xpadd) {
		int xpNeeded = (level * level) * 35;
		final int cap = 100;
		currentxp = currentxp + xpadd;
		if (level >= cap) {
			return;
		}

		if (xpNeeded > currentxp) {
			Levels.calculateLevel(mp, false, true);
			return;
		}

		new BukkitRunnable() {
			@Override
			public void run() {
				int xpNeeded = (level * level) * 35;

				if (level >= cap) {
					this.cancel();
					currentxp = 0;
					Levels.calculateLevel(mp, false, true);
					return;
				}

				if (xpNeeded > currentxp) {
					this.cancel();
					Levels.calculateLevel(mp, false, true);
					return;
				}

				if (xpNeeded <= currentxp) {
					level = level + 1;
					currentxp = currentxp - xpNeeded;
					Levels.calculateLevel(mp, true, true);
				}

			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0L, 2L);

		Levels.calculateLevel(mp, false, true);
	}

	/**
	 * public Location lastLocationfromString() { Location l = MelonCore.spawn;
	 * 
	 * if (lastLocString != null && lastLocString.contains("/")) { String[] s =
	 * lastLocString.split("/")[1].split(","); double x =
	 * Double.parseDouble(s[0]); double y = (Double.parseDouble(s[1])); double z
	 * = (Double.parseDouble(s[2])); float pitch = ((float)
	 * Double.parseDouble(s[3])); float yaw = (float) Double.parseDouble(s[4]);
	 * l = new Location(MelonCore.world, x, y, z, pitch, yaw);
	 * 
	 * }
	 * 
	 * return l; }
	 * 
	 **/

	public void kick(String r) {
		getOnlinePlayer().kickPlayer(ChatColor.RED + ChatColor.BOLD.toString() + "KICKED!" + "\n " + r);
	}

	public void playSound(Sound sound, float f, float g) {

		if (isOnline()) {
			getOnlinePlayer().playSound(getOnlinePlayer().getLocation(), sound, f, g);
		}

	}

	public void teleport(Location loc) {

		if (isOnline()) {
			getOnlinePlayer().teleport(loc);
		}

	}

}