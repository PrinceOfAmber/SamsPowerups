package com.lothrazar.samsprojectiles;
 
import com.lothrazar.samsprojectiles.entity.projectile.*;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer; 
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = ModProj.MODID,  useMetadata = true )
public class ModProj
{
    public static final String MODID = "samsprojectiles"; 
	public static final String TEXTURE_LOCATION = MODID + ":"; 
	@SidedProxy(clientSide="com.lothrazar.samsprojectiles.ClientProxy", serverSide="com.lothrazar.samsprojectiles.CommonProxy")
	public static CommonProxy proxy;   
	@Instance(value = ModProj.MODID)
	public static ModProj instance;
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsProj") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return ItemRegistry.ender_harvest;
		}
	};    
	
	public static int torch_recipe;
	public static int lightning_recipe;
	public static int snow_recipe;
	public static int water_recipe;
	public static int harvest_recipe;
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		config.addCustomCategoryComment(MODID, "For each item, you can decide how many the recipe produces.  Set to zero to disable the crafting recipe.");
		
		torch_recipe = config.getInt("torch_crafted", MODID, 6, 0, 64, "");
		lightning_recipe = config.getInt("lightning_crafted", MODID, 1, 0, 64, "");
		snow_recipe = config.getInt("snow_crafted", MODID, 4, 0, 64, "");
		water_recipe = config.getInt("water_crafted", MODID, 2, 0, 64, "");
		harvest_recipe = config.getInt("harvest_crafted", MODID, 4, 0, 64, "");
		
		
		if(config.hasChanged()){config.save();}
		
		
		//TODO: 1. config file to disable each item
		//TODO: 2. fix soulstone???
		//TODO 3. config each recipe output
		//TODO 
		
		ItemRegistry.registerItems();
		
		FMLCommonHandler.instance().bus().register(instance); 
		MinecraftForge.EVENT_BUS.register(instance);
		
		
	}
	public static void teleportWallSafe(EntityLivingBase player, World world, BlockPos coords)
	{
		player.setPositionAndUpdate(coords.getX(), coords.getY(), coords.getZ()); 

		moveEntityWallSafe(player, world);
	}
	
	public static void moveEntityWallSafe(EntityLivingBase entity, World world) 
	{
		while (!world.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox()).isEmpty())
		{
			entity.setPositionAndUpdate(entity.posX, entity.posY + 1.0D, entity.posZ);
		}
	}
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) 
	{ 
		EntitySoulstoneBolt.onLivingHurt(event);
	}
 
    @EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		 
  		//TODO: we could make our own custom projectileRegistry, that acts as our other ones above do.
  		
  		//TODO: Entity ids are the 999,1000,1001 -> config file
        EntityRegistry.registerModEntity(EntitySoulstoneBolt.class, "soulstonebolt",999, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityLightningballBolt.class, "lightningbolt",1000, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityHarvestBolt.class, "harvestbolt",1001, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityWaterBolt.class, "waterbolt",1002, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntitySnowballBolt.class, "frostbolt",1003, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityTorchBolt.class, "torchbolt",1004, instance, 64, 1, true);
		
		proxy.registerRenderers();
	}
    
    @SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		if(event.pos == null){return;}
		World world = event.world;
		EntityPlayer player = event.entityPlayer;
		ItemStack held = player.getCurrentEquippedItem();
		//Block blockClicked = event.world.getBlockState(event.pos).getBlock(); 
		//TileEntity container = event.world.getTileEntity(event.pos);
		
		if(held != null && event.action.RIGHT_CLICK_AIR == event.action )
		{
			boolean wasThrown = false;
			if(held.getItem() == ItemRegistry.ender_torch)
			{ 
				world.spawnEntityInWorld(new EntityTorchBolt(world,player));
				wasThrown = true;
			}
			/*
			else if(held.getItem() == ItemRegistry.soulstone)
			{
				world.spawnEntityInWorld(new EntitySoulstoneBolt(world,player));
				wasThrown = true;
			}*/
			else if(held.getItem() == ItemRegistry.ender_snow)
			{
				world.spawnEntityInWorld(new EntitySnowballBolt(world,player));
				wasThrown = true;
			}
			else if(held.getItem() == ItemRegistry.ender_water)
			{
				world.spawnEntityInWorld(new EntityWaterBolt(world,player));
				wasThrown = true;
			}
			else if(held.getItem() == ItemRegistry.ender_harvest)
			{
				world.spawnEntityInWorld(new EntityHarvestBolt(world,player));
				wasThrown = true;
			}
			else if(held.getItem() == ItemRegistry.ender_lightning)
			{
				world.spawnEntityInWorld(new EntityLightningballBolt(world,player));
				wasThrown = true;
			}
			
			if(wasThrown)
			{
				player.swingItem();
				world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F);
				if(player.capabilities.isCreativeMode == false)
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
			}
		}
  	}	
    
	public static void addChatMessage(EntityPlayer player,String string) 
	{ 
		player.addChatMessage(new ChatComponentTranslation(string));
	}
	
	public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos)
	{
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
	
	public static String lang(String name)
	{
		return StatCollector.translateToLocal(name);
	}
}