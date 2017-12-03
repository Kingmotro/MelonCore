package net.ScyllaMc.Matan.MelonPlayer;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.blocks.ClothColor.ID;

import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;

public class Duel {

	public static ArrayList<Duel> currentDuels = new ArrayList<Duel>();

	public static Duel getDuelByPlayer(MelonPlayer p) {

		Msg.debug("CHECK: " + currentDuels.size(), Duel.class);

		for (Duel d : currentDuels) {

			if (!d.over) {
				if (d.getAlivePlayers().contains(p)) {

					return d;
				}
			}

		}

		return null;
	}

	public static boolean inDuel(MelonPlayer p) {

		Msg.debug("CHECK -0", Duel.class);

		if (getDuelByPlayer(p) != null) {

			Msg.debug("CHECK -1", Duel.class);

			if (getDuelByPlayer(p).getAlivePlayers().contains(p)) {

				Msg.debug("CHECK -2", Duel.class);
				return true;
			}
		}

		Msg.debug("CHECK -69", Duel.class);
		return false;
	}

	public static boolean canHurt(MelonPlayer p1, MelonPlayer p2) {

		Msg.debug("CHECK 0", Duel.class);

		if (inDuel(p1) && inDuel(p2)) {

			Msg.debug("CHECK 1", Duel.class);

			if (Duel.getDuelByPlayer(p1) == Duel.getDuelByPlayer(p2) && !Duel.getDuelByPlayer(p1).inGrace()) {

				Msg.debug("CHECK 2", Duel.class);

				return true;
			}

		}

		Msg.debug("CHECK 69", Duel.class);

		return false;
	}

	static int max_distance = 120;
	static int max_length = 420;

	private MelonPlayer[] list;
	private ArrayList<MelonPlayer> alive = new ArrayList<MelonPlayer>();
	private Location start;
	private boolean grace = true;
	private boolean over = false;

	public Duel(MelonPlayer[] list) {

		this.list = list;
		this.start = list[0].getLocation();

		for (MelonPlayer p : this.list) {
			this.alive.add(p);
		}

		currentDuels.add(this);

		Msg.debug("ALIVE: " + alive.size(), Duel.class);
		broadcast(Message.BATTLE_STARTING);

		new BukkitRunnable() {

			int len = 0;
			int c = 5;

			@Override
			public void run() {

				if (c > 0) {
					broadcast(Message.BATTLE_COUNTDOWN, new String[] { c + "" });
					c--;
				}

				if (c == 0) {
					grace = false;
					broadcast(Message.BATTLE_STARTED);
					c--;
				}

				if (c <= 0) {

					for (MelonPlayer p : alive) {

						if (!p.isOnline()) {
							broadcast(Message.BATTLE_REMOVE_QUIT, new String[] { p.rank.getTagClosed() + p.getName() });
							remove(p);
						}

						if (p.getLocation().distance(start) > max_distance) {
							broadcast(Message.BATTLE_REMOVE_DISTANCE, new String[] { p.rank.getTagClosed() + p.getName() });
							remove(p);
						}

					}

					if (len > max_length) {
						forceEnd();
						broadcast(Message.BATTLE_FORCEEND_TIME);
						this.cancel();
						currentDuels.remove(this);
						over = true;
						return;
					}

					if (alive.size() == 1) {
						MelonPlayer winner = alive.get(0);
						broadcast(Message.BATTLE_WINNER, new String[] { winner.rank.getTagClosed() + winner.getName() });
						this.cancel();
						currentDuels.remove(this);
						over = true;
						return;
					}

					len++;

				}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0L, 20L);

	}

	public MelonPlayer[] getPlayers() {
		return this.list;
	}

	public ArrayList<MelonPlayer> getAlivePlayers() {
		return this.alive;
	}

	public void remove(MelonPlayer p) {
		ArrayList<MelonPlayer> alive = this.alive;

		if (alive.contains(p)) {
			alive.remove(p);
		}

		this.alive = alive;
	}

	public void forceEnd() {
		alive.clear();
		currentDuels.remove(this);
	}

	public void end(Message m, String[] s) {
		alive.clear();
		currentDuels.remove(this);
	}

	public void broadcast(Message m) {

		for (MelonPlayer p : list) {
			p.sendMessage(m);
		}

	}

	public void broadcast(Message m, String[] s) {

		for (MelonPlayer p : list) {
			p.sendMessage(m, s);
		}

	}

	public boolean inGrace() {
		return this.grace;
	}

	public void died(MelonPlayer p) {
		broadcast(Message.BATTLE_DIED, new String[] { p.rank.getTagClosed() + p.getName() });
		remove(p);
	}

}
