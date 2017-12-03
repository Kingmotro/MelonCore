package com.unon1100.TellrawAPI;

import org.bukkit.ChatColor;

public class TellrawSuggest extends TellrawParent{
	
	public TellrawSuggest(String text, String cmd){
		this(text, cmd, ChatColor.WHITE);
	}
	
	public TellrawSuggest(String text, String cmd, ChatColor cc){
		msg.append("{\"text\":\""+text+"\"," +
				"\"color\":\""+ getChatColor(cc) +"\"," +
				"\"clickEvent\":{" +
				"\"action\":\"suggest_command\"," +
				"\"value\":\""+cmd+"\"");
		msg.append("},");
	}
}
