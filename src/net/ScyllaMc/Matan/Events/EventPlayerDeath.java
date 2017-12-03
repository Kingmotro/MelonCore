package net.ScyllaMc.Matan.Events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.ScyllaMc.Matan.MelonPlayer.Duel;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Util.RandomTp;

public class EventPlayerDeath implements Listener {

	public static HashMap<MelonPlayer, Location> deathLocation = new HashMap<MelonPlayer, Location>();

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		final MelonPlayer p = MelonPlayer.getInstanceOfPlayer(e.getEntity());

		deathLocation.put(p, p.getLocation());
		p.addDeath(1);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
			@Override
			public void run() {
				p.getOnlinePlayer().spigot().respawn();
			}
		}, 1L);

		if (p.getOnlinePlayer().getKiller() != null && p.getOnlinePlayer().getKiller() instanceof Player) {
			
			
			if(Duel.canHurt(p, MelonPlayer.getInstanceOfPlayer(p.getOnlinePlayer().getKiller()))){
				Duel.getDuelByPlayer(p).died(p);
			}
			
			
		}

		e.setDeathMessage(null);

	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {

		Location l = deathLocation.get(MelonPlayer.getInstanceOfPlayer(e.getPlayer()));
		int max = 100;
		int min = 30;
		Location r = RandomTp.getRandomSafeLocation(MelonPlayer.getInstanceOfPlayer(e.getPlayer()), l, max, min);
		e.setRespawnLocation(r);
	}

}
