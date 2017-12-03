package net.ScyllaMc.Matan.MelonPlayer;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;

import net.ScyllaMc.Matan.MelonCore.MelonCore;

public class Buff {

	public static enum BuffType {
		DAMAGE, DEFENCE, COINS;
	}

	public static Buff fromGson(String json) {
		Buff b = new Gson().fromJson(json, Buff.class);
		b.activate();
		return b;
	}

	@Expose
	private int mod = 0;

	@Expose
	private BuffType type;

	@Expose
	private int timeleft, lenght = 0;

	@Expose
	private boolean ended = false, activated = false;

	
	private BuffHandler handler;
	
	private boolean active = false;
	
	private BukkitRunnable task;

	public Buff(BuffHandler handler, BuffType t, int m, int l) {
		this.mod = m;
		this.type = t;
		this.lenght = l;
		this.timeleft = l;
		this.handler = handler;
	}

	public void activate() {

		if (!this.ended && task == null) {

			this.active = true;
			this.activated = true;

			new BukkitRunnable() {
				@Override

				public void run() {

					task = this;

					
					if (timeleft <= 0 || !handler.getPlayer().isOnline()) {
						end();
					}
					

					if (!active) {
						this.cancel();
						return;
					}

					timeleft--;
				}

			}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 20, 20);


		}
	}

	public JsonElement toJson() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJsonTree(this);
	}

	public void pause() {

		if ((!this.ended) && this.active) {
			this.active = false;
		}
	}

	public void end() {
	
		timeleft = 0;
		active = false;
		ended = true;
		this.handler.removeBuff(this);
	}
	
	public int getModifier() {
		return this.mod;
	}

	public BuffType getType() {
		return this.type;
	}

	public boolean isActive() {
		return this.active;
	}

	public int getTimeLeft() {
		return timeleft;
	}

	public String getName() {
		return MelonCore.firstLetterCaps(this.type.toString());
	}
	

	@SuppressWarnings("incomplete-switch")
	public ItemStack getItem() {

		int value = 8261;
		ChatColor color = ChatColor.GRAY;
		switch (this.type) {
		case DAMAGE:
			value = 8201;
			color = ChatColor.RED;
		case DEFENCE:
			value = 8267;
			color = ChatColor.DARK_AQUA;
		}

		ItemStack item = new ItemStack(Material.POTION, 1, (short) value);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(color + getName() + ChatColor.GRAY + " Buff");
		ArrayList<String> lore = new ArrayList<String>();

		if (active) {
			lore.add(ChatColor.LIGHT_PURPLE + "Modifier: " + ChatColor.GRAY + "+" + this.mod + "%");
			lore.add(ChatColor.LIGHT_PURPLE + "Duration: " + ChatColor.GRAY + this.timeleft + " seconds left");
			lore.add(ChatColor.RED + "BUFF CURRENTLY ACTIVE");
		} else if (!ended) {
			lore.add(ChatColor.LIGHT_PURPLE + "Modifier: " + ChatColor.GRAY + "+" + this.mod + "%");
			lore.add(ChatColor.LIGHT_PURPLE + "Duration: " + ChatColor.GRAY + this.lenght + " seconds");
			lore.add(ChatColor.GRAY + "Buff isnt active");
		} else {
			lore.add(ChatColor.RED + "BUFF USED");
		}
		meta.setLore(lore);

		item.setItemMeta(meta);

		return item;
	}
	

}
