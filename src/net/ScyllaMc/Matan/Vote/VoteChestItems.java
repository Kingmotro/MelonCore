package net.ScyllaMc.Matan.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VoteChestItems {
	
	public static int getRandom(int upper) {
		Random random = new Random();
		return random.nextInt(upper);
	}

	public void addItem(ArrayList<ItemStack> list, Material m, String tempitemnumber, int value, String name) {
		if (value < 0) {
			value = 0;
		}
		int itemnumber = 1;
		if ((tempitemnumber.equalsIgnoreCase("1-1")) || (tempitemnumber.equalsIgnoreCase(""))) {
			itemnumber = 1;
		} else if (tempitemnumber.contains("-")) {
			try {
				String[] strArray = tempitemnumber.split("-");
				List<Integer> intList = new ArrayList<Integer>();
				String[] arrayOfString1;
				int j = (arrayOfString1 = strArray).length;
				for (int i = 0; i < j; i++) {
					String strInt = arrayOfString1[i];
					intList.add(Integer.valueOf(Integer.parseInt(strInt)));
				}
				Random r = new Random();
				int Low = ((Integer) intList.get(0)).intValue();
				int High = ((Integer) intList.get(1)).intValue();
				int Result = r.nextInt(High - Low) + Low;
				itemnumber = Result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ItemStack item = new ItemStack(m, itemnumber, (short) value);
		ItemMeta meta = item.getItemMeta();

		ArrayList<String> lore = new ArrayList<String>();
		lore.add(name);
		lore.add(ChatColor.GRAY + "Voting Item");
		meta.setLore(lore);

		item.setItemMeta(meta);

		list.add(item);
	}

	public void LoadVoteItems() {
		addItem(MelonCore.VoteChest.Common, Material.IRON_INGOT, "2-8", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.COOKED_BEEF, "2-4", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.GOLDEN_APPLE, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.BAKED_POTATO, "3-6", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.IRON_PICKAXE, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.IRON_SPADE, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.BOW, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.BUCKET, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.ARROW, "11-21", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.MELON_BLOCK, "3-5", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.SMOOTH_BRICK, "23-32", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.SLIME_BLOCK, "1-2", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.SLIME_BALL, "1-2", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.GLASS, "5-16", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.LOG, "5-16", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.COAL, "5-16", 0, "");
		addItem(MelonCore.VoteChest.Common, Material.IRON_INGOT, "1-3", 0, "");

		addItem(MelonCore.VoteChest.Rare, Material.DIAMOND_SPADE, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.SADDLE, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.IRON_BARDING, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.JUKEBOX, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.ENDER_PEARL, "1-2", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.RECORD_8, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.RECORD_9, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.RECORD_12, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.NETHER_BRICK, "4-16", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.PRISMARINE, "4-16", 2, "");
		addItem(MelonCore.VoteChest.Rare, Material.SPONGE, "1-2", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.GLOWSTONE, "4-16", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.BOOKSHELF, "1-5", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.QUARTZ, "4-16", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.LEASH, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.ANVIL, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.IRON_SWORD, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.GOLDEN_APPLE, "1-6", 0, "");
		addItem(MelonCore.VoteChest.Rare, Material.IRON_INGOT, "4-12", 0, "");

		addItem(MelonCore.VoteChest.Legendery, Material.DIAMOND_SWORD, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Legendery, Material.DIAMOND_PICKAXE, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Legendery, Material.OBSIDIAN, "1-2", 0, "");
		addItem(MelonCore.VoteChest.Legendery, Material.BLAZE_ROD, "1-1", 0, "");
		addItem(MelonCore.VoteChest.Legendery, Material.DIAMOND, "1-4", 0, "");
		addItem(MelonCore.VoteChest.Legendery, Material.GOLD_INGOT, "1-16", 0, "");
		addItem(MelonCore.VoteChest.Legendery, Material.EMERALD, "5-7", 0, "");
		addItem(MelonCore.VoteChest.Legendery, Material.NAME_TAG, "1-1", 0, "");
	}
}
