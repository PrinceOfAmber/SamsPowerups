package com.lothrazar.samsfarmblocks;

import java.util.Random;

 

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFragile extends Block
{
	public BlockFragile() 
	{
		super(Material.wood);
		this.setCreativeTab(ModFarmingBlocks.tabSFarming);
		this.setTickRandomly(true);
		this.setHardness(0F);
		this.setResistance(0F); 
		this.setStepSound(soundTypeWood);
	} 
	@Override
	public boolean isOpaqueCube() 
	{
		return false;//transparency 
	}
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;//transparency 
    } 
	@Override
	public void updateTick(World worldObj,  BlockPos pos, IBlockState state,  Random rand)
    {   
		worldObj.destroyBlock(pos, true);  
    }

	public void addRecipe()
	{
		GameRegistry.addShapedRecipe(new ItemStack(BlockRegistry.block_fragile), 
				"ss",
				"ss", 
				's',Items.stick);
		
		//if(ModMain.cfg.uncraftGeneral)
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick,4), BlockRegistry.block_fragile);
	}
}
