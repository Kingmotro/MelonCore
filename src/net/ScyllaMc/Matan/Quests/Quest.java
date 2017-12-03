package net.ScyllaMc.Matan.Quests;


import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.NPC.NPC;

public interface Quest {

	
    public static enum Quests {
	WELCOME_TO_YAI(new welcometoyai(),1),
	Bandit_Hunter(new Quest_BanditHunter(),1),

	;
    	
    	
    public Integer level;
    public Entity npc;
    public ItemStack reward;
    public Quest q;
    
	Quests(Quest q,Integer level){
	this.level = level;
	this.q = q;}
	
	
	public Quest getQuest(){
	return this.q;}
	
	
	}

    
   
    public void start(MelonPlayer p, NPC e);

	public boolean started(MelonPlayer p);
    
}
