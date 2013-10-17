package settlements;

import java.util.Random;
import java.math.*;

public class Business 
{
	Owner owner;
	Organization personnel;
	Building[] properties;
	
	Random RNG;

	public Business()
	{
		this.properties = new Building[0];
		RNG = new Random();
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
}
