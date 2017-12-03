package net.ScyllaMc.Matan.MelonCore;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.ChatColor;

public class BungeeUtils {

	
	public static void Kick(Player p, String server){
	 ByteArrayDataOutput out = ByteStreams.newDataOutput();
     out.writeUTF("Connect");
     out.writeUTF(server);
     p.sendMessage(ChatColor.RED + "You have been moved to " + ChatColor.GRAY + "'"+ server +"'");
     p.sendPluginMessage(Bukkit.getPluginManager().getPlugin("MelonCore"), "BungeeCord", out.toByteArray());}
	
	
	
	@SuppressWarnings("deprecation")
	public static void sendMessage(String player, String message){	
	ByteArrayDataOutput out = ByteStreams.newDataOutput();
	out.writeUTF("Message");
	out.writeUTF(player);
	out.writeUTF(message); 	 
	if(Bukkit.getOfflinePlayer(player).isOnline()){
	Bukkit.getPlayer(player).sendPluginMessage(Bukkit.getPluginManager().getPlugin("MelonCore"), "BungeeCord", out.toByteArray());
	}else{
	Bukkit.getServer().sendPluginMessage(Bukkit.getPluginManager().getPlugin("MelonCore"), "BungeeCord", out.toByteArray());}}
	
	
	
	public static void playerCount(String server){	
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("PlayerCount");
    out.writeUTF(server); 
	Bukkit.getServer().sendPluginMessage(Bukkit.getPluginManager().getPlugin("MelonCore"), "BungeeCord", out.toByteArray());}

	public static void refreshPlayerCount(){
    playerCount("bending");
    playerCount("raids");    
    playerCount("lobby");}

	
	
	
	public static void serverMessage(String data, String player ,String a){
		  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
          DataOutputStream datastream = new DataOutputStream(bytes);
          try {
              datastream.writeUTF(data);
              datastream.writeUTF(player);
              datastream.writeUTF(a);
              Bukkit.getServer().sendPluginMessage(Bukkit.getPluginManager().getPlugin("MelonCore"), "Melon", bytes.toByteArray());
          } catch (IOException e) {
             e.printStackTrace();
          }
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
