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
		String[] tokens = rawCSV.Split(';');
		
		//Init values
		this.balance = new Account();		
		this.skills = new Skill[0];
		
		//Basic string s
		this.OwnerName = RoomUtilities.snipQuotes(tokens[0]);
		this.PlayerName = RoomUtilities.snipQuotes(tokens[1]);
		
		//Incomes - set through private storage container
		this.balance.GP = float.Parse(tokens[2]);
		this.balance.Labor = Convert.ToInt32(tokens[3]);
		this.balance.Goods = Convert.ToInt32(tokens[4]);
		this.balance.Influence = Convert.ToInt32(tokens[5]);
		this.balance.Magic = Convert.ToInt32(tokens[6]);
		
		//Split skills, build array, skills are key-value pairs
		String[] rawSkillsList = tokens[7].Split(',');
		for (int lcv = 0; lcv < rawSkillsList.Length; lcv++)
		{
			String KeyValuePair[] = rawSkillsList[lcv].Split(':');
			String skillName = KeyValuePair[0];
			int value = Convert.ToInt32(KeyValuePair[1]);
			
			if (SKILL_ENUM.existsInGPSkillList(skillName))
			{
				Skill toBeAdded = new Skill(skillName, value);
				this.skills = RoomUtilities.expand(this.skills);
				this.skills[this.skills.Length-1] = toBeAdded;
			}
			else
				Console.Out.WriteLine("Error in Owner(): " + skillName + " is not a valid skill");
		}
	}
	
	public string  toString()
	{
		String returnString = "";
		
		returnString += "   ---Skills---\n";
		returnString += "      ";
		for (int lcv = 0; lcv < skills.Length; lcv++)
		{
			returnString += skills[lcv].Name + " +" + skills[lcv].Value;
			if (lcv != skills.Length-1)
				 returnString +=  ", "; 
		}
		
		return returnString;
	}
}
