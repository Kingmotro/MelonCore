package com.unon1100.TellrawAPI;

import org.bukkit.ChatColor;

public class TellrawText extends TellrawParent{
	
	public TellrawText(String text){
		this(text, ChatColor.WHITE);
	}
	
	public TellrawText(String text, ChatColor cc){
		msg.append("{\"text\":\""+text+"\",\"color\":\""+getChatColor(cc)+"\",");
	}
}
