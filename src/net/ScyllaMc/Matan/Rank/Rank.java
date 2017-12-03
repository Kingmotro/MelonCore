package net.ScyllaMc.Matan.Rank;

import java.util.List;

import net.md_5.bungee.api.ChatColor;

public enum Rank {
	OBAMA,
	DEFAULT,
	GOLD,
	HERO,
	MASTER,
	CHAMPION,
	VIP,
	PK,
	YOUTUBER,
	TRAILBUILDER,
	BUILDER,
	BUILDER_PLUS,
	JRMOD,
	MOD,
	SRMOD,
	DEVELOPER,
	ADMIN,
	OWNER,
	USELESS;

	public static Rank fromString(String text) {
		if (text == null) {
			return DEFAULT;
		}

		for (Rank rank : Rank.values()) {
			if (text.toUpperCase().equalsIgnoreCase(rank.getName().toUpperCase())) {
				return rank;
			}
		}

		return DEFAULT;
	}

	PermissionManager PermissionManager = new PermissionManager();

	public ChatColor getColor() {
		switch (this) {
		case OBAMA:
			return ChatColor.DARK_GRAY;
		case USELESS:
			return ChatColor.DARK_GRAY;
		case DEFAULT:
			return ChatColor.GRAY;
		case GOLD:
			return ChatColor.YELLOW;
		case HERO:
			return ChatColor.AQUA;
		case MASTER:
			return ChatColor.DARK_GREEN;
		case CHAMPION:
			return ChatColor.DARK_BLUE;
		case VIP:
			return ChatColor.DARK_AQUA;
		case PK:
			return ChatColor.DARK_AQUA;
		case YOUTUBER:
			return ChatColor.DARK_AQUA;
		case TRAILBUILDER:
			return ChatColor.DARK_AQUA;
		case BUILDER:
			return ChatColor.DARK_AQUA;
		case BUILDER_PLUS:
			return ChatColor.DARK_AQUA;
		case JRMOD:
			return ChatColor.RED;
		case MOD:
			return ChatColor.RED;
		case SRMOD:
			return ChatColor.RED;
		case ADMIN:
			return ChatColor.DARK_RED;
		case DEVELOPER:
			return ChatColor.DARK_RED;

		case OWNER:
			return ChatColor.GREEN;
		default:
			return ChatColor.GRAY;
		}
	}

	public String getName() {
		switch (this) {
		case OBAMA:
			return "Obama";
		case USELESS:
			return "Useless";
		case DEFAULT:
			return "Default";
		case GOLD:
			return "Gold";
		case HERO:
			return "Hero";
		case MASTER:
			return "Master";
		case CHAMPION:
			return "Champion";
		case VIP:
			return "Vip";
		case PK:
			return "ProjectKorra";
		case YOUTUBER:
			return "Youtuber";
		case TRAILBUILDER:
			return "Trail Builder";
		case BUILDER:
			return "Builder";
		case BUILDER_PLUS:
			return "Builder+";
		case JRMOD:
			return "JrMod";
		case MOD:
			return "Mod";
		case SRMOD:
			return "SrMod";
		case ADMIN:
			return "Admin";

		case OWNER:
			return "Owner";
		default:
			return "Default";

		}
	}

	public ChatColor getChatColor() {
		switch (this) {

		case GOLD:
			return ChatColor.WHITE;
		case HERO:
			return ChatColor.WHITE;
		case MASTER:
			return ChatColor.WHITE;
		case CHAMPION:
			return ChatColor.WHITE;
		case VIP:
			return ChatColor.DARK_AQUA;
		case YOUTUBER:
			return ChatColor.DARK_AQUA;
		case TRAILBUILDER:
			return ChatColor.DARK_AQUA;
		case BUILDER:
			return ChatColor.DARK_AQUA;
		case BUILDER_PLUS:
			return ChatColor.DARK_AQUA;
		case JRMOD:
			return ChatColor.RED;
		case MOD:
			return ChatColor.RED;
		case SRMOD:
			return ChatColor.RED;
		case ADMIN:
			return ChatColor.RED;
		case DEVELOPER:
			return ChatColor.RED;
		case PK:
			return ChatColor.DARK_AQUA;
		case OWNER:
			return ChatColor.GREEN;
		case OBAMA:
			return ChatColor.DARK_GRAY;
		case USELESS:
			return ChatColor.DARK_GRAY;
		default:
			return ChatColor.GRAY;
		}
	}

	@SuppressWarnings("static-access")
	public List<String> getPermissions() {
		switch (this) {
		case OBAMA:
			return PermissionManager.obama_rank;
		case USELESS:
			return PermissionManager.obama_rank;
		case DEFAULT:
			return PermissionManager.default_rank;
		case GOLD:
			return PermissionManager.gold_rank;
		case HERO:
			return PermissionManager.hero_rank;
		case MASTER:
			return PermissionManager.master_rank;
		case CHAMPION:
			return PermissionManager.champion_rank;
		case VIP:
			return PermissionManager.vip_rank;
		case PK:
			return PermissionManager.vip_rank;
		case YOUTUBER:
			return PermissionManager.vip_rank;
		case TRAILBUILDER:
			return PermissionManager.default_rank;
		case BUILDER:
			return PermissionManager.vip_rank;
		case BUILDER_PLUS:
			return PermissionManager.admin_rank;
		case JRMOD:
			return PermissionManager.jrmod_rank;
		case MOD:
			return PermissionManager.mod_rank;
		case SRMOD:
			return PermissionManager.srmod_rank;
		case ADMIN:
			return PermissionManager.admin_rank;
		case OWNER:
			return PermissionManager.admin_rank;

		default:
			return PermissionManager.default_rank;

		}

	}

	public String getTag() {

		return getColor() + getName();

	}

	public String getTagClosed() {
		if (this.equals(DEFAULT)) {
			return ChatColor.GRAY + "";
		}
		return getColor() + "[" + getName() + "] ";

	}

}
