package com.lothrazar.powerinventory.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.ModInv;
import com.lothrazar.powerinventory.inventory.client.GuiBigInventory;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 */
public class BigContainerPlayer extends ContainerPlayer
{	
	private final int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
	public int scrollPos = 0;
	public BigInventoryPlayer invo;
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
   // private Slot[] slots = new Slot[Const.invoSize];

	//these get used here for actual slot, and in GUI for texture
	public final int pearlX = GuiBigInventory.texture_width - Const.square-6; 
	public final int pearlY = GuiBigInventory.texture_height - Const.square-6; 
	public final int echestX = pearlX - 2*Const.square;
	public final int echestY = pearlY;

	@SuppressWarnings("unchecked")
	public BigContainerPlayer(BigInventoryPlayer playerInventory, boolean isLocal, EntityPlayer player)
	{
		super(playerInventory, isLocal, player);
		
        this.thePlayer = player;
		inventorySlots = Lists.newArrayList();//undo everything done by super()
		craftMatrix = new InventoryCrafting(this, craftSize, craftSize);
		Slot newslot;

       // boolean onHold = false;
       // int[] holdSlot = new int[5];//because 3x3 - 2x2 = 5
       // int[] holdX = new int[holdSlot.length];
      //  int[] holdY = new int[holdSlot.length];

        int i,j,cx,cy,h = 0,slotIndex = 0, rows=3, cols=Const.hotbarSize;//rows and cols of vanilla, not extra

        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, slotIndex, 152, 42));
    
        for (i = 0; i < craftSize; ++i)
        {
        	//onHold = false;
        
            for (j = 0; j < craftSize; ++j)
            {
            	//if(i == this.craftSize - 1 || j == this.craftSize - 1) {onHold = true;} //hold right and bottom column
            	
            	slotIndex = j + i * this.craftSize;
       
        			cx = 89 + j * Const.square ;
        			cy = 26 + i * Const.square ;
        			newslot=new Slot(this.craftMatrix, slotIndex, cx , cy);
            		this.addSlotToContainer(newslot);
             System.out.println("craftin: "+newslot.slotNumber+" at "+cx+","+cy);
  
            //	}
            }
        }

        for (i = 0; i < Const.armorSize; ++i)
        {
        	cx = 8;
        	cy = 8 + i * Const.square;
            final int k = i;
            slotIndex =  playerInventory.getSizeInventory() - 1 - i;

            System.out.println("armor at "+cx+","+cy);
            this.addSlotToContainer(new Slot(playerInventory, slotIndex, cx, cy)
            {
                //   private static final String __OBFID = "CL_00001755";
    
                   public int getSlotStackLimit()
                   {
                       return 1;
                   }
               
                   public boolean isItemValid(ItemStack stack)
                   {
                       if (stack == null) return false;
                       return stack.getItem().isValidArmor(stack, k, thePlayer);
                   }
                   @SideOnly(Side.CLIENT)
                   public String getSlotTexture()
                   {
                       return ItemArmor.EMPTY_SLOT_NAMES[k];
                   }
            }); 
        }

       // for (i = 0; i < rows; ++i)      //inventory is 3 rows by 9 columns
        //{
           // for (j = 0; j < cols; ++j)
           // {
        int sn = 9;
        for( i = 0; i < MathHelper.ceiling_float_int((float)Const.invoSize/(float)(Const.ALL_COLS)); i++)
		{
            for ( j = 0; j < Const.ALL_COLS; ++j)
            {
            	slotIndex = sn;//j + (i + 1) * cols;
sn++;
            	cx = 8 + j * Const.square;
            	cy = 84 + i * Const.square;
                this.addSlotToContainer(new Slot(playerInventory, slotIndex, cx, cy));
            }
        }
		/*
		//the main area
        for ( i = rows; i < MathHelper.ceiling_float_int((float)(Const.invoSize/9F)); ++i)
        {
            for ( j = 0; j < cols; ++j)
            {
            	if(j + (i * cols) >= Const.invoSize && Const.invoSize > rows*cols)
            	{
            		break;
            	} 
            	else
            	{
            		// Moved off screen to avoid interaction until screen scrolls over the row
            		slotIndex =  j + (i + 1) * cols;
 
            		//cx = Const.OFFSCREEN;
            		//cy = Const.OFFSCREEN;
            		cx = 8 + j * Const.square;
            		cy = 84 + (i) * Const.square;
            		Slot ns = new Slot(playerInventory,slotIndex, cx,cy);
            		slots[slotIndex - cols] = ns;
            		this.addSlotToContainer(ns); 
            	}
            }
        }*/
        for (i = 0; i < cols; ++i)//hotbar?
        {
        	slotIndex = i;
        	cx = 8 + i * Const.square;
        	cy = 142 + (Const.square * Const.MORE_ROWS);
 
            this.addSlotToContainer(new Slot(playerInventory, slotIndex, cx, cy));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
		this.invo = (BigInventoryPlayer)playerInventory;
		 
		/*for(i = cols; i < 4*cols; i++)
		{
			// Add all the previous inventory slots to the organised array
			 Slot os = (Slot)this.inventorySlots.get(i);
			 
			 Slot ns = new Slot(os.inventory, os.getSlotIndex(), os.xDisplayPosition, os.yDisplayPosition);
			 ns.slotNumber = os.slotNumber;
			 this.inventorySlots.set(i, ns);
			 ns.onSlotChanged();
			 slots[i - cols] = ns;
		}*/
		

         
        this.addSlotToContainer(new SlotEnderPearl(playerInventory, Const.enderPearlSlot, pearlX, pearlY));
        this.addSlotToContainer(new SlotEnderChest(playerInventory, Const.enderChestSlot, echestX, echestY));
        
        //this.updateScroll();
	}
  
	@Override
	public Slot getSlotFromInventory(IInventory invo, int id)
	{
		Slot slot = super.getSlotFromInventory(invo, id);
		if(slot == null)
		{
			Exception e = new NullPointerException();
			 
			ModInv.logger.log(Level.FATAL, e.getStackTrace()[1].getClassName() + "." + e.getStackTrace()[1].getMethodName() + ":" + e.getStackTrace()[1].getLineNumber() + " is requesting slot " + id + " from inventory " + invo.getName() + " (" + invo.getClass().getName() + ") and got NULL!", e);
		}
		return slot;
	}
	
	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if(playerIn.capabilities.isCreativeMode == false) //i think we were dropping stuff from hotbar?
	        for (int i = 0; i < craftSize*craftSize; ++i) // was 4
	        {
	            ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);
	
	            if (itemstack != null)
	            {
	                playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
	            }
	        }

        this.craftResult.setInventorySlotContents(0, (ItemStack)null);
    }
/*
	public void updateScroll()
	{
		if(scrollPos > MathHelper.ceiling_float_int((float)Const.invoSize/(float)(Const.ALL_COLS)) - Const.ALL_ROWS)
		{
			scrollPos = MathHelper.ceiling_float_int((float)Const.invoSize/(float)(Const.ALL_COLS)) - Const.ALL_ROWS;
		}
		
		if(scrollPos < 0)
		{
			scrollPos = 0;
		}
		System.out.println("update scroll "+0);
		
		for(int i = 0; i < MathHelper.ceiling_float_int((float)Const.invoSize/(float)(Const.ALL_COLS)); i++)
		{
			
            for (int j = 0; j < Const.ALL_COLS; ++j)
            {
            	int index = j + (i * Const.ALL_COLS);
    
            	if(index >= Const.invoSize && index >= 3*Const.hotbarSize)
            	{
            		break;
            	} else
            	{
            		if(i >= scrollPos && i < scrollPos + 3 + Const.MORE_ROWS && index < invo.getSlotsNotArmor() - Const.hotbarSize && index < Const.invoSize)
            		{
            			Slot s = slots[index];
            			s.xDisplayPosition = 8 + j * Const.square;
            			s.yDisplayPosition = 84 + (i - scrollPos) * Const.square;
            		} else
            		{
            			Slot s = slots[index];
            			s.xDisplayPosition = Const.OFFSCREEN;
            			s.yDisplayPosition = Const.OFFSCREEN;
            		}
            	}
            }
		}
	}*/

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p, int craft)
    { 
		//int vLocked =  0;//invo.getSlotsNotArmor() < 36? 36 - invo.getSlotsNotArmor() :
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(craft);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (craft == 0) // Crafting result
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (craft >= 1 && craft <= 9) // Crafting grid
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (craft >= 10 && craft <= 13) // Armor
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack()) // Inventory to armor
            {
                int j = 5 + ((ItemArmor)itemstack.getItem()).armorType;

                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
                {
                    return null;
                }
            }
            else if ((craft >= 9 && craft < 36) || (craft >= 45 && craft < invo.getSlotsNotArmor() + 9))
            {
                if (!this.mergeItemStack(itemstack1, 36, 45, false))
                {
                    return null;
                }
            }
            else if (craft >= 36 && craft < 45) // Hotbar
            {
                if (!this.mergeItemStack(itemstack1, 9, 36, false) && (invo.getSlotsNotArmor() - 36 <= 0 || !this.mergeItemStack(itemstack1, 45, 45 + (invo.getSlotsNotArmor() - 36), false)))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 9, invo.getSlotsNotArmor() + 9, false)) // Full range
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p, itemstack1);
        }

        return itemstack;
    }
}
