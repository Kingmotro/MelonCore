package net.ScyllaMc.Matan.MelonCore;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.ScyllaMc.Matan.Mobs.MountItem;

public class Packets {


	/**

		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this,ListenerPriority.HIGH, PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_ITEMS) {
			@Override
			public void onPacketSending(PacketEvent event) {
						
			 if (event.getPacketType() == PacketType.Play.Server.SET_SLOT) {
				PacketContainer packet = event.getPacket().deepClone();
				StructureModifier<ItemStack> sm = packet.getItemModifier();
					
				for (int j = 0; j < sm.size(); j++) {
				if (sm.getValues().get(j) != null) {
				ItemStack item = sm.getValues().get(j);
				ItemMeta itemMeta = item.getItemMeta();
					
				if (itemMeta.hasLore()) {
				List<String> lore = itemMeta.getLore();
				
				if(lore.get(0).contains("Data: ")){
				BendingItem bi = new BendingItem(item);
				itemMeta.setDisplayName(ChatColor.GRAY + bi.getData().get("Name").toString());
				lore = bi.getFormatedLore();
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				
				}else if(lore.get(0).contains("Mount: ")){

				MountItem mi = MountItem.getFromItem(item);
				lore = mi.getFormatedLore();
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				}}}}
				event.setPacket(packet);}
					
				
			if (event.getPacketType() == PacketType.Play.Server.WINDOW_ITEMS) {
			PacketContainer packet = event.getPacket().deepClone();
			StructureModifier<ItemStack[]> sm = packet.getItemArrayModifier();
					
			for (int j = 0; j < sm.size(); j++) {
				
			ItemStack[] itema = sm.getValues().get(j);
				for (int i = 0; i < itema.length; i++) {
				if(itema[i] != null && itema[i].getItemMeta() != null && !itema[i].getType().equals(Material.AIR)) {
				ItemStack item = itema[i];
				ItemMeta itemMeta = item.getItemMeta();
				
				if (itemMeta.hasLore()) {
				List<String> lore = itemMeta.getLore();
				if(lore.get(0).contains("Data: ")){
			    BendingItem bi = BendingItem.getInstanceofItemstack(item);
				lore = bi.getFormatedLore();
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				}else if(lore.get(0).contains("Mount: ")){
				MountItem mi = MountItem.getFromItem(item);
				lore = mi.getFormatedLore();
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				}}}}
	  
			    event.setPacket(packet);
					 }
					}
					
			
					 
			}});
	
		
		/**
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL,PacketType.Play.Server.NAMED_SOUND_EFFECT) {
				    @Override
				    public void onPacketSending(PacketEvent event) {
				        PacketContainer packet = event.getPacket().deepClone();

				    	if (event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT) {
						String soundName = "";
						MinecraftKey mckey = null;
				    	
								try {
								
								Field f = packet.getModifier().getFields().get(0);
								f.setAccessible(true);
								
								Field f2 = f.get(packet.getHandle()).getClass().getDeclaredFields()[1];	
								f2.setAccessible(true);
								
								mckey = (MinecraftKey) f2.get(f.get(packet.getHandle()));    						
								soundName = mckey.a();
								
								if(soundName.contains("entity.villager") && !soundName.contains("entity.villager.trading")){event.setCancelled(true);return;}
								if(!soundName.contains("entity.zombie")){return;}
								SoundEffect s =  SoundEffects.b;
									
								if(soundName.contains("ambient")){event.setCancelled(true); return;}
								if(soundName.contains("hurt")){s = SoundEffects.ENTITY_PLAYER_HURT;}
								if(soundName.contains("death")){s = SoundEffects.eg;}

   
								packet.getModifier().write(0, s);  
								} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
								e.printStackTrace();
								}
								
  
								event.setPacket(packet);

				    	}}});
	   
    
      
   **/
	
	

		
			
}
