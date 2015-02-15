package com.lothrazar.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class SamsUtilities
{

	public static void incrementPlayerIntegerNBT(EntityPlayer player, String prop)
	{
		int prev = getPlayerIntegerNBT(player,prop);
		player.getEntityData().setInteger(prop, prev + 1);
	}
	
	public static int getPlayerIntegerNBT(EntityPlayer player, String prop)
	{ 
		return player.getEntityData().getInteger(prop);
	}

	public static void teleportWallSafe(EntityPlayer player, World world, BlockPos coords)
	{
		player.setPositionAndUpdate(coords.getX(), coords.getY(), coords.getZ()); 

		while (!world.getCollidingBoundingBoxes(player, player.getEntityBoundingBox()).isEmpty())
		{
			player.setPositionAndUpdate(player.posX, player.posY + 1.0D, player.posZ);
		}
		 
		
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
	}
	
}