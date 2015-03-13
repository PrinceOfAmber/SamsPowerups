package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerAutoPlantExpire
{ 
	
	//for each biome, have list of saplings allowed
	
	@SubscribeEvent
	public void onSaplingGrowTreeEvent(SaplingGrowTreeEvent event)
	{ 
		//IDEA: we could put biome/sapling pairs in config file and use 		BiomeGenBase.getBiome(int)
		//AND OR: put field for saplings/biomes added by other mods
		
		BiomeGenBase biome = event.world.getBiomeGenForCoords(event.pos);
		 
		Block b = event.world.getBlockState(event.pos).getBlock();
		
		boolean treeAllowedToGrow = false;
		
		if(b == Blocks.sapling)//this may not always be true: such as trees added by Mods, so not a vanilla tree, but throwing same event
		{
			int meta = Blocks.sapling.getMetaFromState(event.world.getBlockState(event.pos));

			int biomeID = event.world.getBiomeGenForCoords(event.pos).biomeID;
			
			int growth_data = 8;//0-5 is the type, then it adds on a 0x8  
			//and we know that it is always maxed out at ready to grow 8 since it is turning into a tree.
			
			int tree_type = meta - growth_data;
			
			System.out.println(biome.biomeName + " tree_type   "+tree_type  );
			
			switch(tree_type)
			{
			case Reference.sapling_acacia:
				
				treeAllowedToGrow = (biome == BiomeGenBase.mesa)  || 
					(biome == BiomeGenBase.savanna) ||
					(biome == BiomeGenBase.savannaPlateau) || 
					(biome == BiomeGenBase.mesaPlateau) || 
					(biome == BiomeGenBase.mesaPlateau_F);
		
				break;
			case Reference.sapling_spruce:

				treeAllowedToGrow = (biome == BiomeGenBase.taiga) ||
					(biome == BiomeGenBase.taigaHills) ||
					(biome == BiomeGenBase.coldTaiga) ||
					(biome == BiomeGenBase.coldTaigaHills) ||
					(biome == BiomeGenBase.jungle) ||
					(biome == BiomeGenBase.icePlains) ||
					(biome == BiomeGenBase.iceMountains);
				break; 
			case Reference.sapling_oak:

				treeAllowedToGrow = (biome == BiomeGenBase.forest) ||
					(biome == BiomeGenBase.forestHills) ||
					(biome == BiomeGenBase.jungle) ||
					(biome == BiomeGenBase.jungleEdge) ||
					(biome == BiomeGenBase.jungleHills) ||
					(biome == BiomeGenBase.swampland);
				break;
			case Reference.sapling_birch:

				treeAllowedToGrow = (biome == BiomeGenBase.birchForest)  || 
					(biome == BiomeGenBase.birchForestHills) || 
					(biome == BiomeGenBase.forest) ||
					(biome == BiomeGenBase.forestHills);
				
				break;
			case Reference.sapling_darkoak:

				treeAllowedToGrow = (biome == BiomeGenBase.roofedForest);
				break;
			case Reference.sapling_jungle:

				treeAllowedToGrow = (biome == BiomeGenBase.jungle) ||
					(biome == BiomeGenBase.jungleEdge) ||
					(biome == BiomeGenBase.jungleHills);
				break;
				
				//no saplings for:
				//frozen river
				//ice plains spikes
				//cold beach
				//
			}
			 
			if(treeAllowedToGrow == false)
			{
				event.setResult(Result.DENY);
				System.out.println("treeAllowedToGrow DENY");
				
				//overwrite the sapling. - we could set to Air first, but dont see much reason to
				event.world.setBlockState(event.pos, Blocks.deadbush.getDefaultState());
			} 
			
		}//else a tree grew that was added by some mod
	}
	
	@SubscribeEvent
	public void onItemExpireEvent(ItemExpireEvent event)
	{  
		 if(ModLoader.configSettings.plantDespawningSaplings == false) {return;}
		 
		 ItemStack is = event.entityItem.getEntityItem();
		 if(is != null )
		 { 
			 Block blockhere = event.entity.worldObj.getBlockState(event.entityItem.getPosition()).getBlock(); 
			 Block blockdown = event.entity.worldObj.getBlockState(event.entityItem.getPosition().down()).getBlock();
			   
			if(blockhere == Blocks.air && 
				blockdown == Blocks.dirt || //includes podzol and such
				blockdown == Blocks.grass 
				)
			{
				//plant the sapling, replacing the air and on top of dirt/plantable
				
				if(Block.getBlockFromItem(is.getItem()) == Blocks.sapling)
					event.entity.worldObj.setBlockState(event.entityItem.getPosition(), Blocks.sapling.getStateFromMeta(is.getItemDamage()));
				else if(Block.getBlockFromItem(is.getItem()) == Blocks.red_mushroom)	
					event.entity.worldObj.setBlockState(event.entityItem.getPosition(), Blocks.red_mushroom.getDefaultState());
				else if(Block.getBlockFromItem(is.getItem()) == Blocks.brown_mushroom)	
					event.entity.worldObj.setBlockState(event.entityItem.getPosition(), Blocks.brown_mushroom.getDefaultState());
				
					
						
			
			}
		 }
	} 
}
