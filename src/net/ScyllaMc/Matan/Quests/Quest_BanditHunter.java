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

public class Quest_BanditHunter implements Quest {

	QuestUtils utils = new QuestUtils();
	String id = "BANDITHUNTER";
	String name = "Bandit Hunter";

	@SuppressWarnings("unchecked")
	public void start(MelonPlayer p, NPC e) {
		JSONObject jmain;

		if (p.quests == null) {
			jmain = new JSONObject();
			p.quests = jmain;
		} else {
			jmain = p.quests;
		}

		if (!p.quests.containsKey(id)) {

			jmain.put(id, "act1");
			if (e.getName().contains("Fong")) {
				p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + " Quest Started: " + ChatColor.GREEN + name+ ChatColor.DARK_GREEN + "  Difficulty: " + ChatColor.GREEN + "Easy");
				Title.sendTitle(p, 100, 100, 100, MelonCore.prefix + ChatColor.DARK_GREEN + " Quest Started: " + ChatColor.GREEN + name, ChatColor.DARK_GREEN + "  Difficulty: " + ChatColor.GREEN + "Easy");

				if (p.isOnline()) {
					p.getOnlinePlayer().playSound(p.getOnlinePlayer().getLocation(), Sound.LEVEL_UP, 1, 1);
				}
			}

			act1(p, e);
			return;

		} else {
			String acts = (String) jmain.get(id);

			if (acts.equalsIgnoreCase("act1")) {
				act1(p, e);
				return;
			}
			if (acts.equalsIgnoreCase("act2")) {
				act2(p, e);
				return;
			}
			if (acts.equalsIgnoreCase("actfinal")) {
				actfinal(p, e);
				return;
			}

		}

	}

	private void act1(MelonPlayer p, NPC e) {
		if (e.getName().contains("Fong")) {
			List<String> list = new ArrayList<String>();
			list.add("You need to ....");
			list.add("CONTENT HERE");
			list.add("Kill 100 mobs");
			utils.talk("Sud", p, list, id, "act2", name);
		}
	}

	private void act2(MelonPlayer p, NPC e) {
		
		
		if (e.getName().contains("Fong")) {
			
			if(p.kills > 100){
				List<String> list = new ArrayList<String>();
				list.add("Wonderful!, The world is .... BLAH BLAH BLAH");
				utils.talk("Sud", p, list, id, "actfinal-60", name);	
			}else{
				List<String> list = new ArrayList<String>();
				list.add("Kill 100, then come back");
				utils.talk("Sud", p, list, id, "act2", name);	
			}
	
		}


	}

	private void actfinal(MelonPlayer p, NPC e) {
		if (e.getName().contains("Font")) {
			List<String> list = new ArrayList<String>();
			list.add("Its great to have the roads safe once again!");
			utils.talk("Sud", p, list, id, "actfinal", name);
		}



	}

	public boolean started(MelonPlayer p) {
		if (p.quests != null && p.quests.containsKey(id)) {
			return true;
		}

		return false;
	}

}
