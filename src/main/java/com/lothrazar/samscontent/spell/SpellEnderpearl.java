package com.lothrazar.samscontent.spell;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellEnderpearl implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.pearl;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		world.spawnEntityInWorld(new EntityEnderPearl(world,player 	 ));
		 
		this.onCastSuccess(world, player, pos);
	}

	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		if(Util.getExpTotal(player) < getExpCost()) return false;
		
		return true;
	}

	@Override
	public void drainExpCost(EntityPlayer player)
	{ 
		 Util.drainExp(player, getExpCost());
	}

	private int cost = 10;
	@Override
	public void setExpCost(int c)
	{
		cost = c;
	}
	@Override
	public int getExpCost()
	{
		return cost;
	}
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{

		player.swingItem();
		
		Util.spawnParticle(world, EnumParticleTypes.CRIT, pos);

		Util.playSoundAt(player, Reference.sounds.bowtoss);
		//Util.playSoundAt(player, "random.wood_click");
	}

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.ender_pearl);
	}

}