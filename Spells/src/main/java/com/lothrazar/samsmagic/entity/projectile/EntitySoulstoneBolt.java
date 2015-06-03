package com.lothrazar.samsmagic.entity.projectile; 

import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.spell.SpellSoulstone; 
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World; 

public class EntitySoulstoneBolt extends EntityThrowable//EntitySnowball
{ 
	public static int secondsFrozenOnHit;
	public static int damageToNormal = 0;//TODO CONFIG
 
    public EntitySoulstoneBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntitySoulstoneBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntitySoulstoneBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        if (mop.entityHit != null)
        {
            float damage = damageToNormal;

            if (mop.entityHit instanceof EntityBlaze)
            {
               // damage = damageToBlaze;//TODO: config file blaze damage
            }
            
            //do the snowball damage, which should be none. put out the fire
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
            
            
            if(mop.entityHit instanceof EntityLivingBase)
            {
            	EntityLivingBase e = (EntityLivingBase)mop.entityHit;

            	SpellSoulstone.addEntitySoulstone(e);
            	
            	 if(this.getThrower() instanceof EntityPlayer)
            	 {
            		 ModSpells.addChatMessage((EntityPlayer)this.getThrower(), ModSpells.lang("spell.soulstone.complete") + e.getDisplayName().getFormattedText());
            	 }
            	 
            	 
            	 

            	//if thrower is player?
        		//Util.addChatMessage(player, string);
            	//e.addPotionEffect(new PotionEffect(PotionRegistry.frozen.id, secondsFrozenOnHit * Reference.TICKS_PER_SEC,0));
            } 
        }
         
        this.setDead();
 
    }  
}