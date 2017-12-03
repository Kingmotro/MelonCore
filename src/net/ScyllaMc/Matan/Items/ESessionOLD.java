package net.ScyllaMc.Matan.Items;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.Items.Item.ModifierType;
import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonCore.Msg;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class ESessiwfwefweon {

	
	public static HashMap<MelonPlayer, ESession> sessions = new HashMap<MelonPlayer, ESession>();
	
	public static ESession getESession(MelonPlayer p, Item item){
		
		if(sessions.containsKey(p)){
			sessions.remove(p);
		}
		
		return new ESession(p, item);
	}
	
	public static int getSessionValue(MelonPlayer p){
		
		if(sessions.containsKey(p)){
			return sessions.get(p).getValue();
		}
		
		return 0;
	}
	
	
	public static Item getSessionItem(MelonPlayer p){
		
		if(sessions.containsKey(p)){
			return sessions.get(p).getItem();
		}
		
		return null;
	}
	
	
	private MelonPlayer p;
	private int value;
	private Item item;
	
	public ESession(final MelonPlayer p, Item item) {

		if (MelonCore.Shuttingdown) {
			p.sendMessage(MelonCore.prefix + ChatColor.RED + "You cannot enchant right now, the server is restarting!");
			return;
		}

		this.p = p;
		this.item = item;
		
		sessions.put(p, this);
		
		p.playSound(Sound.ENDERMAN_TELEPORT, 1, 1);
		
		
		
		new BukkitRunnable() {

			Inventory inv = createInv();

			@Override
			public void run() {
				
				if(!p.isOnline()){
					sessions.remove(p);
					this.cancel();
					return;
				}
				
				if(inv.getViewers().size() == 0){
					
					for(ItemStack i : getAllItems(inv)){
						
						if(i != null){
							p.getOnlinePlayer().getInventory().addItem(i);
						}
					}
					
					Msg.debug(ChatColor.RED + "ESESSION OVER FOR " + p.getName(), ESession.class);
					this.cancel();
					return;
				}
				
				value = getItemValue(inv);
				inv.setItem(8, getConfirmStack(value));
				
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("MelonCore"), 0L, 20L);

	}


	public Inventory createInv() {
		
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Place shards to power");

		ItemStack b = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);{
			ItemMeta meta = b.getItemMeta();
			meta.setDisplayName(" ");
			b.setItemMeta(meta);
		}

		
		inv.setItem(7, b);
		inv.setItem(8, getConfirmStack(0));

		Player op = p.getOnlinePlayer();
		op.openInventory(inv);
		
		
		Msg.debug(ChatColor.RED + "OPENING ENCHANT INV", this.getClass());
		return inv;
	}

	
	
	public ItemStack getConfirmStack(int c){
		ItemStack confirm = new ItemStack(Material.STAINED_CLAY, c, (short) 5);{
			ItemMeta meta = confirm.getItemMeta();
			meta.setDisplayName(ChatColor.GREEN + "Click to enchant");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "-----[ Info: ] -----");
			lore.add(ChatColor.GRAY + "Enchanting power -> " + ChatColor.GREEN + c);
			lore.add(ChatColor.GRAY + "this will consume the shard you have");
			lore.add(ChatColor.GRAY + "placed in the window and enchant your item");
			lore.add(ChatColor.GRAY + "-------------------------");
			meta.setLore(lore);
			confirm.setItemMeta(meta);
		}

		return confirm;
	}
	
	public ItemStack[] getAllItems(Inventory inv){
		
		ItemStack[] items = new ItemStack[inv.getSize()];
		
		int c = 0;
		for(ItemStack i : inv.getContents()){
		
			if(i != null){
				
				if(Item.isItem(i)){
					items[c] = i;
					c++;
				}	
			}
		}
				
		return items;
	}
	
	

	public Integer getItemCount(Inventory inv){
		
		int c = 0;
		for(ItemStack i : inv.getContents()){
		
			if(i != null){
				
				if(Item.isItem(i)){
					c++;
				}	
			}
		}
				
		return c;
	}
	

	public Integer getItemValue(Inventory inv){
		
		int c = 0;
		for(ItemStack i : inv.getContents()){
		
			if(i != null){
				
				if(Item.isItem(i) && Item.fromItemStack(i).isEnchantShard()){
					
					Item item = Item.fromItemStack(i);
					c+= item.getModifier(null, ModifierType.ENCHANT).getModifier() * i.getAmount();
				}	
			}
		}
				
		return c;
	}

	
	public int getValue(){
		return this.value;
	}
	
	public Item getItem(){
		return this.item;
	}
	
}

