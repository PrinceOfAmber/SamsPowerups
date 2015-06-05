package com.lothrazar.samsblocks;
 
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem; 
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModBlocks.MODID, version = ModBlocks.VERSION,	name = ModBlocks.NAME, useMetadata = true )  
public class ModBlocks
{
	public static final String MODID = "samsblocks";
	public static final String TEXTURE_LOCATION = ModBlocks.MODID + ":";
	public static final String VERSION = "1.8-1.0.0";
	public static final String NAME = "Builder's Blocks";
	@Instance(value = ModBlocks.MODID)
	public static ModBlocks instance;
	public static Logger logger; 
	public static ConfigRegistry cfg;
	@SidedProxy(clientSide="com.lothrazar.samsblocks.ClientProxy", serverSide="com.lothrazar.samsblocks.CommonProxy")
	public static CommonProxy proxy;  
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsBlocks") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return Item.getItemFromBlock(BlockRegistry.block_fishing);
		}
	};    
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		cfg = new ConfigRegistry(new Configuration(event.getSuggestedConfigurationFile()));

		BlockRegistry.registerBlocks();
		
		
		
		
	  	ArrayList<Object> handlers = new ArrayList<Object>();
	    
     	handlers.add(instance                         ); 
     	handlers.add(BlockRegistry.block_storelava    );//TODO: why are these four done so weirdly
		handlers.add(BlockRegistry.block_storewater   );
		handlers.add(BlockRegistry.block_storemilk    ); 
		handlers.add(BlockRegistry.block_storeempty   );   

     	for(Object h : handlers)
     		if(h != null)
	     	{ 
	    		FMLCommonHandler.instance().bus().register(h); 
	    		MinecraftForge.EVENT_BUS.register(h); 
	    		//MinecraftForge.TERRAIN_GEN_BUS.register(h);
	    		//MinecraftForge.ORE_GEN_BUS.register(h); 
	     	} 
	}
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    	

		proxy.registerRenderers();
    }
	@SubscribeEvent
	public void onBreakEvent(BreakEvent event)
	{
		TileEntity ent = event.world.getTileEntity(event.pos);
		  
		//TODO; check tool/pickaxe? if notHarvestable or whatever, drop the buckets and the ..glass?
		 
		if(ent != null && ent instanceof TileEntityBucketStorage)
		{
			TileEntityBucketStorage t = (TileEntityBucketStorage)ent;
			ItemStack stack = new ItemStack(event.state.getBlock());
			
			ModBlocks.setItemStackNBT(stack, "buckets", t.getBuckets());
		
			ModBlocks.dropItemStackInWorld(event.world, event.pos, stack);

			t.setBuckets(0);
		}
	}
	public static void setItemStackNotNull(ItemStack item)
	{
		if(item.getTagCompound() == null) {item.setTagCompound(new NBTTagCompound());}
	}
	
	public static void setItemStackNBT(ItemStack item,	String prop, int value) 
	{
		setItemStackNotNull(item); 
		item.getTagCompound().setInteger(prop, value);
	} 
	public static void playSoundAt(Entity player, String sound)
	{ 
		player.worldObj.playSoundAtEntity(player, sound, 1.0F, 1.0F);
	}
	public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos)
	{
		if(pos != null)
			spawnParticle(world,type,pos.getX(),pos.getY(),pos.getZ());
    }
	public static void spawnParticle(World world, EnumParticleTypes type, double x, double y, double z)
	{ 
		//http://www.minecraftforge.net/forum/index.php?topic=9744.0
		for(int countparticles = 0; countparticles <= 10; ++countparticles)
		{
			world.spawnParticle(type, x + (world.rand.nextDouble() - 0.5D) * (double)0.8, y + world.rand.nextDouble() * (double)1.5 - (double)0.1, z + (world.rand.nextDouble() - 0.5D) * (double)0.8, 0.0D, 0.0D, 0.0D);
		} 
    }
	
	public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, ItemStack stack)
	{
		EntityItem entityItem = new EntityItem(worldObj, pos.getX(),pos.getY(),pos.getZ(), stack); 

 		if(worldObj.isRemote==false)//do not spawn a second 'ghost' one on client side
 			worldObj.spawnEntityInWorld(entityItem);
    	return entityItem;
	}
}