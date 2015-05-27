package com.lothrazar.samscontent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import com.lothrazar.samscontent.item.*; 
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference; 

public class ItemRegistry 
{ 
	public static ArrayList<Item> items = new ArrayList<Item>();
	
	public static ItemEnderBook itemEnderBook; 
	public static ItemChestSack itemChestSack; 
	public static ItemRespawnEggEmpty respawn_egg_empty; 
	public static ItemFoodAppleMagic apple_emerald;
	public static ItemFoodAppleMagic apple_diamond; 
	public static ItemFoodAppleMagic apple_lapis;
	public static ItemFoodAppleMagic apple_chocolate;
	public static ItemFoodAppleMagic apple_netherwart; 
	public static ItemPaperCarbon carbon_paper;  
	public static ItemFoodAppleMagic apple_ender;
	//public static ItemWandWater wandWater;
 
	public static Item beetroot_seed ;
	public static Item beetrootItem;
	public static Item beetrootSoup;	
	
	public static ItemHorseFood emeraldCarrot; 
	public static ItemHorseFood lapisCarrot; 
	public static ItemHorseFood diamondCarrot; 
	 
	public static ItemRespawnEggAnimal respawn_egg; 
	public static ItemSoulstone soulstone;
	public static ItemSoulstone soulstone_persist;
   
	public static int emerald_harvestLevel=2;
	public static int emerald_maxUses=32;
	public static float emerald_efficiency=12F;
	public static float emerald_damage=0F;
	public static int emerald_enchantability=22;
	//String name, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability)
	public static ToolMaterial MATERIAL_EMERALD;
	public static int timePotionShort = 90; // 1:30
	public static int timePotionLong = 8 * 60;// 8:00
	
	public static Item spell_water_dummy;
	public static Item spell_frostbolt_dummy;
	public static Item spell_waterwalk_dummy;
	public static Item spell_harvest_dummy;
	public static Item spell_lightning_dummy;
	public static Item spell_jump_dummy;
	public static Item spell_ghost_dummy;
	public static Item spell_enderinv_dummy;
	public static void registerItems()
	{   
		ItemRegistry.itemChestSack = new ItemChestSack();   
		ItemRegistry.registerItem(ItemRegistry.itemChestSack, "chest_sack");
		
		spell_water_dummy = new Item();
		ItemRegistry.registerItem(spell_water_dummy, "spell_water_dummy"); 
		spell_jump_dummy = new Item();
		ItemRegistry.registerItem(spell_jump_dummy, "spell_jump_dummy");
		spell_frostbolt_dummy = new Item();
		ItemRegistry.registerItem(spell_frostbolt_dummy, "spell_frostbolt_dummy");
		spell_waterwalk_dummy = new Item();
		ItemRegistry.registerItem(spell_waterwalk_dummy, "spell_waterwalk_dummy");
		spell_harvest_dummy = new Item();
		ItemRegistry.registerItem(spell_harvest_dummy, "spell_harvest_dummy");
		spell_lightning_dummy = new Item();
		ItemRegistry.registerItem(spell_lightning_dummy, "spell_lightning_dummy");
		spell_ghost_dummy = new Item();
		ItemRegistry.registerItem(spell_ghost_dummy, "spell_ghost_dummy");
		spell_enderinv_dummy = new Item();
		ItemRegistry.registerItem(spell_enderinv_dummy, "spell_enderinv_dummy");
		
		if(ModMain.cfg.emerald_armor)
		{
			MATERIAL_EMERALD = EnumHelper.addToolMaterial("emerald", 
					emerald_harvestLevel, 
					emerald_maxUses, 
					emerald_efficiency, 
					emerald_damage, 
					emerald_enchantability);
			//thanks for help: http://bedrockminer.jimdo.com/modding-tutorials/basic-modding-1-7/custom-tools-swords/
			 int xp = 1;
			
			ItemSword emerald_sword = new ItemSword(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_sword, "emerald_sword");
			emerald_sword.setCreativeTab(ModMain.tabSamsContent);
			
			GameRegistry.addShapedRecipe(new ItemStack(emerald_sword)
				, " e "," e "," s "
				,'e',new ItemStack(Items.emerald)
				,'s',new ItemStack(Items.stick));
			

 // gggg these Constructors are protected./ this is the only reason i have subclasses, need subclasses
			
			ItemSamPickaxe emerald_pickaxe = new ItemSamPickaxe(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_pickaxe, "emerald_pickaxe");
			emerald_pickaxe.setCreativeTab(ModMain.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_pickaxe)
				, "eee"," s "," s "
				,'e',new ItemStack(Items.emerald)
				,'s',new ItemStack(Items.stick));

			ItemAxe emerald_axe = new ItemSamAxe(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_axe, "emerald_axe");
			emerald_axe.setCreativeTab(ModMain.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_axe)
				, "ee ","es "," s "
				,'e',new ItemStack(Items.emerald)
				,'s',new ItemStack(Items.stick));
			GameRegistry.addShapedRecipe(new ItemStack(emerald_axe)
				, " ee"," se"," s "
				,'e',new ItemStack(Items.emerald)
				,'s',new ItemStack(Items.stick));
			
			ItemSpade emerald_spade = new ItemSpade(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_spade, "emerald_spade");
			emerald_spade.setCreativeTab(ModMain.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_spade)
				, " e "," s "," s "
				,'e',new ItemStack(Items.emerald)
				,'s',new ItemStack(Items.stick));
			
			ItemHoe emerald_hoe = new ItemHoe(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_hoe, "emerald_hoe");
			emerald_hoe.setCreativeTab(ModMain.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_hoe)
				, "ee "," s "," s "
				,'e',new ItemStack(Items.emerald)
				,'s',new ItemStack(Items.stick));
			GameRegistry.addShapedRecipe(new ItemStack(emerald_hoe)
				, " ee"," s "," s "
				,'e',new ItemStack(Items.emerald)
				,'s',new ItemStack(Items.stick));
			

			if(ModMain.cfg.uncraftGeneral)
			{
				GameRegistry.addSmelting(emerald_spade, new ItemStack(Items.emerald,1), xp);
				GameRegistry.addSmelting(emerald_sword, new ItemStack(Items.emerald,2), xp);
				GameRegistry.addSmelting(emerald_hoe, new ItemStack(Items.emerald,2), xp);
				GameRegistry.addSmelting(emerald_pickaxe, new ItemStack(Items.emerald,3), xp);
				GameRegistry.addSmelting(emerald_axe, new ItemStack(Items.emerald,3), xp);
			}
		}
 
		if(ModMain.cfg.item_soulstone)
		{
			soulstone = new ItemSoulstone(false);
			ItemRegistry.registerItem(soulstone, "soulstone");
			soulstone.addRecipe();

			soulstone_persist = new ItemSoulstone(true);
			ItemRegistry.registerItem(soulstone_persist, "soulstone_persist");
			soulstone_persist.addRecipe();
		}
 
		
		if(ModMain.cfg.horse_food_upgrades )
		{  
			emeraldCarrot = new ItemHorseFood();
			ItemRegistry.registerItem(emeraldCarrot, "horse_upgrade_type");
			
			lapisCarrot = new ItemHorseFood();
			ItemRegistry.registerItem(lapisCarrot, "horse_upgrade_variant");
			
			diamondCarrot = new ItemHorseFood();
			ItemRegistry.registerItem(diamondCarrot, "horse_upgrade_health"); 
			
			ItemHorseFood.addRecipes();
		}
		
		if(ModMain.cfg.beetroot)
		{  
			
			beetroot_seed = new ItemSeeds(BlockRegistry.beetroot_crop, Blocks.farmland).setCreativeTab(ModMain.tabSamsContent);
			ItemRegistry.registerItem(beetroot_seed, "beetroot_seed");
			
			beetrootItem = new ItemFood(3, false).setCreativeTab(ModMain.tabSamsContent);
			ItemRegistry.registerItem(beetrootItem, "beetroot_item");
		
			beetrootSoup = new ItemSoup(8).setCreativeTab(ModMain.tabSamsContent); 
			ItemRegistry.registerItem(beetrootSoup, "beetroot_soup");
			
			GameRegistry.addRecipe(new ItemStack(beetrootSoup), 
					"bbb", 
					"bbb",
					" u ", 
					'b', beetrootItem, 
					'u', Items.bowl
					);
		}
  

		if(ModMain.cfg.carbon_paper)
		{ 
			ItemRegistry.carbon_paper = new ItemPaperCarbon();

			ItemRegistry.registerItem(ItemRegistry.carbon_paper, "carbon_paper");

			ItemPaperCarbon.addRecipe();  
		}
	 
		if(ModMain.cfg.respawn_egg)
		{   
			respawn_egg = new ItemRespawnEggAnimal();
			ItemRegistry.registerItem(respawn_egg, "respawn_egg");
			
			ItemRegistry.respawn_egg_empty = new ItemRespawnEggEmpty(); 
			ItemRegistry.registerItem(ItemRegistry.respawn_egg_empty, "respawn_egg_empty"); 
			ItemRespawnEggEmpty.addRecipe();  
		}
 
		if(ModMain.cfg.enderBook)
		{ 
			ItemRegistry.itemEnderBook = new ItemEnderBook(); 
			ItemRegistry.registerItem(ItemRegistry.itemEnderBook, "book_ender"); 
			ItemEnderBook.addRecipe();
		}
		 
		if(ModMain.cfg.appleEmerald) 
		{
	 
			//potion effects
			
			ItemRegistry.apple_emerald = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, false);
			ItemRegistry.apple_emerald.addEffect(Potion.digSlowdown.id, timePotionLong, PotionRegistry.I);
			ItemRegistry.apple_emerald.addEffect(Potion.weakness.id, timePotionLong, PotionRegistry.I);
			ItemRegistry.apple_emerald.addEffect(Potion.moveSpeed.id, timePotionLong, PotionRegistry.V);  
			
			ItemRegistry.registerItem(ItemRegistry.apple_emerald, "apple_emerald");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald,new ItemStack(Items.emerald));
	
		}   
		 
		if(ModMain.cfg.appleDiamond) 
		{  
			ItemRegistry.apple_diamond = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			//ItemRegistry.apple_diamond.addEffect(PotionRegistry.flying.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.apple_diamond.addEffect(Potion.resistance.id, timePotionLong, PotionRegistry.IV);   
			ItemRegistry.apple_diamond.addEffect(Potion.absorption.id, timePotionLong, PotionRegistry.V);  
			ItemRegistry.registerItem(ItemRegistry.apple_diamond, "apple_diamond");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_diamond,new ItemStack(Items.diamond));

		}
 
		if(ModMain.cfg.appleLapis)
		{ 
			ItemRegistry.apple_lapis = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, false);
			ItemRegistry.apple_lapis.addEffect(Potion.absorption.id, timePotionLong, PotionRegistry.I); 
			ItemRegistry.apple_lapis.addEffect(Potion.weakness.id, timePotionShort, PotionRegistry.I);
			ItemRegistry.apple_lapis.addEffect(Potion.digSpeed.id, timePotionLong, PotionRegistry.II); 
			 
			ItemRegistry.registerItem(ItemRegistry.apple_lapis, "apple_lapis");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_lapis,new ItemStack(Items.dye, 1, Reference.dye_lapis) );

		}
		  
		if(ModMain.cfg.appleChocolate)
		{
			ItemRegistry.apple_chocolate = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false); 
			ItemRegistry.apple_chocolate.addEffect(Potion.weakness.id, timePotionShort, PotionRegistry.I);
			ItemRegistry.apple_chocolate.addEffect(Potion.digSpeed.id, timePotionShort, PotionRegistry.I); 
			ItemRegistry.registerItem(ItemRegistry.apple_chocolate, "apple_chocolate");
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_chocolate, new ItemStack(Items.dye, 1, Reference.dye_cocoa) );
	
		}
	 
		if(ModMain.cfg.appleNetherStar) 
		{ 
			ItemRegistry.apple_netherwart = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);  
		 
			ItemRegistry.apple_netherwart.addEffect( PotionRegistry.lavawalk.id, timePotionLong,  PotionRegistry.I);  
			 
			ItemRegistry.registerItem(ItemRegistry.apple_netherwart, "apple_nether_star");
		 
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_netherwart,new ItemStack(Items.nether_wart));
		}
	 
		if(ModMain.cfg.apple_ender) 
		{ 
			int timeInSeconds = 60 * 60; //TODO:Config?one hour
			
			ItemRegistry.apple_ender = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, false);   
			ItemRegistry.apple_ender.addEffect(PotionRegistry.ender.id, timeInSeconds, PotionRegistry.I);  
			 
			ItemRegistry.registerItem(ItemRegistry.apple_ender, "apple_ender");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_ender,new ItemStack(Items.ender_pearl)) ;
		}  
	}
	
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(item, name);
		 
		 items.add(item);
	}
}
