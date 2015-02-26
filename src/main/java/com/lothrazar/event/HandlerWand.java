package com.lothrazar.event;

import org.apache.logging.log4j.Logger;  

import com.lothrazar.item.*;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities; 

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;   
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class HandlerWand  
{   
	@SuppressWarnings("unused")
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return ;}//server side only!
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		 
		//if it drains hunnger, we cant use it when food is empty
		//if(ItemWandHarvest.drainsHunger)
		//	if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;} 
	
		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		 
		if(event.action.LEFT_CLICK_BLOCK == event.action) {return;}
		 
		//only right clicks
			
		if(held.getItem() == ItemWandChest.itemChestSack)
		{
			TileEntity container = event.entityPlayer.worldObj.getTileEntity(event.pos); 
			
			if(container == null){return;}
			 
			if((container instanceof TileEntityChest) == false){return;}
			
			TileEntityChest chest = (TileEntityChest)container ;

			if(chest == null){return;}
			 
			TileEntityChest teAdjacent = null; 
	  	  	if(chest.adjacentChestXNeg != null)
	  	  	{
	  	  		teAdjacent = chest.adjacentChestXNeg; 
	  	  	}
	  		if(chest.adjacentChestXPos != null)
	  	  	{
	  	  		teAdjacent = chest.adjacentChestXPos; 
	  	  	}
	  		if(chest.adjacentChestZNeg != null)
	  	  	{
	  	  		teAdjacent = chest.adjacentChestZNeg ; 
	  	  	}
	  		if(chest.adjacentChestZPos != null)
	  	  	{
	  	  		teAdjacent = chest.adjacentChestZPos; 
	  	  	}
	  		 
	  		ItemWandChest.itemChestSack.sortFromSackToChestEntity(chest,held,event);
	  		
	  		if(teAdjacent != null)
	  		{
	  			ItemWandChest.itemChestSack.sortFromSackToChestEntity(teAdjacent,held,event);
	  		} 	
		} 
		else if(held.getItem() == ItemWandDungeon.itemWand)
		{ 
			if(event.action == event.action.RIGHT_CLICK_BLOCK){return;}
			ItemWandDungeon.itemWand.searchSpawner(event.entityPlayer,held,event.pos); 
		}
		else if(held.getItem() == ItemWandChest.itemWand)
		{ 
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			if(blockClicked instanceof BlockChest)// && event.entityPlayer.isSneaking()
			{   
				TileEntity container = event.world.getTileEntity(event.pos);
				if(container instanceof TileEntityChest)
				{
					ItemWandChest.itemWand.convertChestToSack(event.entityPlayer,held,(TileEntityChest)container,event.pos);  
				}
			} 
		}
		else if(held.getItem() == ItemWandHarvest.itemWand && 
		    (blockClicked == Blocks.wheat || blockClicked == Blocks.carrots || blockClicked == Blocks.potatoes))
		{ 
			 ItemWandHarvest.itemWand.replantField(event.entityPlayer,held,event.pos); 
		}
		else if(held.getItem() == ItemWandProspect.itemWand)
		{ 
			ItemWandProspect.itemWand.searchProspect(event.entityPlayer,held,event.pos);   
		}
		else if(held.getItem() == ItemWandChest.itemChestSack)
		{ 	
			if(  held.getTagCompound() == null){return;}
		    
			// : is y+1 actually air?
			if(event.entityPlayer.worldObj.isAirBlock(event.pos.add(0,1,0)) == false
					|| event.entityPlayer.worldObj.getActualHeight() < event.pos.getY() + 1)//do not place above world height
			{
				//can only be placed on valid air location
				return;
			}
			
			ItemWandChest.itemChestSack.createAndFillChest(event.entityPlayer,held,  event.pos.add(0,1,0));
		}  
  	}
  
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		if(held == null || held.getItem() != ItemWandLivestock.itemWand ){ return;}
		if(event.entityPlayer.worldObj.isRemote ){ return;}
   
		//if it drains hunnger, we cant use it when food is empty
		if(ItemWandHarvest.drainsHunger)
			if(event.entityPlayer.getFoodStats().getFoodLevel() <= 0){return;} 
		 
		ItemWandLivestock.itemWand.entitySpawnEgg(event.entityPlayer, event.target); 
  	} 
	 
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event)
	{
		if (event.entityPlayer == null || event.itemStack == null || event.itemStack.getItem() == null) { return; }

		if (event.getResult() == Result.DENY) { return; }
/*
		Item item = event.itemStack.getItem();
		if(item == ItemWandHarvest.itemWand)
			if(SamsUtilities.isShiftKeyDown())  //thanks to http://www.minecraftforge.net/forum/index.php?topic=24991.0 
			{  
				event.toolTip.add("Turn chest into a sack for transport");
				event.toolTip.add("Attack crops to harvest the whole field");
				event.toolTip.add("Livestock Transport");
				event.toolTip.add("Attack Smoothstone");
				event.toolTip.add("Attack Diamond Block"); 
			}
			else
			{
				event.toolTip.add("[Hold Shift for details]");
			}
		  */
	 
	}
	
	
	@SubscribeEvent
	public void onItemToss(ItemTossEvent event)
	{
		//TODO: make dungeon wand/searching stuff consumables?
	}
}