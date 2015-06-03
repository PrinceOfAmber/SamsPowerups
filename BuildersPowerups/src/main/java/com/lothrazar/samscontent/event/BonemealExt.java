package com.lothrazar.samscontent.event;

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BonemealExt 
{
	public static void useBonemeal(World world, EntityPlayer entityPlayer, BlockPos pos, Block blockClicked)
	{
		boolean success = false; 
		 
  		//we could do a 1/3 odds chance or something, but tall flowers work everytime so following that
  		
  		if(ModMain.cfg.bonemealAllFlowers )
  		{ 
		 	if ( blockClicked.equals(Blocks.yellow_flower))//yellow flowers have no damage variations
		 	{   
		 		Util.dropItemStackInWorld(world, pos,new ItemStack(Blocks.yellow_flower));
		
			  	success = true;
		 	}
		 	else if ( blockClicked.equals(Blocks.red_flower)) 	//the red flower is ALL the flowers
		 	{    
				Util.dropItemStackInWorld(world, pos, new ItemStack(Blocks.red_flower ,1,Blocks.red_flower.getMetaFromState(world.getBlockState(pos))));
		
			  	success = true; 
		 	} 
  		}
	 	
	 	if(ModMain.cfg.bonemealLilypads && blockClicked.equals(Blocks.waterlily))
	 	{ 
	 		Util.dropItemStackInWorld(world, pos,new ItemStack(Blocks.waterlily));
	 	 
		  	success = true;
	 	} 
	 	if(ModMain.cfg.bonemealReeds && blockClicked.equals(Blocks.reeds))
	 	{
	 		//do not drop items, just grow them upwards
	 	 
	 		int goUp = 0;
	 		
	 		if(world.isAirBlock(pos.up(1))) goUp = 1;
	 		else if(world.isAirBlock(pos.up(2))) goUp = 2;

			if(goUp > 0)
			{
				world.setBlockState(pos.up(goUp), Blocks.reeds.getDefaultState());

			  	success = true; 
			} 
	 	}
	 	
	 	if(success)
	 	{ 
	 		//the game also uses VILLAGER_HAPPY for their bonemeal events so i copy
	 		Util.spawnParticle(world, EnumParticleTypes.VILLAGER_HAPPY, pos);
	 		 
			Util.decrHeldStackSize(entityPlayer); 
	 	} 
	}
}