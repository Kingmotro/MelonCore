package net.ScyllaMc.Matan.Items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.Mcbender.Matan.MelonCore.ParticleEffect;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class EventEnchantInventory implements Listener {

	public static ParticleEffect Effect = new ParticleEffect(net.Mcbender.Matan.MelonCore.ParticleEffect.ParticleType.CLOUD, 0.02, 5, 0.0001);

	@EventHandler
	public void PlayerRightClick(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) {
			return;
		}
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(e.getPlayer());

		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE)) {
				p.sendMessage(MelonCore.prefix + ChatColor.RED + "Enchantment tables are disabled due the custom leveling system.");
				e.setCancelled(true);
			}
		}

		return;
	}

	@EventHandler
	public void clickEvent(InventoryClickEvent e) {
		ItemStack i = e.getCurrentItem();
		Inventory inventory = e.getInventory();
		MelonPlayer p = MelonPlayer.getInstanceOfPlayer(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()));

		if (!e.getSlotType().equals(SlotType.CONTAINER)) {
			return;
		}

		if (inventory.getName().contains("Place")) {

			if (!Item.isItem(i) || !Item.fromItemStack(i).isEnchantShard()) {
				Msg.debug("FORBIDDEN CLICK ", this.getClass());

				e.setCancelled(true);
				return;
			}

			if (i.getItemMeta() != null && i.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Click to enchant")) {

				//int value = ESession.getSessionValue(p);
				//Item item = ESession.getSessionItem(p);

				//ArrayList<Modifier> mods = Enchantments.getRandomModifiers(p, value, item, item.getItemStack());

				e.setCancelled(true);
				return;
			}

			Msg.debug("CLICKED ", this.getClass());

		}
	}

}
