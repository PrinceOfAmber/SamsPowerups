package com.lothrazar.pagedinventory.inventory;

import com.lothrazar.pagedinventory.Const; 
import com.lothrazar.pagedinventory.PlayerExtended;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 */
public class BigInventoryPlayer extends InventoryPlayer
{
    @SideOnly(Side.CLIENT)
    private ItemStack currentItemStack;
 
    //public ItemStack[] bonusInventory;// = new ItemStack[Const.BONUS_SIZE];
 
	public BigInventoryPlayer(EntityPlayer p)
	{
		super(p);
		this.mainInventory = new ItemStack[Const.ALL_SLOTS];
		//bonusInventory = new ItemStack[Const.BONUS_SIZE];
		player=p;
 
	}
 
    public int getCurrentPage()
    {
    	return PlayerExtended.get(player).getPageCurrent();
    }
	public boolean incrementPage()
	{
		int currentPage = getCurrentPage();
		if(currentPage==Const.PAGES-1){return false;}
		currentPage++;
		PlayerExtended.get(player).setPageCurrent(currentPage);
		
		return true;
	}
	public boolean setPage(int page) 
	{
		PlayerExtended.get(player).setPageCurrent(page);
		return true;
	}
	public boolean decrementPage()
	{
		int currentPage = getCurrentPage();
		if(currentPage==0){return false;}
		currentPage--;
		PlayerExtended.get(player).setPageCurrent(currentPage);
		return true;
	}
	
	/*
	@Override
	public ItemStack getStackInSlot(int index)
    {
   //reminder, it happens that ARMOR_START == this.mainInventory.length
        if (index >= Const.ARMOR_START && index < Const.ARMOR_START + Const.ARMOR_SIZE)//armor slots depend on being right at the end like 384
        {
            return this.armorInventory[index - Const.ARMOR_START];
        }
   
        else if(index >= this.mainInventory.length && index < this.mainInventory.length+this.armorInventory.length)
        { 
        	//should be using armor slots 
        	 return this.armorInventory[index - this.mainInventory.length];
        	
        }
        return this.mainInventory[index];//exception at 384?454?
    }
	*/
	@Override
	public void dropAllItems()
	{
		super.dropAllItems();
	}
	/*
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
    {
		if(this.player.capabilities.isCreativeMode && this.player.worldObj.isRemote)
		{
            Minecraft.getMinecraft().playerController.sendSlotPacket(stack, slot);
		}
		
		else
		{
			//if(slot > Const.ALL_SLOTS)
			//	System.out.println("setInv super "+slot);
			
			if(slot >= Const.ARMOR_START && slot < Const.ARMOR_START + Const.ARMOR_SIZE)
			{
				slot -= Const.ARMOR_START;
				this.armorInventory[slot] = stack;
			}
			
			
			 
		        if (slot >= this.mainInventory.length && slot < this.mainInventory.length + Const.ARMOR_SIZE)
		        {
		        	//System.out.println("Slot="+slot);//??666
		        	slot = slot - this.mainInventory.length;
		            //out of bounds -586
			        this.armorInventory[slot] = stack;
		        }
			else
		        this.mainInventory[slot] = stack;
			//super.setInventorySlotContents(slot, stack);
		}
    }*/
	 
    private int func_146029_c(Item stack)
    {
        for (int i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == stack)
            {
                return i;
            }
        }

        return -1;
    }

    private int storeItemStack(ItemStack stack)
    {
        for (int i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == stack.getItem() && this.mainInventory[i].isStackable() && this.mainInventory[i].stackSize < this.mainInventory[i].getMaxStackSize() && this.mainInventory[i].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[i].getHasSubtypes() || this.mainInventory[i].getItemDamage() == stack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.mainInventory[i], stack))
            {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getFirstEmptyStack()
    {
        for (int i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] == null)
            {
                return i;
            }
        }

        return -1;
    }

    @SideOnly(Side.CLIENT)
    private int func_146024_c(Item item, int meta)//getSlotNumberForItem
    {
        for (int j = 0; j < this.mainInventory.length; ++j)
        {
            if (this.mainInventory[j] != null && this.mainInventory[j].getItem() == item && this.mainInventory[j].getItemDamage() == meta)
            {
                return j;
            }
        }

        return -1;
    }
    //int hotbarest = 9+9;
    @SideOnly(Side.CLIENT)
    @Override
    public void setCurrentItem(Item item, int meta, boolean var3flag, boolean var4flag)
    {
        this.currentItemStack = this.getCurrentItem();
        int k;
//System.out.println("setCurrentItem");
        if (var3flag)
        {
            k = this.func_146024_c(item, meta);
        }
        else
        {
            k = this.func_146029_c(item);
          //  System.out.println("k = "+k);
        }

        if (k >= 0 && k < 9)
        {
            this.currentItem = k;
        }
        else
        {
            if (var4flag && item != null)
            {
                int j = this.getFirstEmptyStack();

                if (j >= 0 && j < 9)
                {
                   // System.out.println("j = "+j);
                    this.currentItem = j;
                }

                this.func_70439_a(item, meta);
            }
        }
    }

    @SideOnly(Side.CLIENT)  // @Override
    public void func_70439_a(Item item, int meta)//set item in slot
    {
    
        if (item != null)
        { 
            if (this.currentItemStack != null && this.currentItemStack.isItemEnchantable() && this.func_146024_c(this.currentItemStack.getItem(), this.currentItemStack.getItemDamage()) == this.currentItem)
            {
                return;
            }

            int j = this.func_146024_c(item, meta);

            if (j >= 0)
            {
                int k = this.mainInventory[j].stackSize;
                this.mainInventory[j] = this.mainInventory[this.currentItem];
                this.mainInventory[this.currentItem] = new ItemStack(item, k, meta);
            }
            else
            {
                this.mainInventory[this.currentItem] = new ItemStack(item, 1, meta);
            }
        }
    }

    /**
     * This function stores as many items of an ItemStack as possible in a matching slot and returns the quantity of
     * left over items.
     */
    private int storePartialItemStack(ItemStack stack)
    {
        Item item = stack.getItem();
        int i = stack.stackSize;
        int j;

        if (stack.getMaxStackSize() == 1)
        {
            j = this.getFirstEmptyStack();

            if (j < 0)
            {
                return i;
            }
            else
            {
                if (this.mainInventory[j] == null)
                {
                    this.mainInventory[j] = ItemStack.copyItemStack(stack);
                }

                return 0;
            }
        }
        else
        {
            j = this.storeItemStack(stack);

            if (j < 0)
            {
                j = this.getFirstEmptyStack();
            }

            if (j < 0)
            {
                return i;
            }
            else
            {
                if (this.mainInventory[j] == null)
                {
                    this.mainInventory[j] = new ItemStack(item, 0, stack.getItemDamage());

                    if (stack.hasTagCompound())
                    {
                        this.mainInventory[j].setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
                    }
                }

                int k = i;

                if (i > this.mainInventory[j].getMaxStackSize() - this.mainInventory[j].stackSize)
                {
                    k = this.mainInventory[j].getMaxStackSize() - this.mainInventory[j].stackSize;
                }

                if (k > this.getInventoryStackLimit() - this.mainInventory[j].stackSize)
                {
                    k = this.getInventoryStackLimit() - this.mainInventory[j].stackSize;
                }

                if (k == 0)
                {
                    return i;
                }
                else
                {
                    i -= k;
                    this.mainInventory[j].stackSize += k;
                    this.mainInventory[j].animationsToGo = 5;
                    return i;
                }
            }
        }
    }

    /**
     * Adds the item stack to the inventory, returns false if it is impossible.
     */
    @SuppressWarnings("rawtypes")
	@Override
    public boolean addItemStackToInventory(final ItemStack stack)
    {
        if (stack != null && stack.stackSize != 0 && stack.getItem() != null)
        {
            try
            {
                int i;

                if (stack.isItemDamaged())
                {
                    i = this.getFirstEmptyStack();

                    if (i >= 0)
                    {
                        this.mainInventory[i] = ItemStack.copyItemStack(stack);
                        this.mainInventory[i].animationsToGo = 5;
                        stack.stackSize = 0;
                        return true;
                    }
                    else if (this.player.capabilities.isCreativeMode)
                    {
                        stack.stackSize = 0;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    do
                    {
                        i = stack.stackSize;
                        stack.stackSize = this.storePartialItemStack(stack);
                    }
                    while (stack.stackSize > 0 && stack.stackSize < i);

                    if (stack.stackSize == i && this.player.capabilities.isCreativeMode)
                    {
                        stack.stackSize = 0;
                        return true;
                    }
                    else
                    {
                        return stack.stackSize < i;
                    }
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
                crashreportcategory.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(stack.getItem())));
                crashreportcategory.addCrashSection("Item data", Integer.valueOf(stack.getItemDamage()));
                crashreportcategory.addCrashSectionCallable("Item name", new Callable()
                {
                    public String call()
                    {
                        return stack.getDisplayName();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Modified from the original to allow for more than 255 inventory slots
     */
    public NBTTagList writeToNBT(NBTTagList tags)
    {
        int i;
        NBTTagCompound nbttagcompound;

        for (i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger(Const.NBT_SLOT, i);
                this.mainInventory[i].writeToNBT(nbttagcompound);
                tags.appendTag(nbttagcompound);
            }
        }

        for (i = 0; i < this.armorInventory.length; ++i)
        {
            if (this.armorInventory[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger(Const.NBT_SLOT, i +Const.ARMOR_START); // Give armor slots the last 100 integer spaces
                this.armorInventory[i].writeToNBT(nbttagcompound);
                tags.appendTag(nbttagcompound);
                
            }
        }
        
        return tags;
    }
    /*
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack;

        if(index >= Const.BONUS_START && index < Const.BONUS_START + Const.BONUS_SIZE)
    	{
    		int bonusType = index - Const.BONUS_START;// like armorType
    		
    		 if (this.bonusInventory[bonusType].stackSize <= count)
             {
                 itemstack = this.bonusInventory[bonusType];
                 this.bonusInventory[bonusType] = null;
                 return itemstack;
             }
    		 else
             {
                 itemstack = this.bonusInventory[bonusType].splitStack(count);

                 if (this.bonusInventory[bonusType].stackSize == 0)
                 {
                	 this.bonusInventory[bonusType] = null;
                 }

                 return itemstack;
             }
    	}
    	else 
    		return super.decrStackSize(index, count);
    }*/

    /**
     * Modified from the original to allow for more than 255 inventory slots
     */
	@Override
    public void readFromNBT(NBTTagList tags)
    {
        this.mainInventory = new ItemStack[Const.ALL_SLOTS];//was Const.ALL_ROWS * Const.ALL_COLS + Const.HOTBAR_SIZE
        this.armorInventory = new ItemStack[Const.ARMOR_SIZE]; 
     //   this.bonusInventory = new ItemStack[Const.BONUS_SIZE]; 
        for (int i = 0; i < tags.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = tags.getCompoundTagAt(i);
            int j = nbttagcompound.getInteger(Const.NBT_SLOT);
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

            if (itemstack != null)
            {
                if (j >= 0 && j < this.mainInventory.length)
                {
            		this.mainInventory[j] = itemstack;
                }
                if (j >= Const.ARMOR_START && j < Const.ARMOR_START  + Const.ARMOR_SIZE)
                {
                	this.armorInventory[j - Const.ARMOR_START] = itemstack;
                }
         
            }
        }
    }

}
