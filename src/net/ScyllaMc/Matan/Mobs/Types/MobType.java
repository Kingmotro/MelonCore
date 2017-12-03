package net.ScyllaMc.Matan.Mobs.Types;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import net.ScyllaMc.Matan.MelonCore.Msg.Language;
import net.ScyllaMc.Matan.Mobs.Mob;

public interface MobType {



	
    public double getBaseHealth();
    
    public double getBaseDamage();
    
    public double getSpeed();

    public double getLevelModifier();
    
    public int getViewRange();
    
    public String getTypeName(Language lan);
    
    public void attack(Mob m, LivingEntity t);
    
    public void assign(Mob m);

    public ItemStack[] getDrops(Mob m);

    public boolean shouldTarget(Mob m, LivingEntity t);

	
	
}
