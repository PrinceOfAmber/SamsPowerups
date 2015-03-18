package com.lothrazar.samscontent.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.item.*;
import com.lothrazar.util.SamsRegistry;

public class ItemRegistry 
{
	public static ItemBucketStorage itemWater;
	public static ItemBucketStorage itemLava;
	public static ItemEnderBook itemEnderBook = null;
	public static ItemWandBuilding wandBuilding;
	public static ItemWandChest wandChest; 
	public static ItemChestSack itemChestSack;
	public static ItemWandHarvest wandHarvest;
	public static ItemWandTransform wandTransform; 
	public static ItemWandLivestock wandLivestock;
	public static ItemWandProspect wandProspect;  
	public static ItemFoodAppleMagic apple_emerald;
	public static ItemFoodAppleMagic apple_emerald_rich;
	public static ItemFoodAppleMagic apple_diamond; 
	public static ItemFoodAppleMagic apple_lapis;
	public static ItemFoodAppleMagic apple_lapis_rich;
	public static ItemFoodAppleMagic apple_chocolate;
	public static ItemFoodAppleMagic apple_chocolate_rich;
	public static ItemFoodAppleMagic apple_nether_star;
	public static ItemWandFire wandFire;
	public static ItemWandCopyPaste wandCopy;
	public static ItemBaseWand baseWand;
	public static ItemToolFlint flintTool;
	public static ItemFoodAppleMagic apple_diamond_rich;
	public static ItemFoodAppleMagic apple_ender;
	 
	public static void registerItems()
	{
		ItemBaseWand.Init();
		ItemToolFlint.init();
		ItemWandFire.init();
		ItemWandCopyPaste.init();
		
		if(ModLoader.configSettings.lavaStorage)
		{ 
			ItemRegistry.itemLava = new ItemBucketStorage();

			SamsRegistry.registerItem(ItemRegistry.itemLava, "bucket_storage_lava");
	 
			ItemBucketStorage.addRecipeLava();
		}

		if(ModLoader.configSettings.waterStorage)
		{ 
			ItemRegistry.itemWater = new ItemBucketStorage();

			SamsRegistry.registerItem(ItemRegistry.itemWater, "bucket_storage_water");

			ItemBucketStorage.addRecipeWater();
		}

		if(ModLoader.configSettings.wandBuilding)
		{ 
			ItemRegistry.wandBuilding = new ItemWandBuilding(); 
			SamsRegistry.registerItem(ItemRegistry.wandBuilding, "wand_building" );  
			 
			ItemWandBuilding.addRecipe(); 
		}
		 
		if(ModLoader.configSettings.wandChest)
		{   
			ItemRegistry.itemChestSack = new ItemChestSack();   
			SamsRegistry.registerItem(ItemRegistry.itemChestSack, "chest_sack");
			
			ItemRegistry.wandChest = new ItemWandChest(); 
			SamsRegistry.registerItem(ItemRegistry.wandChest, "wand_chest");
	 
			ItemWandChest.addRecipe();  
		}

		if(ModLoader.configSettings.wandTransform)
		{   
			ItemRegistry.wandTransform = new ItemWandTransform(); 
			SamsRegistry.registerItem(ItemRegistry.wandTransform, "wand_transform");

			ItemWandTransform.addRecipe();  
		}

		if(ModLoader.configSettings.wandHarvest)
		{   
			ItemRegistry.wandHarvest = new ItemWandHarvest();
			SamsRegistry.registerItem(ItemRegistry.wandHarvest, "wand_harvest");

			ItemWandHarvest.addRecipe();  
		}
		
		if(ModLoader.configSettings.wandLivestock)
		{   
			ItemRegistry.wandLivestock = new ItemWandLivestock();
	  
			SamsRegistry.registerItem(ItemRegistry.wandLivestock, "wand_livestock");

			ItemWandLivestock.addRecipe();  
		}

		if(ModLoader.configSettings.wandProspect)
		{   
			ItemRegistry.wandProspect = new ItemWandProspect();
	  
			SamsRegistry.registerItem(ItemRegistry.wandProspect, "wand_prospect");

			ItemWandProspect.addRecipe();  
		}

		if(ModLoader.configSettings.enderBook)
		{ 
			ItemRegistry.itemEnderBook = new ItemEnderBook();

			SamsRegistry.registerItem(ItemRegistry.itemEnderBook, "book_ender");

			ItemEnderBook.addRecipe();
		}
		
		ItemFoodAppleMagic.initEmerald();

		ItemFoodAppleMagic.initDiamond();

		ItemFoodAppleMagic.initLapis();

		ItemFoodAppleMagic.initChocolate();

		ItemFoodAppleMagic.initNether();
		
		ItemFoodAppleMagic.initEnder();
	}
}
