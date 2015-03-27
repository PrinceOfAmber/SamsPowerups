package com.lothrazar.samscontent.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.block.BlockCommandBlockCraftable.CommandType;
import com.lothrazar.util.Reference; 

public class BlockRegistry 
{ 
	public static BlockCommandBlockCraftable command_block_regen;
	public static BlockCommandBlockCraftable command_block_mobgrief;
	public static BlockCommandBlockCraftable command_block_firetick;
	public static BlockCommandBlockCraftable command_block_daycycle;
	public static BlockCommandBlockCraftable command_block_weather ;
	public static BlockCommandBlockCraftable command_block_tpspawn;
	public static BlockCommandBlockCraftable command_block_tpbed; 
	public static BlockFishing block_fishing ;
	public static BlockXRay block_xray ;
	public static BlockShearWool block_spike;
	public static BlockBucketStorage block_storelava;
	public static BlockBucketStorage block_storewater;
	public static BlockBucketStorage block_storemilk;
	public static BlockBucketStorage block_storeempty;
	public static BlockCropBeetroot beetrootCrop;
	
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	
	 public static void registerBlock(Block s, String name)
	 {    
		 s.setUnlocalizedName(name); 
		 
		 GameRegistry.registerBlock(s, name);
		  
		 blocks.add(s);
	 }
	
	
	public static void registerBlocks() 
	{  
		if(ModLoader.configSettings.beetroot)
		{
			beetrootCrop = (BlockCropBeetroot) new BlockCropBeetroot();

			BlockRegistry.registerBlock(beetrootCrop, "beetroot_crop"); 
		}
		
		
		 
		if(ModLoader.configSettings.storeWaterBlock)
		{
			BlockRegistry.block_storewater = new BlockBucketStorage(Items.water_bucket); 
			
			registerBlock(BlockRegistry.block_storewater, "block_storewater");

		}
		if(ModLoader.configSettings.storeMilkBlock)
		{
			BlockRegistry.block_storemilk = new BlockBucketStorage(Items.milk_bucket); 
			
			BlockRegistry.registerBlock(BlockRegistry.block_storemilk, "block_storemilk");

		}
		if(ModLoader.configSettings.storeLavaBlock)
		{
			BlockRegistry.block_storelava = new BlockBucketStorage(Items.lava_bucket); 
			
			BlockRegistry.registerBlock(BlockRegistry.block_storelava, "block_storelava");
	  
		}

		if(ModLoader.configSettings.storeLavaBlock || ModLoader.configSettings.storeWaterBlock || ModLoader.configSettings.storeMilkBlock)
		{
			GameRegistry.registerTileEntity(com.lothrazar.samscontent.block.TileEntityBucketStorage.class, Reference.MODID);
		
			BlockRegistry.block_storeempty = new BlockBucketStorage(null); 

			BlockRegistry.registerBlock(BlockRegistry.block_storeempty, "block_storeempty");
			

			BlockRegistry.block_storeempty.addRecipe();
			
		}
		if(ModLoader.configSettings.shearSheepBlock)
		{
			BlockRegistry.block_spike = new BlockShearWool(); 
			
			BlockRegistry.registerBlock(BlockRegistry.block_spike, "block_spike");

			BlockShearWool.addRecipe();
		}
		
		if(ModLoader.configSettings.fishingNetBlock)
		{
			BlockRegistry.block_fishing = new BlockFishing(); 
			
			registerBlock(BlockRegistry.block_fishing,BlockFishing.name);

			BlockFishing.addRecipe();
		}
  
		if(ModLoader.configSettings.weatherBlock) 
		{
			BlockRegistry.command_block_weather = new BlockCommandBlockCraftable(CommandType.Weather);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_weather,"command_block_weather");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_weather,new ItemStack(Items.water_bucket));
		}
		 
		if(ModLoader.configSettings.teleportSpawnBlock) 
		{ 
			BlockRegistry.command_block_tpspawn = new BlockCommandBlockCraftable(CommandType.TeleportSpawn);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_tpspawn,"command_block_tpspawn");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpspawn,new ItemStack(Items.ender_eye));
		}

		if(ModLoader.configSettings.teleportBedBlock) 
		{ 
			BlockRegistry.command_block_tpbed = new BlockCommandBlockCraftable(CommandType.TeleportBed);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_tpbed,"command_block_tpbed");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpbed,new ItemStack(Items.ender_pearl));
		}
		
		if(ModLoader.configSettings.gameruleBlockRegen)
		{ 
			BlockRegistry.command_block_regen = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.naturalRegeneration);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_regen, "command_block_regen");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_regen,new ItemStack(Items.golden_apple) );  
		} 
		
		if(ModLoader.configSettings.gameruleBlockDaylight)
		{
			BlockRegistry.command_block_daycycle = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.doDaylightCycle);
			 
			BlockRegistry.registerBlock(BlockRegistry.command_block_daycycle, "command_block_daycycle");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_daycycle,new ItemStack( Blocks.glowstone) ); 
		}
		
		if(ModLoader.configSettings.gameruleBlockFiretick)
		{ 
			BlockRegistry.command_block_firetick = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.doFireTick);
 
			BlockRegistry.registerBlock(BlockRegistry.command_block_firetick, "command_block_firetick");

			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_firetick,new ItemStack( Items.lava_bucket) ); 
		}
		
		if(ModLoader.configSettings.gameruleBlockMobgrief)
		{ 
			BlockRegistry.command_block_mobgrief = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.mobGriefing);
 
			BlockRegistry.registerBlock(BlockRegistry.command_block_mobgrief, "command_block_mobgrief");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_mobgrief,new ItemStack( Blocks.tnt) );  
		}
 
		if(ModLoader.configSettings.xRayBlock)
		{ 
			BlockRegistry.block_xray = new BlockXRay(); 
			
			BlockRegistry.registerBlock(BlockRegistry.block_xray,"block_xray");
 
			BlockXRay.addRecipe();
		}
	}
}
