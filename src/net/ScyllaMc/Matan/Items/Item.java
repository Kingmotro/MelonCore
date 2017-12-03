package net.ScyllaMc.Matan.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class Item {

	public static enum Items {

		STAFF(new Item(Material.STICK, null, "The test Staff!", 1)),
		STAFF_HIGHLVL(new Item(Material.BLAZE_ROD, null, "HIGH LEVEL STAFF", 53)),

		ENCHANT_I(new Item(Material.PRISMARINE_CRYSTALS, Modifier.ENCHANT_I, ChatColor.LIGHT_PURPLE + "Enchtment Crystal")),
		ENCHANT_II(new Item(Material.PRISMARINE_SHARD, Modifier.ENCHANT_II, ChatColor.LIGHT_PURPLE + "Enchtment Shard"));

		private final Item item;

		Items(Item item) {
			this.item = item;
		}

		public Item getItem() {
			return this.item;
		}

	}

	public static enum ModifierType {
		DAMAGE, DEFENCE, FIRE, POSION, ENCHANT;
	}

	public static enum Modifier {
		DAMAGE_I(ModifierType.DAMAGE, 10, 1, 10),
		DAMAGE_II(ModifierType.DAMAGE, 30, 2, 20),
		DAMAGE_III(ModifierType.DAMAGE, 60, 3, 30),
		DAMAGE_IV(ModifierType.DAMAGE, 100, 4, 40),
		DEFENCE_I(ModifierType.DEFENCE, 10, 1, 10),
		DEFENCE_II(ModifierType.DEFENCE, 30, 2, 20),
		DEFENCE_III(ModifierType.DEFENCE, 60, 3, 30),
		DEFENCE_IV(ModifierType.DEFENCE, 100, 4, 40),
		FIRE_I(ModifierType.FIRE, 60, 1, 5),
		FIRE_II(ModifierType.FIRE, 100, 2, 10),
		POSION_I(ModifierType.POSION, 60, 1, 5),
		POSION_II(ModifierType.POSION, 100, 2, 10),
		ENCHANT_I(ModifierType.ENCHANT, 100000000, 1, 20),
		ENCHANT_II(ModifierType.ENCHANT, 100000000, 2, 50);

		int weight = 10;
		int level = 1;
		int mod = 10;
		ModifierType type;

		Modifier(ModifierType type, int weight, int level, int mod) {
			this.level = level;
			this.weight = weight;
			this.mod = mod;
			this.type = type;
		}

		public static Modifier fromString(String text) {

			for (Modifier mod : Modifier.values()) {
				if (text.toUpperCase().equalsIgnoreCase(mod.getName().toUpperCase())) {
					return mod;
				}
			}

			return null;
		}

		public int getModifier() {
			return this.mod;
		}

		public int getLevel() {
			return this.level;
		}

		public int getWeight() {
			return this.weight;
		}

		public ModifierType getType() {
			return this.type;
		}

		public String getName() {
			return this.toString();
		}

	}

	public static Item fromJson(String s) {

		if (MelonCore.isJSONValid(s, Item.class)) {
			return new Gson().fromJson(s, Item.class);
		}

		return null;
	}

	public static Item fromItemStack(ItemStack is) {

		if (is != null && is.hasItemMeta() && is.getItemMeta().getDisplayName() != null && MelonCore.isJSONValid(is.getItemMeta().getDisplayName(), Item.class)) {
			Item item = new Gson().fromJson(is.getItemMeta().getDisplayName(), Item.class);
			return item;

		}

		return null;
	}

	public static boolean isItem(ItemStack item) {

		if (fromItemStack(item) != null) {
			return true;
		}

		return false;
	}

	@Expose
	private int level;
	@Expose
	private String name;
	@Expose
	private int speed;
	@Expose
	public JSONObject modifiers = new JSONObject();
	@Expose
	private Material material;

	public Item(Material material, JSONObject modifiers, String name, int level) {
		this.material = material;

		if (modifiers != null) {
			this.modifiers = modifiers;
			Msg.debug("MODS: " + this.modifiers, this.getClass());
		}
		this.level = level;
		this.name = name;

	}

	@SuppressWarnings("unchecked")
	public Item(Material material, Modifier mod, String name) {
		this.material = material;
		this.name = name;
		this.modifiers.put(mod, mod.level);
	}

	public String getName(MelonPlayer p) {

		return this.name;
	}

	public ItemStack getItemStack() {

		ItemStack i = new ItemStack(material, 1);
		ItemMeta meta = i.getItemMeta();

		meta.setDisplayName(new Gson().toJson(this));
		i.setItemMeta(meta);

		return i;
	}

	public ItemStack giveItem(MelonPlayer p) {
		if (p.isOnline()) {

			ItemStack is = this.getItemStack();
			p.getOnlinePlayer().getInventory().addItem(is);

			return is;
		}

		return null;
	}

	public void update(ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(new Gson().toJson(this));
		i.setItemMeta(meta);
	}

	public Modifier getModifier(MelonPlayer p, ModifierType type) {

		for (Object m : this.modifiers.keySet()) {

			if (p != null && p.level >= this.level) {

				if (Modifier.fromString(m.toString()).getType().equals(type)) {
					return Modifier.fromString(m.toString());
				}

			} else if (p == null) {

				if (Modifier.fromString(m.toString()).getType().equals(type)) {
					return Modifier.fromString(m.toString());
				}
			}

		}

		return null;
	}

	public int getLevel() {

		return this.level;
	}

	public void setModifiers(JSONObject o) {
		this.modifiers = o;
	}

	public JSONObject getModifiers() {
		return this.modifiers;
	}

	public boolean isEnchantShard() {

		if (this.getModifier(null, ModifierType.ENCHANT).getModifier() > 0) {
			return true;
		}

		return false;
	}
}
