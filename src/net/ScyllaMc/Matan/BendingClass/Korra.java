package net.ScyllaMc.Matan.BendingClass;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;

import net.ScyllaMc.Matan.MelonCore.MelonCore;

public class Korra {
	

	 public static ArrayList<String> air = new ArrayList<String>();
	 public static ArrayList<String> fire = new ArrayList<String>();
	 public static ArrayList<String> earth = new ArrayList<String>();
	 public static ArrayList<String> water = new ArrayList<String>();
	 public static ArrayList<String> chi = new ArrayList<String>();

	 
	 public static void registerAllDefaults(){
		 air.add("AirBlast");
		 air.add("AirSwipe");
		 air.add("AirBubble");
		 air.add("AirShield");
		 air.add("SonicBlast");
		 air.add("Meditate");
		 air.add("AirSuction");
		 air.add("AirSpout");
		 air.add("Tornado");

		 
		 fire.add("FireBlast");
		 fire.add("Blaze");
		 fire.add("FireBurst");
		 fire.add("Lightning");
		 fire.add("FireShots");
		 fire.add("FirePunch");
		 fire.add("WallOfFire");
		 fire.add("FireShield");
		 fire.add("FireJet");
		 
		 
		 earth.add("EarthGrab");
		 earth.add("Shockwave");
		 earth.add("RaiseEarth");
		 earth.add("Collapse");
		 earth.add("EarthSmash");
		 earth.add("SandSpout");
		 earth.add("EarthTunnel");
		 earth.add("EarthArmor");
		 earth.add("Catapult");
		 
	 
		 
		 chi.add("smokescreen");
		 chi.add("Paralyze");
		 chi.add("rapidpunch");
		 chi.add("backstab");
		 chi.add("quickstrike");
		 chi.add("swiftkick");
		 chi.add("acrobatstance");
		 chi.add("warriorstance");
		 chi.add("highjump");
		 
		 water.add("Surge");
		 water.add("Torrent");
		 water.add("WaterSpout");
		 water.add("OctopusForm");
		 water.add("IceSpike");
		 water.add("WaterArms");
		 water.add("HealingWaters");
		 water.add("WakeFishing");
		 water.add("WaterManipulation");}

	 
	 
	 
	 
	public static void chooseDefault(Player np, String e){
	 BendingPlayer b =  BendingPlayer.getBendingPlayer(np.getName());
	 ArrayList<String> list = air;
	 ChatColor color = ChatColor.GRAY;
	
	 if(e.equalsIgnoreCase("air")){list = air; color = ChatColor.GRAY;  b.setElement(Element.AIR);}
	 if(e.equalsIgnoreCase("fire")){list = fire; color = ChatColor.RED; b.setElement(Element.FIRE);}
	 if(e.equalsIgnoreCase("water")){list = water; color = ChatColor.AQUA; b.setElement(Element.WATER);}
	 if(e.equalsIgnoreCase("earth")){list = earth; color = ChatColor.DARK_GREEN; b.setElement(Element.EARTH);}
	 if(e.equalsIgnoreCase("chi")){list = chi; color = ChatColor.GOLD; b.setElement(Element.CHI);}


	 
    HashMap<Integer, String> moves = new HashMap<Integer, String>();
	 int i = 1;
		np.sendMessage(MelonCore.prefix + ChatColor.RED + "Auto Binding moves...");

	 for(String s : list){ 
	np.sendMessage(MelonCore.prefix + color + s + ChatColor.GRAY + " Bounded to slot " + color + i);
	 moves.put(i, s);
	 i = i +1;}
	 
	 b.setAbilities(moves);}
	 
	 
	 
}
