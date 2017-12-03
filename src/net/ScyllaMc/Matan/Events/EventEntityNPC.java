package net.ScyllaMc.Matan.Events;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.NPC.NPC;
import net.ScyllaMc.Matan.NPC.NPCBlock;

public class EventEntityNPC implements Listener{

	
	
	@EventHandler
	public void PlayerRightClick(PlayerInteractEntityEvent event) {
		  Entity entity = event.getRightClicked(); 

		  if(NPC.fromEntity(entity) != null){
		  NPC npc = NPC.fromEntity(entity);
		  npc.interact(MelonPlayer.getInstanceOfPlayer(event.getPlayer()));
		  event.setCancelled(true);
		  return;}
		  
		  
	}

	
	
	@EventHandler
	public void PlayerRightClick(PlayerInteractEvent event) {
		
		if(NPCBlock.list.containsKey(event.getClickedBlock())){
		NPCBlock npc = NPCBlock.fromBlock(event.getClickedBlock());
		npc.interact(MelonPlayer.getInstanceOfPlayer(event.getPlayer()));
		event.setCancelled(true);
		return;
		}
		  
		  
	}


	@EventHandler
    public void onChunkUnload(ChunkUnloadEvent event){
    Chunk chunk = event.getChunk();
    for (Entity entity : chunk.getEntities()) {
    if (NPC.list.containsKey(entity.getUniqueId())){
    MelonCore.NPCManager.addUnloadedNpc(NPC.list.get(entity));
    }}}
	
	
	
	
	@EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
    Chunk chunk = event.getChunk();
    MelonCore.NPCManager.loadUnloadedNpc(chunk);   
    }
	
	

	
	
	
}
