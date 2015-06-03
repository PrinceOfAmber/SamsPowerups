package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ModMain; 
import com.lothrazar.util.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemFoodAppleHeart extends ItemFood
{ 
	public ItemFoodAppleHeart()
	{  
		super(4,false);//is not edible by wolf
		this.setAlwaysEdible(); //can eat even if full hunger
		this.setCreativeTab(ModMain.tabSamsContent); 
	}
	 
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World world, EntityPlayer player)
    {     
		if(world.isRemote == false)  //false means serverside
		{ 
			Util.setMaxHealth(player, (int)(player.getMaxHealth() + 2));
		}
		else
			Util.spawnParticle(world, EnumParticleTypes.VILLAGER_HAPPY, player.getPosition());
			
		Util.playSoundAt(player, Reference.sounds.zombieremedy);
		
    }
	
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer, depending on if this was set in constructor
    }
	 
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.EPIC;
	} 
	 
	public static void addRecipe(ItemFoodAppleHeart apple) 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(apple)
			, Items.diamond
			, Items.apple);
		
		if(ModMain.cfg.uncraftGeneral) 
			GameRegistry.addSmelting(apple, new ItemStack(Items.diamond, 1),	0);
	} 
	 /*
	@Override
	public void addInformation(ItemStack held, EntityPlayer player, List list, boolean par4) 
	{   
		Potion p;
		for(int i = 0; i < potionIds.size(); i++)  
  		{ 
  			p = Potion.potionTypes[potionIds.get(i)];
  			 
  			list.add(Util.lang(p.getName()));   
  		}   
	} */
}