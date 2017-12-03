package net.ScyllaMc.Matan.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Util.RandomTp;
import net.ScyllaMc.Matan.Util.Title;

public class EventPlayerPortal implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void event(PlayerInteractEvent event) {
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(event.getPlayer());

		Msg.debug("INTERACT T1" , this.getClass());

		Block clicked = event.getClickedBlock();

		if (clicked == null) {
			Msg.debug("INTERACT T2" , this.getClass());
			return;
		}

		if (clicked.getType().equals(Material.STONE_PLATE) && p.getLocation().distance(MelonCore.portal) < 7) {
			Msg.debug("INTERACT T3" , this.getClass());
			event.setCancelled(true);

			p.getOnlinePlayer().teleport(RandomTp.getRandomSafeLocation(p, MelonCore.spawn, 1200, 700));
			Title.sendTitle(p, 10, 10, 10, ChatColor.LIGHT_PURPLE + "Teleported to the wild");
		}

	}

}
