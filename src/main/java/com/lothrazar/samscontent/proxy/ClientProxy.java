package com.lothrazar.samscontent.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.input.Keyboard;   
import  net.minecraft.item.Item;
import com.lothrazar.samscontent.BlockRegistry;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy 
{  
	public static KeyBinding keyShiftUp;
	public static KeyBinding keyShiftDown; 
	public static KeyBinding keyBarUp;
	public static KeyBinding keyBarDown; 
	public static KeyBinding keyBind1;
	public static KeyBinding keyBind2;
	public static KeyBinding keyBind3;
	public static KeyBinding keyBind4;
	public static KeyBinding keyTransform;
	public static KeyBinding keyWaterwalk;
	public static KeyBinding keyBindGhostmode;
	public static KeyBinding keyBindJumpboost;
	public static KeyBinding keyBindEnder;
	public static KeyBinding keyBindSlowfall; 
  
    @Override
    public void registerRenderers() 
    {  
    	registerKeyBindings(); 

        registerModels(); 
    }

	private void registerModels() 
	{
		//More info on proxy rendering
        //http://www.minecraftforge.net/forum/index.php?topic=27684.0
       //http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2272349-lessons-from-my-first-mc-1-8-mod
   
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        String name;
        Item item;
	 
        for(Block b : BlockRegistry.blocks)
        { 
        	item = Item.getItemFromBlock(b);
        	name = Reference.TEXTURE_LOCATION + b.getUnlocalizedName().replaceAll("tile.", "");

   			mesher.register(item, 0, new ModelResourceLocation( name , "inventory"));	 
        }
         
        for(Item i : ItemRegistry.items)
        {  
        	name = Reference.TEXTURE_LOCATION + i.getUnlocalizedName().replaceAll("item.", "");

   			mesher.register(i, 0, new ModelResourceLocation( name , "inventory"));	 
        }
        if(ModMain.cfg.respawn_egg)
        {
        	for(Object key : EntityList.entityEggs.keySet())
            {
            	mesher.register(ItemRegistry.respawn_egg, (Integer)key, new ModelResourceLocation(Reference.TEXTURE_LOCATION + "respawn_egg" , "inventory"));	 
            }
        }
	}

	private void registerKeyBindings() 
	{
		keyShiftUp = new KeyBinding(Reference.keyUpName, Keyboard.KEY_C, Reference.keyCategoryInventory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
     
		keyShiftDown = new KeyBinding(Reference.keyDownName, Keyboard.KEY_V, Reference.keyCategoryInventory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 

        keyBarUp = new KeyBinding(Reference.keyBarUpName, Keyboard.KEY_N, Reference.keyCategoryInventory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarUp);
         
        keyBarDown = new KeyBinding(Reference.keyBarDownName, Keyboard.KEY_M, Reference.keyCategoryInventory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarDown);
        
        keyBind1 = new KeyBinding(Reference.keyBind1Name, Keyboard.KEY_Y, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBind1);

        keyBind2 = new KeyBinding(Reference.keyBind2Name, Keyboard.KEY_U, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBind2);

        keyBind3 = new KeyBinding(Reference.keyBind3Name, Keyboard.KEY_L, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBind3);

        keyBind4 = new KeyBinding(Reference.keyBind4Name, Keyboard.KEY_O,  Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBind4);
         
        keyTransform = new KeyBinding(Reference.keyBind5Name, Keyboard.KEY_P, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyTransform);

        keyWaterwalk = new KeyBinding(Reference.keyWaterwalkName, Keyboard.KEY_H, Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyWaterwalk);

        keyBindGhostmode = new KeyBinding(Reference.keyGhostmodeName, Keyboard.KEY_J, Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindGhostmode);

        keyBindJumpboost = new KeyBinding(Reference.keyJumpboostName, Keyboard.KEY_K,  Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindJumpboost);
        
        keyBindEnder = new KeyBinding(Reference.keyBindEnderName, Keyboard.KEY_I,  Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindEnder);
        
        keyBindSlowfall = new KeyBinding(Reference.keyBindSlowName, Keyboard.KEY_SEMICOLON,  Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindSlowfall);
     
	} 
	
	/*public static int getKey(String descr)
	{
		// input and search from this, instead of 1-9
		if(getKeyDescription(keyBind1.getKeyCode()).toLowerCase() == descr.toLowerCase())
		{
			return 1;
		}
		
		return Keyboard.getKeyIndex(descr);
	}*/
	public static String getKeyDescription(int key)
	{
		//getKeyDescription gets something like 'key.macro1' like lang file data
		
		//thanks http://stackoverflow.com/questions/10893455/getting-keytyped-from-the-keycode
	 
		KeyBinding binding = null;
		switch(key)//TODO:...maybe find better way. switch for now
		{
		case 1:
			binding = keyBind1;
			break;
		case 2:
			binding = keyBind2;
			break;
		case 3:
			binding = keyBind3;
			break;
		case 4:
			binding = keyBind4;
			break;
		case 5:
			binding = keyTransform;
			break;
		case 6:
			binding = keyWaterwalk;
			break;
		case 7:
			binding = keyBindGhostmode;
			break;
		case 8:
			binding = keyBindJumpboost;
			break; 
		}
		
		 
		if(binding == null)
			return "";
		else
			return GameSettings.getKeyDisplayString(binding.getKeyCode());
			//return I18n.format(binding.getKeyDescription(), new Object[0]);
			//return java.awt.event.KeyEvent.getKeyText(binding.getKeyCode());
	}
}
