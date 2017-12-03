package net.ScyllaMc.Matan.Events;


import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.Mobs.Mount;
import net.ScyllaMc.Matan.Mobs.MountItem;

public class EventEntityMount implements Listener{

	
	
	@EventHandler
	public void PlayerRightClick(PlayerInteractEntityEvent event) {
    Entity entity = event.getRightClicked(); 

    if(entity.getType().equals(EntityType.HORSE)){
    event.setCancelled(true);
    
    Mount m = Mount.getFromEntity((LivingEntity) entity);
    if(m != null && m.getOwner().getUniqueId().equals(event.getPlayer().getUniqueId())){
    m.getEntity().setPassenger(event.getPlayer()); return;}
    return;}}
	
	
	@EventHandler
	public void damage(EntityDamageEvent event) {
	Entity entity = event.getEntity();
	if(entity instanceof LivingEntity){
	if(!(Mount.getFromEntity((LivingEntity) entity) == null)){
    event.setCancelled(true);
	}}}
	

	
	
	

	@EventHandler
	public void interact(InventoryClickEvent event) {
	Player p = (Player) event.getWhoClicked(); 
	
	if(Mount.getFromPlayer(MelonPlayer.getInstanceOfPlayer(p)) != null){
	Mount m = Mount.getFromPlayer(MelonPlayer.getInstanceOfPlayer(p));
	if(m != null){
	m.despawn();
	event.setCancelled(true);
	}
	}}

	
	
	@EventHandler
	public void itemdrop(PlayerDropItemEvent event) {
	Player p = (Player) event.getPlayer(); 
	if(Mount.getFromPlayer(MelonPlayer.getInstanceOfPlayer(p)) != null){
	Mount.getFromPlayer(MelonPlayer.getInstanceOfPlayer(p)).despawn();}}
	
	
	



	@EventHandler
	public void openpowerinv(PlayerInteractEvent event){
    Player p = event.getPlayer();
	if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction() == Action.RIGHT_CLICK_BLOCK){
	if(p.getItemInHand().getType() == Material.SADDLE){
	ItemStack i = event.getItem(); 
	MountItem mi = MountItem.getFromItem(i);
	if(mi != null){
	Mount m = new Mount(MelonPlayer.getInstanceOfPlayer(p),mi.getLevel());
	m.spawn();
	m.getEntity().setPassenger(p);
	return;
	}
		
	}}}
	
	
	
	
	
}
