package net.ScyllaMc.Matan.Mobs;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;

public class Mount {
	 
    public static Map<Mount, MelonPlayer> mounts = new HashMap<Mount, MelonPlayer>();
    
    
    public static HashMap<LivingEntity, Mount> mountsentity = new HashMap<LivingEntity, Mount>();

    Plugin plugin = Bukkit.getPluginManager().getPlugin("MelonCore");
  
    
    public static Mount getFromPlayer(MelonPlayer p) {
    for(Mount m : mounts.keySet()){
    if(m.getOwner().getUniqueId().equals(p.getUniqueId())){
    return m; }}
    return null;}


    public static Mount getFromEntity(LivingEntity e) {
    if(mountsentity.containsKey(e)){
    return mountsentity.get(e);}
    return null;}
    
    private LivingEntity e;
    private MelonPlayer p;
    private int level = 1;
 
    
	public Mount(MelonPlayer p, int level){
	if(getFromPlayer(p) != null){
	getFromPlayer(p).despawn();}
	
 	this.p = p;
	this.level  = level;
	mounts.put(this, p);
	
	
	}
	
	
	public void spawn(){
	if(e != null){despawn();}
	
	this.e = (LivingEntity) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
	mountsentity.put(e, this);
    final Horse h = (Horse) e;
	h.setAdult();
	h.setBreed(false);
	h.setTamed(true);
	h.setColor(Color.BROWN);
	h.setVariant(Variant.HORSE);
	h.setOwner(p.getOnlinePlayer());
	h.setStyle(Style.NONE);
	h.getInventory().addItem(new ItemStack(Material.SADDLE));
	h.setMaxHealth(1);
	h.setJumpStrength(10);
	
	double speed =  level * 0.008333333333 + 0.1634;
	
	((EntityInsentient)((CraftEntity)h).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speed);
	
	if(level < 10){h.setVariant(Variant.DONKEY);
	}else{h.setVariant(Variant.HORSE);}
	
	if(level > 10 && level < 20){h.setColor(Color.CREAMY);}
	if(level > 20 && level < 30){h.setColor(Color.CHESTNUT);}
	if(level > 30 && level < 40){h.setColor(Color.GRAY);}
	if(level > 40 && level < 50){h.setColor(Color.WHITE);}
	if(level > 50){h.setColor(Color.BLACK);}

	h.setCustomName(ChatColor.DARK_AQUA + p.getName() + "'s Horse " + ChatColor.GRAY + "[Lvl. " + level + "]");
	h.setCustomNameVisible(true);
	
	
	
	new BukkitRunnable(){
	public void run(){	
	if(e == null || e.isDead()){cancel(); return;}
	if(p == null){despawn(); cancel(); return;}
	if(!p.isOnline()){despawn(); cancel(); return;}
	
	if(p.getLocation().getWorld() != e.getWorld()){despawn(); cancel(); return;}
	if(p.getLocation().distance(e.getLocation()) > 10){despawn(); cancel(); return;}

	}}.runTaskTimerAsynchronously(plugin, 0, 100);
	
	}
	
	
	
	public void despawn(){
	mounts.remove(this);
	mountsentity.remove(this.getEntity());
	e.remove();
	if(p.isOnline()){p.sendMessage(MelonCore.prefix + ChatColor.RED + "Your mount has despawned.");}}
	

	
	public MelonPlayer getOwner(){return p;}
	public LivingEntity getEntity(){return e;}
	
	public void setLevel(int level){this.level = level;}
	

   
   
	
 
    
    
    
    
   
	
}