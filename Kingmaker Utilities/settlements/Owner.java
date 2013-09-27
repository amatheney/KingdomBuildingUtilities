package settlements;

import java.util.Random;
import java.math.*;

public class Owner 
{
	String OwnerName;
	String PlayerName;
	
	Account balance;
	Building[] properties;
	Skill[] skills;
	
	Random RNG;
	
	public Owner()
	{
		this.OwnerName = "NPC";
		this.PlayerName = "GM";
		
		this.balance = new Account();
		this.properties = new Building[0];
		this.skills = new Skill[0];
		RNG = new Random();
	}
	
	public Owner(String rawCSV, Building[] completeBuildingList)
	{
		//Quink Brodert;DM;2100.7;21;7;2;Cathedral,Jail,Castle,House;Bluff:1,Craft:9,Disable Device:2,Intimidate:8,Linguistics:3,
		String[] tokens = rawCSV.split("\\;");
		
		//Init values
		this.balance = new Account();
		this.properties = new Building[0];
		this.skills = new Skill[0];
		RNG = new Random();
		
		//Basic strings
		this.OwnerName = RoomUtilities.snipQuotes(tokens[0]);
		this.PlayerName = RoomUtilities.snipQuotes(tokens[1]);
		
		//Incomes - set through private storage container
		this.balance.GP = Float.parseFloat(tokens[2]);
		this.balance.Labor = Integer.parseInt(tokens[3]);
		this.balance.Goods = Integer.parseInt(tokens[4]);
		this.balance.Influence = Integer.parseInt(tokens[5]);
		this.balance.Magic = Integer.parseInt(tokens[6]);
		
		//Split buildings, build array, buildings are fully-qualified copies
		String[] rawBuildingList = tokens[7].split("\\,");
		for (int lcv = 0; lcv < rawBuildingList.length; lcv++)
		{
			this.properties = RoomUtilities.expand(this.properties);
			Building toBeAdded = new Building();
			toBeAdded = completeBuildingList[RoomUtilities.indexOf(rawBuildingList[lcv], completeBuildingList)];
			this.properties[this.properties.length-1] = toBeAdded;		                
		}
		
		//Split skills, build array, skills are key-value pairs
		String[] rawSkillsList = tokens[8].split("\\,");
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
	
	public String getHoldings()
	{
		String returnString = "";
		
		returnString += "---------===" + OwnerName + " (" + PlayerName + ")===---------\n";
		returnString += "----Personal Assets, Capital and Employees----\n";
		returnString += "--Liquid Assets--\n";
		returnString += balance.toString();
		returnString += "--Capital Assets--\n";
		for (int lcv = 0; lcv < properties.length; lcv++)
		{
			returnString += "   " + properties[lcv].name + "\n"; 
		}
		
		return returnString;
	}
	
	public Account generateDailyIncome(String PreferredIncome)
	{
		Account returnAccount = new Account();
		
		for (int lcv = 0; lcv < this.properties.length; lcv++)
		{
			BalanceSheet tempTotals = this.properties[lcv].generateBalanceSheet(PreferredIncome);
			if (this.properties[lcv].isGPEarnable())
			{
				int GPd20 = RNG.nextInt(20) + 1;
				float result = (GPd20 + tempTotals.GP)/ 10;
				returnAccount.GP += result;
			}
			if (this.properties[lcv].isLaborEarnable())
			{
				int Labord20 = RNG.nextInt(20) + 1;
				returnAccount.Labor += (int)(Labord20 + tempTotals.Labor) / 10;
			}
			if (this.properties[lcv].isInfluenceEarnable())
			{
				int Influenced20 = RNG.nextInt(20) + 1;
				returnAccount.Influence += (int)(Influenced20 + tempTotals.Influence) / 10;
			}
			if (this.properties[lcv].isGoodsEarnable())
			{
				int Goodsd20 = RNG.nextInt(20) + 1;
				returnAccount.Goods += (int)(Goodsd20 + tempTotals.Goods) / 10;
			}
			if (this.properties[lcv].isMagicEarnable())
			{
				int Magicd20 = RNG.nextInt(20) + 1;
				returnAccount.Magic += (int)(Magicd20 + tempTotals.Magic) / 10;
			}	
		}	
		return returnAccount;
	}
	
	/**A workweek in pathfinder is 6 days - no one works 7 days a week, nor is a business open 7 days a week*/
	public Account generateMultiDayIncome(String PreferredIncome, int numDays, boolean attended)
	{
		Account returnAccount = new Account();
		for (int lcv = 0; lcv < numDays; lcv++)
		{
			returnAccount = returnAccount.combineAccounts(generateDailyIncome(PreferredIncome));
		}
		
		int numWeeks = numDays / 7;
		if (!attended && numWeeks > 0)
		{
			//First week suffers no penalty
			for (int lcv = 0; lcv < numWeeks; lcv++)
			{
				returnAccount.GP -= 1;
				returnAccount.Goods -= 1;
				returnAccount.Labor -= 1;
				returnAccount.Influence -=1;
				returnAccount.Magic -= 1;
			}
			//Can't lose money on the income phase
			if (returnAccount.GP < 0)
				returnAccount.GP = 0;
			if (returnAccount.Goods < 0)
				returnAccount.Goods = 0;
			if (returnAccount.Labor < 0)
				returnAccount.Labor = 0;
			if (returnAccount.Influence < 0)
				returnAccount.Influence = 0;
			if (returnAccount.Magic < 0)
				returnAccount.Magic = 0;
		}
		return returnAccount;
	}
	
	public String toString()
	{
		String returnString = "";
		
		returnString += getHoldings();
		returnString += "--Skills--\n";
		for (int lcv = 0; lcv < skills.length; lcv++)
		{
			returnString += "   " + skills[lcv].Name + "\n"; 
		}
		
		return returnString;
	}
	
	private class Account
	{
		float GP;
		int Labor;
		int Influence;
		int Goods;
		int Magic;
		
		private Account()
		{
			GP = 0;
			Labor = 0;
			Influence = 0;
			Goods = 0;
			Magic = 0;
		}
		
		private Account(float GP, int Labor, int Influence, int Goods, int Magic)
		{
			this.GP = GP;
			this.Labor = Labor;
			this.Influence = Influence;
			this.Goods = Goods;
			this.Magic = Magic;
		}
		
		private String getTotals()
		{
			String returnString = "";
			
			returnString += "GP        : " + GP + "\n";
			returnString += "Labor     : " + Labor + "\n";
			returnString += "Influence : " + Influence + "\n";
			returnString += "Goods     : " + Goods + "\n";
			returnString += "Magic     : " + Magic + "\n";
			
			return returnString;
		}
		
		private Account combineAccounts(Account toCombine)
		{
			Account returnAccount = new Account();
			
			returnAccount.GP = this.GP + toCombine.GP;
			returnAccount.Labor = this.Labor + toCombine.Labor;
			returnAccount.Influence = this.Influence + toCombine.Influence;
			returnAccount.Goods = this.Goods + toCombine.Goods;
			returnAccount.Magic = this.Magic + toCombine.Magic;
			
			return returnAccount;
		}
	}
}
