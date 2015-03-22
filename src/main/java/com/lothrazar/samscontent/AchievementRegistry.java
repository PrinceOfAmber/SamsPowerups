package com.lothrazar.samscontent;

import com.lothrazar.samscontent.item.ItemRegistry;
import com.lothrazar.util.Reference;

import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class AchievementRegistry 
{
	private AchievementPage page;
	
	public Achievement test;
	 
	
	public void registerAll()
	{
		//reference http://www.minecraftforge.net/wiki/How_to_add_an_Achievement
		
		//TODO: lang file
		test = new Achievement(Reference.MODID + "_appleChoc", "appleChoc", 1, -2, (Item)ItemRegistry.apple_chocolate, null);
		test.registerStat(); //optional//.setSpecial()		 
		
		page = new AchievementPage("Sam's Content",test);//, ach1, ach2, ach3, ach4
  
	 	AchievementPage.registerAchievementPage(page);
	}
	
	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event)
	{
		if(event.crafting == null){return;}

		if(event.crafting.getItem() == ItemRegistry.apple_chocolate)
		{ 
			event.player.addStat(test, 1);
		}
		//else if ... others
	}
}
