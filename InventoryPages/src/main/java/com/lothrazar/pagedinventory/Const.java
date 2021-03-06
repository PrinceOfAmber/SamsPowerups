package com.lothrazar.pagedinventory;
 
/** 
 * @author Sam Bassett aka Lothrazar
 */
public class Const
{ 
    public static final String MODID = "pagedinventory"; 
   // public static final String INVENTORY_TEXTURE = "textures/gui/inventory_12x18.png";
    public static final String getInventoryTexture()
    {
    	if(ALL_ROWS == 6 && ALL_COLS == 18)
    		return "textures/gui/inventory_6x18.png";//108 per page
    	if(ALL_ROWS == 12 && ALL_COLS == 18)
    		return "textures/gui/inventory_12x18.png";//216
    	else if(ALL_ROWS == 15 && ALL_COLS == 25)
    		return "textures/gui/inventory_15x25.png";//375
    	else if(ALL_ROWS == 3 && ALL_COLS == 9)
    		return "textures/gui/inventory_3x9.png";//375
    	else 
    		return null;//a non supported resolution
    }
    public static final int textureWidth()
    {
    	if(ALL_ROWS == 6 && ALL_COLS == 18)
    		return 336;//108 per page
    	if(ALL_ROWS == 12 && ALL_COLS == 18)
    		return 336;//216
    	else if(ALL_ROWS == 15 && ALL_COLS == 25)
    		return 464;//375
    	else if(ALL_ROWS == 3 && ALL_COLS == 9)
    		return 176;
    	else 
    		return 0;//a non supported resolution
    }
    public static final int textureHeight()
    {

    	if(ALL_ROWS == 6 && ALL_COLS == 18)
    		return 241;//108 per page
    	if(ALL_ROWS == 12 && ALL_COLS == 18)
    		return 328;//216
    	else if(ALL_ROWS == 15 && ALL_COLS == 25)
    		return 382;//375
    	else if(ALL_ROWS == 3 && ALL_COLS == 9)
    		return 195;
    	else 
    		return 0;//a non supported resolution
    }
    /*
	//these for 12x18. TODO link these
	public static final int texture_width = 336;
	public static final int texture_height = 328;
//ONLY FOR THE 15x25 one
	public static final int texture_width = 464;
	public static final int texture_height = 382;
*/
	public static final String NBT_SLOT = "Slot";
	public static final String NBT_PLAYER = "Player";
	public static final String NBT_WORLD = "World";
	public static final String NBT_ID = "ID";
	public static final String NBT_Settings = "Settings";
	public static final String NBT_Unlocked = "Unlocked";
	public static final String NBT_INVENTORY = "Inventory";
	public static final String NBT_INVOSIZE = "invoSize";

	public static final int sq = 18;
	public final static int HOTBAR_SIZE = 9 ;
	public final static int ARMOR_SIZE = 4; 
//	public final static int BONUS_SIZE = 5;

	public static int ALL_ROWS = 3;//3 + 12;//3+12=15//or 12
	public static int ALL_COLS = 9;//9 + 16;//9+16=25
	public static final int PAGES = 9;
	public static final int PAGESIZE = ALL_ROWS*ALL_COLS;
	public static final int ALL_SLOTS = PAGES*PAGESIZE + HOTBAR_SIZE;//was 384+9
 
	//these are slot indices. different than slot numbers (important) comes right after armor
	public static final int ARMOR_START = ALL_SLOTS;// HOTBAR_SIZE+ALL_ROWS * ALL_COLS;
 
 
	public final static int INV_ENDER = 1;
	public final static int INV_PLAYER = 2;
	
	public final static int SORT_LEFT = 1;
	public final static int SORT_RIGHT = 2;
	public final static int SORT_LEFTALL = -1;
	public final static int SORT_RIGHTALL = -2;
}
