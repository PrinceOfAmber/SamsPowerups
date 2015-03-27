package com.lothrazar.samscontent.event;

import java.util.ArrayList;
import java.util.List;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

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

public class SaplingDespawnGrowth
{  
	public List<Integer> oakBiomes = new ArrayList<Integer>();
	public List<Integer> spruceBiomes = new ArrayList<Integer>();
	public List<Integer> birchBiomes = new ArrayList<Integer>();
	public List<Integer> jungleBiomes = new ArrayList<Integer>();
	public List<Integer> darkoakBiomes = new ArrayList<Integer>();
	public List<Integer> acaciaBiomes = new ArrayList<Integer>();
	
	public SaplingDespawnGrowth()
	{
			
		oakBiomes.add(BiomeGenBase.forest.biomeID);
		oakBiomes.add(BiomeGenBase.forestHills.biomeID);
		oakBiomes.add(132);  //Flower Forest
		oakBiomes.add(BiomeGenBase.mesaPlateau.biomeID); //no trees in regular mesa, only plateaus 
		oakBiomes.add(BiomeGenBase.getBiome(166).biomeID) ;//mesa plateau F M 
		oakBiomes.add(BiomeGenBase.getBiome(167).biomeID) ;//mesa plateau M 
		oakBiomes.add(BiomeGenBase.jungle.biomeID);
		oakBiomes.add(BiomeGenBase.jungleEdge.biomeID) ;
		oakBiomes.add(BiomeGenBase.getBiome(151).biomeID) ;//jungle edge M
		oakBiomes.add(BiomeGenBase.getBiome(149).biomeID);//jungle M
		oakBiomes.add(BiomeGenBase.jungleHills.biomeID) ;
		oakBiomes.add(BiomeGenBase.swampland.biomeID) ;
		oakBiomes.add(BiomeGenBase.getBiome(134).biomeID) ;//Swampland M
		oakBiomes.add(BiomeGenBase.extremeHills.biomeID) ; 
		oakBiomes.add(BiomeGenBase.extremeHillsEdge.biomeID) ;
		oakBiomes.add(BiomeGenBase.extremeHillsPlus.biomeID) ;
		oakBiomes.add(BiomeGenBase.icePlains.biomeID); 
		
		acaciaBiomes.add(BiomeGenBase.savanna.biomeID);
		acaciaBiomes.add(BiomeGenBase.savannaPlateau.biomeID);
		acaciaBiomes.add(BiomeGenBase.mesaPlateau_F.biomeID);
		acaciaBiomes.add(BiomeGenBase.getBiome(163).biomeID); // Savanna M
		acaciaBiomes.add(BiomeGenBase.getBiome(164).biomeID);  // Savanna Plateau M
	  
		spruceBiomes.add(BiomeGenBase.taiga.biomeID);
		spruceBiomes.add(BiomeGenBase.taigaHills.biomeID);
		spruceBiomes.add(BiomeGenBase.megaTaiga.biomeID);
		spruceBiomes.add(BiomeGenBase.getBiome(160).biomeID);//megasprucetaiga 
		spruceBiomes.add(BiomeGenBase.getBiome(161).biomeID);//megasprucetaiga M);
		spruceBiomes.add(BiomeGenBase.megaTaigaHills.biomeID);
		spruceBiomes.add(BiomeGenBase.coldTaiga.biomeID);
		spruceBiomes.add(BiomeGenBase.coldTaigaHills.biomeID);
		spruceBiomes.add(BiomeGenBase.getBiome(158).biomeID);//Cold Tagia M
		spruceBiomes.add(BiomeGenBase.extremeHills.biomeID);
		spruceBiomes.add(BiomeGenBase.extremeHillsEdge.biomeID);
		spruceBiomes.add(BiomeGenBase.extremeHillsPlus.biomeID);
		spruceBiomes.add(BiomeGenBase.jungle.biomeID);
		spruceBiomes.add(BiomeGenBase.icePlains.biomeID);
		spruceBiomes.add(BiomeGenBase.iceMountains.biomeID);

		birchBiomes.add(BiomeGenBase.birchForest.biomeID);
		birchBiomes.add(BiomeGenBase.birchForestHills.biomeID);
		birchBiomes.add(BiomeGenBase.getBiome(155).biomeID); //Birch forest M
		birchBiomes.add(BiomeGenBase.getBiome(156).biomeID);//Birch forest hills M
		birchBiomes.add(BiomeGenBase.forest.biomeID);
		birchBiomes.add(BiomeGenBase.forestHills.biomeID);
		birchBiomes.add(BiomeGenBase.getBiome(132).biomeID);//Flower Forest;
 
		darkoakBiomes.add(BiomeGenBase.roofedForest.biomeID);
		darkoakBiomes.add(BiomeGenBase.getBiome(157).biomeID); // Roofed Forest M 
		
		jungleBiomes.add(BiomeGenBase.jungle.biomeID);
		jungleBiomes.add(BiomeGenBase.jungleEdge.biomeID);
		jungleBiomes.add(BiomeGenBase.jungleHills.biomeID);
		jungleBiomes.add(BiomeGenBase.getBiome(149).biomeID);//jungle edge M
		jungleBiomes.add(BiomeGenBase.getBiome(151).biomeID);//jungle M

		//no saplings for:
		//frozen river 
		//ice plains spikes 
		//cold beach
		//stone beach
		//the end
		//plains
		//sunflower plains
		//river
		//beach
		//mooshroom island (14)
		//mooshroom island shore (15)
		//desert (2)
		//desert M (130)
		//mesa (37)
		// mesa bryce (165)
		// plateaus (36 38 39)
		//nether
		//sky (the end)
		//ocean
		//deepocean
		//frozen ocean(10N  
		 
		if(ModLoader.configSettings.saplingAllNether)
		{
			acaciaBiomes.add(BiomeGenBase.hell.biomeID);
			oakBiomes.add(BiomeGenBase.hell.biomeID);
			birchBiomes.add(BiomeGenBase.hell.biomeID);
			spruceBiomes.add(BiomeGenBase.hell.biomeID);
			darkoakBiomes.add(BiomeGenBase.hell.biomeID);
			jungleBiomes.add(BiomeGenBase.hell.biomeID);
		}
		
		if(ModLoader.configSettings.saplingAllEnd)
		{ 
			acaciaBiomes.add(BiomeGenBase.sky.biomeID);
			oakBiomes.add(BiomeGenBase.sky.biomeID);
			birchBiomes.add(BiomeGenBase.sky.biomeID);
			spruceBiomes.add(BiomeGenBase.sky.biomeID);
			darkoakBiomes.add(BiomeGenBase.sky.biomeID);
			jungleBiomes.add(BiomeGenBase.sky.biomeID);
		}  
	}
	
	@SubscribeEvent
	public void onSaplingGrowTreeEvent(SaplingGrowTreeEvent event)
	{  
		if(ModLoader.configSettings.saplingGrowthRestricted == false) {return;}
		
		Block b = event.world.getBlockState(event.pos).getBlock();
		
		boolean treeAllowedToGrow = false;
		
		if(b == Blocks.sapling)//this may not always be true: such as trees added by Mods, so not a vanilla tree, but throwing same event
		{
			int meta = Blocks.sapling.getMetaFromState(event.world.getBlockState(event.pos));
			
			int biomeID = event.world.getBiomeGenForCoords(event.pos).biomeID;

			int growth_data = 8;//0-5 is the type, then it adds on a 0x8  
			//and we know that it is always maxed out at ready to grow 8 since it is turning into a tree.
			
			int tree_type = meta - growth_data;
			 
			//IDS: http://www.minecraftforum.net/forums/minecraft-discussion/recent-updates-and-snapshots/381405-full-list-of-biome-ids-as-of-13w36b
			//as of 12 march 2015, it seems biome id 168 does not exist, so 167 is highest used (vanilla minecraft)
			switch(tree_type)
			{
			case Reference.sapling_acacia: 
				treeAllowedToGrow = acaciaBiomes.contains(biomeID); 
				break;
			case Reference.sapling_spruce: 
				treeAllowedToGrow = spruceBiomes.contains(biomeID); 
				break; 
			case Reference.sapling_oak: 
				treeAllowedToGrow = oakBiomes.contains(biomeID);  
				break;
			case Reference.sapling_birch: 
				treeAllowedToGrow = birchBiomes.contains(biomeID); 
				break;
			case Reference.sapling_darkoak: 
				treeAllowedToGrow = darkoakBiomes.contains(biomeID);
				break;
			case Reference.sapling_jungle: 
				treeAllowedToGrow = jungleBiomes.contains(biomeID);
				break;
				
			}
			
			if(treeAllowedToGrow == false)
			{
				event.setResult(Result.DENY);
				 
				//overwrite the sapling. - we could set to Air first, but dont see much reason to
				event.world.setBlockState(event.pos, Blocks.deadbush.getDefaultState());
				
				SamsUtilities.dropItemStackInWorld(event.world, event.pos, new ItemStack(Blocks.sapling,1,tree_type));
			}  
		}//else a tree grew that was added by some mod
	}
	
	@SubscribeEvent
	public void onItemExpireEvent(ItemExpireEvent event)
	{  
		 if(ModLoader.configSettings.plantDespawningSaplings == false) {return;}
		 
		 ItemStack is = event.entityItem.getEntityItem();
		 if(is == null ) {return;}//has not happened in the wild, yet
		 
		 
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