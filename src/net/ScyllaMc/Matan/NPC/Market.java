package net.ScyllaMc.Matan.NPC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class Market {

	public static ArrayList<Market> list = new ArrayList<Market>();
	public static HashMap<Material, Market> items = new HashMap<Material, Market>();

	public static Inventory Menu;

	public enum MarketType {
		WEAPON_SELLER, BLOCK_TRADER, FOOD_MERCHANT, DYE_MERCHANT, FISH_MERCHANT, HORSE_MERCHANT, POTION_MERCHANT;
	}

	MarketType type;
	Material material;
	Inventory inv;
	String name;
	HashMap<ItemStack, Integer> wares;

	public Market(MarketType t, Material m, String n, HashMap<ItemStack, Integer> map) {
		this.type = t;
		this.material = m;
		this.name = n;
		this.wares = map;
		this.inv = Bukkit.createInventory(null, 9, "ERROR MARKET");
		createInventory();
		list.add(this);
		items.put(this.material, this);
	}

	public void createInventory() {

		if (type == null) {
			return;
		}

		boolean sell = false;
		Inventory inv = Bukkit.createInventory(null, 45, name);;

		if (type == MarketType.BLOCK_TRADER) {
			sell = true;
		}


		int pos = 0;

		Msg.debug(wares.toString(), this.getClass());

		for (Map.Entry<ItemStack, Integer> entry : wares.entrySet()) {
			ItemStack item = entry.getKey();
			Integer price = entry.getValue();

			if (type != MarketType.HORSE_MERCHANT) {
				inv.addItem(getItemBUY(item, price));

			} else {

				inv.setItem(pos, getHorseItemBuy(item, price));
				pos++;
			}

		}

		if (sell) {

			int place = 18;

			ItemStack b = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
			ItemMeta m = b.getItemMeta();
			m.setDisplayName(" ");
			b.setItemMeta(m);

			while (place < 27 && place > 17) {
				inv.setItem(place, b);
				place++;
			}

			for (Map.Entry<ItemStack, Integer> entry : wares.entrySet()) {
				ItemStack item = entry.getKey();
				Integer price = entry.getValue();
				
				inv.setItem(place, getItemSELL(item, price));
				place++;

			}
		}

		this.inv = inv;
	}

	public ItemStack getItemSELL(ItemStack item, int price) {
		ItemMeta meta = item.getItemMeta();
		double np = price * 0.7;
		price = (int) Math.round(np);
		meta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "Sell " + ChatColor.DARK_AQUA
				+ MelonCore.firstLetterCaps(item.getType().toString().toLowerCase().replace("_", " ")) + " X"
				+ item.getAmount());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.GREEN + "Earn: " + ChatColor.GRAY + price + " Coins");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack getItemBUY(ItemStack item, int price) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "Buy " + ChatColor.DARK_AQUA
				+ MelonCore.firstLetterCaps(item.getType().toString().toLowerCase().replace("_", " ")) + " X"
				+ item.getAmount());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.RED + "Cost: " + ChatColor.GRAY + price + " Coins");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack getHorseItemBuy(ItemStack item, int price) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "Buy " + meta.getDisplayName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.RED + "Cost: " + ChatColor.GRAY + price + " Coins");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public void openMarketMenu(MelonPlayer p) {

		if (inv == null) {
			p.getOnlinePlayer().openInventory(inv);
		} else {
			this.createInventory();
			p.getOnlinePlayer().openInventory(inv);

		}
	}

	public static void openGlobalMenu(MelonPlayer p) {
		Player pl = p.getOnlinePlayer();

		if (list.size() == 0) {
			p.sendMessage(MelonCore.prefix + ChatColor.RED + "This server has no markets available");
			return;
		}

		if (Menu == null) {
			Menu = Bukkit.createInventory(null, 9, "Market");
			for (Market m : list) {
				ItemStack item = new ItemStack(m.material, 1);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(m.name);
				item.setItemMeta(meta);
				Menu.addItem(item);
			}
			pl.openInventory(Menu);

		} else {
			pl.openInventory(Menu);
		}

	}

}
