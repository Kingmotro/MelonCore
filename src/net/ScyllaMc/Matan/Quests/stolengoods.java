package net.ScyllaMc.Matan.Quests;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.json.simple.JSONObject;

import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.ScyllaMc.Matan.NPC.NPC;
import net.ScyllaMc.Matan.Util.Title;

public class stolengoods implements Quest{

	 QuestUtils utils = new QuestUtils();
	 String id = "stolengoods";
	 String name = "Stolen Food";
	 
	 
		
	@SuppressWarnings("unchecked")
	public void start(MelonPlayer p,NPC e){		
	JSONObject jmain;
			
	if(p.quests == null){
	jmain = new JSONObject();
    p.quests = jmain;
	}else{
	jmain = p.quests;
	}
				
	if(!p.quests.containsKey(id)){ 
		
	jmain.put(id, "act1");
	if(e.getName().contains("Fong")){
	p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + " Quest Started: "  + ChatColor.GREEN + name  + ChatColor.DARK_GREEN + "  Difficulty: " + ChatColor.DARK_AQUA +  "Medium");
	Title.sendTitle(p, 100, 100, 100, MelonCore.prefix + ChatColor.DARK_GREEN + " Quest Started: "  + ChatColor.GREEN + name, ChatColor.DARK_GREEN + "  Difficulty: " + ChatColor.DARK_AQUA + "Medium");

	if(p.isOnline()){p.getOnlinePlayer().playSound(p.getOnlinePlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP ,1, 1);}	}
	
     act1(p,e);
			return;
			
			}else{
			String acts = (String) jmain.get(id);
			
			if(acts.equalsIgnoreCase("act1")){act1(p,e);return;}
			if(acts.equalsIgnoreCase("act2")){act2(p,e);return;}
			if(acts.equalsIgnoreCase("act3")){act3(p,e);return;}
			if(acts.equalsIgnoreCase("actfinal")){actfinal(p,e);return;}

			}
			
			
			
		}

		
		
		
	 
	private void act1(MelonPlayer p,NPC e){
	if(e.getName().contains("Fong")){
    List<String> list = new ArrayList<String>();
	list.add("Hello, You must be one of the survivors from ba sing se!");
	list.add("You seem like a capable fellow, could yu please help us?");
	list.add("You see, since the capital has fallen bandits have stolen our food and supplies,");
	list.add("We have very little left in our farms, ");
	list.add("Could you please go find the bandits and take back our food?");
	list.add("There is a bandit camp in a tower in the woods, west of here.");
    utils.talk(e.getName(), p, list,id,"act2",name);}

	if(e.getName().contains("Stolen Food")){
	p.sendMessage(MelonCore.prefix + ChatColor.RED + " You have found a quest item, start the quest to interact with it.");
	}
		
	}
	

	
	@SuppressWarnings("unchecked")
	private void act2(MelonPlayer p,NPC e){
	if(e.getName().contains("Fong")){
	List<String> list = new ArrayList<String>();
	list.add("The bandit camp is west of here in a tower they have taken control of.");
    utils.talk(e.getName(), p, list,id,"act2",name);}
	
	if(e.getName().contains("Stolen Food")){
	p.sendMessage(MelonCore.prefix + ChatColor.GREEN + " You have found " + e.getName() + ChatColor.GREEN + " Take it back to " + ChatColor.DARK_GREEN +  "Fong!");
	if(p.isOnline()){p.getOnlinePlayer().playSound(p.getOnlinePlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP ,1, 1);}
	p.quests.put(id, "act3");
	}

	

	
	}

	

	
	@SuppressWarnings("unchecked")
	private void act3(MelonPlayer p,NPC e){
	if(e.getName().contains("Stolen Food")){
	p.sendMessage(MelonCore.prefix + ChatColor.GREEN + " You have found " + e.getName() + ChatColor.GREEN + " Take it back to " + ChatColor.DARK_GREEN +  "Fong!");}	
	
	
	if(e.getName().contains("Fong")){
	p.quests.put(id, "actfinal");
	List<String> list = new ArrayList<String>();
	list.add("Marvelous!");
	list.add("We can now survive for months,");
	list.add("Thank you but we sre yet to be done, we have much work left until we are safe.");
	utils.talk(e.getName(), p, list,id,"actfinal-52",name);
	return;}

	
	}

	private void actfinal(MelonPlayer p,NPC e){
		
			
  if(e.getName().contains("Fong")){
	List<String> list = new ArrayList<String>();
  list.add("Thank you again. Would you like to play some pai shu?");
	utils.talk(e.getName(), p, list,id,"actfinal",name);
			
			}	
		
		
	}

		
		
		
	
	
		
	public boolean started(MelonPlayer p){		
	if(p.quests != null && p.quests.containsKey(id)){
	return true;}
	
	return false;}
	
	
	
	
	
	
	
}
