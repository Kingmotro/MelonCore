package net.ScyllaMc.Matan.Items;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;

import net.ScyllaMc.Matan.Items.Item.ModifierType;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class RuneSession {

	public static HashMap<MelonPlayer, RuneSession> sessions = new HashMap<MelonPlayer, RuneSession>();
	public static Location table = new Location(MelonCore.world, 238, 144, 1369);
	public static Location runelocholo = new Location(MelonCore.world, 238, 147.3, 1369);

	public static void resetHolo(MelonPlayer player) {

		if (player != null && player.isOnline()) {
			Hologram holo = player.runeHolo;
			Language lan = player.lan;

			holo = HologramsAPI.createHologram(Bukkit.getPluginManager().getPlugin("MelonCore"), runelocholo);
			VisibilityManager visiblityManager = holo.getVisibilityManager();
			holo.appendTextLine(Message.TABLE_HOLO.getTransMsg(lan));
			holo.appendTextLine(ChatColor.GRAY + "---------------");
			holo.appendTextLine(Message.TABLE_HOLO_RUNE.getTransMsg(lan));
			holo.appendTextLine(Message.TABLE_HOLO_RIGHT.getTransMsg(lan));
			visiblityManager.showTo(player.getOnlinePlayer());
			visiblityManager.setVisibleByDefault(false);
		}
	}

	public static void interact(MelonPlayer p, Item item) {

		if(sessions.containsKey(p)){
			
		}
	}
	

	
	public static RuneSession getRuneSession(MelonPlayer p) {

		if (sessions.containsKey(p)) {
			return sessions.get(p);
		}

		return new RuneSession(p);
	}

	private HashMap<Item, UUID> shards;
	private MelonPlayer player;
	private double power;
	private boolean over;

	public RuneSession(MelonPlayer p) {
		this.shards = new HashMap<Item, UUID>();
		this.player = p;
		this.over = false;

		new BukkitRunnable() {

			@Override
			public void run() {

				if (player == null || !player.isOnline() || player.getLocation().distance(table) > 10) {
					over = true;
				}

				if (over) {

					if (player.isOnline()) {
						Iterator<Entry<Item, UUID>> in = shards.entrySet().iterator();

						while (in.hasNext()) {
							Item item = (Item) in.next().getKey();

							if (item != null) {
								player.getOnlinePlayer().getInventory().addItem(item.getItemStack());
							}
						}
					}

					Msg.debug(ChatColor.RED + "Rune-Session OVER FOR " + player.getName(), RuneSession.class);

					resetHolo(player);

					sessions.remove(player);
					this.cancel();
					return;
				}

				power = getPower();

				Hologram holo = player.runeHolo;
				Language lan = player.lan;

				if (holo == null || holo.size() != 6) {
					holo = HologramsAPI.createHologram(Bukkit.getPluginManager().getPlugin("MelonCore"), runelocholo);
					VisibilityManager visiblityManager = holo.getVisibilityManager();
					holo.appendTextLine(Message.TABLE_HOLO.getTransMsg(lan));
					holo.appendTextLine(ChatColor.GRAY + "---------------");
					holo.appendTextLine(Message.TABLE_HOLO_TOP.getTransMsg(lan, new String[] { power + "" }));
					holo.appendTextLine(Message.TABLE_HOLO_RUNE.getTransMsg(lan));
					holo.appendTextLine(Message.TABLE_HOLO_RIGHT.getTransMsg(lan));
					holo.appendTextLine(Message.TABLE_HOLO_LEFT.getTransMsg(lan));
					visiblityManager.showTo(player.getOnlinePlayer());
					visiblityManager.setVisibleByDefault(false);
				} else {
					holo.insertTextLine(2, Message.TABLE_HOLO_TOP.getTransMsg(lan, new String[] { power + "" }));
				}

			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0L, 20L);

	}

	public Integer getPower() {

		int c = 0;

		Iterator<Entry<Item, UUID>> in = shards.entrySet().iterator();

		while (in.hasNext()) {
			Item item = (Item) in.next().getKey();

			c += item.getModifier(null, ModifierType.ENCHANT).getModifier();

		}

		return c;
	}

}
