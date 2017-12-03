package net.ScyllaMc.Matan.Util;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class Title {

	@Deprecated
	  public static void sendSubtitle(MelonPlayer player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
	  {
	    sendTitle(player, fadeIn, stay, fadeOut, null, message);
	  }
	  
	  @Deprecated
	  public static void sendTitle(MelonPlayer player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
	  {
	    sendTitle(player, fadeIn, stay, fadeOut, message, null);
	  }
	  
	  
	  public static void sendTitle(MelonPlayer player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle){
	    PlayerConnection connection = ((CraftPlayer)player.getOnlinePlayer()).getHandle().playerConnection;
	    
	    PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
	    connection.sendPacket(packetPlayOutTimes);
	    if (subtitle != null)
	    {
	      subtitle = subtitle.replaceAll("%player%", player.getOnlinePlayer().getDisplayName());
	      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
	      IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
	      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
	      connection.sendPacket(packetPlayOutSubTitle);
	    }
	    if (title != null)
	    {
	      title = title.replaceAll("%player%", player.getOnlinePlayer().getDisplayName());
	      title = ChatColor.translateAlternateColorCodes('&', title);
	      IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
	      PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
	      connection.sendPacket(packetPlayOutTitle);
	    }
	  }
	  
	  
	  
}
