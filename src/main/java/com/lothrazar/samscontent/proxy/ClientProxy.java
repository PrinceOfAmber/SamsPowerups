package com.lothrazar.samscontent.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.input.Keyboard;   
import net.minecraft.init.Items;
import  net.minecraft.item.Item;
import com.lothrazar.samscontent.BlockRegistry;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain; 
import com.lothrazar.samscontent.entity.projectile.*;
import com.lothrazar.util.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
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
	public static KeyBinding keyBindMacro1;
	public static KeyBinding keyBindMacro2;
	public static KeyBinding keyPush;
	public static KeyBinding keyPull; 
	public static KeyBinding keyTransform; 
	public static KeyBinding keySpellCast;
	public static KeyBinding keySpellUp;
	public static KeyBinding keySpellDown;
	public static KeyBinding keySpellToggle;
 
    @Override
    public void registerRenderers() 
    {  
    	registerKeyBindings(); 

        registerModels(); 
        
        registerEntities();
    }
    
    private void registerEntities()
    {
    	RenderManager rm = Minecraft.getMinecraft().getRenderManager();
    	RenderItem ri = Minecraft.getMinecraft().getRenderItem();
    	
    	//works similar to vanilla which is like
    	//Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntitySoulstoneBolt.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), ItemRegistry.soulstone, Minecraft.getMinecraft().getRenderItem()));

    	RenderingRegistry.registerEntityRenderingHandler(EntitySoulstoneBolt.class, new RenderSnowball(rm, ItemRegistry.soulstone, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityLightningballBolt.class, new RenderSnowball(rm, ItemRegistry.spell_lightning_dummy, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityHarvestbolt.class, new RenderSnowball(rm, ItemRegistry.spell_harvest_dummy, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWaterBolt.class, new RenderSnowball(rm, ItemRegistry.spell_frostbolt_dummy, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntitySnowballBolt.class, new RenderSnowball(rm, ItemRegistry.spell_frostbolt_dummy, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityTorchBolt.class, new RenderSnowball(rm, Items.glowstone_dust, ri));
        
    	
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
		keyShiftUp = new KeyBinding(Reference.keyUpName, Keyboard.KEY_Y, Reference.keyCategoryInventory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
    
		keyShiftDown = new KeyBinding(Reference.keyDownName, Keyboard.KEY_H, Reference.keyCategoryInventory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 

        keyBarUp = new KeyBinding(Reference.keyBarUpName, Keyboard.KEY_U, Reference.keyCategoryInventory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarUp);
         
        keyBarDown = new KeyBinding(Reference.keyBarDownName, Keyboard.KEY_J, Reference.keyCategoryInventory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarDown);

        keyBindMacro1 = new KeyBinding(Reference.keyBind1Name, Keyboard.KEY_N, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindMacro1);

        keyBindMacro2 = new KeyBinding(Reference.keyBind2Name, Keyboard.KEY_M, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindMacro2);

        keyTransform = new KeyBinding(Reference.keyTransformName, Keyboard.KEY_V, Reference.keyCategoryBlocks); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyTransform);
 
        keyPush = new KeyBinding(Reference.keyPushName, Keyboard.KEY_G, Reference.keyCategoryBlocks); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyPush);
        keyPull = new KeyBinding(Reference.keyPullName, Keyboard.KEY_B,  Reference.keyCategoryBlocks); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyPull);
        
        keySpellCast = new KeyBinding(Reference.keySpellCastName, Keyboard.KEY_X, Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keySpellCast);

        keySpellUp = new KeyBinding(Reference.keySpellUpName, Keyboard.KEY_Z, Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keySpellUp);

        keySpellDown = new KeyBinding(Reference.keySpellDownName, Keyboard.KEY_C,  Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keySpellDown);
        
        keySpellToggle = new KeyBinding(Reference.keySpellToggleName, Keyboard.KEY_SEMICOLON,  Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keySpellToggle);
	} 

	public static String getKeyDescription(int key)
	{
		//getKeyDescription gets something like 'key.macro1' like lang file data
		
		//thanks http://stackoverflow.com/questions/10893455/getting-keytyped-from-the-keycode
	 
		KeyBinding binding = null;
		switch(key)//TODO:...maybe find better way. switch for now
		{
		case 1:
			binding = keyBindMacro1;
			break;
		case 2:
			binding = keyBindMacro2;
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
