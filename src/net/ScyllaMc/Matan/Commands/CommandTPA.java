package net.ScyllaMc.Matan.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.unon1100.TellrawAPI.TellrawAPI;
import com.unon1100.TellrawAPI.TellrawCommand;
import com.unon1100.TellrawAPI.TellrawText;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandTPA {

	public static HashMap<UUID, ArrayList<UUID>> invites = new HashMap<UUID, ArrayList<UUID>>();

	@SuppressWarnings("unused")
	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		Player player = (Player) sender;
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(player);

		if (args.length == 0) {
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_AQUA + "Tpa Commands:");
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + "/tpa (Player) " + ChatColor.GRAY+ "- Offer to teleport to a player");
			p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + "/tpa accept (Player) " + ChatColor.GRAY+ "- Accepts a teleport request");
			return;
		}

		if (args[0].equalsIgnoreCase("accept")) {
			if (args.length == 2) {
				if (Bukkit.getPlayer(args[1]) == null) {
					p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
					return;
				}
				MelonPlayer invited = MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(args[1]));
				if (!invited.isOnline()) {
					p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
					return;
				}
				if (alreadyInvited(invited.getUniqueId(), p.getUniqueId())) {
					invited.getOnlinePlayer().teleport(p.getLocation());
					p.sendMessage(MelonCore.prefix + invited.rank.getTag() + " " + invited.getName() + ChatColor.GREEN
							+ " Has teleported to you.");
					invited.sendMessage(MelonCore.prefix + ChatColor.GREEN + "Teleported to: " + p.rank.getTag() + " "
							+ p.getName());
					invites.get(p.getUniqueId()).remove(invited.getUniqueId());
					return;
				}

				p.sendMessage(MelonCore.prefix + ChatColor.RED + "That player did not ask to teleport to you!");
				return;
			}
		}

		if (args[0].equalsIgnoreCase("decline")) {
			if (args.length == 2) {
				if (Bukkit.getPlayer(args[1]) == null) {
					p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
					return;
				}
				MelonPlayer invited = MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(args[1]));
				if (!invited.isOnline()) {
					p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
					invites.get(invited).remove(p);
					return;
				}

				if (alreadyInvited(invited.getUniqueId(), p.getUniqueId())) {
					p.sendMessage(MelonCore.prefix + ChatColor.RED + "You have declined " + invited.rank.getTagClosed()
							+ invited.getName() + ChatColor.RED + "'s ofer to teleported to you.");
					invited.sendMessage(MelonCore.prefix + p.rank.getTagClosed() + p.getName() + ChatColor.RED
							+ " Has declined your offer to teleport to him.");
					invites.get(p.getUniqueId()).remove(invited.getUniqueId());
					return;
				}

				p.sendMessage(MelonCore.prefix + ChatColor.RED + "That player did not ask to teleport to you!");
				return;
			}
		}

		if (args.length == 1) {
			if (Bukkit.getPlayer(args[0]) == null) {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}
			MelonPlayer invited = MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(args[0]));

			if (alreadyInvited(p.getUniqueId(), invited.getUniqueId())) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You have already sent an invite to that player!");
				return;
			}

			if (p.getUniqueId().equals(invited.getUniqueId())) {
				p.sendMessage(
						MelonCore.prefix + ChatColor.RED + "You cannot tpa to yourself, please find some friends.");
				return;
			}

			if (!invited.isOnline()) {
				p.sendMessage(Message.GLOBAL_COMMAND_PLAYERNOTFOUND);
				return;
			}

			TellrawText text = new TellrawText(MelonCore.prefix + p.rank.getTagClosed() + p.getName()+ ChatColor.DARK_AQUA + " Has asked to teleport to you! /tpa accept " + p.getName() + " to accept.");
			TellrawCommand command = new TellrawCommand(ChatColor.GREEN + ChatColor.BOLD.toString() + "ACCEPT",
					"/tpa accept " + p.getName());
			StringBuilder sb = TellrawAPI.finalize(text);
			TellrawAPI.sendTo(sb, invited.getOnlinePlayer());

			p.sendMessage(MelonCore.prefix + ChatColor.DARK_AQUA + "You have asked to tp to " + invited.rank.getTagClosed() + invited.getName());
			invite(p.getUniqueId(), invited.getUniqueId());
			return;
		}

		p.sendMessage(Message.GLOBAL_COMMAND_INVALIDUSE);

	}

	public static void invite(final UUID p, final UUID invited) {
		ArrayList<UUID> list = new ArrayList<UUID>();

		if (invites.containsKey(invited)) {
			list = invites.get(invited);
		}
		list.add(p);
		invites.put(invited, list);

		
		new BukkitRunnable() {

			@Override
			public void run() {
				
				if(invites.containsKey(invited) && invites.get(invited).contains(p)){
					MelonPlayer inv = MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(invited));
					MelonPlayer mp = MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(p));

					mp.sendMessage(MelonCore.prefix + ChatColor.RED + "Tpa request to " +  inv.rank.getTagClosed() + inv.getName() + ChatColor.RED + " Timed out!");
					inv.sendMessage(MelonCore.prefix + ChatColor.RED + "Tpa request from " +  mp.rank.getTagClosed() + mp.getName() + ChatColor.RED + " Timed out!");

					invites.get(invited).remove(p);
				}
				
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), 400);

	}

	public static boolean alreadyInvited(UUID p, UUID invited) {
		if (invites.containsKey(invited) && invites.get(invited).contains(p)) {
			return true;
		}
		return false;
	}

}
