package com.lothrazar.samsenderpower;
/*
 * 
 * imported from my 
 * 
 * https://github.com/PrinceOfAmber/BuildersDream_Minecraft/tree/master/mod_enderchestcommand/src/main/java/com/lothrazar/enderchestcommand
 * 
 * 
 * */
import java.util.ArrayList;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
public class CommandEnderChest implements ICommand
{
	private ArrayList<String> aliases;
	public CommandEnderChest()
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("ec");
		this.aliases.add("enderchest");
	}
	@Override
	public String getCommandName()
	{
		return "enderchest";
	}
	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "enderchest";
	}
	@Override
	public ArrayList<String> getCommandAliases()
	{
		return this.aliases;
	}
	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		EntityPlayer p = (EntityPlayer)icommandsender;
		p.displayGUIChest(p.getInventoryEnderChest());
	}
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		return true;
	}
	@Override
	public ArrayList<String> addTabCompletionOptions(ICommandSender icommandsender, String[] astring)
	{
		return null;
	}
	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}
	@Override
	public int compareTo(Object o)
	{
		return 0;
	}
}