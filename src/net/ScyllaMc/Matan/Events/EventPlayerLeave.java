package net.ScyllaMc.Matan.Events;



import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class EventPlayerLeave implements Listener {



	

	@EventHandler
    public void PlayerleaveEvent(PlayerQuitEvent event) {
	Player p = event.getPlayer();
	
	MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(p);
			
	mp.saveData();
	mp.invDowloaded = false;
	mp.loggedIn = false;

	
	event.setQuitMessage(MelonCore.prefix + mp.rank.getTagClosed() + p.getName() + ChatColor.RED +  " has left the server!");     

		    
		   
		    
		    
	}

	
	

	
}
