package net.Mcbender.Matan.MelonCore;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.Bukkit;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;

public class BungeeUtils {

	
	public static void Kick(MelonPlayer player , String r){
	 ByteArrayDataOutput out = ByteStreams.newDataOutput();
     out.writeUTF("Connect");
     out.writeUTF("lobby");
     player.getOnlinePlayer().sendPluginMessage(Bukkit.getPluginManager().getPlugin("MelonCore"), "BungeeCord", out.toByteArray());}
	

	
	
	
	public static void sendMessage(String tosend, String m){	
		 ByteArrayOutputStream b = new ByteArrayOutputStream();
		 DataOutputStream out = new DataOutputStream(b);
		 try{
		 out.writeUTF("Message");
		 out.writeUTF(tosend);
		 out.writeUTF(m);
		 } catch (Exception e1) {e1.printStackTrace();}
		 Bukkit.getServer().sendPluginMessage(Bukkit.getServer().getPluginManager().getPlugin("MelonCore"), "BungeeCord", b.toByteArray()); 
	}
	
	
/**
	public static void PlayerCount(Player p, String servername){
	ByteArrayDataOutput out = ByteStreams.newDataOutput();
    ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
	out.writeUTF("PlayerList");	     
	out.writeUTF(servername);
	String server = in.readUTF(); 
	String[] playerList = in.readUTF().split(", ");}
	**/	
	   
	   
   
}
