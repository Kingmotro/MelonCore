package net.ScyllaMc.Matan.MelonCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.MelonPlayer.Univentory;

public class Mysql {

	public static Connection connection = MelonCore.connection;
	public Plugin plugin = Bukkit.getPluginManager().getPlugin("MelonCore");

	public void addIntegerValue(final MelonPlayer p, final String data, final int add) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {

				ResultSet rs;
				PreparedStatement st;
				int olddata = 0;
				try {

					rs = query("SELECT * FROM `network_player_data` WHERE player= '" + p.getUniqueId().toString() + "'");

					if (!rs.next()) {
						return;
					}

					olddata = rs.getInt(data);
					int newdata = olddata + add;

					st = connection.prepareStatement("update network_player_data set " + data + " ='" + newdata + "' where player =?;");
					// st.setInt(1, newdata);
					st.setString(1, p.getUniqueId().toString());
					st.executeUpdate();
					st.close();

					// updateMysql("UPDATE `network_player_data` SET " + data +
					// "='" + newdata + "' WHERE player= '" +
					// p.getUniqueId().toString() + "';");

				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (p.isOnline()) {
					if (data.equalsIgnoreCase("coins")) {
						if (add >= 50) {
							p.sendMessage(MelonCore.prefix + ChatColor.GREEN + "+" + add + " " + data);
						}
						if (add <= -20) {
							p.sendMessage(MelonCore.prefix + ChatColor.RED + "" + add + " " + data);
						}
					}

					int newadd = add;

					if (data.equalsIgnoreCase("votes")) {

						if (newadd >= 1) {
							p.sendMessage(MelonCore.prefix + ChatColor.GREEN + "+" + newadd + " " + data);
						}
						if (newadd < 0) {
							p.sendMessage(MelonCore.prefix + ChatColor.RED + "" + newadd + " " + data);
						}
					}

				}

			}
		});

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				if (p.isOnline() && add > 0) {
					p.showScoreboard();
				}
			}
		}, 50l);
	}

	public synchronized void closeConnection() {
		try {
			if (!(connection == null) && !connection.isClosed()) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void openConnection() {
		try {
			Bukkit.broadcastMessage("OPENING SQL CONNECTION");
			connection = DriverManager.getConnection("jdbc:mysql://95.85.7.36:3306/mysql", "minecraft", "7bc4e3a1-09d5-4318-bcbc-cf81b07b187d");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet query(String qry) {
		ResultSet rs = null;
		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void setIntegerValue(final MelonPlayer p, final String data, final Integer s) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {
				updateMysql("UPDATE `network_player_data` SET " + data + "= '" + s + "' WHERE player= '" + p.getUniqueId().toString() + "';");
			}
		});

	}

	public void setStringValue(final MelonPlayer p, final String data, final String s) {

		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {
				updateMysql("UPDATE `network_player_data` SET " + data + "= '" + s + "' WHERE player= '" + p.getUniqueId().toString() + "';");
			}
		});

	}

	public void updateMysql(final String qry) {

		try {
			Statement st = connection.createStatement();
			st.executeUpdate(qry);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public HashMap<Integer, String> getTop(String type, int amount) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		try {
			Statement s = connection.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM network_player_data ORDER BY CONVERT(" + type.toLowerCase() + ", UNSIGNED INTEGER) DESC LIMIT " + amount);
			int counter = 1;
			while (r.next()) {
				map.put(counter, r.getString("player").toString() + "&" + r.getInt(type) + "&" + r.getString("rank").toLowerCase());
				counter++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static HashMap<MelonPlayer, Integer> updateQueue = new HashMap<MelonPlayer, Integer>();

	public void addVotes(final MelonPlayer mp, int add) {
		addIntegerValue(mp, "tokens", add);
	}

	public void updateCoins(final MelonPlayer mp, int coins) {

		if (updateQueue.containsKey(mp)) {
			int con = updateQueue.get(mp);
			con = con + coins;
			updateQueue.put(mp, con);
		} else {
			updateQueue.put(mp, coins);
		}

		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {

				if (updateQueue.containsKey(mp)) {
					int queueCoins = updateQueue.get(mp);
					updateQueue.put(mp, 0);
					addIntegerValue(mp, "coins", queueCoins);

				}

			}
		}, 20);

	}

	public static void checkifNew(final MelonPlayer p) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {
				try {
					

					PreparedStatement sql = connection.prepareStatement("SELECT * FROM `network_player_data` WHERE player=?;");
					sql.setString(1, p.getUniqueId().toString());
					ResultSet rs = sql.executeQuery();

					if (!rs.next()) {

						PreparedStatement newPlayer = connection.prepareStatement("INSERT INTO `network_player_data` values(?,?,default,default,default,default,default,default,default,?);");
						newPlayer.setString(1, p.getUniqueId().toString());
						newPlayer.setString(2, p.getName());
						newPlayer.setString(3, "{}");

						newPlayer.execute();
						newPlayer.close();
					}

					PreparedStatement sql1 = connection.prepareStatement("SELECT * FROM `network_login_data` WHERE player=?;");
					sql1.setString(1, p.getUniqueId().toString());
					ResultSet rs1 = sql1.executeQuery();

					if (!rs1.next()) {

						PreparedStatement newPlayer2 = connection.prepareStatement("INSERT INTO `network_login_data` values(?,default,default,default,default);");
						newPlayer2.setString(1, p.getUniqueId().toString());
						newPlayer2.execute();
						newPlayer2.close();

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}
}
