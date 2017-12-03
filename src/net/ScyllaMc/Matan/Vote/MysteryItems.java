package net.ScyllaMc.Matan.Vote;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MysteryItems {

	public enum Type {
		ITEMSTACK, COINS,;
	}

	public enum Level {
		COMMON, RARE, EPIC;

		public ChatColor getColor() {

			if (this == COMMON) {
				return ChatColor.GRAY;
			} else if (this == RARE) {
				return ChatColor.GOLD;
			} else {
				return ChatColor.DARK_PURPLE;
			}

		}

	}

	public static ArrayList<Reward> REWARD_LIST = new ArrayList<Reward>();

	public static void loadAll() {

		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.COMMON, i(Material.COOKED_MUTTON), 70, "8-24"));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.COMMON, i(Material.SLIME_BALL), 70, "3-5"));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.COMMON, i(Material.IRON_BLOCK), 70, "1-3"));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.COMMON, i(Material.POTION, 8229), 70));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.COMMON, i(Material.POTION, 8233), 70));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.COMMON, i(Material.POTION, 16457), 70));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.COMMON, i(Material.MONSTER_EGG, 91), 70));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.COMMON, i(Material.MONSTER_EGG, 92), 70));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.COMMON, i(Material.MONSTER_EGG, 92), 70));
		REWARD_LIST.add(new Reward(Level.COMMON, 10, "100-200"));

		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.GOLDEN_APPLE), 50, "1-4"));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.ENDER_PEARL), 50));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.MYCEL), 45, "1-3"));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.GLOWSTONE), 45, "4-8"));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.BLAZE_ROD), 50));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.POTION, 16454), 50));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.POTION, 16456), 50));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.POTION, 16427), 50));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.POTION, 16428), 50));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.POTION, 8262), 50));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.POTION, 8269), 50));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.POTION, 8235), 50));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.RARE, i(Material.POTION, 8270), 50));
		REWARD_LIST.add(new Reward(Level.RARE, 55, "200-400"));

		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.EPIC, i(Material.DIAMOND_BLOCK), 10));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.EPIC, i(Material.DIAMOND), 10, "1-3"));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.EPIC, i(Material.NETHER_STAR), 5));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.EPIC, i(Material.SKULL_ITEM, 0), 10));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.EPIC, i(Material.SKULL_ITEM, 1), 10));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.EPIC, i(Material.SKULL_ITEM, 2), 10));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.EPIC, i(Material.SKULL_ITEM, 3), 10));
		REWARD_LIST.add(new Reward(Type.ITEMSTACK, Level.EPIC, i(Material.SKULL_ITEM, 4), 10));
		REWARD_LIST.add(new Reward(Level.EPIC, 105, "500-600"));

	}

	public static ItemStack i(Material m) {
		return new ItemStack(m);
	}

	public static ItemStack i(Material m, int value) {
		return new ItemStack(m, 1, (short) value);
	}

}
