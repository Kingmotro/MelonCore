package net.ScyllaMc.Matan.Vote;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class VoteChestHologram {

	public void addLine(MelonPlayer p, String s) {
		if (!p.isOnline()) {
			return;
		}

		if (p.voteHolo == null) {
			display(p);
			return;
		}
		p.voteHolo.appendTextLine(s);
		p.voteHolo = p.voteHolo;
		p.getOnlinePlayer().playSound(p.getOnlinePlayer().getLocation(), Sound.LAVA_POP, 1.0F, 1.0F);
	}

	public void delete(MelonPlayer p) {
		if (p.voteHolo == null) {
			return;
		}
		p.voteHolo.delete();
	}

	public void display(MelonPlayer p) {
		if (!p.isOnline()) {
			return;
		}

		if (p.voteHolo != null) {
			delete(p);
		}
		Player op = p.getOnlinePlayer();

		p.voteHolo = HologramsAPI.createHologram(Bukkit.getPluginManager().getPlugin("MelonCore"), MelonCore.votelocholo);
		VisibilityManager visiblityManager = p.voteHolo.getVisibilityManager();
		p.voteHolo.insertTextLine(0, Message.MYSTERY_BOX.getTransMsg(p.lan));
		p.voteHolo.insertTextLine(1, ChatColor.GRAY + "---------------");
		p.voteHolo.insertTextLine(2, Message.MYSTERY_BOX_HOLO_GETMOREKEYS.getTransMsg(p.lan));
		p.voteHolo.insertTextLine(3, Message.MYSTERY_BOX_HOLO_KEYS.getTransMsg(p.lan, new String[] { p.votetokens + "" }));
		visiblityManager.showTo(op);
		visiblityManager.setVisibleByDefault(false);
	}

	public void setLine(MelonPlayer p, int l, String s) {
		if (!p.isOnline()) {
			return;
		}
		if (p.voteHolo.getLine(l) == null) {
			return;
		}
		p.voteHolo.removeLine(l);
		p.voteHolo.insertTextLine(l, s);
		p.voteHolo = p.voteHolo;
	}
}
