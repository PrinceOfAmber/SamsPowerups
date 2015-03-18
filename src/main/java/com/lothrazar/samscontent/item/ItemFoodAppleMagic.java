package com.lothrazar.samscontent.item;

import java.util.ArrayList; 

import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.samscontent.ModLoader; 
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ItemFoodAppleMagic extends ItemFood
{  
	private boolean hasEffect = false;
	private static int FLYING_COUNT_PER_EAT = 1;//num of ticks
	private ArrayList<Integer> potionIds;
	private ArrayList<Integer> potionDurations;
	private ArrayList<Integer> potionAmplifiers;
	
	public ItemFoodAppleMagic(int fillsHunger,boolean has_effect)
	{  
		super(fillsHunger,false);//is not edible by wolf
		hasEffect = has_effect;//true gives it enchantment shine
 
		this.setAlwaysEdible(); //can eat even if full hunger
		potionIds = new ArrayList<Integer>();
		potionDurations = new ArrayList<Integer>();
		potionAmplifiers = new ArrayList<Integer>();
		this.setCreativeTab(ModLoader.tabSamsContent);
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
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {     
		if(par2World.isRemote == false)///false means serverside
	  		for(int i = 0; i < potionIds.size(); i++)  
	  		{ 
	  			par3EntityPlayer.addPotionEffect(new PotionEffect(potionIds.get(i) ,potionDurations.get(i),potionAmplifiers.get(i)));
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
	 
	public static int timeShort = 90; // 1:30
	public static int timeLong = 8 * 60;// 8:00

	public static int hungerSmall = 1;
	public static int hungerLarge = 4; //how much it fills us up
  
	public static int chocolatePotion = Potion.digSpeed.id;//Haste
	public static int chocolateLevel = com.lothrazar.samscontent.potion.PotionRegistry.I;
	public static void initChocolate()
	{
		if(!ModLoader.configSettings.appleChocolate)
		{
			ItemRegistry.apple_chocolate = new ItemFoodAppleMagic(hungerSmall, false); 
			ItemRegistry.apple_chocolate.addEffect(chocolatePotion, timeShort, chocolateLevel + 1); 
			SamsRegistry.registerItem(ItemRegistry.apple_chocolate, "apple_chocolate");
			addRecipe(ItemRegistry.apple_chocolate, new ItemStack(Items.dye, 1, Reference.dye_cocoa) );
		 
			
			ItemRegistry.apple_chocolate_rich = new ItemFoodAppleMagic(hungerLarge, true);  
			ItemRegistry.apple_chocolate_rich.addEffect(chocolatePotion, timeLong, chocolateLevel);  
			SamsRegistry.registerItem(ItemRegistry.apple_chocolate_rich, "apple_chocolate_rich");
			 
			addRecipe(ItemRegistry.apple_chocolate_rich, new ItemStack(Items.cookie));
	 
		}
	}

	public static int lapisPotion = PotionRegistry.waterwalk.id;
	public static int lapisLevel = PotionRegistry.I;
	public static void initLapis()
	{   
		if(!ModLoader.configSettings.appleLapis)
		{ 
			ItemRegistry.apple_lapis = new ItemFoodAppleMagic(hungerSmall, false);
			ItemRegistry.apple_lapis.addEffect(lapisPotion, timeShort, lapisLevel); 
			SamsRegistry.registerItem(ItemRegistry.apple_lapis, "apple_lapis");
			
			addRecipe(ItemRegistry.apple_lapis,new ItemStack(Items.dye, 1, Reference.dye_lapis) );
	 
			ItemRegistry.apple_lapis_rich = new ItemFoodAppleMagic(hungerSmall, true);
			ItemRegistry.apple_lapis_rich.addEffect(lapisPotion, timeLong, lapisLevel); 
			SamsRegistry.registerItem(ItemRegistry.apple_lapis_rich, "apple_lapis_rich");
			
			addRecipe(ItemRegistry.apple_lapis_rich,new ItemStack(Blocks.lapis_block));
		}
	}

	public static int emeraldPotion = PotionRegistry.slowfall.id;  
	public static int emeraldLevel = PotionRegistry.I;
	public static void initEmerald()
	{   
		if(ModLoader.configSettings.appleEmerald) 
		{
			ItemRegistry.apple_emerald = new ItemFoodAppleMagic(hungerSmall, false);
			ItemRegistry.apple_emerald.addEffect(emeraldPotion, timeShort, emeraldLevel);  
			SamsRegistry.registerItem(ItemRegistry.apple_emerald, "apple_emerald");
			
			addRecipe(ItemRegistry.apple_emerald,new ItemStack(Items.emerald));
			 
			ItemRegistry.apple_emerald_rich = new ItemFoodAppleMagic(hungerSmall, true);
			ItemRegistry.apple_emerald_rich.addEffect(emeraldPotion, timeLong, emeraldLevel); 
			SamsRegistry.registerItem(ItemRegistry.apple_emerald_rich, "apple_emerald_rich");

			addRecipe(ItemRegistry.apple_emerald,new ItemStack(Blocks.emerald_block));
		}
	}
 
	public static void addRecipe(ItemFoodAppleMagic apple, ItemStack ingredient) 
	{
		GameRegistry.addRecipe(new ItemStack(apple)
			,"lll","lal","lll"  
			,'l', ingredient
			,'a', Items.apple);
		
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(apple, new ItemStack(ingredient.getItem(), 8),	0);
	} 

	public static int diamondPotion = PotionRegistry.flying.id;
	public static int diamondPotion2 = Potion.resistance.id; 
	public static int dimondLevel = PotionRegistry.I;
	public static int dimond2Level = PotionRegistry.I;
	public static void initDiamond()
	{ 
		if(!ModLoader.configSettings.appleDiamond) 
		{ 
			ItemRegistry.apple_diamond = new ItemFoodAppleMagic(hungerSmall, false);
			ItemRegistry.apple_diamond.addEffect(diamondPotion, timeShort, dimondLevel);  
			ItemRegistry.apple_diamond.addEffect(diamondPotion2, timeShort, dimond2Level);  
			SamsRegistry.registerItem(ItemRegistry.apple_diamond, "apple_diamond");
			
			addRecipe(ItemRegistry.apple_diamond,new ItemStack(Items.diamond));
		 
			 
			ItemRegistry.apple_diamond_rich = new ItemFoodAppleMagic(hungerSmall, true);
			ItemRegistry.apple_diamond_rich.addEffect(diamondPotion, timeLong, dimondLevel); 
			ItemRegistry.apple_diamond_rich.addEffect(diamondPotion2, timeLong, dimond2Level); 
			SamsRegistry.registerItem(ItemRegistry.apple_diamond_rich, "apple_diamond_rich");

			addRecipe(ItemRegistry.apple_diamond_rich,new ItemStack(Blocks.diamond_block));
			 
		}
	}

	public static int netherwartApplePotion = PotionRegistry.lavawalk.id;
	public static int netherwartAppleLevel = PotionRegistry.I;
	public static void initNether()
	{  
		if(!ModLoader.configSettings.appleNetherStar) 
		{ 
			ItemRegistry.apple_nether_star = new ItemFoodAppleMagic(hungerSmall, true);  
			ItemRegistry.apple_nether_star.addEffect(netherwartApplePotion, timeLong, netherwartAppleLevel);  
	
			SamsRegistry.registerItem(ItemRegistry.apple_nether_star, "apple_nether_star");
			 
			addRecipe(ItemRegistry.apple_nether_star,new ItemStack(Items.nether_wart));
			 
		}
	}

	public static int enderPotion = PotionRegistry.ender.id;
	public static int enderLevel = PotionRegistry.I;
	public static void initEnder()
	{  
		if(!ModLoader.configSettings.appleNetherStar) 
		{ 
			ItemRegistry.apple_ender = new ItemFoodAppleMagic(hungerLarge, false);   
			ItemRegistry.apple_ender.addEffect(enderPotion, timeLong, enderLevel);  
	
			SamsRegistry.registerItem(ItemRegistry.apple_ender, "apple_ender");
			
			GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_ender)
				,"lll","lal","lll"  
				,'l', Items.ender_pearl
				,'a', Items.apple); 
			
			if(ModLoader.configSettings.uncraftGeneral) 
				GameRegistry.addSmelting(ItemRegistry.apple_ender, new ItemStack(Items.ender_pearl, 8),	0); 
		}
	}
}
