package net.ScyllaMc.Matan.NPC;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;

public class NPCRegister{

		
		 
		 public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
		        try {
		     
		            List<Map<?, ?>> dataMap = new ArrayList<Map<?, ?>>();
		            for (Field f : EntityTypes.class.getDeclaredFields()){
		                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())){
		                    f.setAccessible(true);
		                    dataMap.add((Map<?, ?>) f.get(null));
		                }
		            }
		     
		            if (dataMap.get(2).containsKey(id)){
		                dataMap.get(0).remove(name);
		                dataMap.get(2).remove(id);
		            }
		     
		            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
		            method.setAccessible(true);
		            method.invoke(null, customClass, name, id);
		     
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		 
		 
		 
		 
			public static Entity spawnEntity(Entity entity, Location loc){
				
				
		    entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
			((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
			return entity;}

			
		 /**
		 
	   @SuppressWarnings({ "unchecked", "rawtypes" })
	 public void registerMob(Class clas, String name, int id){
		
	       ((Map)getPrivateField("c", EntityTypes.class, null)).put(name, clas);
	       ((Map)getPrivateField("d", EntityTypes.class, null)).put(clas, name);
	       ((Map)getPrivateField("f", EntityTypes.class, null)).put(clas, Integer.valueOf(id));  
		    }
	   
	   
		public static Entity spawnEntity(Entity entity, Location loc){
	    entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
		return entity;}

		
		
		
	   @SuppressWarnings("rawtypes")
	   public Object getPrivateField(String fieldName, Class clazz, Object object){
	   Field field = null;
	   Object o = null;
	   try {
	   field = clazz.getDeclaredField(fieldName);
	   field.setAccessible(true);
	   o = field.get(object);
	   } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
	   e.printStackTrace();}

	   return o;}
		**/
	
	

	
}
