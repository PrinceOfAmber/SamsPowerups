package com.lothrazar.samscontent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList; 
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;   
import com.lothrazar.samscontent.block.*;
import com.lothrazar.samscontent.cfg.ConfigRegistry;
import com.lothrazar.samscontent.command.*;
import com.lothrazar.samscontent.common.PlayerPowerups;
import com.lothrazar.samscontent.entity.projectile.*; 
import com.lothrazar.samscontent.event.*;
import com.lothrazar.samscontent.item.*;
import com.lothrazar.samscontent.potion.*; 
import com.lothrazar.samscontent.proxy.*; 
import com.lothrazar.samscontent.spell.*;
import com.lothrazar.samscontent.stats.*;
import com.lothrazar.samscontent.tileentity.TileEntityBucketStorage;
import com.lothrazar.samscontent.world.*; 
import com.lothrazar.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*; 
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent; 
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
  
@Mod(modid = Reference.MODID, version = Reference.VERSION,	name = Reference.NAME, useMetadata = true )  
public class ModMain
{
	@Instance(value = Reference.MODID)
	public static ModMain instance;
	@SidedProxy(clientSide="com.lothrazar.samscontent.proxy.ClientProxy", serverSide="com.lothrazar.samscontent.proxy.CommonProxy")
	public static CommonProxy proxy;   
	public static Logger logger; 
	public static ConfigRegistry cfg;
	public static SimpleNetworkWrapper network; 
	public static AchievementRegistry achievements;  
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsContent") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return ItemRegistry.apple_chocolate;
		}
	};    
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		cfg = new ConfigRegistry(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    	network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
 		
		PotionRegistry.registerPotionEffects();

		BlockRegistry.registerBlocks();
		
		ItemRegistry.registerItems();
		
		ArmorRegistry.registerItems();
		
		achievements = new AchievementRegistry();
		 
		this.registerEventHandlers(); 
		
		BlockHardnessRegistry.registerChanges(); 
		
		SpellRegistry.setup();
		
	}
        
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		achievements.registerAll();
		
		CreativeInventoryRegistry.registerTabImprovements();
	
		MobSpawningRegistry.registerSpawns();
  
		ChestLootGenerator.regsiterLoot();
		  
		RecipeRegistry.registerRecipes();
		 
		StackSizeRegistry.registerChanges(); 
 
  		if(ModMain.cfg.moreFuel) 
  		{
  			GameRegistry.registerFuelHandler(new FurnaceFuelRegistry()); 
  		}
  		
  		if(ModMain.cfg.worldGenOceansNotUgly)
		{ 
  			int weight = 0;
			GameRegistry.registerWorldGenerator(new WorldGeneratorOcean(), weight);
		}
  		 
  		//TODO: we could make our own custom projectileRegistry, that acts as our other ones above do.
  		
  		//TODO: Entity ids are the 999,1000,1001 -> config file
        EntityRegistry.registerModEntity(EntitySoulstoneBolt.class, "soulstonebolt",999, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityLightningballBolt.class, "lightningbolt",1000, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityHarvestbolt.class, "harvestbolt",1001, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityWaterBolt.class, "waterbolt",1002, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntitySnowballBolt.class, "frostbolt",1003, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityTorchBolt.class, "torchbolt",1004, ModMain.instance, 64, 1, true);
		
        
		proxy.registerRenderers();
	}
	
	@EventHandler 
	public void onPostInit(FMLPostInitializationEvent event)
	{ 		
	
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		if(ModMain.cfg.cmd_searchtrade) 
			event.registerServerCommand(new CommandSearchTrades()); 
		
		if(ModMain.cfg.cmd_searchitem) 
			event.registerServerCommand(new CommandSearchItem()); 
		
		if(ModMain.cfg.cmd_searchspawner) 
			event.registerServerCommand(new CommandSearchSpawner()); 
		 
		if(ModMain.cfg.cmd_simplewaypoint) 
			event.registerServerCommand(new CommandSimpleWaypoints()); 
		
		if(ModMain.cfg.cmd_enderchest) 
			event.registerServerCommand(new CommandEnderChest()); 
		
		if(ModMain.cfg.cmd_todo) 
			event.registerServerCommand(new CommandTodoList());  
		 
		if(ModMain.cfg.cmd_kit)  
			event.registerServerCommand(new CommandKit()); 
  
		if(ModMain.cfg.cmd_home) 
			event.registerServerCommand(new CommandWorldHome()); 
		
		if(ModMain.cfg.worldhome) 
			event.registerServerCommand(new CommandHome());

		if(ModMain.cfg.cmd_place_blocks) 
			event.registerServerCommand(new CommandPlaceBlocks());
	 
		if(ModMain.cfg.cmd_recipe) 
			event.registerServerCommand(new CommandRecipe());

		if(ModMain.cfg.cmd_uses) 
			event.registerServerCommand(new CommandUses());

		if(ModMain.cfg.cmd_effectpay) 
			event.registerServerCommand(new CommandEffectPay());

		if(ModMain.cfg.cmd_ping) 
			event.registerServerCommand(new CommandPing());
		
		//these ones are always here. no reason to disable.
	
		event.registerServerCommand(new CommandBindMacro());


	}
  
	private void registerEventHandlers() 
	{ 
    	ArrayList<Object> handlers = new ArrayList<Object>();
  
      	handlers.add(new SaplingDespawnGrowth());//this is only one needs terrain gen buff, plus one of the regular ones
      	handlers.add(new DebugScreenText()          );  //This one can stay  
     	handlers.add(instance                         ); 
     	handlers.add(achievements);  
		handlers.add(BlockRegistry.block_storelava    );//TODO: why are these four done so weirdly
		handlers.add(BlockRegistry.block_storewater   );
		handlers.add(BlockRegistry.block_storemilk    ); 
		handlers.add(BlockRegistry.block_storeempty   );   

     	for(Object h : handlers)
     		if(h != null)
	     	{ 
	    		FMLCommonHandler.instance().bus().register(h); 
	    		MinecraftForge.EVENT_BUS.register(h); 
	    		MinecraftForge.TERRAIN_GEN_BUS.register(h);
	    		MinecraftForge.ORE_GEN_BUS.register(h); 
	     	} 
	}
	
	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{ 
		//PotionRegistry.onEnderTeleportEvent(event); 
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) 
	{  
		PotionRegistry.onEntityUpdate(event);
		
		//TODO: make class/event handler for fragile torches
		if(ModMain.cfg.fragileTorches && 
				event.entityLiving.worldObj.getBlockState(event.entityLiving.getPosition()).getBlock() == Blocks.torch) 
		{ 
			float oddsWillBreak = 0.01F;//TODO: in config or something? or make this 1/100
			boolean playerCancelled = false;
			if(event.entityLiving instanceof EntityPlayer)
			{
				EntityPlayer p = (EntityPlayer)event.entityLiving;
				if(p.isSneaking())
				{
					playerCancelled = true;//torches are safe from breaking
				}
			}
			
			if(playerCancelled == false //if its a player, then the player is not sneaking
					&& event.entityLiving.worldObj.rand.nextDouble() < oddsWillBreak
					&& event.entityLiving.worldObj.isRemote == false)
			{ 
				event.entityLiving.worldObj.destroyBlock(event.entityLiving.getPosition(), true);  
			}
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) 
	{ 
		SpellSoulstone.onLivingHurt(event);
	}
	
	@SubscribeEvent
	public void onLivingDropsEvent(LivingDropsEvent event)
	{
		BlockPos pos = event.entity.getPosition();
		World world = event.entity.worldObj;

		if( event.entity instanceof EntityZombie) //how to get this all into its own class
		{  
			EntityZombie z = (EntityZombie)event.entity;
			
			if(ModMain.cfg.removeZombieCarrotPotato)
				for(int i = 0; i < event.drops.size(); i++) 
				{
					EntityItem item = event.drops.get(i);
					
					if(item.getEntityItem().getItem() == Items.carrot || item.getEntityItem().getItem() == Items.potato)
					{ 
						event.drops.remove(i);
					}
				}
			
			
			if(z.isChild() && ModMain.cfg.chanceZombieChildFeather > 0 && 
					event.entity.worldObj.rand.nextInt(100) <= ModMain.cfg.chanceZombieChildFeather)
			{ 
				event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ()
						,new ItemStack(Items.feather)));
			}
			 
			if(z.isVillager() && ModMain.cfg.chanceZombieVillagerEmerald > 0
					&& event.entity.worldObj.rand.nextInt(100) <=  ModMain.cfg.chanceZombieVillagerEmerald)
			{
				event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ() 
						,new ItemStack(Items.emerald)));
			} 
		} 
		 
		if(ModMain.cfg.petNametagDrops //no need to restrict to pets && SamsUtilities.isPet(event.entity)
		  && event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
		   event.entity.getCustomNameTag() != ""   
		   ) 
		{  
			//item stack NBT needs the name enchanted onto it
			ItemStack nameTag = Util.buildEnchantedNametag(event.entity.getCustomNameTag());
		  
			Util.dropItemStackInWorld(world, event.entity.getPosition(), nameTag);  
		}
		
		
		if(ModMain.cfg.petNametagChat && 
			event.entity instanceof EntityLivingBase && 
			event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
		    event.entity.getCustomNameTag() != ""   &&
		    event.entity.worldObj.isRemote == false) 
		{    
           
			//show message as if player, works since EntityLiving extends EntityLivingBase
	 
			Util.printChatMessage((event.source.getDeathMessage((EntityLivingBase)event.entity)));
		}
		 
		
		if(ModMain.cfg.cowExtraLeather > 0 && event.entity instanceof EntityCow)
		{
			event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(), new ItemStack(Items.leather,ModMain.cfg.cowExtraLeather)));
		} 
	}
	
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		
		if(held != null && held.getItem() == ItemRegistry.respawn_egg_empty )
		{
			ItemRespawnEggEmpty.entitySpawnEgg(event.entityPlayer, event.target); 
		}
		  
		if(ModMain.cfg.canNameVillagers &&  //how to get this all into its own class
		  held != null && held.getItem() == Items.name_tag && 
		  held.hasDisplayName()  )
		{    
			if(event.target instanceof EntityVillager)
			{
				
				EntityVillager v = (EntityVillager)event.target;
				  
				v.setCustomNameTag(held.getDisplayName()); 
				
				Util.decrHeldStackSize(event.entityPlayer); 
				
				event.setCanceled(true);//stop the GUI inventory opening 
			} 
		} 
  
		if(held != null && held.getItem() instanceof ItemHorseFood)
		{     
			if(event.target instanceof EntityHorse)
			{ 
				ItemHorseFood.onHorseInteract((EntityHorse)event.target,event.entityPlayer,held);  
				
				event.setCanceled(true);//stop the GUI inventory opening
			}  
		}  
		/*
		if(held != null && held.getItem() instanceof ItemSoulstone)
		{      
			ItemSoulstone.onEntityInteract(event);   
		}  
		*/
  	} 
	 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		Block blockClicked = event.world.getBlockState(event.pos).getBlock(); 
		TileEntity container = event.world.getTileEntity(event.pos);

		if(held != null && held.getItem() == Items.experience_bottle  && 
				event.action.RIGHT_CLICK_BLOCK == event.action && 
				event.entityPlayer.capabilities.isCreativeMode == false && 
				ModMain.cfg.experience_bottle_return)
		{ 
			Util.dropItemStackInWorld(event.world, event.pos, Items.glass_bottle);
		}
		
		if(held != null && held.getItem() == ItemRegistry.carbon_paper &&   
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			ItemPaperCarbon.rightClickBlock(event); 
		}
	
		if(held != null && held.getItem() == ItemRegistry.itemChestSack &&  //how to get this all into its own class
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			/*
			if(blockClicked instanceof BlockChest && container instanceof TileEntityChest)// && event.entityPlayer.isSneaking()
			{    
				 
				ItemChestSackEmpty.convertChestToSack(event.entityPlayer,held,(TileEntityChest)container,event.pos);  
			 
				//TODO: other containers could go here: dispenser, trapped chest. 
				//heck maybe thers another way such as with IInventory?
				
			} 
			else
			{
				*/
				if(event.face != null && event.world.isAirBlock(event.pos.offset(event.face)) == false)
					ItemChestSack.createAndFillChest(event.entityPlayer, held, event.pos.offset(event.face));
			//}
		}
	
		if (held != null && 
			held.getItem() != null && 
			ItemRegistry.itemEnderBook != null &&
			held.getItem() == ItemRegistry.itemEnderBook && 
			event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			ItemEnderBook.rightClickBlock(event.world,event.entityPlayer, held);
			
		}
		/*
		if(event.action == event.action.LEFT_CLICK_BLOCK && 
			event.entityPlayer.isPotionActive(PotionRegistry.ender) && 
			event.entityPlayer.getCurrentEquippedItem() != null && 
			event.entityPlayer.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.ender_chest))
		{
			event.entityPlayer.displayGUIChest(event.entityPlayer.getInventoryEnderChest()); 
		} */
		
		if(ModMain.cfg.swiftDeposit  &&  //how to get this all into its own class
				event.action == event.action.LEFT_CLICK_BLOCK && 
				event.entityPlayer.isSneaking()  && 
				event.entityPlayer.getCurrentEquippedItem() == null)
		{ 
	  	  	TileEntity te =	event.entity.worldObj.getTileEntity(event.pos);
	  
	  	  	if(te != null && (te instanceof TileEntityChest))
	  	  	{ 
				TileEntityChest chest = (TileEntityChest)te ; 
				 
		  		ChestDeposit.sortFromPlayerToChestEntity(event.world,chest,event.entityPlayer);

		  	  	//check for double chest 
		  	    TileEntityChest teAdjacent = Util.getChestAdj(chest);
		  		if(teAdjacent != null)
		  		{
		  			ChestDeposit.sortFromPlayerToChestEntity(event.world,teAdjacent,event.entityPlayer);
		  		}
	  	  	}
		}
		
		if(  event.action == event.action.RIGHT_CLICK_BLOCK && 
  				Util.isBonemeal(held)  && 
  				blockClicked != null ) 
		{    
			BonemealExt.useBonemeal(event.world, event.entityPlayer, event.pos, blockClicked);
		}
		
		if(ModMain.cfg.flintPumpkin &&  //how to get this all into its own class
				held != null && held.getItem() == Items.flint_and_steel && 
				event.action.RIGHT_CLICK_BLOCK == event.action )
		{   
			if(blockClicked == Blocks.pumpkin)
			{
				event.world.setBlockState(event.pos, Blocks.lit_pumpkin.getDefaultState());
				 
				Util.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos);
				Util.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos.offset(event.entityPlayer.getHorizontalFacing()));
			
				Util.playSoundAt(event.entityPlayer, "fire.ignite"); 
			}
			else if(blockClicked == Blocks.lit_pumpkin)//then un-light it
			{
				event.world.setBlockState(event.pos, Blocks.pumpkin.getDefaultState());
				 
				Util.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos);
				Util.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos.offset(event.entityPlayer.getHorizontalFacing()));
				
				Util.playSoundAt(event.entityPlayer, "random.fizz"); 
			}
		}		
		
		if(ModMain.cfg.skullSignNames &&  //how to get this all into its own class
				event.action == event.action.LEFT_CLICK_BLOCK && 
				event.entityPlayer.isSneaking() && 
				held != null && held.getItem() == Items.skull && 
				held.getItemDamage() == Reference.skull_player	&& 
				container != null &&
				container instanceof TileEntitySign)
		{
			TileEntitySign sign = (TileEntitySign)container; 
			String firstLine = sign.signText[0].getUnformattedText();
			
			if(firstLine == null) { firstLine = ""; }
			if(firstLine.isEmpty() || firstLine.split(" ").length == 0)
			{
				held.setTagCompound(null); 
			}
			else
			{ 
				firstLine = firstLine.split(" ")[0];
				
				if(held.getTagCompound() == null) held.setTagCompound(new NBTTagCompound());
				
				held.getTagCompound().setString("SkullOwner",firstLine);
			} 
		} //end of skullSignNames
   	}
	
	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event)
	{  
		//this fires BEFORE the block turns into farmland (is cancellable) so check for grass and dirt, not farmland
		
		Block clicked = event.world.getBlockState(event.pos).getBlock();
		
		if( (clicked == Blocks.grass || clicked == Blocks.dirt ) 
			&& event.world.isAirBlock(event.pos.up()) 
			&& ItemRegistry.beetroot_seed != null
			&& event.world.rand.nextInt(16) == 0) //it is a 1/15 chance
		{			
			if(event.world.isRemote == false)
			{
				Util.dropItemStackInWorld(event.world, event.pos, ItemRegistry.beetroot_seed);
			}

			event.entityPlayer.addStat(achievements.beetrootSeed, 1);
		}
	}

	@SubscribeEvent
	public void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event)
	{
		
	}

	@SubscribeEvent
	public void onPlayerWakeUpEvent(PlayerWakeUpEvent event)
	{
		if(event.entityPlayer.worldObj.isRemote == false)
		{
			boolean didSleepAllNight = !event.updateWorld;
			
			if(didSleepAllNight && ModMain.cfg.sleeping_hunger_seconds > 0)
			{ 
				int levelBoost = 0;//1 means Hunger II. int = 2 means Hunger III, etc.
				
				event.entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id,  ModMain.cfg.sleeping_hunger_seconds * Reference.TICKS_PER_SEC, levelBoost));
			}
		}
	}
	/*
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{     
		EntityPlayer player = event.player;
		
		//this one only applies to players
		PotionRegistry.tickEnder(player); 
	} */
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
		//TODO: key handler class? maybe a better way to do this than copying the same code??
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	     
        	 ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }        
        else if(ClientProxy.keyShiftDown.isPressed() )
        { 	      
        	 ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }      
        else if(ClientProxy.keyBarDown.isPressed() )
        { 	      
        	 ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keyBarDown.getKeyCode()));  
        }  
        else if(ClientProxy.keyBarUp.isPressed() )
        { 	      
        	 ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keyBarUp.getKeyCode()));  
        }   
        else if(ClientProxy.keyBindMacro1.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keyBindMacro1.getKeyCode()));
        }
        else if(ClientProxy.keyBindMacro2.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keyBindMacro2.getKeyCode()));
        }
        else if(ClientProxy.keyPush.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keyPush.getKeyCode()));
        }
        else if(ClientProxy.keyPull.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keyPull.getKeyCode()));
        }
        else if(ClientProxy.keyTransform.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keyTransform.getKeyCode()));
        }
        else if(ClientProxy.keySpellToggle.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellToggle.getKeyCode()));
        }
        else if(ClientProxy.keySpellCast.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellCast.getKeyCode()));
        }
        else if(ClientProxy.keySpellUp.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellUp.getKeyCode()));
        }
        else if(ClientProxy.keySpellDown.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellDown.getKeyCode()));
        }
    } 
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if( ModMain.cfg.endermenDropCarryingBlock
			&& event.entity instanceof EntityEnderman)
		{ 
			EntityEnderman mob = (EntityEnderman)event.entity;
 
			IBlockState bs = mob.func_175489_ck();//mcp/forge just did not translate this
			
			if(bs != null && bs.getBlock() != null && event.entity.worldObj.isRemote == false)
			{
				Util.dropItemStackInWorld(event.entity.worldObj, mob.getPosition(), bs.getBlock());
			} 
		}
		
		if(event.entity instanceof EntityPlayer)
		{ 
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(ModMain.cfg.dropPlayerSkullOnDeath)
			{  
				ItemStack skull = Util.buildNamedPlayerSkull(player);
				 
				Util.dropItemStackInWorld(event.entity.worldObj, player.getPosition(), skull);
			}
			
			if(ModMain.cfg.playerDeathCoordinates)
			{
				String coordsStr = Util.posToString(player.getPosition()); 
				Util.printChatMessage(player.getDisplayNameString() + " has died at " + coordsStr);
			}
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) 
	{ 
		PlayerPowerups.get(event.entityPlayer).copy(PlayerPowerups.get(event.original));
	}
	@SubscribeEvent
 	public void onEntityConstructing(EntityConstructing event)
 	{ 
 		if (event.entity instanceof EntityPlayer && PlayerPowerups.get((EntityPlayer) event.entity) == null)
 		{ 
 			PlayerPowerups.register((EntityPlayer) event.entity);
 		} 
 	}
	
	@SubscribeEvent
	public void onHarvestDropsEvent(HarvestDropsEvent event)
	{
		
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
			
			Util.setItemStackNBT(stack, "buckets", t.getBuckets());
		
			Util.dropItemStackInWorld(event.world, event.pos, stack);

			t.setBuckets(0);
		}
	}
	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent event)
	{
		//every time the player joins the world
		//Util.addChatMessage(event.player, "login.new.first");

	}


	@SubscribeEvent
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
	{ 
		if(event.entity instanceof EntityLivingBase && event.world.isRemote)
		{
			EntityLivingBase living = (EntityLivingBase)event.entity;
			if(living instanceof EntityPlayer)// && ((EntityPlayer)living).
			{
				EntityPlayer player = ((EntityPlayer)living);
				PlayerPowerups props = PlayerPowerups.get(player);
				//max health
				//Hearts to Health ratio is 2:1
				//so config file might say 3 which is 6 health points (6 half-hearts)
				
				int max = props.getHealthMaxCustom();

		//	System.out.println("EntityPlayer spawn event max="+max);
			//	System.out.println("healthPlayerStart="+ModMain.cfg.heartsPlayerStart);
				
				//start at whatever config file says is min health.  do nothing if already upgraded
				if(max < ModMain.cfg.heartsPlayerStart)
				{
					props.setHealthMaxCustom(ModMain.cfg.heartsPlayerStart*2);
				}
				
			} 
			if(living instanceof EntityWolf && ((EntityWolf)living).isTamed())
			{
				Util.setMaxHealth(living,ModMain.cfg.heartsWolfTamed*2);
			}
			if(living instanceof EntityOcelot && ((EntityOcelot)living).isTamed())
			{
				Util.setMaxHealth(living,ModMain.cfg.heartsCatTamed*2);
			}
			
			if(living instanceof EntityVillager && ((EntityVillager)living).isChild() == false)
			{
				//??living.getMaxHealth()
				Util.setMaxHealth(living,ModMain.cfg.heartsVillager*2);			
			}
		}
	}
	
}
