package com.lothrazar.samscontent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lothrazar.samscontent.item.*;
import com.lothrazar.samscontent.item.ItemFoodTeleport.TeleportType;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference; 

public class ItemRegistry 
{ 
	public static ItemEnderBook itemEnderBook = null;
	public static ItemWandBuilding wandBuilding;
	public static ItemChestSackEmpty wandChest; 
	public static ItemChestSack itemChestSack;
	public static ItemMagicHarvester magic_harvester;
	public static ItemWandTransform wandTransform; 
	public static ItemRespawnEggEmpty respawn_egg_empty; 
	public static ItemFoodAppleMagic apple_emerald;
	public static ItemFoodAppleMagic apple_emerald_rich;
	public static ItemFoodAppleMagic apple_diamond; 
	public static ItemFoodAppleMagic apple_lapis;
	public static ItemFoodAppleMagic apple_lapis_rich;
	public static ItemFoodAppleMagic apple_chocolate;
	public static ItemFoodAppleMagic apple_chocolate_rich;
	public static ItemFoodAppleMagic apple_nether_star; 
	public static ItemPaperCarbon carbon_paper;
	public static ItemBaseWand baseWand;
	//public static ItemToolFlint flintTool;
	public static ItemFoodAppleMagic apple_diamond_rich;
	public static ItemFoodAppleMagic apple_ender;
	public static ItemWandWater wandWater;
	public static ItemWandLightning wandLightning;
	public static 	Item beetrootSeed ;
	public static Item beetrootItem;
	public static Item beetrootSoup;
	
	public static ArrayList<Item> items = new ArrayList<Item>();
	public static ItemFireballThrowable fire_charge_throw;
	public static ItemSnowballFrozen frozen_snowball;
	public static ItemFoodTeleport foodBed;
	public static ItemFoodTeleport foodSpawn;
	public static ItemFoodTeleport foodSky;
	public static ItemRespawnEggAnimal respawn_egg;
	
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(item, name);
		 
		 items.add(item);
	}
	 
	public static void registerItems()
	{
		//needed for all wands; no config.
		ItemRegistry.baseWand = new ItemBaseWand(); 
		ItemRegistry.registerItem(ItemRegistry.baseWand, "base_wand" );   
		ItemBaseWand.addRecipe();	
		 
		if(ModSamsContent.configSettings.beetroot)
		{ 
			beetrootSeed = new ItemSeeds(BlockRegistry.beetrootCrop, Blocks.farmland).setCreativeTab(ModSamsContent.tabSamsContent);
			ItemRegistry.registerItem(beetrootSeed, "beetroot_seed");
			
			beetrootItem = new ItemFood(3, false).setCreativeTab(ModSamsContent.tabSamsContent);
			ItemRegistry.registerItem(beetrootItem, "beetroot_item");
			
			beetrootSoup = new ItemSoup(8).setCreativeTab(ModSamsContent.tabSamsContent); 
			ItemRegistry.registerItem(beetrootSoup, "beetroot_soup");
		}
		 
		if(ModSamsContent.configSettings.wandFireball)
		{ 
			ItemRegistry.fire_charge_throw = new ItemFireballThrowable();

			registerItem(ItemRegistry.fire_charge_throw, "fire_charge_throw");
	 
			ItemFireballThrowable.addRecipe();		 
		}
		 
		if(ModSamsContent.configSettings.frozen_snowball)
		{ 
			ItemRegistry.frozen_snowball = new ItemSnowballFrozen();

			registerItem(ItemRegistry.frozen_snowball, "frozen_snowball");
	 
			ItemSnowballFrozen.addRecipe();		 
		}
		 
		if(ModSamsContent.configSettings.wandWater)
		{  
			ItemRegistry.wandWater = new ItemWandWater();

			ItemRegistry.registerItem(ItemRegistry.wandWater, "wand_water");
	 
			ItemWandWater.addRecipe();		 
		}
		
		if(ModSamsContent.configSettings.wandLightning)
		{  
			ItemRegistry.wandLightning = new ItemWandLightning();

			ItemRegistry.registerItem(ItemRegistry.wandLightning, "wand_lightning");
	 
			ItemWandLightning.addRecipe();		 
		}

		if(ModSamsContent.configSettings.wandCopy)
		{ 
			ItemRegistry.carbon_paper = new ItemPaperCarbon();

			ItemRegistry.registerItem(ItemRegistry.carbon_paper, "carbon_paper");

			ItemPaperCarbon.addRecipe();  
		}
		   
		if(ModSamsContent.configSettings.wandBuilding)
		{ 
			ItemRegistry.wandBuilding = new ItemWandBuilding(); 
			ItemRegistry.registerItem(ItemRegistry.wandBuilding, "wand_building" );  
			 
			ItemWandBuilding.addRecipe(); 
		}
		 
		if(ModSamsContent.configSettings.chest_sack)
		{   
			ItemRegistry.itemChestSack = new ItemChestSack();   
			ItemRegistry.registerItem(ItemRegistry.itemChestSack, "chest_sack");
			
			ItemRegistry.wandChest = new ItemChestSackEmpty(); 
			ItemRegistry.registerItem(ItemRegistry.wandChest, "chest_sack_empty");
	 
			ItemChestSackEmpty.addRecipe();  
		}

		if(ModSamsContent.configSettings.wandTransform)
		{   
			ItemRegistry.wandTransform = new ItemWandTransform(); 
			ItemRegistry.registerItem(ItemRegistry.wandTransform, "wand_transform");

			ItemWandTransform.addRecipe();  
		}

		if(ModSamsContent.configSettings.wandHarvest)
		{   
			ItemRegistry.magic_harvester = new ItemMagicHarvester();
			ItemRegistry.registerItem(ItemRegistry.magic_harvester, "magic_harvester");

			ItemMagicHarvester.addRecipe();  
		}
		
		if(ModSamsContent.configSettings.respawn_egg)
		{   
			respawn_egg = new ItemRespawnEggAnimal();
			ItemRegistry.registerItem(respawn_egg, "respawn_egg");
			
			ItemRegistry.respawn_egg_empty = new ItemRespawnEggEmpty(); 
			ItemRegistry.registerItem(ItemRegistry.respawn_egg_empty, "respawn_egg_empty"); 
			ItemRespawnEggEmpty.addRecipe();  
		}
 
		if(ModSamsContent.configSettings.enderBook)
		{ 
			ItemRegistry.itemEnderBook = new ItemEnderBook(); 
			ItemRegistry.registerItem(ItemRegistry.itemEnderBook, "book_ender"); 
			ItemEnderBook.addRecipe();
		}
		 
		if(ModSamsContent.configSettings.appleEmerald) 
		{
			ItemRegistry.apple_emerald = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_emerald.addEffect(PotionRegistry.slowfall.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.apple_emerald.addEffect(Potion.jump.id, ItemFoodAppleMagic.timeShort, PotionRegistry.V); 
			 
			ItemRegistry.registerItem(ItemRegistry.apple_emerald, "apple_emerald");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald,new ItemStack(Items.emerald));
			 
			ItemRegistry.apple_emerald_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);
			ItemRegistry.apple_emerald_rich.addEffect(PotionRegistry.slowfall.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I); 
			ItemRegistry.apple_emerald_rich.addEffect(Potion.jump.id, ItemFoodAppleMagic.timeShort, PotionRegistry.V); 
			ItemRegistry.registerItem(ItemRegistry.apple_emerald_rich, "apple_emerald_rich");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald,new ItemStack(Blocks.emerald_block));
			 
		}   
		 
		if(ModSamsContent.configSettings.appleDiamond) 
		{  
			ItemRegistry.apple_diamond = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_diamond.addEffect(PotionRegistry.flying.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.apple_diamond.addEffect(Potion.resistance.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);   
			ItemRegistry.apple_diamond.addEffect(Potion.absorption.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.registerItem(ItemRegistry.apple_diamond, "apple_diamond");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_diamond,new ItemStack(Items.diamond));
		 
			ItemRegistry.apple_diamond_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);
			ItemRegistry.apple_diamond_rich.addEffect(PotionRegistry.flying.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I); 
			ItemRegistry.apple_diamond_rich.addEffect(Potion.resistance.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I); 
			ItemRegistry.apple_diamond_rich.addEffect(Potion.absorption.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.registerItem(ItemRegistry.apple_diamond_rich, "apple_diamond_rich");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_diamond_rich,new ItemStack(Blocks.diamond_block));
		}
 
		if(ModSamsContent.configSettings.appleLapis)
		{ 
			ItemRegistry.apple_lapis = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_lapis.addEffect(PotionRegistry.waterwalk.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I); 
			 
			ItemRegistry.registerItem(ItemRegistry.apple_lapis, "apple_lapis");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_lapis,new ItemStack(Items.dye, 1, Reference.dye_lapis) );
	 
			ItemRegistry.apple_lapis_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);
			ItemRegistry.apple_lapis_rich.addEffect(PotionRegistry.waterwalk.id, ItemFoodAppleMagic.timeLong,PotionRegistry.I);
			 
			ItemRegistry.registerItem(ItemRegistry.apple_lapis_rich, "apple_lapis_rich");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_lapis_rich,new ItemStack(Blocks.lapis_block));
			 
		}
		  
		if(ModSamsContent.configSettings.appleChocolate)
		{
			ItemRegistry.apple_chocolate = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false); 
			ItemRegistry.apple_chocolate.addEffect(Potion.digSpeed.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I + 1); 
			ItemRegistry.registerItem(ItemRegistry.apple_chocolate, "apple_chocolate");
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_chocolate, new ItemStack(Items.dye, 1, Reference.dye_cocoa) );
		  
			ItemRegistry.apple_chocolate_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, true);  
			ItemRegistry.apple_chocolate_rich.addEffect(Potion.digSpeed.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I);  
			ItemRegistry.registerItem(ItemRegistry.apple_chocolate_rich, "apple_chocolate_rich");
			 
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_chocolate_rich, new ItemStack(Items.cookie));
		 
		}
	 
		if(ModSamsContent.configSettings.appleNetherStar) 
		{ 
			ItemRegistry.apple_nether_star = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);  
		 
			ItemRegistry.apple_nether_star.addEffect( PotionRegistry.lavawalk.id, ItemFoodAppleMagic.timeLong,  PotionRegistry.I);  
			 
			ItemRegistry.registerItem(ItemRegistry.apple_nether_star, "apple_nether_star");
		 
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_nether_star,new ItemStack(Items.nether_wart));
		}
	 
		if(ModSamsContent.configSettings.appleNetherStar)//TODO: apple ender in config 
		{ 
			ItemRegistry.apple_ender = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, false);   
			ItemRegistry.apple_ender.addEffect(PotionRegistry.ender.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I);  
			 
			ItemRegistry.registerItem(ItemRegistry.apple_ender, "apple_ender");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_ender,new ItemStack(Items.ender_pearl)) ;
		} 
		 
		//TODO: config for TP food
		
		ItemRegistry.foodBed = new ItemFoodTeleport(2, TeleportType.BEDHOME);
		ItemRegistry.registerItem(foodBed, "tpfood_bed");
		ItemFoodTeleport.addRecipe(ItemRegistry.foodBed,new ItemStack(Blocks.wool));
		
		ItemRegistry.foodSky = new ItemFoodTeleport(2, TeleportType.WORLDHEIGHT);
		ItemRegistry.registerItem(foodSky, "tpfood_sky");
		ItemFoodTeleport.addRecipe(ItemRegistry.foodSky,new ItemStack(Items.emerald));
		
		ItemRegistry.foodSpawn = new ItemFoodTeleport(2, TeleportType.WORLDSPAWN);
		ItemRegistry.registerItem(foodSpawn, "tpfood_spawn");
		ItemFoodTeleport.addRecipe(ItemRegistry.foodSpawn,new ItemStack(Items.gold_ingot));
		 
	}
}
