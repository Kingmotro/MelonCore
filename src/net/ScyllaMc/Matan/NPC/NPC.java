package net.ScyllaMc.Matan.NPC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Quests.Quest;
import net.minecraft.server.v1_8_R3.World;

public class NPC {

	public static HashMap<UUID, NPC> list = new HashMap<UUID, NPC>();

	public enum NPCType {
		QUEST_NPC, QUEST_START_NPC, MERCHENT_NPC, NPC, BLOCK_NPC, BENDER;
	}

	public enum MerchantType {
		WEAPON_SELLER, BLOCK_TRADER, FOOD_MERCHANT, DYE_MERCHANT, FISH_MERCHANT, HORSE_MERCHANT, POTION_MERCHANT;
	}

	NPCType type;
	Location loc;
	EntityType et;
	String name;
	Entity e;
	Quest q;
	Hologram holo;
	int level = 1;
	MerchantType mt;
	Inventory inv;

	public NPC(NPCBlock npc) {
		type = NPCType.QUEST_NPC;
		this.name = npc.getName();
		this.holo = npc.holo;
		this.level = npc.level;
		// this.q = npc.;
		this.mt = null;
	}

	public NPC(EntityType et, Location loc, NPCType type, String name, int level, MerchantType mt, Quest q) {
		Entity e = null;
		this.et = et;
		this.loc = loc;
		this.type = type;
		this.level = level;
		this.name = name;
		this.q = q;
		this.mt = mt;
		this.inv = Bukkit.createInventory(null, 9, "ERROR MERHCANT");
		createInventory();

		if (!loc.getChunk().isLoaded()) {
			MelonCore.NPCManager.addUnloadedNpc(this);
		} else {
			if (et.equals(EntityType.VILLAGER)) {
				e = NPCRegister.spawnEntity(new NPCVillager(((CraftWorld) loc.getWorld()).getHandle()), loc)
						.getBukkitEntity();
				((Villager) e).setProfession(getProfession(mt));

			} else if (et.equals(EntityType.WITCH)) {
				e = NPCRegister.spawnEntity(new NPCWitch(((CraftWorld) loc.getWorld()).getHandle()), loc)
						.getBukkitEntity();

			} else {
				e = loc.getWorld().spawnEntity(loc, et);
			}
			list.put(e.getUniqueId(), this);
			e.setCustomNameVisible(false);
			e.setCustomName("");
			this.e = e;
		}

		if (this.holo != null) {
			this.holo.delete();
		}
		this.holo = HologramsAPI.createHologram(Bukkit.getPluginManager().getPlugin("MelonCore"), loc.add(0, 1.8, 0));
		holo.appendTextLine(ChatColor.DARK_GREEN + name);
		if (type == NPCType.QUEST_NPC) {
			holo.appendTextLine(ChatColor.RED + "Quest Npc" + ChatColor.GRAY + " [Lvl " + level + "]");
		}
		if (type == NPCType.QUEST_START_NPC) {
			holo.appendTextLine(ChatColor.RED + "Quest Start Npc" + ChatColor.GRAY + " [Lvl " + level + "]");
		}
		if (type == NPCType.MERCHENT_NPC && !name.contains("Trader")) {
			holo.appendTextLine(ChatColor.DARK_AQUA + "Merchant");
		} else if (type == NPCType.MERCHENT_NPC && name.contains("Trader")) {
			holo.appendTextLine(ChatColor.DARK_AQUA + "Trader merchant");
		}
		if (type == NPCType.BENDER) {
			holo.appendTextLine(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Bending Master Npc");
		}

		if (type == NPCType.NPC) {
			holo.appendTextLine(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Npc");
		}

	}

	public static NPC fromEntity(Entity e) {
		if (list.containsKey(e.getUniqueId())) {
			return list.get(e.getUniqueId());
		}
		return null;
	}

	public void interact(MelonPlayer p) {

		if (type == NPCType.QUEST_NPC) {
			if (q.started(p)) {
				if (!p.hasPermission("server.mod")) {
					p.sendMessage(MelonCore.prefix + ChatColor.RED + "You do not have the permission to enter the quests beta!");
				} else if (p.level < level) {
					p.sendMessage(MelonCore.prefix + ChatColor.RED + "You need to be level " + level
							+ " Or above to start this quest!");
					return;
				} else {
					q.start(p, this);
					return;
				}
			} else {
				p.sendMessage(MelonCore.prefix + ChatColor.RED
						+ "You have not started this quest yet! find the start npc to start it!");
			}
		}

		if (type == NPCType.QUEST_START_NPC) {
			if (!p.hasPermission("server.mod")) {
				p.sendMessage(
						MelonCore.prefix + ChatColor.RED + "You do not have the permission to enter the quests beta!");
			} else if (p.level < level) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "You need to be level " + level
						+ " Or above to start this quest!");
				return;
			} else {
				q.start(p, this);
				return;
			}
		}

		if (type == NPCType.MERCHENT_NPC) {
			if (!p.isOnline()) {
				return;
			}
			Player op = p.getOnlinePlayer();
			op.openInventory(inv);

			return;
		}

	}

	public String getName() {
		return name;
	}

	public void respawnNPC() {
		if (e == null && !e.isDead()) {
			e.remove();
		}
		for (UUID npc : list.keySet()) {
			if (list.get(npc).equals(this)) {
				e.remove();
			}
		}

		if (et.equals(EntityType.VILLAGER)) {
			e = NPCRegister.spawnEntity(new NPCVillager(((CraftWorld) loc.getWorld()).getHandle()), loc)
					.getBukkitEntity();

		} else if (et.equals(EntityType.WITCH)) {
			e = NPCRegister.spawnEntity(new NPCWitch(((CraftWorld) loc.getWorld()).getHandle()), loc).getBukkitEntity();

		} else {
			e = loc.getWorld().spawnEntity(loc, et);
			e.setCustomNameVisible(false);
		}
		e.setCustomName("");
	}

	public void createInventory() {

		if (mt == null) {
			return;
		}
		HashMap<ItemStack, Integer> map = new HashMap<ItemStack, Integer>();
		final HashMap<ItemStack, Integer> mapprices = new HashMap<ItemStack, Integer>();

		boolean sell = false;
		Inventory inv = null;
		if (mt == MerchantType.BLOCK_TRADER) {
			inv = Bukkit.createInventory(null, 45, "Block Trader");
			map = NPCManager.SHOP_BLOCKS;
			sell = true;
		}
		if (mt == MerchantType.FOOD_MERCHANT) {
			inv = Bukkit.createInventory(null, 18, "Food Merchant");
			map = NPCManager.SHOP_FOOD;
		}
		if (mt == MerchantType.DYE_MERCHANT) {
			inv = Bukkit.createInventory(null, 18, "Dye Merchant");
			map = NPCManager.SHOP_DYE;
		}
		if (mt == MerchantType.FISH_MERCHANT) {
			inv = Bukkit.createInventory(null, 9, "Fish Hut Merchant");
			map = NPCManager.SHOP_FISH;
		}
		if (mt == MerchantType.HORSE_MERCHANT) {
			inv = Bukkit.createInventory(null, 9, "Horse Merchant");
			map = NPCManager.SHOP_HORSE;
		}
		if (mt == MerchantType.POTION_MERCHANT) {
			inv = Bukkit.createInventory(null, 45, "Potion Merchant");
			map = NPCManager.SHOP_POTION;
		}

		if (map == null) {
			return;
		}

		int pos = 0;
		for (ItemStack item : map.keySet()) {
			ItemStack i = item;
			int price = map.get(item);
			mapprices.put(i, price);

			if (mt != MerchantType.HORSE_MERCHANT) {
				inv.addItem(getItemBUY(i, price));

			} else {

				inv.setItem(pos, getHorseItemBuy(i, price));
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

			for (Entry<ItemStack, Integer> e : mapprices.entrySet()) {
				ItemStack i = e.getKey();
				int price = e.getValue();
				inv.setItem(place, getItemSELL(i, price));
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

	public Profession getProfession(MerchantType mrt) {

		if (mrt != null) {
			if (mrt.equals(MerchantType.POTION_MERCHANT)) {
				return Profession.PRIEST;
			}
			if (mrt.equals(MerchantType.BLOCK_TRADER)) {
				return Profession.BLACKSMITH;
			}
			if (mrt.equals(MerchantType.DYE_MERCHANT)) {
				return Profession.LIBRARIAN;
			}
			if (mrt.equals(MerchantType.HORSE_MERCHANT)) {
				return Profession.FARMER;
			}
			if (mrt.equals(MerchantType.FOOD_MERCHANT)) {
				return Profession.FARMER;
			}
			if (mrt.equals(MerchantType.FISH_MERCHANT)) {
				return Profession.FARMER;
			}
		}
		return Profession.BUTCHER;
	}

}
