package com.lothrazar.samscontent.item;

import com.google.common.collect.Sets;  
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.*;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper; 
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemChestSackEmpty extends Item
{
	public ItemChestSackEmpty( )
	{   
		super(); 
		this.setMaxStackSize(64);
		this.setCreativeTab(ModSamsContent.tabSamsContent);
	}
	  
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandChest),
			Items.leather,
			Items.string,
			Blocks.chest  );
		
		if(ModSamsContent.configSettings.uncraftGeneral)
			GameRegistry.addSmelting(ItemRegistry.wandChest, new ItemStack(Items.leather), 0);
	}

	public void convertChestToSack(EntityPlayer entityPlayer, ItemStack heldWand, TileEntityChest chestTarget, BlockPos pos)
	{ 
		ItemStack chestItem;  
		int chestMax;
		 
		int ROWS = 3;
		int COLS = 9;
		int START_CHEST = 0;
		int START_INV = 9;//because we are ignoring the item hotbar, we skip the first row this way
		int END_CHEST =  START_CHEST + ROWS * COLS;
		int END_INV = START_INV + ROWS * COLS;

		ItemStack drop = new ItemStack(ItemRegistry.itemChestSack ,1,0); 
		
		if(drop.getTagCompound() == null)  drop.setTagCompound(new NBTTagCompound());
 
		int stacks = 0;
		int count = 0;
		
		int[] itemids = new int[END_CHEST - START_CHEST];
		int[] itemqty = new int[END_CHEST - START_CHEST];		
		int[] itemdmg = new int[END_CHEST - START_CHEST];
		
		//inventory and chest has 9 rows by 3 columns, never changes. same as 64 max stack size
		for(int islotChest = START_CHEST; islotChest < END_CHEST; islotChest++)
		{
			//zeroes to avoid nulls, and signify nothing goes there
			itemids[islotChest] = 0;
			itemqty[islotChest] = 0;
			itemids[islotChest] = 0;
			chestItem = chestTarget.getStackInSlot(islotChest);
		
			if(chestItem == null){continue;}//not an error; empty chest slot
			if(chestItem.getTagCompound() != null)
			{
				//probably has an enchantment
				entityPlayer.dropPlayerItemWithRandomChoice(chestItem, false); 
			}
			else
			{
				stacks++; 
				count += chestItem.stackSize;
				
				itemids[islotChest] = Item.getIdFromItem(chestItem.getItem());
				itemdmg[islotChest] = chestItem.getItemDamage(); 
				itemqty[islotChest] = chestItem.stackSize;
				
			}
			//its either in the bag, or dropped on the player
			chestTarget.setInventorySlotContents(islotChest, null);	
		}
		 
		if(drop.getTagCompound() == null) drop.setTagCompound(new NBTTagCompound());
		
		drop.getTagCompound().setIntArray("itemids", itemids);
		drop.getTagCompound().setIntArray("itemdmg", itemdmg);
		drop.getTagCompound().setIntArray("itemqty", itemqty);
		 
		drop.getTagCompound().setString("count",""+count);
		drop.getTagCompound().setString("stacks",""+stacks);
	 	 
		entityPlayer.entityDropItem(drop, 1);//quantity = 1
			 
		 //the 2 here is just a magic flag it passes to the world to propogate the event
	
		entityPlayer.worldObj.setBlockToAir(pos);//, Blocks.air, 0,2);	 

		entityPlayer.swingItem();
		 //TODO: paticle and sound
		entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
	}
 
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return; }//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		  
		
		if(held.getItem() == ItemRegistry.wandChest && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
			
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			if(blockClicked instanceof BlockChest)// && event.entityPlayer.isSneaking()
			{   
				TileEntity container = event.world.getTileEntity(event.pos);
				
				if(container instanceof TileEntityChest)
				{
					ItemRegistry.wandChest.convertChestToSack(event.entityPlayer,held,(TileEntityChest)container,event.pos);  
				}
			} 
		}
  	}
}