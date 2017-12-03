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

public class welcometoyai implements Quest {

	QuestUtils utils = new QuestUtils();
	String id = "welcometoyai";
	String name = "Welcome to Yai";

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
			if (e.getName().contains("Sud")) {
				p.sendMessage(MelonCore.prefix + ChatColor.DARK_GREEN + " Quest Started: " + ChatColor.GREEN + name
						+ ChatColor.DARK_GREEN + "  Difficulty: " + ChatColor.GREEN + "Very Easy");
				Title.sendTitle(p, 100, 100, 100,
						MelonCore.prefix + ChatColor.DARK_GREEN + " Quest Started: " + ChatColor.GREEN + name,
						ChatColor.DARK_GREEN + "  Difficulty: " + ChatColor.GREEN + "Very Easy");

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
		if (e.getName().contains("Sud")) {
			List<String> list = new ArrayList<String>();
			list.add("Great you have finalty arrived.");
			list.add("But still it is not safe even here,");
			list.add("Since Ba Sing Se has fallen the whole earth kindgom is riddeled with bandits");
			list.add("My cusin Rohan lives at the inn above this cave, he will help you.");
			list.add("But we careful these lands are filled with bandits and other dangers, ");
			list.add("O and one last thing, Welcome to Yai.");
			utils.talk("Sud", p, list, id, "act2", name);
		}

		if (e.getName().contains("Rohan")) {
			List<String> list = new ArrayList<String>();
			list.add("Hello strager! will you be needing any room?");
			utils.talk("Rohan", p, list, id, "act1", name);
		}

	}

	@SuppressWarnings("unchecked")
	private void act2(MelonPlayer p, NPC e) {
		if (e.getName().contains("Sud")) {
			List<String> list = new ArrayList<String>();
			list.add("Go talk to " + ChatColor.DARK_GREEN + "Rohan " + ChatColor.DARK_AQUA
					+ "He lives in the inn above");
			utils.talk("Sud", p, list, id, "act2", name);
		}

		if (e.getName().contains("Rohan")) {
			p.quests.put(id, "actfinal");
			List<String> list = new ArrayList<String>();
			list.add("Welcome! You must be one of the survivors from ba sing se,");
			list.add("We have expected you, ");
			list.add("After you get some rest talk to " + ChatColor.DARK_GREEN + "Fong " + ChatColor.DARK_AQUA
					+ "out side he will need some assistance.");
			utils.talk("Rohan", p, list, id, "actfinal-45", name);
			return;
		}

	}

	private void actfinal(MelonPlayer p, NPC e) {
		if (e.getName().contains("Sud")) {
			List<String> list = new ArrayList<String>();
			list.add("Its great to have some more help around here!");
			utils.talk("Sud", p, list, id, "actfinal", name);
		}

		if (e.getName().contains("Rohan")) {
			List<String> list = new ArrayList<String>();
			list.add("After you get some rest talk to the merchent out side he will need some assistance.");
			utils.talk("Rohan", p, list, id, "actfinal", name);

		}

	}

	public boolean started(MelonPlayer p) {
		if (p.quests != null && p.quests.containsKey(id)) {
			return true;
		}

		return false;
	}

}
