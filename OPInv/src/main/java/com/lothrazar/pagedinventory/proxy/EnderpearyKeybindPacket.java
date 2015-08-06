package com.lothrazar.pagedinventory.proxy;

import com.lothrazar.pagedinventory.*;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class EnderpearyKeybindPacket implements IMessage , IMessageHandler<EnderpearyKeybindPacket, IMessage>
{
	public EnderpearyKeybindPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public EnderpearyKeybindPacket(NBTTagCompound ptags)
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
	public IMessage onMessage(EnderpearyKeybindPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		 
 		ItemStack pearls = p.inventory.getStackInSlot(Const.BONUS_START+Const.type_epearl);
 
 		if(pearls != null)
 		{
 	 		p.worldObj.spawnEntityInWorld(new EntityEnderPearl(p.worldObj, p));
 	 		
 	 		p.worldObj.playSoundAtEntity(p, "random.bow", 1.0F, 1.0F);   // ref http://minecraft.gamepedia.com/Sounds.json
 	 		
 	 		if(p.capabilities.isCreativeMode == false)
 	 			p.inventory.decrStackSize(Const.BONUS_START+Const.type_epearl, 1);
 		}
 	
		return null;
	}
}