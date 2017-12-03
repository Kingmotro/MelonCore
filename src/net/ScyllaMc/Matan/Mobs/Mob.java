package net.ScyllaMc.Matan.Mobs;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.Mobs.Types.Mob_Bandit;
import net.ScyllaMc.Matan.Mobs.Types.Mob_Boar;
import net.ScyllaMc.Matan.Mobs.Types.Mob_Cow;
import net.ScyllaMc.Matan.Mobs.Types.Mob_Duck;
import net.ScyllaMc.Matan.Mobs.Types.Mob_Ram;
import net.ScyllaMc.Matan.Mobs.Types.Mob_Sheep;
import net.ScyllaMc.Matan.Mobs.Types.MobType;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

public class Mob {

	public static enum MobTypes {

		BANDIT(new Mob_Bandit()),
			BOAR(new Mob_Boar()),
			DUCK(new Mob_Duck()),
			COW(new Mob_Cow()),
			SHEEP(new Mob_Sheep()),
			RAM(new Mob_Ram());

		private final MobType type;

		MobTypes(MobType type) {
			this.type = type;
		}

		public MobType getType() {
			return this.type;
		}

	}

	public static Mob fromJson(String s) {

		if (MelonCore.isJSONValid(s, Mob.class)) {
			Mob mob = new Gson().fromJson(s, Mob.class);

			return mob;
		}

		return null;
	}

	public static Mob fromLivingEntity(LivingEntity e) {

		if (e != null && e.getCustomName() != null && e.getCustomName() != "" && MelonCore.isJSONValid(e.getCustomName(), Mob.class)) {
			Mob mob = new Gson().fromJson(e.getCustomName(), Mob.class);
			mob.setEntity(e);
			mob.reassign();
			return mob;

		}

		return null;
	}

	public static boolean isMob(LivingEntity e) {

		if (fromLivingEntity(e) != null) {
			return true;
		}

		return false;
	}

	private LivingEntity e;
	private LivingEntity target;
	private int cooldown = 0;

	@Expose
	private boolean angry = false;
	@Expose
	public MobTypes type;
	@Expose
	public int level = 1;
	@Expose
	private double damage = 0;
	@Expose
	private double health = 10.0;

	public Mob(LivingEntity entity, MobTypes type, int level) {
		this.e = entity;
		this.level = level;
		this.type = type;

		this.initialize();
	}

	public String getGson() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(this);
	}

	public void upate() {
		this.e.setCustomNameVisible(true);
		this.e.setCustomName(this.getGson());
	}

	public LivingEntity getEntity() {
		return e;
	}

	public void setLevel(int lvl) {
		this.level = lvl;
	}

	public void setDamage(double d) {
		this.damage = d;
	}

	public double getDamage() {
		return damage;
	}

	public double getMaxHealth() {
		return this.health;
	}

	public double getCurrentHealth() {
		return this.e.getHealth();
	}

	public boolean isAngry() {
		return this.angry;
	}

	public String getName() {
		return this.type.getType().getTypeName(null) + ChatColor.GRAY + " [" + getLevelColor() + "level: " + level + ChatColor.GRAY + "]";
	}

	public String getName(Language lan) {
		return this.type.getType().getTypeName(lan) + ChatColor.GRAY + " [" + getLevelColor() + "level: " + level + ChatColor.GRAY + "]";
	}

	public Mob getSelf() {
		return this;
	}

	public int getLevel() {
		return this.level;
	}

	public EntityCreature getCreature() {

		if (e == null) {
			return null;
		}

		return (EntityCreature) ((EntityInsentient) ((CraftEntity) e).getHandle());
	}

	public MobType getType() {
		return this.type.getType();
	}

	public ItemStack[] getDrops() {
		return getType().getDrops(this);
	}

	public boolean shouldTarget(LivingEntity e) {

		if (this.target != null) {
			return false;
		}

		return this.type.getType().shouldTarget(this, e);
	}

	public void initialize() {

		this.health = this.type.getType().getBaseHealth() + (this.level * this.type.getType().getLevelModifier());
		this.damage = this.type.getType().getBaseDamage() + (this.level * this.type.getType().getLevelModifier());

		EntityInsentient entity = ((EntityInsentient) ((CraftEntity) e).getHandle());

		try {
			entity.getAttributeInstance(GenericAttributes.maxHealth).setValue(this.health);
			entity.setHealth(entity.getMaxHealth());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (entity.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE) != null) {
				entity.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(this.damage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (entity.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED) != null) {
				entity.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.type.getType().getSpeed());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		type.getType().assign(this);

		this.upate();
		this.setCooldown(3);
	}

	public void reassign() {
		type.getType().assign(this);
	}

	public void setCooldown(int i) {

		this.cooldown += i;

	}

	public void setEntity(LivingEntity e) {
		this.e = e;
	}

	public void setSpeed(double speed) {

		try {
			if (getCreature().getAttributeInstance(GenericAttributes.MOVEMENT_SPEED) != null) {
				getCreature().getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speed);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void attack(LivingEntity nt) {

		if (this.shouldTarget(nt)) {
			this.target = nt;

			new BukkitRunnable() {
				@Override
				public void run() {

					if (e == null || target == null || e.isDead()) {
						target = null;
						this.cancel();
						return;
					}

					setSpeed(getType().getSpeed());
					setDamage(getType().getBaseDamage() + (getLevel() * getType().getLevelModifier()));

					if (e.getLocation().distance(target.getLocation()) > type.getType().getViewRange()) {
						target = null;
						this.cancel();
						return;
					}

					if (target instanceof Player) {

						if (!(((OfflinePlayer) target).isOnline())) {
							this.cancel();
							return;
						}

						Player p = (Player) target;
						if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) {
							target = null;
							this.cancel();
							return;
						}

					}

					type.getType().attack(getSelf(), target);

					if (cooldown > 0) {
						cooldown--;
					}

				}
			}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0L, 25L);

		}

	}

	public boolean inCooldown() {

		if (cooldown <= 0) {

			return false;

		}

		return true;
	}

	public ChatColor getLevelColor() {
		if (level > 50) {
			return ChatColor.BLACK;
		}
		if (level > 40) {
			return ChatColor.DARK_RED;
		}
		if (level > 30) {
			return ChatColor.RED;
		}
		if (level > 20) {
			return ChatColor.YELLOW;
		}
		if (level > 10) {
			return ChatColor.GRAY;
		}

		return ChatColor.DARK_GRAY;
	}

	public void clearGoals() {
		try {

			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			bField.set(this.getCreature().goalSelector, new UnsafeList<PathfinderGoalSelector>());
			bField.set(this.getCreature().targetSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(this.getCreature().goalSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(this.getCreature().targetSelector, new UnsafeList<PathfinderGoalSelector>());

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}