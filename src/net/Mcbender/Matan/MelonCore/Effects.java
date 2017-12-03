package net.Mcbender.Matan.MelonCore;

import java.lang.reflect.Method;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class Effects {

	
	private static Method world_getHandle = null;
	
	
	
	  private  static Method nms_world_broadcastEntityEffect = null;
	    private static Method firework_getHandle = null;
	    public static void deathFirework(Player p){
		Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		Random r = new Random();  
		       int rt = r.nextInt(4) + 1;
		       Type type = Type.BALL;      
		       if (rt == 1) type = Type.BALL;
		       if (rt == 2) type = Type.BALL_LARGE;
		       if (rt == 3) type = Type.BURST;
		       if (rt == 4) type = Type.CREEPER;
		       if (rt == 5) type = Type.STAR;	     
		       FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.RED).withFade(Color.GRAY).with(type).trail(r.nextBoolean()).build();
		       fwm.addEffect(effect);
		       int rp = r.nextInt(2) + 1;
		       fwm.setPower(rp);
		       fw.setFireworkMeta(fwm);}
	   
	    /**
	     * Internal method, used as shorthand to grab our method in a nice friendly manner
	     * @param cl
	     * @param method
	     * @return Method (or null)
	     */
	    private static Method getMethod(Class<?> cl, String method) {
	        for(Method m : cl.getMethods()) {
	            if(m.getName().equals(method)) {
	                return m;
	            }
	        }
	        return null;
	    }
	   
	    /**
	     * Play a pretty firework at the location with the FireworkEffect when called
	     * @param world
	     * @param loc
	     * @param fe
	     * @throws Exception
	     */
	    public static void playFirework(World world, Location loc, FireworkEffect fe) throws Exception {
	        // Bukkity load (CraftFirework)
	        Firework fw = world.spawn(loc, Firework.class);
	        // the net.minecraft.server.World
	        Object nms_world = null;
	        Object nms_firework = null;
	        /*
	         * The reflection part, this gives us access to funky ways of messing around with things
	         */
	        if(world_getHandle == null) {
	            // get the methods of the craftbukkit objects
	            world_getHandle = getMethod(world.getClass(), "getHandle");
	            firework_getHandle = getMethod(fw.getClass(), "getHandle");
	        }
	        // invoke with no arguments
	        nms_world = world_getHandle.invoke(world, (Object[]) null);
	        nms_firework = firework_getHandle.invoke(fw, (Object[]) null);
	        // null checks are fast, so having this seperate is ok
	        if(nms_world_broadcastEntityEffect == null) {
	            // get the method of the nms_world
	            nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
	        }
	        /*
	         * Now we mess with the metadata, allowing nice clean spawning of a pretty firework (look, pretty lights!)
	         */
	        // metadata load
	        FireworkMeta data = fw.getFireworkMeta();
	        // clear existing
	        data.clearEffects();
	        // power of one
	        data.setPower(1);
	        // add the effect
	        data.addEffect(fe);
	        // set the meta
	        fw.setFireworkMeta(data);
	        /*
	         * Finally, we broadcast the entity effect then kill our fireworks object
	         */
	        // invoke with arguments
	        nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] {nms_firework, (byte) 17});
	        // remove from the game
	        fw.remove();
	    }
	 
	    
	    
	

	     
	    
	    
	
}
