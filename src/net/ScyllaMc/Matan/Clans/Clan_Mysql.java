package net.ScyllaMc.Matan.Clans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.ScyllaMc.Matan.Clans.Clan;
import net.ScyllaMc.Matan.Clans.Clan_Utils;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonCore.Mysql;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Clans.Clan.Clan_Status;
import net.ScyllaMc.Matan.Clans.Clan_Member.Clan_Rank;

public class Clan_Mysql {

	private static Connection connection = Mysql.connection;

	public List<String> getColumn(String columnName) {
		List<String> temp = new ArrayList<String>();

		String query = "SELECT * FROM `network_player_data` WHERE player=?";

		try {
			ResultSet res = connection.prepareStatement(query).executeQuery();

			while (res.next()) {
				temp.add(res.getString(columnName));
			}

			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return temp;
	}


	public static ResultSet resultQuery(String qry) {
		ResultSet rs = null;

		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {

			System.err.println(e);
		}
		return rs;
	}

	public static void updateQuery(String qry) {
		try {
			Statement st = connection.createStatement();
			st.executeUpdate(qry);
			st.close();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public HashMap<Integer, Clan> getTopClans(int amount) {
		HashMap<Integer, Clan> map = new HashMap<Integer, Clan>();
		try {
			Statement s = connection.createStatement();
			ResultSet r = s.executeQuery("SELECT * FROM network_player_clan ORDER BY CONVERT(POINTS, UNSIGNED INTEGER) DESC LIMIT " + amount);
			int counter = 1;
			while (r.next()) {
				UUID ID = UUID.fromString(r.getString("ID"));
				map.put(counter, Clan.getClan(ID));
				counter++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;
	}

	public static void existsInDb(final String name, final String data, final String tn, final IResponseListener listener) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {			

				try {
					PreparedStatement sql = connection.prepareStatement("SELECT * FROM `" + tn + "` WHERE " + data + "=?;");
					sql.setString(1, name);
					ResultSet resultSet = sql.executeQuery();
					boolean bool = resultSet.next();

					sql.close();
					resultSet.close();

					ResponseEvent event = new ResponseEvent();

					event.setResponse(bool);
					listener.response(event);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public static HashMap<UUID, Clan> DB_CLANS = new HashMap<UUID, Clan>();

	public static synchronized Clan getClan(final Object t) {

		String type = "ID";
		String value = t.toString();
		final UUID task = UUID.randomUUID();

		if (!(t instanceof UUID)) {
			type = "NAME_UPPERCASE";
			value = t.toString().toUpperCase();
		}

		getFromDB(value, type, "network_clan_data", new IResponseListener() {
			public void response(ResponseEvent event) {

				try {

					ResultSet rs = event.getResponse();

					if (rs.next()) {
						UUID ID = UUID.fromString(rs.getString("ID"));

						String NAME = rs.getString("NAME");
						UUID LEADER_UUID = UUID.fromString(rs.getString("LEADER"));
						Integer POINTS = rs.getInt("POINTS");

						JsonObject MEMBERS = new JsonObject();

						JsonParser parser = new JsonParser();

						String memberss = rs.getString("MEMBERS");
						if (memberss != null && !memberss.equals("none") && !memberss.equals("")) {
							MEMBERS = (JsonObject) parser.parse(memberss);
						}

						Clan_Status STATUS = Clan_Status.statusFromString(rs.getString("STATUS"));

						rs.close();

						DB_CLANS.put(task, new Clan(ID, LEADER_UUID, NAME, MEMBERS, STATUS, POINTS));

					} else {
						DB_CLANS.put(task, null);
					}

				} catch (SQLException exep) {
					exep.printStackTrace();
				}
			}
		});

		while (DB_CLANS.containsKey(task)) {
			return DB_CLANS.get(task);
		}

		return null;
	}

	public static void getFromDB(final String name, final String data, final String table, final IResponseListener listener) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {
				
				try {
					PreparedStatement sql = connection.prepareStatement("SELECT * FROM `" + table + "` WHERE " + data + "=?;");
					sql.setString(1, name);

					ResultSet resultSet = sql.executeQuery();
					ResponseEvent event = new ResponseEvent();

					event.setResponse(resultSet);
					listener.response(event);

					sql.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public static void createClan(final MelonPlayer p, final String name) {

		p.sendMessage(Message.CLAN_CREATING);

		if (p.inClan()) {
			p.sendMessage(Message.CLAN_ALREADYIN);
			return;
		}

		if (Clan.clanByName(name) != null) {
			p.sendMessage(Message.CLAN_NAMETAKEN);
			return;
		}

		if (!Clan_Utils.checkClanName(p, name)) {
			return;
		}

		final UUID ID = UUID.randomUUID();
		HashMap<String, String> members = new HashMap<String, String>();
		members.put(p.getUniqueId().toString(), Clan_Rank.LEADER.toString());

	
		final String ms = Clan_Utils.mapToString(members);
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {				
				try {

					PreparedStatement sql = Clan_Mysql.connection.prepareStatement("SELECT * FROM `network_clan_data` WHERE NAME_UPPERCASE=? LIMIT 1;");
					sql.setString(1, name.toUpperCase());
					ResultSet rs = sql.executeQuery();

					if (rs.next()) {
						p.sendMessage(Message.CLAN_NAMETAKEN);
						return;
					}

					PreparedStatement newBase = Clan_Mysql.connection.prepareStatement("INSERT INTO `network_clan_data` values(?,?,?,?,?,?,?);");
					newBase.setString(1, ID.toString());
					newBase.setString(2, name);
					newBase.setString(3, name.toUpperCase());
					newBase.setString(4, p.getUniqueId().toString());
					newBase.setString(5, ms);
					newBase.setString(6, Clan_Status.CLOSED.toString());
					newBase.setInt(7, 0);
					newBase.execute();
					newBase.close();

					Clan_Mysql.setString(p.getUniqueId(), "clan", ID.toString());

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {			

				Clan clan = Clan.getClan(ID);
				p.setClan(clan);
				p.sendMessage(Message.CLAN_NOWLEADER, new String[] { name });

			}
		}, 1);

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

	public static String getMysqlString(UUID id, String data) {
		String datasent = "default";
		try {
			ResultSet rs = resultQuery("SELECT * FROM `network_player_data` WHERE player= '" + id.toString() + "'");
			if (rs.next()) {
				datasent = rs.getString(data);
				rs.close();
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return datasent;
	}

	public static void setString(final UUID id, final String data, final String string) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {			
				updateQuery("UPDATE `network_player_data` SET " + data + "= '" + string + "' WHERE player= '" + id.toString() + "';");
			}
		});
	}

	public static void setClanString(final Clan clan, final String data, final String string) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {			
				updateQuery("UPDATE `network_clan_data` SET " + data + "= '" + string + "' WHERE ID= '" + clan.getID().toString() + "';");
			}
		});
	}

	public static void setStringLogin(final MelonPlayer p, final String data, final String string) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {			
				updateQuery("UPDATE `network_login_data` SET " + data + "= '" + string + "' WHERE player= '" + p.getUniqueId().toString() + "';");
			}
		});
	}

	public static void setStringTimeStamp(final MelonPlayer p, final String data, final Timestamp string) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {			
				updateQuery("UPDATE `network_login_data` SET " + data + "= '" + string + "' WHERE player= '" + p.getUniqueId().toString() + "';");
			}
		});
	}

	public static void addIntegerValue(final Clan c, final String data, final int add) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {			
				int olddata = 0;
				try {
					ResultSet rs = Clan_Mysql.resultQuery("SELECT * FROM `network_clan_data` WHERE ID= '" + c.getID().toString() + "'");
					if (!rs.next()) {
						return;
					}
					olddata = rs.getInt(data);
					int newdata = olddata + add;

					PreparedStatement st = Clan_Mysql.connection.prepareStatement("update network_clan_data set " + data + " ='" + newdata + "' where ID =?;");
					st.setString(1, c.getID().toString());
					st.executeUpdate();
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
