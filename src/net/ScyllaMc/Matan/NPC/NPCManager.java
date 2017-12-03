package net.ScyllaMc.Matan.NPC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.NPC.Market.MarketType;
import net.ScyllaMc.Matan.NPC.NPC.NPCType;
import net.ScyllaMc.Matan.Quests.Quest.Quests;
import net.md_5.bungee.api.ChatColor;

public class NPCManager {

	public static HashMap<Chunk, ArrayList<NPC>> chunknpc = new HashMap<Chunk, ArrayList<NPC>>();

	public static HashMap<ItemStack, Integer> SHOP_BLOCKS = new HashMap<ItemStack, Integer>();
	public static HashMap<ItemStack, Integer> SHOP_FOOD = new HashMap<ItemStack, Integer>();
	public static HashMap<ItemStack, Integer> SHOP_DYE = new HashMap<ItemStack, Integer>();
	public static HashMap<ItemStack, Integer> SHOP_FISH = new HashMap<ItemStack, Integer>();
	public static HashMap<ItemStack, Integer> SHOP_HORSE = new HashMap<ItemStack, Integer>();
	public static HashMap<ItemStack, Integer> SHOP_POTION = new HashMap<ItemStack, Integer>();
	public static HashMap<ItemStack, Integer> SHOP_CLANS = new HashMap<ItemStack, Integer>();

	public void registerAll() throws IOException{	
		
	      for (@SuppressWarnings("rawtypes")
		Iterator iterator = MelonCore.world.getEntities().iterator(); iterator.hasNext();){
	          Entity entity = (Entity)iterator.next();
	          if ((entity.getType() != EntityType.PLAYER) && (entity.getType() != EntityType.ITEM_FRAME) && (entity.getType() != EntityType.MINECART)) {
	            entity.remove();}}
	     
	      

	/**
	
	NPC fong = new NPC(EntityType.VILLAGER, new Location(MelonCore.world,673,61,-427), NPCType.QUEST_START_NPC, ChatColor.DARK_GREEN  +"Fong", 2 ,null , Quests.STOLEN_GOODS.getQuest());
	NPCBlock stolen_food = new NPCBlock(Material.CHEST, new Location(MelonCore.world,471,66,-345), ChatColor.GRAY  +"Stolen Food" , 2, Quests.STOLEN_GOODS.getQuest());


	NPC captainguard = new NPC(EntityType.VILLAGER, new Location(Bukkit.getWorld("world"),580,59,-210), NPCType.QUEST_START_NPC, ChatColor.DARK_GREEN  +"Captain of the Guard", 6 ,null , Quests.PARLAY.getQuest());
	NPC banditlord = new NPC(EntityType.VILLAGER, new Location(Bukkit.getWorld("world"),464,53,-290), NPCType.QUEST_NPC, ChatColor.DARK_GREEN  +"Bandit Leader", 6 ,null , Quests.PARLAY.getQuest());
    new Spawner(EntityType.ZOMBIE, new Location(Bukkit.getWorld("world"),466,51,-274) , 200 , 0 ,MobType.EARTH_BANDIT_PEACEFUL , 5 , 1, 3);
    new Spawner(EntityType.ZOMBIE, new Location(Bukkit.getWorld("world"),435,49,-204) , 200 , 8 ,MobType.EARTH_BANDIT , 7 , 1, 7); //Bandit camp near leader
	NPCBlock bandit_stash = new NPCBlock(Material.ENDER_CHEST, new Location(MelonCore.world,447,44,-206), ChatColor.GRAY  +"Bandit Stash" , 6, Quests.PARLAY.getQuest());

	
	
	new NPC(EntityType.VILLAGER, new Location(MelonCore.world,24,70,341), NPCType.MERCHENT_NPC, ChatColor.BLUE  +"Weaponsmith", 1 ,MerchentType.WEAPON_SELLER , null);
	new NPC(EntityType.VILLAGER, new Location(MelonCore.world,10,70,328), NPCType.MERCHENT_NPC, ChatColor.BLUE  +"Material & Ore Trader", 1 ,MerchentType.BLOCK_TRADER , null);
	new NPC(EntityType.VILLAGER, new Location(Bukkit.getWorld("flatroom"),54,20,-11), NPCType.MERCHENT_NPC, ChatColor.BLUE  +"Material & Ore Trader", 1 ,MerchentType.BLOCK_TRADER , null);

    new Spawner(EntityType.ZOMBIE, new Location(Bukkit.getWorld("world"),681,57,-332) , 200 , 2 ,MobType.EARTH_BANDIT , 5 , 1, 3); //Abandoned farming village
    new Spawner(EntityType.ZOMBIE, new Location(Bukkit.getWorld("world"),470,50,-354) , 200 , 3 ,MobType.EARTH_BANDIT , 4 , 1, 3); //Bandit tower #1
    new Spawner(EntityType.ZOMBIE, new Location(Bukkit.getWorld("world"),217,48,-68) , 200 , 9 ,MobType.EARTH_BANDIT , 5 , 1, 3); //Abandoned farm village in the middle of the forest
   **/
    
   // new Spawner(EntityType.ZOMBIE, new Location(Bukkit.getWorld("world"),263,52,-262) , 200 , 10 ,MobType.SOLDIER , 6 , 1, 2); //Outpost #2 gaurds

	
	      
   // new Spawner(EntityType.ZOMBIE, LOCATION , INTERVAL , LEVEL ,MobType , LIMIT , RAD_MIN, RAD_MAX);

		new MerchentsItems().loadItemsToHashMap();

	      
	  	new Market(MarketType.BLOCK_TRADER,Material.BRICK, ChatColor.BLUE  +"Block Trader", SHOP_BLOCKS);
	  	new Market(MarketType.DYE_MERCHANT,Material.INK_SACK, ChatColor.BLUE  +"Dye Shop", SHOP_DYE);
	  	new Market(MarketType.FOOD_MERCHANT,Material.APPLE, ChatColor.BLUE  +"Food Shop", SHOP_FOOD);
	  	new Market(MarketType.POTION_MERCHANT,Material.POTION, ChatColor.BLUE  +"Potion Shop", SHOP_POTION);
	  	new Market(MarketType.HORSE_MERCHANT,Material.SADDLE, ChatColor.BLUE  +"Horse Shop", SHOP_HORSE);

	  
	      /**
	      
	      if(MelonCore.server.equals(MelonCore.ServerType.FIGHT)){
	  	  	new NPC(EntityType.WITCH, new Location(MelonCore.world,0,73,5), NPCType.BENDER, ChatColor.RED  +"Fire " + ChatColor.GRAY + "(Click to Select)", 1 ,null , null);
	  	  	new NPC(EntityType.WITCH, new Location(MelonCore.world,5,73,0), NPCType.BENDER, ChatColor.GRAY  +"Air  " + ChatColor.GRAY + "(Click to Select)", 1 ,null , null);
	  	  	new NPC(EntityType.WITCH, new Location(MelonCore.world,0,73,-4), NPCType.BENDER, ChatColor.BLUE  +"Water  " + ChatColor.GRAY + "(Click to Select)", 1 ,null , null);
	  	  	new NPC(EntityType.WITCH, new Location(MelonCore.world,-4,73 , 0), NPCType.BENDER, ChatColor.DARK_GREEN  +"Earth  " + ChatColor.GRAY + "(Click to Select)", 1 ,null , null);

	    	  
	    	  
	      }
	     **/ 
	      
	      
	      
    
	}

	public void addUnloadedNpc(NPC npc) {
		if (npc == null) {
			return;
		}
		if (npc.loc == null) {
			return;
		}
		Chunk chunk = npc.loc.getChunk();
		if (!chunknpc.containsKey(chunk)) {
			chunknpc.put(chunk, new ArrayList<NPC>());
		}
		chunknpc.get(chunk).add(npc);
	}

	public void loadUnloadedNpc(Chunk chunk) {

		if (chunknpc.containsKey(chunk)) {
			for (NPC npc : chunknpc.get(chunk)) {
				npc.respawnNPC();
				chunknpc.get(chunk).remove(npc);
				Bukkit.broadcastMessage("RESPAWNING NPC");
			}
			chunknpc.remove(chunk);
		}
	}

}
