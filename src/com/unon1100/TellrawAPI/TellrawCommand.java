package com.unon1100.TellrawAPI;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;

public class TellrawCommand extends TellrawParent{
	
	public TellrawCommand(String text, Command cmd, String args, ChatColor cc){
		this(text,cmd+" "+args,cc);
	}
	
	public TellrawCommand(String text, String cmd){
		this(text, cmd, ChatColor.WHITE);
	}
	
	public TellrawCommand(String text, String chat, ChatColor cc){
		msg.append("{\"text\":\""+text+"\"," +
				"\"color\":\""+ getChatColor(cc) +"\"," +
				"\"clickEvent\":{" +
				"\"action\":\"run_command\"," +
				"\"value\":\""+chat+"\"");
		msg.append("},");
	}
}