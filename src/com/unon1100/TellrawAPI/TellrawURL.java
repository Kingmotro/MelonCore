package com.unon1100.TellrawAPI;

import java.net.URL;

import org.bukkit.ChatColor;

public class TellrawURL extends TellrawParent{
	
	
	public TellrawURL(String text, String url){
		this(text, url, ChatColor.AQUA);
	}
	
	public TellrawURL(String text, String url, ChatColor cc){
		if(!url.startsWith("http://")){
			url = "http://"+url;
		}
		msg.append("{\"text\":\""+text+"\"," +
				"\"color\":\""+ getChatColor(cc) +"\"," +
				"\"clickEvent\":{" +
				"\"action\":\"open_url\"," +
				"\"value\":\""+url+"\"");
		msg.append("},");
	}
	
	
	public TellrawURL(String text, URL url){
		this(text, url.toString(), ChatColor.AQUA);
	}
	
	public TellrawURL(String text, URL url, ChatColor cc){
		this(text, url.toString(), cc);
	}
}
