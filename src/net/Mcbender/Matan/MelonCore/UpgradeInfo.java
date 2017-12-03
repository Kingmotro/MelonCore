package net.Mcbender.Matan.MelonCore;

import java.util.HashMap;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;



public class UpgradeInfo {

	
	
	
	

	
	public static HashMap<Integer, Integer> DamageUpgradesPrice(){
	HashMap<Integer, Integer> Upgrades = new HashMap<Integer, Integer>();
	Upgrades.put(1, 0);
	Upgrades.put(2, 500);
	Upgrades.put(3, 1000);
	Upgrades.put(4, 2500);
	Upgrades.put(5, 4000);
	Upgrades.put(6, 5500);
	Upgrades.put(7, 7500);
	Upgrades.put(8, 10500);
	Upgrades.put(9, 14500);
	Upgrades.put(10, 16500);
	Upgrades.put(11, 17500);
	Upgrades.put(12, 18500);
	Upgrades.put(13, 20500);
	Upgrades.put(14, 25000);
	Upgrades.put(15, 30000);
	Upgrades.put(16, 35000);
	Upgrades.put(17, 40000);
	Upgrades.put(18, 45000);
	Upgrades.put(19, 50000);
	Upgrades.put(20, 55000);
	Upgrades.put(21, 60000);
    return Upgrades;}
	
	
	public static double getBonusDamage(int l){
	double damage = ((double) l / 10);
	return damage;
	}
	
	

	public static int getDamageUpgradePrice(int l){
		int p = DamageUpgradesPrice().get(l);
		return p;
		}
	

	public static boolean isUnlockedeDamage(MelonPlayer p , int l){
	if(p.damagelevel >= l){
	return true;
	}else{
	return false;
	}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static HashMap<Integer, Integer> LuckUpgradesPrice(){
		HashMap<Integer, Integer> Upgrades = new HashMap<Integer, Integer>();
		Upgrades.put(1, 0);
		Upgrades.put(2, 500);
		Upgrades.put(3, 1000);
		Upgrades.put(4, 2500);
		Upgrades.put(5, 4000);
		Upgrades.put(6, 5500);
		Upgrades.put(7, 7500);
		Upgrades.put(8, 10500);
		Upgrades.put(9, 14500);
		Upgrades.put(10, 16500);
		Upgrades.put(11, 17500);
		Upgrades.put(12, 18500);
		Upgrades.put(13, 20500);
		Upgrades.put(14, 25000);
		Upgrades.put(15, 30000);
		Upgrades.put(16, 35000);
		Upgrades.put(17, 40000);
		Upgrades.put(18, 45000);
		Upgrades.put(19, 50000);
		Upgrades.put(20, 55000);
		Upgrades.put(21, 60000);
	    return Upgrades;}
		



		public static int getLuckUpgradePrice(int l){
			int p = DamageUpgradesPrice().get(l);
			return p;
			}
		

		public static boolean isUnlockedeLuck(MelonPlayer p , int l){
		if(p.luck >= l){
		return true;
		}else{
		return false;
		}

		}
	
	

	
	
	
}
