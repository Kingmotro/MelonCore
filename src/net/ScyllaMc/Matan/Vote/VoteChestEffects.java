package net.ScyllaMc.Matan.Vote;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;

import java.util.ArrayList;
import java.util.Random;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Util.RandomTp;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.MelonCore.Msg.Message;

public class VoteChestEffects {
	public void playOpening(final MelonPlayer p) {

		Location chest = MelonCore.voteloc;

		final PlayerConnection pc = ((CraftPlayer) p.getOnlinePlayer()).getHandle().playerConnection;
		final PacketPlayOutBlockAction close = new PacketPlayOutBlockAction(new BlockPosition(chest.getX(), chest.getY(), chest.getZ()), CraftMagicNumbers.getBlock(chest.getWorld().getBlockAt(chest)), 1, 0);
		final PacketPlayOutBlockAction open = new PacketPlayOutBlockAction(new BlockPosition(chest.getX(), chest.getY(), chest.getZ()), CraftMagicNumbers.getBlock(chest.getWorld().getBlockAt(chest)), 1, 1);

		p.addVotes(-1);

		p.playSound(Sound.ANVIL_USE, 1.0F, 1.0F);
		new BukkitRunnable() {
			int timer = 0;
			String item1 = ChatColor.GRAY + "?????";
			String item2 = ChatColor.GRAY + "?????";

			public void run() {
				if (!p.isOnline()) {
					cancel();
					return;
				}

				if (timer <= 2) {
					p.playSound(Sound.CLICK, 1.0F, 1.0F);
				}

				playLava(p);

				if (timer == 0) {
					shootFirework(p);
				}

				if (this.timer == 2) {
					MelonCore.VoteChest.delete(p);
					p.voteHolo = HologramsAPI.createHologram(Bukkit.getPluginManager().getPlugin("MelonCore"), MelonCore.votelocholo);
					Player op = p.getOnlinePlayer();
					p.voteHolo.insertTextLine(0, Message.MYSTERY_BOX_OPEN.getTransMsg(p.lan));
					p.voteHolo.insertTextLine(1, item1);
					p.voteHolo.insertTextLine(2, item2);
					VisibilityManager visiblityManager = p.voteHolo.getVisibilityManager();
					visiblityManager.showTo(op);
					visiblityManager.setVisibleByDefault(false);
				}

				if (this.timer == 3) {
					pc.sendPacket(open);
					playLava(p);
					p.playSound(Sound.FIREWORK_BLAST, 1.0F, 1.0F);
					item1 = getRandomItem(p);
					MelonCore.VoteChest.setLine(p, 1, item1);
				}

				if (this.timer == 5) {
					playLava(p);
					p.playSound(Sound.FIREWORK_BLAST, 1.0F, 1.0F);
					item2 = getRandomItem(p);
					MelonCore.VoteChest.setLine(p, 2, item2);
				}

				if (this.timer == 9) {
					p.playSound(Sound.CHEST_CLOSE, 1.0F, 1.0F);
					pc.sendPacket(close);
					MelonCore.VoteChest.display(p);
					p.saveInv();
					cancel();
					return;
				}

				this.timer += 1;
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0L, 20L);
	}

	public String getRandomItem(MelonPlayer p) {

		Reward reward = null;

		int stenght = 90;
		ArrayList<Reward> possible = getPossibilities(stenght);

		double weight = 0.0;

		for (Reward r : possible) {
			weight += r.getWeight();
		}

		double random = Math.random() * weight;

		for (Reward r : possible) {
			random -= r.getWeight();

			if (random <= 0) {
				reward = r;
				break;
			}
		}

		return reward.giveToPlayer(p);

	}

	public static ArrayList<Reward> getPossibilities(int s) {
		ArrayList<Reward> possible = new ArrayList<Reward>();

		for (Reward r : MysteryItems.REWARD_LIST) {

			if (r.getWeight() <= s) {

				possible.add(r);

			}

		}

		return possible;
	}

	public void shootFirework(MelonPlayer p) {
		Firework f = (Firework) p.getLocation().getWorld().spawn(MelonCore.voteloc, Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.FUCHSIA).withFade(Color.PURPLE).build());
		fm.setPower(0);
		f.setFireworkMeta(fm);
	}

	@SuppressWarnings("deprecation")
	public void playLava(MelonPlayer p) {
		Random rand = new Random();
		int y = rand.nextInt(5) + 8;
		Location l = RandomTp.getRandomLocation(MelonCore.votelocholo, 2, 1, y);
		p.getOnlinePlayer().playEffect(l, Effect.LAVA_POP, 2);
	}
}
