package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class CommandPlaceLine implements ICommand
{
	public static boolean REQUIRES_OP=false;  
	public static int XP_COST_PER_PLACE = 1; 
	private ArrayList<String> aliases = new ArrayList<String>();
	
	public CommandPlaceLine()
	{
		this.aliases.add(getName().toUpperCase());
	}
	
	@Override
	public int compareTo(Object arg0) 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return "placeline";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		return "/"+getName() + "<qty> <skip=1>";
	}

	@Override
	public List getAliases() 
	{
		return aliases;
	}
	
	@Override
	public void execute(ICommandSender sender, String[] args)	throws CommandException 
	{
		EntityPlayer player = (EntityPlayer)sender;
		
		if(player == null){return;}//was sent by command block or something, ignore it
		
		
		if(player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem().stackSize == 0)
		{
			Util.addChatMessage(player, "command.place.empty"); 
			return;
		}
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());

		if(pblock == null){return;}

		if(PlaceCmdLib.allowed.size() > 0 && PlaceCmdLib.allowed.contains(pblock) == false)
		{ 
			Util.addChatMessage(player, "command.place.notallowed"); 
			return;
		}
		
		World world = player.worldObj;
	
        boolean isLookingUp = (player.getLookVec().yCoord >= 0);//TODO: use this somehow? to place up/down? 
        
		IBlockState placing = pblock.getStateFromMeta(player.inventory.getCurrentItem().getMetadata());

		int want = player.inventory.getCurrentItem().stackSize;
        if(args.length > 0 && args[0] != null)
        {
        	want =  Math.min(Integer.parseInt(args[0]), player.inventory.getCurrentItem().stackSize);
        }
		 
        int skip = 1;
        if(args.length > 1 && args[1] != null)
        {
        	skip =  Math.max(Integer.parseInt(args[1]), 1);
        }
		 
		BlockPos off;
		EnumFacing efacing = (player.isSneaking()) ? EnumFacing.DOWN : Util.getPlayerFacing(player);
		
		int numPlaced = 0;
		for(int i = 1; i < want + 1; i = i + skip)
		{
			off = player.getPosition().offset(efacing, i);
			
			if(world.isAirBlock(off) == false){break;}
			//halted, do not continue the path
			
			world.setBlockState(off, placing);
			Util.decrHeldStackSize(player);
 
			Util.playSoundAt(player, pblock.stepSound.getPlaceSound());
			
			numPlaced ++;
		}
		
        Util.tryDrainXp(player,numPlaced * XP_COST_PER_PLACE);
	}
	
	@Override
	public boolean canCommandSenderUse(ICommandSender ic) 
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,			BlockPos pos) 
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
		return false;
	}
}
