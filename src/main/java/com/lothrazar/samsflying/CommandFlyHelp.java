package com.lothrazar.samsflying;
 
import java.util.ArrayList;  

import com.lothrazar.util.Chat;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer; 
import net.minecraft.util.ChatComponentTranslation;

public class CommandFlyHelp implements ICommand
{ 
	  private ArrayList<String> aliases;
	  public CommandFlyHelp()
	  {
	      this.aliases = new ArrayList<String>();
	      this.aliases.add("fh");
	      this.aliases.add("flyhelp"); 
	      this.aliases.add("flyhelp");  
	  }
 
	  @Override
	  public String getCommandName()
	  {
	    return "flyhelp";
	  }

	  @Override
	  public String getCommandUsage(ICommandSender icommandsender)
	  {
	    return "flyhelp";
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
  
		  Chat.addMessage(p,"Expensive flying is enabled if:");
		  
		  String strdiff = "";
		  switch(SurvivalFlyingMod.difficultyRequiredToFly)//iknow i know, there is a better way maybe with EnumDifficulty ....
		  {
		  	case 0: strdiff = "Peaceful";break;
		  	case 1: strdiff = "Easy"; break;
		  	case 2: strdiff = "Normal"; break;
		  	case 3: strdiff = "Hard"; break;
		  }
		  
		  Chat.addMessage(p, "- Your world difficulty is "+strdiff+" ("+SurvivalFlyingMod.difficultyRequiredToFly+") or greater"); 
		   
		  if(SurvivalFlyingMod.NoArmorOnly) Chat.addMessage(p, "- You are not wearing armor");
		  if(SurvivalFlyingMod.cannotFlyWhileBurning) Chat.addMessage(p, "- You are not on fire");
		    
		  if(SurvivalFlyingMod.cannotFlyAtNight) Chat.addMessage(p, "- It is not night");
		  if(SurvivalFlyingMod.cannotFlyInRain) Chat.addMessage(p, "- It is not raining");
		  
		  double hearts = SurvivalFlyingMod.StartFlyingHealth / 2;
		  double hunger = SurvivalFlyingMod.StartFlyingHunger / 2;
		  
		  Chat.addMessage(p, "- You have at least "+hearts+" hearts , and at least "+hunger+" hunger");
  
		  Chat.addMessage(p, "- You have at least "+SurvivalFlyingMod.StartFlyingLevel+" levels");  
		  
		  //no message needed for xp drain
	 
	  }
	   

	  @Override
	  public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	  { 
	     return true;
	  }

	  @Override
	  public ArrayList<String> addTabCompletionOptions(ICommandSender icommandsender,  String[] astring)
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