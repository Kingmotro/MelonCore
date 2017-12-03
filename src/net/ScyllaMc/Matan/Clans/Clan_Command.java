package net.ScyllaMc.Matan.Clans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import org.bukkit.command.CommandExecutor;

import net.ScyllaMc.Matan.Clans.Clan.Clan_Status;
import net.ScyllaMc.Matan.Clans.Clan_Member.Clan_Rank;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class Clan_Command implements CommandExecutor {

	/**
	 * public Clan_Command() { super("clan", "bungeecord.command.clan", new
	 * String[] { "clans", "factions", "faction", "guild", "guilds", "team" });
	 * 
	 * }
	 **/

	public void savePlayerInventory(Player p) {

		try {
			String base64 = inventoryToBase64(p.getInventory());
			//Save this string to file.
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Could not convert to base64, do not save
	}

	public ItemStack[] getSavedPlayerInventory(String data) {

		try {
			return inventoryFromBase64(data).getContents();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ItemStack[0];
	}

	public static String inventoryToBase64(Inventory inventory) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			dataOutput.writeInt(inventory.getSize());

			for (int i = 0; i < inventory.getSize(); i++) {
				dataOutput.writeObject(inventory.getItem(i));
			}

			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
			
			//Converts the inventory and its contents to base64, This also saves item meta-data and inventory type
		} catch (Exception e) {
			throw new IllegalStateException("Could not convert inventory to base64.", e);
		}
	}

	public static Inventory inventoryFromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			CraftInventoryCustom inventory = new CraftInventoryCustom(null, dataInput.readInt());

			for (int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, (ItemStack) dataInput.readObject());
			}

			dataInput.close();
			return inventory;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IOException("Could not decode inventory.", e);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, final String[] args) {

		final Player op = (Player) sender;

		final MelonPlayer p = MelonPlayer.getInstanceOfPlayer(op);

		if (args.length == 0) {

			p.sendMessage(Msg.clan + ChatColor.WHITE.toString() + ChatColor.BOLD + "Clan Commands: ");
			p.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + "Create {name} ");
			p.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + "Info {name} ");
			p.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + "Join {name} ");

			if (p.inClan()) {
				p.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + "Leave ");
				p.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + "Invite {player}");
				p.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + "Kick {player}");
				p.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + "Rename {name}");
				p.sendMessage(ChatColor.GRAY + "- " + ChatColor.WHITE + "Setrank {player} {rank} ");
			}

			return true;
		}

		if (args[0].equalsIgnoreCase("Create")) {

			if (args.length != 2) {
				p.sendMessage(Message.GLOBAL_INVALIDUSE);
				inventories.put(op, op.getInventory().getContents());
				return true;
			}

			p.createClan(args[1]);

			return true;
		}

		if (args[0].equalsIgnoreCase("join")) {

			if (args.length != 2) {
				p.sendMessage(Message.GLOBAL_INVALIDUSE);
				return true;
			}

			Clan.clanByName(args[1]);

			Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
				@Override
				public void run() {

					if (Clan.clanByName(args[1]) != null) {
						Clan.clanByName(args[1]).join(p);
					}

				}
			}, 10);

			return true;
		}

		if (args[0].equalsIgnoreCase("info")) {

			if (args.length == 1 && p.inClan()) {

				Clan_Utils.getStringInfo(p, p.getClan());
				return true;

			} else if (args.length == 2) {

				Clan.clanByName(args[1]);

				Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
					@Override
					public void run() {

						Clan_Utils.getStringInfo(p, Clan.clanByName(args[1]));
					}
				}, 10);

				return true;
			}

			p.sendMessage(Message.GLOBAL_INVALIDUSE);

			return true;
		}

		if (args[0].equalsIgnoreCase("invite")) {

			if (args.length != 2) {
				p.sendMessage(Message.GLOBAL_INVALIDUSE);
				return true;
			}

			if (p.getClan() == null) {
				p.sendMessage(Message.CLAN_NOTIN);
				return true;
			}

			if (p.getClan().getStatus().equals(Clan_Status.OPEN)) {
				p.sendMessage(Msg.clan + ChatColor.RED + "The clan you are in is open and does not require invitations to join ");
				return true;
			}

			if (p.getClan().getStatus().equals(Clan_Status.LOCKDOWN) && !p.getClan().getClanMember(p).getClanRank().equals(Clan_Rank.LEADER)) {
				p.sendMessage(Msg.clan + ChatColor.RED + "The clan is in lockdown mode and only the leader can invite new players");
				return true;
			}

			if (p.getClan().getClanMember(p).getClanRank().equals(Clan_Rank.MEMBER)) {
				p.sendMessage(Message.GLOBAL_NOPERM);
				return true;
			}

			Player target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				p.sendMessage(Msg.clan + ChatColor.RED + args[1] + " is not online!");
				return true;
			}

			p.getClan().invitePlayer(p, MelonPlayer.getInstanceOfPlayer(target));
			return true;
		}

		if (args[0].equalsIgnoreCase("leave")) {

			Clan_Utils.leaveClan(p);
			return true;
		}

		if (args[0].equalsIgnoreCase("kick")) {

			if (args.length != 2) {
				p.sendMessage(Message.GLOBAL_INVALIDUSE);
				return true;
			}

			if (!p.inClan()) {
				p.sendMessage(Message.CLAN_NOTIN);
				return true;
			}

			if (p.getClan().getClanMember(p).getClanRank().equals(Clan_Rank.MEMBER)) {
				p.sendMessage(Message.GLOBAL_NOPERM);
				return true;
			}

			final MelonPlayer kicked = MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(args[1]));
			final MelonPlayer fp = p;

			Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
				@Override
				public void run() {

					if (!p.getClan().getMembersUUID().contains(kicked.getUniqueId())) {
						fp.sendMessage(Msg.clan + ChatColor.RED + "That player isnt in the clan");
						return;
					}

					if (p.getClan().getLeader().getUniqueId().equals(kicked.getUniqueId())) {
						fp.sendMessage(Msg.clan + ChatColor.RED + "The leader of the clan cannot be kicked!");
						return;
					}

					p.getClan().broadcast(Msg.clan + kicked.rank.getTagClosed() + kicked.getName() + ChatColor.RED + " has been kicked from the clan by " + p.rank.getTagClosed() + fp.getName());

					p.getClan().leave(kicked.getUniqueId());

				}
			}, 10);

			return true;
		}

		if (args[0].equalsIgnoreCase("disband")) {

			if (!p.getClan().getClanMember(p).getClanRank().equals(Clan_Rank.LEADER)) {
				p.sendMessage(Message.GLOBAL_NOPERM);
				return true;
			}

			p.getClan().delete();

			return true;
		}

		if (args[0].equalsIgnoreCase("rename")) {
			if (args.length != 2) {
				p.sendMessage(Message.GLOBAL_INVALIDUSE);
				return true;
			}

			if (!p.inClan()) {
				p.sendMessage(Message.CLAN_NOTIN);
				return true;
			}

			if (!p.getClan().getClanMember(p).getClanRank().equals(Clan_Rank.LEADER)) {
				p.sendMessage(Message.GLOBAL_NOPERM);
				return true;
			}

			Clan.clanByName(args[1]);

			Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MelonCore"), new Runnable() {
				@Override
				public void run() {

					Clan_Utils.changeName(p, args[1]);

				}
			}, 10);

			return true;
		}

		if (args[0].equalsIgnoreCase("setrank")) {
			if (args.length != 3) {
				p.sendMessage(Message.GLOBAL_INVALIDUSE);
				p.sendMessage(Msg.clan + ChatColor.RED + "Advilable clan ranks: MEMBER, COUNCIL");
				return true;
			}

			if (!p.inClan()) {
				p.sendMessage(Message.CLAN_NOTIN);
				return true;
			}

			if (!p.getClan().getLeader().getName().toUpperCase().equalsIgnoreCase(p.getName().toUpperCase())) {
				p.sendMessage(Message.GLOBAL_NOPERM);
				return true;
			}

			Player target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				p.sendMessage(Msg.clan + ChatColor.RED + args[1] + " is not online!");
				return true;
			}

			Clan_Mysql_Utils.setRank(p, MelonPlayer.getInstanceOfPlayer(target), Clan_Rank.fromString(args[2]));
			return true;
		}

		p.sendMessage(Message.GLOBAL_INVALIDUSE);
		return true;

	}

}
