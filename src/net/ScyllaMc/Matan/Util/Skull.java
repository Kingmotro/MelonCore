package net.ScyllaMc.Matan.Util;

import java.util.UUID;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;


public class Skull {

		
	public static String firebender = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODI1MWRjMzY2ZDJiMmVkYjNiMDM1NDRlOGI5OGRiODVjYjNkNjEwNjQ0MGFlZjI3ZWE3OWI5ZjM5Y2QxNzhiIn19fQ==";
	public static String bandit1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODI1MWRjMzY2ZDJiMmVkYjNiMDM1NDRlOGI5OGRiODVjYjNkNjEwNjQ0MGFlZjI3ZWE3OWI5ZjM5Y2QxNzhiIn19fQ==";
	public static String bandit2 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZkNjYwMTI0MGE5ZWRiOGE2NWY2ZmU0MzRlMzZiZmI3ODNhYjE4NGFmOTc5NzlhOTQxM2UyNGI1M2RlNTkifX19=";
	public static String bandit3 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ5ODQ0MmJlMTNhNjRlMjFlZmFmMWQzMTYzZDY3MGU4ZWYzZGM2MWY3MzBlNzliZjNlZDk3NTYyMWIifX19";
	
	 public  static ItemStack getSkull(String s) {
	       
			  net.minecraft.server.v1_8_R3.ItemStack itemStack = new net.minecraft.server.v1_8_R3.ItemStack(net.minecraft.server.v1_8_R3.Item.getById(397), 1, 3);
			  net.minecraft.server.v1_8_R3.NBTTagCompound nbt = new net.minecraft.server.v1_8_R3.NBTTagCompound();
			  nbt.setString("Value", s);
			  NBTTagList nbtx = new NBTTagList();
			  nbtx.add(nbt);
			  net.minecraft.server.v1_8_R3.NBTTagCompound nbt2 = new net.minecraft.server.v1_8_R3.NBTTagCompound();
			  nbt2.set("textures", nbtx);
			  net.minecraft.server.v1_8_R3.NBTTagCompound nbt3 = new NBTTagCompound();
			  nbt3.set("Properties", nbt2);
			  nbt3.setString("Id", UUID.randomUUID().toString());
			  net.minecraft.server.v1_8_R3.NBTTagCompound nbt4 = new net.minecraft.server.v1_8_R3.NBTTagCompound();
			  nbt4.set("SkullOwner", nbt3);
			  itemStack.setTag(nbt4);
			  return CraftItemStack.asBukkitCopy(itemStack);
			 }


	    }
	

