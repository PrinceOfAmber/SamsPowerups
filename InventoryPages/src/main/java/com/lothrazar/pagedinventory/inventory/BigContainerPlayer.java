package com.lothrazar.pagedinventory.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.lothrazar.pagedinventory.Const;
import com.lothrazar.pagedinventory.ModInv;
import com.lothrazar.pagedinventory.inventory.client.GuiBigInventory;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 */
public class BigContainerPlayer extends ContainerPlayer
{	
	private final int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
    private final EntityPlayer thePlayer;
 
	public BigInventoryPlayer invo;
    public boolean isLocalWorld;

	final int padding = 6;
	//these get used here for actual slot, and in GUI for texture
    //ender pearl is in the far bottom right corner, and the others move left relative to this

	public final int pearlX = 80-1; 
	public final int pearlY = 8; 
	public final int compassX = pearlX;
	public final int compassY = pearlY + Const.sq;
	public final int clockX = pearlX;
	public final int clockY = pearlY + 2*Const.sq;
	public final int echestX = pearlX;
	public final int echestY = pearlY + 3*Const.sq;

	public final int bottleX = Const.textureWidth() - Const.sq - padding;
	public final int bottleY = 20 + 2 * Const.sq;

//store slot numbers  (not indexes) as we go. so that transferStack.. is actually readable
	 
	static int S_RESULT;
	static int S_CRAFT_START;
	static int S_CRAFT_END;
	static int S_ARMOR_START;
	static int S_ARMOR_END;
	static int S_BAR_START;
	static int S_BAR_END;
	static int S_MAIN_START;
	static int S_MAIN_END;
	//static int S_ECHEST;
	//static int S_PEARL;
	//static int S_CLOCK;
	//static int S_COMPASS;
	//static int S_BOTTLE;
	public BigContainerPlayer(BigInventoryPlayer playerInventory, boolean isLocal, EntityPlayer player)
	{
		super(playerInventory, isLocal, player);
 
        this.thePlayer = player;
		inventorySlots = Lists.newArrayList();//undo everything done by super()
		craftMatrix = new InventoryCrafting(this, craftSize, craftSize);
 
        int i,j,cx,cy,slotIndex,padding=8;//rows and cols of vanilla, not extra
   
        S_RESULT = this.inventorySlots.size();
        slotIndex = 0;//was 0
        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, slotIndex, 
        		padding + 145,  
        		padding+34));

        S_CRAFT_START = this.inventorySlots.size();
        for (i = 0; i < craftSize; ++i)
        { 
            for (j = 0; j < craftSize; ++j)
            {  
    			cx = padding + 74 + j * Const.sq ; 
    			cy = padding + 18 + i * Const.sq ;
    			slotIndex = j + i * this.craftSize;
        		this.addSlotToContainer(new Slot(this.craftMatrix, slotIndex, cx , cy)); 
            }
        }
        S_CRAFT_END = this.inventorySlots.size() - 1;
       
        S_BAR_START = this.inventorySlots.size();
        
    	cy = 19 +padding + Const.sq * (Const.ALL_ROWS + 5);//so 12, since allrows=15
        for (i = 0; i < Const.HOTBAR_SIZE; ++i)
        { 
        	cx = padding + i * Const.sq; 
        	slotIndex = i;
            this.addSlotToContainer(new Slot(playerInventory, slotIndex, cx, cy));
        }
        S_BAR_END = this.inventorySlots.size() - 1;
        
        

        S_MAIN_START = this.inventorySlots.size();
        slotIndex = Const.HOTBAR_SIZE;
        
        for( i = 0; i < Const.ALL_ROWS; i++)
		{
            for ( j = 0; j < Const.ALL_COLS; ++j)
            { 
            	cx = padding + j * Const.sq;
            	cy = 76+padding + i * Const.sq;
                this.addSlotToContainer(new Slot(playerInventory, slotIndex, cx, cy));
            	slotIndex++;
            }
        }
        
        S_MAIN_END = this.inventorySlots.size() - 1;
        
        
        
      //384 85 86 87 is what it gets, when INV_SIZE = 375, meaning 375+9=384
        S_ARMOR_START = this.inventorySlots.size(); 
        for (i = 0; i < Const.ARMOR_SIZE; ++i)
        {
        	cx = padding;
        	cy = padding + i * Const.sq;
            final int k = i;
            //getSizeInventory==this.mainInventory.length + 4;
            //;//
            slotIndex = Const.ARMOR_START + i;//playerInventory.getSizeInventory() - 1 - i;
            this.addSlotToContainer(new Slot(playerInventory,  slotIndex, cx, cy)
            { 
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
        S_ARMOR_END = this.inventorySlots.size() - 1;
 

        
        this.onCraftMatrixChanged(this.craftMatrix);
		this.invo = playerInventory; 
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
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p, int slotNumber)
    {  
		if(p.capabilities.isCreativeMode)
		{
			return super.transferStackInSlot(p, slotNumber);
		}
		//Thanks to coolAlias on the forums : 
		//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571051-custom-container-how-to-properly-override-shift
		//above is from 2013 but still relevant
        ItemStack stackCopy = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack())
        {
            ItemStack stackOrig = slot.getStack();
            stackCopy = stackOrig.copy();
            if (slotNumber == S_RESULT)  
            { 
                if (!this.mergeItemStack(stackOrig, S_BAR_START, S_MAIN_END, true))
                {
                    return null;
                }

                slot.onSlotChange(stackOrig, stackCopy);
            }
            else if (slotNumber >= S_CRAFT_START && slotNumber <= S_CRAFT_END) 
            { 
                if (!this.mergeItemStack(stackOrig,  S_BAR_START, S_MAIN_END, false))//was 9,45
                {
                    return null;
                }
            }
            else if (slotNumber >= S_ARMOR_START && slotNumber <= S_ARMOR_END) 
            { 
                if (!this.mergeItemStack(stackOrig, S_MAIN_START, S_MAIN_END, false))
                {
                    return null;
                }
            }
            else if (stackCopy.getItem() instanceof ItemArmor 
            		//armor type: 0 is helmet, 1 is plate, 2 is legs and 3 is boots
            		&& !((Slot)this.inventorySlots.get(S_ARMOR_START + ((ItemArmor)stackCopy.getItem()).armorType)).getHasStack()) // Inventory to armor
            { 
            	int j = S_ARMOR_START + ((ItemArmor)stackCopy.getItem()).armorType;
           
            	if (!this.mergeItemStack(stackOrig, j, j+1, false))
                {
                    return null;
                } 
            }
            else if (slotNumber >= S_MAIN_START && slotNumber <= S_MAIN_END) // main inv grid
            { 
            	if (!this.mergeItemStack(stackOrig, S_BAR_START, S_BAR_END+1, false)            			)
            	{
            		
                    return null;
                }
            }
            else if (slotNumber >= S_BAR_START && slotNumber <= S_BAR_END) // Hotbar
            { 
            	if (!this.mergeItemStack(stackOrig, S_MAIN_START, S_MAIN_END, false))
            	{
                    return null;
                }
            }
            else if (!this.mergeItemStack(stackOrig, Const.HOTBAR_SIZE, invo.getSizeInventory() - Const.ARMOR_SIZE + Const.HOTBAR_SIZE, false)) // ?Full range
            {
                return null;
            }
            
            //finally, notify of changes
            if (stackOrig.stackSize == 0)
            { 
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (stackOrig.stackSize == stackCopy.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p, stackOrig);
        }

        return stackCopy;
    }*/
}
