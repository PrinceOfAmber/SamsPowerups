package com.lothrazar.pagedinventory.proxy;

import java.util.ArrayList;

import com.lothrazar.pagedinventory.*;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class DumpButtonPacket implements IMessage , IMessageHandler<DumpButtonPacket, IMessage>
{
	public DumpButtonPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public DumpButtonPacket(NBTTagCompound ptags)
	{
		tags = ptags;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		tags = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, this.tags);
	}

	@Override
	public IMessage onMessage(DumpButtonPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;

		ArrayList<BlockPos> locations = UtilInventory.findBlocks(p, Blocks.chest, ModConfig.filterRange);
		locations.addAll(UtilInventory.findBlocks(p, Blocks.trapped_chest, ModConfig.filterRange));

		for(BlockPos pos : locations)
		{
			if(p.worldObj.getTileEntity(pos) instanceof TileEntityChest)
			{
				//merge first then dump
				//UtilInventory.sortFromPlayerToChestEntity(p.worldObj, (TileEntityChest)p.worldObj.getTileEntity(pos), p);
				UtilInventory.dumpFromPlayerToChestEntity(p.worldObj, (TileEntityChest)p.worldObj.getTileEntity(pos), p);
			}
		}
		
		return null; 
	}
}