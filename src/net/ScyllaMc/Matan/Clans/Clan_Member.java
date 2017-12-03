package net.ScyllaMc.Matan.Clans;

import java.util.UUID;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;

public class Clan_Member {

	public enum Clan_Rank {
		MEMBER, COUNCIL, LEADER;

		public String getTag() {
			// if(this == LEADER){return ChatColor.GOLD + "✷ "; }
			// if(this == COUNCIL){return ChatColor.DARK_AQUA + "✳ "; }

			if (this == LEADER) {
				return ChatColor.GOLD + "";
			}
			if (this == COUNCIL) {
				return ChatColor.DARK_AQUA + "";
			}

			return ChatColor.GRAY + "";

		}

		public static Clan_Rank fromString(String text) {
			if (text == null) {
				return Clan_Rank.MEMBER;
			}

			for (Clan_Rank rank : Clan_Rank.values()) {
				if (text.toUpperCase().equalsIgnoreCase(rank.toString().toUpperCase())
						|| rank.toString().toUpperCase().contains(text.toUpperCase())) {
					return rank;
				}
			}

			return Clan_Rank.MEMBER;
		}

	}
	

	private UUID ID;
	private MelonPlayer player;
	private Clan_Rank clan_rank;

	
	public Clan_Member(UUID id, Clan_Rank rank) {
		this.ID = id;
		this.player = MelonPlayer.getInstanceOfPlayerFromUUID(id);
		this.player.dowloadData();
		this.clan_rank = rank;

	}

	public MelonPlayer getMelonPlayer() {
		return this.player;
	}

	public String getName() {
		return this.player.getName();
	}

	public Clan getClan() {
		return this.player.getClan();
	}

	public Clan_Rank getClanRank() {
		return this.clan_rank;
	}

	public UUID getUUID() {
		return this.ID;
	}

}
