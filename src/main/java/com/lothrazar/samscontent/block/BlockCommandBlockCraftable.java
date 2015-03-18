package com.lothrazar.samscontent.block;

import java.util.Random; 

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraftforge.fml.common.registry.GameRegistry; 
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BlockCommandBlockCraftable extends BlockCommandBlock
{  
	public static enum CommandType
	{
		Gamerule, Weather, TeleportSpawn, TeleportBed 
	}
	
	private CommandType type;
	private String rule = null;
	
	@Override
	public boolean isOpaqueCube() 
	{
		return true;//transparency stuff
	}
	
	private void setConstructorDefaults()
	{ 
		this.setHardness(3F);
		this.setResistance(5F);
		this.setCreativeTab(ModLoader.tabSamsContent);
	}
	
	public BlockCommandBlockCraftable(CommandType t)
	{   
		type = t;
		this.rule = null;
		setConstructorDefaults(); 
	}
	
	public BlockCommandBlockCraftable(CommandType t, String rl)
	{     
		type = t;
		this.rule = rl;
		setConstructorDefaults();
	}
 
	@Override
	public void updateTick(World w, BlockPos pos, IBlockState state, Random r)
    {   
        TileEntity tileentity = w.getTileEntity(pos); 
        if (tileentity == null ) {return;}
        if(!(tileentity instanceof TileEntityCommandBlock)) {return;}
     
        String command = null;   //set the command of the block as a string, just as a player would type it

        switch(type)
        { 
	        case TeleportSpawn: 
	        	
	    		command = "worldhome";

	        break; 
	        case TeleportBed:

	    		command = "home";
	        	 
        	break;
	        case Weather:
	        	
	        	command = "toggledownfall";
	        	
	        break;
	        case Gamerule:
	        	
	        	String lastVal = w.getGameRules().getGameRuleStringValue(rule); 
	
				lastVal = (lastVal.equals("false")) ? "true" : "false"; //toggle it based on previous value 
	 
	            command = "gamerule "+ rule +" "+lastVal;
	            
	        break;
        }
         
        //?: does the excecute respect op powers? can non op use toggledownfall from this
        String pre = "execute @p ~ ~ ~ ";//pre = "/"
        
        if(command != null)
        {
	        CommandBlockLogic commandblocklogic = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
	         
	        commandblocklogic.setCommand(pre + command);  
	        
	        commandblocklogic.trigger(w);
        }
    }
	  
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    { 
		 //disables the player from opening the edit screen to alter the command
		return false;
    }
	
	@Override 
	public int quantityDropped(Random p_149745_1_)
    {
		//change from 0 to 1 so it is harvestable
        return 1;
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {    
       return Item.getItemFromBlock(Blocks.diamond_block);//force them to use silk touch to get it back
    }
	
	@Override
	public boolean canSilkHarvest(World world,BlockPos pos, IBlockState state, EntityPlayer player)
    {
		this.canSilkHarvest(world, pos, state, player);
		return true;
    }
	 
	public static void initDaylight()
	{ 
		if(ModLoader.configSettings.gameruleBlockDaylight)
		{
			BlockRegistry.command_block_daycycle = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.doDaylightCycle);
			 
			SamsRegistry.registerBlock(BlockRegistry.command_block_daycycle, "command_block_daycycle");
			
			addRecipe(BlockRegistry.command_block_daycycle,new ItemStack( Blocks.glowstone) ); 
		}
	}

	public static void initFiretick()
	{
		if(ModLoader.configSettings.gameruleBlockFiretick)
		{ 
			BlockRegistry.command_block_firetick = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.doFireTick);
 
			SamsRegistry.registerBlock(BlockRegistry.command_block_firetick, "command_block_firetick");

			addRecipe(BlockRegistry.command_block_firetick,new ItemStack( Items.lava_bucket) ); 
		}
	}

	public static void initMobgrief()
	{
		if(ModLoader.configSettings.gameruleBlockMobgrief)
		{ 
			BlockRegistry.command_block_mobgrief = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.mobGriefing);
 
			SamsRegistry.registerBlock(BlockRegistry.command_block_mobgrief, "command_block_mobgrief");
			addRecipe(BlockRegistry.command_block_mobgrief,new ItemStack( Blocks.tnt) );  
		}
	}

	public static void initRegen()
	{
		if(ModLoader.configSettings.gameruleBlockRegen)
		{ 
			BlockRegistry.command_block_regen = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.naturalRegeneration);
	 
			SamsRegistry.registerBlock(BlockRegistry.command_block_regen, "command_block_regen");
			addRecipe(BlockRegistry.command_block_regen,new ItemStack(Items.golden_apple) );  
	
		}
	}
		
	public static void initWeatherBlock()
	{ 
		if(ModLoader.configSettings.weatherBlock) 
		{
			BlockRegistry.command_block_weather = new BlockCommandBlockCraftable(CommandType.Weather);
	 
			SamsRegistry.registerBlock(BlockRegistry.command_block_weather,"command_block_weather");
	
			addRecipe(BlockRegistry.command_block_weather,new ItemStack(Items.water_bucket));
		}
	}

	public static void addRecipe(BlockCommandBlockCraftable block, ItemStack flavor) 
	{
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.command_block_weather), 
				"rcr", 
				"cec",
				"rcr", 
				'c', Items.comparator, 
				'e', Items.water_bucket, 
				'r', Blocks.redstone_block
				);
			
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(BlockRegistry.command_block_weather, new ItemStack(Blocks.redstone_block, 5), 0);
	} 
	
	public static void initTeleportBlock()
	{ 
		if(ModLoader.configSettings.teleportSpawnBlock) 
		{ 
			BlockRegistry.command_block_tpspawn = new BlockCommandBlockCraftable(CommandType.TeleportSpawn);
	 
			SamsRegistry.registerBlock(BlockRegistry.command_block_tpspawn,"command_block_tpspawn");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpspawn,new ItemStack(Items.ender_eye));
			
		}
	}
	 
	public static void initTeleportBedBlock()
	{ 
		if(ModLoader.configSettings.teleportBedBlock) 
		{
			 
			BlockRegistry.command_block_tpbed = new BlockCommandBlockCraftable(CommandType.TeleportBed);
	 
			SamsRegistry.registerBlock(BlockRegistry.command_block_tpbed,"command_block_tpbed");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpbed,new ItemStack(Items.ender_pearl));

		}
	}
	 
}
