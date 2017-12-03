package net.ScyllaMc.Matan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Message;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class CommandGive {

	@SuppressWarnings("deprecation")
	public ItemStack getItem(String s, int amonth) {
		Material m = null;
		int data = 0;

		if (s.contains(":")) {
			String[] split = s.split(":");
			try {
				if (CommandManager.isInt(split[0])) {
					m = Material.getMaterial(Integer.parseInt(split[0]));
				} else {
					if (Material.getMaterial(split[0].toUpperCase()) != null) {
						m = Material.getMaterial(split[0].toUpperCase());
					} else {
						m = Material.AIR;
					}
				}

				data = Integer.parseInt(split[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			if (CommandManager.isInt(s)) {
				m = Material.getMaterial(Integer.parseInt(s));
			} else {
				if (Material.getMaterial(s.toUpperCase()) != null) {
					m = Material.getMaterial(s.toUpperCase());
				}

			}
		}

		if (m == null) {
			return null;
		}
		try {
			return new ItemStack(m, amonth, (short) data);
		} catch (Exception e) {
			return new ItemStack(m, amonth, (short) 0);
		}
	}

	
	public void run(CommandSender sender, String[] args) throws Exception {
		if (!(sender instanceof Player)) {
			return;
		}

		MelonPlayer p = MelonPlayer.getInstanceOfPlayer((Player) sender);
		
		if (!p.hasPermission("server.give")) {
			p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
			return;
		}

		if (args.length <= 2) {
			int amonth = 1;
			if (args.length > 1) {
				if (CommandManager.isInt(args[1])) {
					amonth = Integer.parseInt(args[1]);
				}
			}

			if (getItem(args[0], amonth) != null) {
				ItemStack stack = getItem(args[0], amonth);
				p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Added " + ChatColor.DARK_AQUA + MelonCore.firstLetterCaps(stack.getType().toString().toLowerCase().replace("_", " ")) + " X" + amonth);
				p.getOnlinePlayer().getInventory().addItem(stack);
				return;
			}
		}

		if (args.length >= 2) {
			
			if (!p.hasPermission("server.give.others")) {
				p.sendMessage(Message.GLOBAL_COMMAND_NOPERM);
				return;
			}
			
			if (Bukkit.getPlayer(args[0]) == null) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Player not found");
				return;
			}

			if (!CommandManager.isInt(args[0])) {
				Player gp = Bukkit.getPlayer(args[0]);
				int amonth = 1;
				if (args.length > 2) {
					if (CommandManager.isInt(args[2])) {
						amonth = Integer.parseInt(args[2]);
					}
				}

				if (getItem(args[1], amonth) != null) {
					ItemStack stack = getItem(args[1], amonth);

					p.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Added " + ChatColor.DARK_AQUA
							+ MelonCore.firstLetterCaps(stack.getType().toString().toLowerCase().replace("_", " "))
							+ " X" + amonth + ChatColor.GRAY + " to " + gp.getName());
					gp.sendMessage(MelonCore.prefix + ChatColor.GRAY + "Added " + ChatColor.DARK_AQUA
							+ MelonCore.firstLetterCaps(stack.getType().toString().toLowerCase().replace("_", " "))
							+ " X" + amonth + ChatColor.GRAY + " by " + p.getName());
					gp.getInventory().addItem(stack);
					return;
				}
			}
		}

		p.sendMessage(MelonCore.prefix + ChatColor.RED + "Wrong use of command");
		return;

	}

}
