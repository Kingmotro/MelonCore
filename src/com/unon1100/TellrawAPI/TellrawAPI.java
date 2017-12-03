package com.unon1100.TellrawAPI;


import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class TellrawAPI {
	
	public static StringBuilder finalize(StringBuilder...sb){
		StringBuilder msg = new StringBuilder("{\"text\":\"\",\"extra\":[");
		for(int i=0; i<sb.length;i++){
			if(i==sb.length-1){
				msg.append(sb[i]+"]}");
			}else{
				msg.append(sb[i]+",");
			}
		}
		return msg;
	}
	
	public static StringBuilder finalize(TellrawParent...tr){
		StringBuilder msg = new StringBuilder("{\"text\":\"\",\"extra\":[");
		
		for(int i=0; i<tr.length;i++){
			if(i==tr.length-1){
				msg.append(tr[i].getStringBuilder()+"]}");
			}else{
				msg.append(tr[i].getStringBuilder()+",");
			}
		}
		return msg;
	}
	public static void sendTo(String msg, Player... players){
		for(Player p:players){
			IChatBaseComponent comp = ChatSerializer.a(msg);
			PacketPlayOutChat packet = new PacketPlayOutChat(comp);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public static void sendTo(StringBuilder msg, Player... players){
		for(Player p:players){
			IChatBaseComponent comp = ChatSerializer.a(msg.toString());
			PacketPlayOutChat packet = new PacketPlayOutChat(comp);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public static void sendToAll(String msg){
		for(Player p:Bukkit.getOnlinePlayers()){
			IChatBaseComponent comp = ChatSerializer.a(msg);
			PacketPlayOutChat packet = new PacketPlayOutChat(comp);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public static void sendToAll(StringBuilder msg){
		for(Player p:Bukkit.getOnlinePlayers()){
			IChatBaseComponent comp = ChatSerializer.a(msg.toString());
			PacketPlayOutChat packet = new PacketPlayOutChat(comp);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
