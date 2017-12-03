package net.ScyllaMc.Matan.Vote;

import java.util.ArrayList;
import net.ScyllaMc.Matan.MelonPlayer.MelonPlayer;
import org.bukkit.inventory.ItemStack;

public class VoteChest
{
  public VoteChestHologram handler = new VoteChestHologram();
  public VoteChestEffects effects = new VoteChestEffects();
  public ArrayList<ItemStack> Common = new ArrayList<ItemStack>();
  public ArrayList<ItemStack> Rare = new ArrayList<ItemStack>();
  public ArrayList<ItemStack> Legendery = new ArrayList<ItemStack>();
  
  public void addLine(MelonPlayer p, String s)
  {
    this.handler.addLine(p, s);
  }
  
  public void delete(MelonPlayer p)
  {
    this.handler.delete(p);
  }
  
  public void display(MelonPlayer p)
  {
    this.handler.display(p);
  }
  
  public void playOpening(MelonPlayer p)
  {
    this.effects.playOpening(p);
  }
  
  public void setLine(MelonPlayer p, int l, String s)
  {
    this.handler.setLine(p, l, s);
  }
}
