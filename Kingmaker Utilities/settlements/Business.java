package settlements;

import java.util.Random;
import java.math.*;

public class Business 
{
	Owner owner;
	Manager manager;
	Organization personnel;
	Building[] properties;
	
	Random RNG;

	public Business()
	{
		this.properties = new Building[0];
		RNG = new Random();
	}
	
	public Business(Business clone)
	{
		this.owner = clone.owner;
		this.manager = clone.manager;
		this.personnel = clone.personnel;
		assignBuildings(clone.properties);
	}
	
	public Business(Owner owner, Manager manager, Organization personnel, Building[] properties)
	{
		this.owner = owner;
		this.manager = manager;
		this.personnel = personnel;
		assignBuildings(properties);
	}
	
	public Business(Owner owner, Organization personnel, Building[] properties)
	{
		this.owner = owner;
		this.manager = null;
		this.personnel = personnel;
		assignBuildings(properties);
	}
	
	public void assignBuildings(Building[] properties)
	{
		this.properties = properties;
		for (int lcv = 0; lcv < properties.length; lcv++)
		{
			this.properties[lcv] = new Building(properties[lcv]);
		}
		assignOwner();
	}
	
	public void assignOwner()
	{
		for (int lcv = 0; lcv < properties.length; lcv++)
		{
			properties[lcv].owner = owner.OwnerName;
		}
	}

/*
 * 		//Split buildings, build array, buildings are fully-qualified copies
		String[] rawBuildingList = tokens[7].split("\\,");
		//System.out.println("---RawBuildingList is " + rawBuildingList.length + " elements long");
		for (int lcv = 0; lcv < rawBuildingList.length; lcv++)
		{
			this.properties = RoomUtilities.expand(this.properties);
			Building toBeAdded = new Building(completeBuildingList[RoomUtilities.indexOf(rawBuildingList[lcv], completeBuildingList)]);
			//Set the owner
			toBeAdded.owner = OwnerName;
			//System.out.println("Data integrity has been lost, maybe: " + completeBuildingList[RoomUtilities.indexOf(rawBuildingList[lcv], completeBuildingList)].owner);
			this.properties[this.properties.length-1] = toBeAdded;		                
		}
		*/

	public String getHoldings()
	{
		String returnString = "\n";
		
		returnString += "---------===" + this.owner.OwnerName + " (" + this.owner.PlayerName + ")===---------\n";
		returnString += "   ----Personal Assets, Capital and Employees----\n";
		returnString += "      ---Liquid Assets---\n";
		returnString += this.owner.balance.getTotals();
		returnString += "      ---Capital Assets---\n";
		for (int lcv = 0; lcv < properties.length; lcv++)
		{
			returnString += "         " + properties[lcv].name + "\n"; 
		}
		if (manager != null)
			returnString += "      ---Manager: " + manager.Name + "---\n";
		returnString += "      ---Organization: " + personnel.name + "---\n";
		for (int lcv = 0; lcv < personnel.teams.length; lcv++)
		{
			returnString += "         " + personnel.teams[lcv].Name + "\n";
		}
		
		return returnString;
	}
	
	private int getUsableSkillValue(boolean UseManager, String earningsDesired)
	{
		int returnValue = 0;
		
		Skill[] validList;
		if (UseManager)
			validList = manager.Skills;
		else
			validList = owner.skills;
		
		for (int lcv = 0; lcv < validList.length; lcv++)
		{
			if (earningsDesired.equals("GP"))
			{
				if (SKILL_ENUM.existsInGPSkillList(validList[lcv].Name))
				{
					if (validList[lcv].Value > returnValue)
						returnValue = validList[lcv].Value;
				}
			}
			if (earningsDesired.equals("GOODS"))
			{
				if (SKILL_ENUM.existsInGoodsSkillList(validList[lcv].Name))
				{
					if (validList[lcv].Value > returnValue)
						returnValue = validList[lcv].Value;
				}
			}
			if (earningsDesired.equals("LABOR"))
			{
				if (SKILL_ENUM.existsInLaborSkillList(validList[lcv].Name))
				{
					if (validList[lcv].Value > returnValue)
						returnValue = validList[lcv].Value;
				}
			}
			if (earningsDesired.equals("INFLUENCE"))
			{
				if (SKILL_ENUM.existsInInfluenceSkillList(validList[lcv].Name))
				{
					if (validList[lcv].Value > returnValue)
						returnValue = validList[lcv].Value;
				}
			}
			if (earningsDesired.equals("MAGIC"))
			{
				if (SKILL_ENUM.existsInMagicSkillList(validList[lcv].Name))
				{
					if (validList[lcv].Value > returnValue)
						returnValue = validList[lcv].Value;
				}
			}
		}
		
		return returnValue;
	}
	
	public Account generateDailyIncome(String PreferredIncome, boolean UseManager)
	{
		Account returnAccount = new Account();
		
		for (int lcv = 0; lcv < this.properties.length; lcv++)
		{
			BalanceSheet tempTotals = this.properties[lcv].generateBalanceSheet(PreferredIncome);
			if (this.properties[lcv].isGPEarnable())
			{
				int GPd20 = RNG.nextInt(20) + 1 + getUsableSkillValue(UseManager, "GP");
				float result = (GPd20 + tempTotals.GP)/ 10;
				returnAccount.GP += result;
			}
			if (this.properties[lcv].isLaborEarnable())
			{
				int Labord20 = RNG.nextInt(20) + 1 + getUsableSkillValue(UseManager, "LABOR");
				returnAccount.Labor += (int)(Labord20 + tempTotals.Labor) / 10;
			}
			if (this.properties[lcv].isInfluenceEarnable())
			{
				int Influenced20 = RNG.nextInt(20) + 1 + getUsableSkillValue(UseManager, "INFLUENCE");
				returnAccount.Influence += (int)(Influenced20 + tempTotals.Influence) / 10;
			}
			if (this.properties[lcv].isGoodsEarnable())
			{
				int Goodsd20 = RNG.nextInt(20) + 1 + getUsableSkillValue(UseManager, "GOODS");
				returnAccount.Goods += (int)(Goodsd20 + tempTotals.Goods) / 10;
			}
			if (this.properties[lcv].isMagicEarnable())
			{
				int Magicd20 = RNG.nextInt(20) + 1 + getUsableSkillValue(UseManager, "MAGIC");
				returnAccount.Magic += (int)(Magicd20 + tempTotals.Magic) / 10;
			}	
		}
		//Assign the profits / losses to this owner
		owner.balance.combineAccounts(returnAccount);
		return returnAccount;
	}
	
	/**A workweek in pathfinder is 6 days - no one works 7 days a week, nor is a business open 7 days a week*/
	public Account generateMultiDayIncome(String PreferredIncome, int numDays, boolean attended)
	{
		Account returnAccount = new Account();
		for (int lcv = 0; lcv < numDays; lcv++)
		{
			if (lcv % 7 != 0)
			{
				if (lcv > 7)
					returnAccount = returnAccount.combineAccounts(generateDailyIncome(PreferredIncome, true));
				else
					returnAccount = returnAccount.combineAccounts(generateDailyIncome(PreferredIncome, false));
			}
		}
		
		int numWeeks = numDays / 7;
		if (!attended && numWeeks > 0 && manager == null)
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
//		Assign the profits / losses to this owner
		owner.balance.combineAccounts(returnAccount);
		return returnAccount;
	}

	public String earningsReport(String PreferredIncome, int numDays, boolean attended)
	{
		String returnString = "";
		
		returnString += "\nGenerating income for " + numDays + ", favoring production of " + PreferredIncome + "...\n";
		if (!attended && manager == null)
			returnString += "**Warning: You are not attending this business, and income after the first week will be penalized\n";
		Account temp = generateMultiDayIncome(PreferredIncome, numDays, attended);
		returnString += "---Incomes Generated---\n";
		returnString += "   GP: " + temp.GP;
		returnString += "\n   Goods: " + temp.Goods;
		returnString += "\n   Influence: " + temp.Influence;
		returnString += "\n   Labor: " + temp.Labor;
		returnString += "\n   Magic: " + temp.Magic;
		
		return returnString;
	}
}
