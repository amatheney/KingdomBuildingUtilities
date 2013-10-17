package settlements;

import java.util.Random;
import java.math.*;

public class Owner 
{
	String OwnerName;
	String PlayerName;
	
	public Account balance;
	Skill[] skills;
	
	public Owner()
	{
		this.OwnerName = "NPC";
		this.PlayerName = "GM";
		
		this.balance = new Account();
		this.skills = new Skill[0];
	}
	
	public Owner(String rawCSV, Building[] completeBuildingList)
	{
		//Quink Brodert;DM;2100.7;21;7;2;Bluff:1,Craft:9,Disable Device:2,Intimidate:8,Linguistics:3,
		String[] tokens = rawCSV.split("\\;");
		
		//Init values
		this.balance = new Account();		
		this.skills = new Skill[0];
		
		//Basic strings
		this.OwnerName = RoomUtilities.snipQuotes(tokens[0]);
		this.PlayerName = RoomUtilities.snipQuotes(tokens[1]);
		
		//Incomes - set through private storage container
		this.balance.GP = Float.parseFloat(tokens[2]);
		this.balance.Labor = Integer.parseInt(tokens[3]);
		this.balance.Goods = Integer.parseInt(tokens[4]);
		this.balance.Influence = Integer.parseInt(tokens[5]);
		this.balance.Magic = Integer.parseInt(tokens[6]);
		
		//Split skills, build array, skills are key-value pairs
		String[] rawSkillsList = tokens[7].split("\\,");
		for (int lcv = 0; lcv < rawSkillsList.length; lcv++)
		{
			String KeyValuePair[] = rawSkillsList[lcv].split("\\:");
			String skillName = KeyValuePair[0];
			int value = Integer.parseInt(KeyValuePair[1]);
			
			if (SKILL_ENUM.existsInGPSkillList(skillName))
			{
				Skill toBeAdded = new Skill(skillName, value);
				this.skills = RoomUtilities.expand(this.skills);
				this.skills[this.skills.length-1] = toBeAdded;
			}
			else
				System.out.println("Error in Owner(): " + skillName + " is not a valid skill");
		}
	}
	
	public String toString()
	{
		String returnString = "";
		
		returnString += "   ---Skills---\n";
		returnString += "      ";
		for (int lcv = 0; lcv < skills.length; lcv++)
		{
			returnString += skills[lcv].Name + " +" + skills[lcv].Value;
			if (lcv != skills.length-1)
				 returnString +=  ", "; 
		}
		
		return returnString;
	}
}
