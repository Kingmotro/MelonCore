package net.ScyllaMc.Matan.Vote;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class VoteListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onVotifierEvent(VotifierEvent event) {

		Msg.debug("VOTE EVENT", this.getClass());

		Vote vote = event.getVote();
		String name = vote.getUsername();
		@SuppressWarnings("deprecation")
		OfflinePlayer vp = Bukkit.getOfflinePlayer(name);
		if (vp.isOnline()) {
			MelonPlayer p = MelonPlayer.getInstanceOfPlayer(vp);
			p.addVotes(1);

			MelonCore.VoteChest.display(p);
			p.sendMessage(ChatColor.GOLD + "Thank you for supporting our server! claim your rewards at spawn!");
			Bukkit.broadcastMessage(MelonCore.prefix + ChatColor.GREEN + p.getName() + ChatColor.DARK_AQUA + " Has just voted and recived a " + ChatColor.LIGHT_PURPLE + "Mystery Key!");
			Player op = Bukkit.getPlayer(name);
			op.playSound(op.getLocation(), Sound.LEVEL_UP, 1, 1);
		}
	}

}
