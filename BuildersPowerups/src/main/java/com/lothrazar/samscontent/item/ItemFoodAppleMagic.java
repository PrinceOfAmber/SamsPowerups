package com.lothrazar.samscontent.item;

import java.util.ArrayList; 
import java.util.List;
 
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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFoodAppleMagic extends ItemFood
{  
	private boolean hasEffect = false;
	private ArrayList<Integer> potionIds;
	private ArrayList<Integer> potionDurations;
	private ArrayList<Integer> potionAmplifiers;
	
	public ItemFoodAppleMagic(int fillsHunger,boolean has_effect)
	{  
		super(fillsHunger,false);//is not edible by wolf
		hasEffect = has_effect;//true gives it enchantment shine
 
		this.setAlwaysEdible(); //can eat even if full hunger
		this.setCreativeTab(ModMain.tabSamsContent);
		potionIds = new ArrayList<Integer>();
		potionDurations = new ArrayList<Integer>();
		potionAmplifiers = new ArrayList<Integer>();
	}
	 
	public ItemFoodAppleMagic addEffect(int potionId,int potionDuration,int potionAmplifier)
	{
		int TICKS_PER_SEC = 20;
		
		potionIds.add(potionId);
		potionDurations.add(potionDuration * TICKS_PER_SEC);
		potionAmplifiers.add(potionAmplifier);
		 
		return this;//to chain together
	}
	
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World world, EntityPlayer player)
    {     
		if(world.isRemote == false)  //false means serverside
	  		for(int i = 0; i < potionIds.size(); i++)  
	  		{ 
	  			Util.addOrMergePotionEffect(player, new PotionEffect(potionIds.get(i) ,potionDurations.get(i),potionAmplifiers.get(i)));
	  		//	par3EntityPlayer.addPotionEffect();
	  		}  
    }
	
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return hasEffect; //give it shimmer, depending on if this was set in constructor
    }
	 
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		 if(hasEffect)
			 return EnumRarity.EPIC; //dynamic text to match the two apple colours
		 else 
			 return EnumRarity.RARE;
	} 
	 
	

	public static int hungerSmall = 1;
	public static int hungerLarge = 4; //how much it fills us up
   
	public static void addRecipe(ItemFoodAppleMagic apple, ItemStack ingredient) 
	{
		GameRegistry.addRecipe(new ItemStack(apple)
			,"lll","lal","lll"  
			,'l', ingredient
			,'a', Items.apple);
		
		if(ModMain.cfg.uncraftGeneral) 
			GameRegistry.addSmelting(apple, new ItemStack(ingredient.getItem(), 8, ingredient.getMetadata()),	0);
	} 
	 
	@Override
	public void addInformation(ItemStack held, EntityPlayer player, List list, boolean par4) 
	{   
		Potion p;
		for(int i = 0; i < potionIds.size(); i++)  
  		{ 
  			p = Potion.potionTypes[potionIds.get(i)];
  			 
  			list.add(Util.lang(p.getName()));   
  		}   
	} 
}