package com.lothrazar.samsrecipes;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ModRecipes.MODID, version = ModRecipes.VERSION,	name = ModRecipes.NAME, useMetadata = true)
public class ModRecipes
{
    public static final String MODID = "samsrecipes";
	public static final String TEXTURE_LOCATION = MODID + ":";
	public static final String VERSION = "1.8-1.0.0";
	public static final String NAME = "Sam's Recipes";
	public static Logger logger; 
	public static ConfigRecipes cfg;
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		cfg = new ConfigRecipes(new Configuration(event.getSuggestedConfigurationFile()));
	  
	}
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	RecipeRegistry.registerRecipes();
    	
    	if(cfg.moreFuel) 
  		{
  			GameRegistry.registerFuelHandler(new FurnaceFuelRegistry()); 
  		}
    	
    }
}
