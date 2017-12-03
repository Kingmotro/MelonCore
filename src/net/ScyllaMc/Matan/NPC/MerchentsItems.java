package net.ScyllaMc.Matan.NPC;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.Mobs.MountItem;

public class MerchentsItems {

	
	
	@SuppressWarnings("static-access")
	public void loadItemsToHashMap(){


	
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.LOG,16), 60);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.WOOD,32), 30);
	
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.GRASS,32), 64);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.DIRT,32), 32);

	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.WOOL,32), 90);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.WOOL,32, (short) 1), 90);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.WOOL,32, (short) 2), 90);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.WOOL,32, (short) 3), 90);

	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.COBBLESTONE,32), 90);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.STONE,32), 110);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.SMOOTH_BRICK,32), 120);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.QUARTZ_BLOCK,32), 180);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.NETHERRACK,32), 90);

	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.STAINED_CLAY,32,(short) 0), 120);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.GLASS,32), 120);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.THIN_GLASS,32), 80);

	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.SAND,32), 64);
	MelonCore.NPCManager.SHOP_BLOCKS.put(new ItemStack(Material.SANDSTONE,32), 90);


	
	

	MelonCore.NPCManager.SHOP_FOOD.put(new ItemStack(Material.COOKED_BEEF,16), 32);
	MelonCore.NPCManager.SHOP_FOOD.put(new ItemStack(Material.BREAD,16), 25);
	MelonCore.NPCManager.SHOP_FOOD.put(new ItemStack(Material.COOKIE,16), 16);
	MelonCore.NPCManager.SHOP_FOOD.put(new ItemStack(Material.WHEAT,16), 16);
	MelonCore.NPCManager.SHOP_FOOD.put(new ItemStack(Material.CARROT,16), 20);
	MelonCore.NPCManager.SHOP_FOOD.put(new ItemStack(Material.MELON,16), 32);
	MelonCore.NPCManager.SHOP_FOOD.put(new ItemStack(Material.WHEAT,16), 16);

	MelonCore.NPCManager.SHOP_FOOD.put(new ItemStack(Material.GOLDEN_APPLE,1), 100);

	
	
	MelonCore.NPCManager.SHOP_FISH.put(new ItemStack(Material.COOKED_FISH,1), 2);
	MelonCore.NPCManager.SHOP_FISH.put(new ItemStack(Material.RAW_FISH,1), 1);

	
	
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 8), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 10), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 9), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 12), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 13), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 5), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 1), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 14), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 2), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 4), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 6), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 7), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 11), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 0), 60);
	MelonCore.NPCManager.SHOP_DYE.put(new ItemStack(Material.INK_SACK,16,(short) 3), 60);

	
	
	MelonCore.NPCManager.SHOP_HORSE.put(new MountItem(8).getItem(), 3000);
	MelonCore.NPCManager.SHOP_HORSE.put(new MountItem(13).getItem(), 7000);
	MelonCore.NPCManager.SHOP_HORSE.put(new MountItem(17).getItem(), 11000);
	MelonCore.NPCManager.SHOP_HORSE.put(new MountItem(22).getItem(), 17000);
	MelonCore.NPCManager.SHOP_HORSE.put(new MountItem(30).getItem(), 37000);
	MelonCore.NPCManager.SHOP_HORSE.put(new MountItem(45).getItem(), 50000);
	MelonCore.NPCManager.SHOP_HORSE.put(new MountItem(60).getItem(), 100000);

	
	
	
	
	
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8198), 50); //NIGHT VISION
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8262), 65);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8206), 50); //INVISIBLITY
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8270), 65);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8195), 50); //FIRE PROTECTION
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8259), 65);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8205), 50); //WATER PROTECTION
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8269), 65);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8261), 50); //HEALTH
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8229), 65);
	
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8267), 50); //JUMP BOOST
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8203), 65);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8235), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8194), 50); //SPEED BOOST
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8258), 65);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8226), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8193), 50); //REGENERATION BOOST
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8257), 65);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8225), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8201), 50); //STRENGHT BOOST
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8265), 65);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 8233), 75);
	
	//////////////////////////
	
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16454), 60); //SPLASH NIGHT
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16390), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16398), 60); //SPLASH INVISIBILITY
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16462), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16387), 60); //SPLASH FIRE PROTECTION 
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16451), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16397), 60); //SPLASH WATER PROTECTION 
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16461), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16453), 60); //SPLASH HEALTH
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16421), 75);
	
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16395), 60); //SPLASH JUMP
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16459), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16427), 85); 
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16386), 60); //SPLASH SPEED
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16450), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16418), 85); 
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16385), 60); //SPLASH REGEN
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16449), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16417), 85); 
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16393), 60); //SPLASH STRENGHT
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16457), 75);
	MelonCore.NPCManager.SHOP_POTION.put(new ItemStack(Material.POTION,1,(short) 16425), 85); 
	
	}
	

	
	


	
	
	
	
	
	
	
}
