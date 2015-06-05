package com.lothrazar.samscarbonpaper;
  
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ModCarbon.MODID, version = ModCarbon.VERSION,	name = ModCarbon.NAME, useMetadata = true )
public class ModCarbon
{
    public static final String MODID = "samscarbonpaper";
    public static final String NAME = "Sam's Carbon Paper";
    public static final String VERSION = "1.8-1.0.0";
    @Instance(value = ModCarbon.MODID)
	public static ModCarbon instance;
    @SidedProxy(clientSide="com.lothrazar.samscarbonpaper.ClientProxy", serverSide="com.lothrazar.samscarbonpaper.CommonProxy")
	public static CommonProxy proxy;   
	
	public static ItemPaperCarbon carbon_paper;  

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		carbon_paper = new ItemPaperCarbon();

		registerItem(carbon_paper, "carbon_paper");

		ItemPaperCarbon.addRecipe(); 
	}
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
   		FMLCommonHandler.instance().bus().register(instance); 
		MinecraftForge.EVENT_BUS.register(instance); 
		

		   
		proxy.registerRenderers();
    }
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		//Block blockClicked = event.world.getBlockState(event.pos).getBlock(); 
//		TileEntity container = event.world.getTileEntity(event.pos);
 
		if(held != null && held.getItem() == carbon_paper &&   
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			ItemPaperCarbon.rightClickBlock(event); 
		}
  	}
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(item, name);
		 
		 //items.add(item);
	}
	
}
