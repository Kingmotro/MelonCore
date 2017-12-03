package net.ScyllaMc.Matan.Commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.MelonCore.ServerType;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.Duel;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandBattle {

	public static HashMap<MelonPlayer, ArrayList<MelonPlayer>> invites = new HashMap<MelonPlayer, ArrayList<MelonPlayer>>();

	
	
	public void run(CommandSender sender, String[] args) throws Exception {
		
		if (!(sender instanceof Player)) {
			return;
		}
		
		Player player = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(player);

		if (MelonCore.server != ServerType.BEND) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOTHERE);
			return;
		}

		if (args.length == 0) {
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_AQUA + "Duel Commands:");
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + "/Duel (player) " + ChatColor.GRAY + "- Offer a player to duel / accept a duel");
			return;
		}


		
		
		if (args.length == 1) {
			
			if (Bukkit.getPlayer(args[0]) == null) {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}			

			MelonPlayer invited = MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(args[0]));


			if(invited(invited, p)){
				new Duel(new MelonPlayer[] { p, invited });
				invites.get(p).remove(invited);
				return;
			}
			
			if (Duel.getDuelByPlayer(p) != null) {
				p.sendMessage(Message.BATTLE_INVITE_ALREADYIN_YOU);
				return;
			}
			
			if (Duel.getDuelByPlayer(invited) != null) {
				p.sendMessage(Message.BATTLE_INVITE_ALREADYIN);
				return;
			}
			
			
			if (p.equals(invited)) {
				p.sendMessage(Message.BATTLE_INVITE_SELF);
				return;
			}
			
			if (invited(p, invited)) {
				p.sendMessage(Message.BATTLE_INVITE_ALREADYINVITED);
				return;
			}


			if (p.getLocation().distance(invited.getLocation()) > 6.0D) {
				p.sendMessage(Message.BATTLE_INVITE_TOOFAR);
				return;
			}
			
			p.sendMessage(Message.BATTLE_INVITE_TO, new String[] {invited.rank.getTagClosed() + invited.getName()});
			invited.sendMessage(Message.BATTLE_INVITE_FROM, new String[] {p.rank.getTagClosed() + p.getName()});
			
			invite(p, invited);

			return;
		}

		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);

	}

	public static void invite(final MelonPlayer inviter, final MelonPlayer invited) {

		ArrayList<MelonPlayer> l = new ArrayList<MelonPlayer>();

		if (invites.containsKey(invited)) {
			l = invites.get(invited);
		}
		
		l.add(inviter);
		
		invites.put(invited, l);

		
		new BukkitRunnable() {

			@Override
			public void run() {

				
				if (invited(inviter, invited)) {

					inviter.sendMessage(Message.BATTLE_INVITE_TIMEOUT_TO, new String[] { invited.rank.getTagClosed() + invited.getName()} );
					invited.sendMessage(Message.BATTLE_INVITE_TIMEOUT_FROM, new String[] { inviter.rank.getTagClosed() + inviter.getName()} );

					invites.get(invited).remove(inviter);
				}

			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), 400);

	}

	
	public static boolean invited(MelonPlayer inviter, MelonPlayer invited) {
		
		if (invites.containsKey(invited) && invites.get(invited).contains(inviter)) {
			return true;
		}
		return false;
	}

}
