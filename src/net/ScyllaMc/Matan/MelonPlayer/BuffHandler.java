package net.ScyllaMc.Matan.MelonPlayer;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;


import net.ScyllaMc.Matan.MelonCore.MelonCore;
import net.ScyllaMc.Matan.MelonPlayer.Buff.BuffType;
import net.md_5.bungee.api.ChatColor;

public class BuffHandler {

	
	
	
	

	private MelonPlayer player;	
	public HashMap<Buff, String> buffs = new HashMap<Buff, String>();
	public JSONObject buffsj = new JSONObject();

	
	public static BuffHandler fromGson(String json, MelonPlayer p) {
		BuffHandler b = new BuffHandler(p);
		
		b.loadAll(json);
		b.activateAll();
		return b;
	}

	public BuffHandler(MelonPlayer player){
		this.player = player;
	}
	
	public boolean addBuff(Buff b) {

		for (Buff bl : buffs.keySet()) {

			if (bl.isActive() && bl.getType().equals(b.getType())) {
				player.sendMessage(MelonCore.prefix + ChatColor.RED + "Failed to add buff " + ChatColor.LIGHT_PURPLE + MelonCore.firstLetterCaps(b.getType().toString()) + ChatColor.RED + " becuase a buff of that type is currently active!");
				return false;
			}
		}

		player.sendMessage(MelonCore.prefix + ChatColor.LIGHT_PURPLE + MelonCore.firstLetterCaps(b.getType().toString()) + " +" + b.getModifier() + "%" + ChatColor.GRAY + " Buffer has been activated for " + ChatColor.LIGHT_PURPLE + b.getTimeLeft() + " Seconds!");
		buffs.put(b, "");
		b.activate();
		player.saveData();
		return true;
	}

	
	public void loadAll(String json){	
		JSONObject a = new JSONObject();
		try {
			a = (JSONObject) new JSONParser().parse(json);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for(Object s : a.keySet()){
			Buff b = new Gson().fromJson(s.toString(), Buff.class);
			addBuff(b);
		}	
	}
	
	@SuppressWarnings("unchecked")
	public String toJson() {
		
		HashMap<String, String> data = new HashMap<String, String>();
		for (Buff bl : buffs.keySet()) {
			data.put(bl.toJson().toString(), "");
		}

		buffsj.putAll(data);
			
		return new Gson().toJson(this.buffsj);
	}
	
	
	public void activateAll(){
		for (Buff s : buffs.keySet()) {
			s.activate();
		}
	}
	
	public Buff getBuff(BuffType type) {
		for (Buff bl : buffs.keySet()) {
			
			if (bl.isActive() && bl.getType().equals(type)) {
				return bl;
			}
		}
		return null;
	}
	
	public double getBuffMod(BuffType type) {
		for (Buff bl : buffs.keySet()) {
			if (bl.isActive() && bl.getType().equals(type)) {
				return bl.getModifier();
			}
		}
		return 0;
	}

	
	public String getActiveBuffs() {

		String s = ChatColor.GRAY + " * ";
		String b = ChatColor.GRAY + " * ";

		for (Object os : buffs.keySet()) {
			Buff bl = new Gson().fromJson(os.toString(), Buff.class);
			if (bl.isActive()) {

				s = s + ChatColor.LIGHT_PURPLE + MelonCore.firstLetterCaps(bl.getType().toString()) + " " + bl.getModifier() + "% " + ChatColor.DARK_AQUA + bl.getTimeLeft() + " Sec left" + b;
			}
		}

		return s;
	}
	
	public void removeBuff(Buff b){
		
		if(this.buffs.containsKey(b.toJson())){
			
			try{
			this.buffs.remove(b);
			}catch (ConcurrentModificationException e){
				e.printStackTrace();
			}
		}
	}

	public MelonPlayer getPlayer() {
		return this.player;
	}
	
}
