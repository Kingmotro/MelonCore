package net.ScyllaMc.Matan.Items;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import net.ScyllaMc.Matan.Items.Item.Modifier;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class Enchantments {

	@SuppressWarnings("unchecked")
	public static ArrayList<Modifier> getRandomModifiers(MelonPlayer p, int strength, Item item, ItemStack is) {

		ArrayList<Modifier> mods = new ArrayList<Modifier>();
		int s = strength;

		Msg.debug(ChatColor.RED + "CHECK ONE", Enchantments.class);
		while (s >= 20) {

			Msg.debug(ChatColor.RED + "CHECK TWO", Enchantments.class);

			Modifier mod = getRandomMod(s, item);

			if (mod != null) {
				JSONObject o = item.modifiers;
				o.put(mod, mod.getLevel());
				
				Msg.debug(ChatColor.RED + "CHECK THREE " + mod.getName(), Enchantments.class);
				item.setModifiers(o);
				s -= mod.getWeight();
				mods.add(mod);
			} else {
				break;
			}

		}

		p.getOnlinePlayer().getInventory().remove(is);
		p.getOnlinePlayer().getInventory().addItem(item.getItemStack());
		
		
		Msg.debug(ChatColor.RED + "CHECK FOUR", Enchantments.class);

		return mods;
	}

	
	
	public static Modifier getRandomMod(int stenght, Item item) {

		ArrayList<Modifier> possible = getPossibilities(stenght, item);
		Msg.debug(ChatColor.RED + "CHECK TWO.2 " + possible.size(), Enchantments.class);

		double weight = 0.0;

		for (Modifier r : possible) {
			weight += r.getWeight();
		}
		Msg.debug(ChatColor.RED + "CHECK TWO.3 " + weight, Enchantments.class);

		double random = Math.random() * weight;

		for (Modifier r : possible) {
			random -= r.getWeight();
			Msg.debug(ChatColor.RED + "CHECK TWO.4", Enchantments.class);

			if (random <= 0) {
				Msg.debug(ChatColor.RED + "CHECK TWO.5", Enchantments.class);

				return r;
			}
		}

		return null;
	}

	public static ArrayList<Modifier> getPossibilities(int s, Item item) {
		ArrayList<Modifier> possible = new ArrayList<Modifier>();

		for (Modifier m : Modifier.values()) {

			if (m.getWeight() < s) {

				int c = 0;
				for (Object o : item.getModifiers().keySet()) {

					if (m.getType().equals(Modifier.fromString(o.toString()).getType())) {
						c++;
					}

				}

				if (c == 0) {
					possible.add(m);
				}

			}

		}

		return possible;
	}

}
