package settlements;

public class Organization 
{
	String name;
	String owner;
	Team[] teams;
	
	BalanceSheet GPEarnable;
	BalanceSheet GoodsEarnable;
	BalanceSheet LaborEarnable;
	BalanceSheet InfluenceEarnable;
	BalanceSheet MagicEarnable;
	
	String Description;
	
	public Organization()
	{
		String name = "";
		String owner = "";
		Team[] teams = new Team[0];
		
		BalanceSheet GPEarnable = new BalanceSheet();
		BalanceSheet GoodsEarnable = new BalanceSheet();
		BalanceSheet LaborEarnable = new BalanceSheet();
		BalanceSheet InfluenceEarnable = new BalanceSheet();
		BalanceSheet MagicEarnable = new BalanceSheet();
		
		String Description = "";
	}
	
	public Organization(Organization input)
	{
		this.name = input.name;
		this.owner = input.owner;
		this.teams = input.teams;
		
		this.GPEarnable = input.GPEarnable;
		this.GoodsEarnable = input.GoodsEarnable;
		this.LaborEarnable = input.LaborEarnable;
		this.InfluenceEarnable = input.InfluenceEarnable;
		this.MagicEarnable = input.MagicEarnable;
		
		this.Description = input.Description;
	}
	
	public Organization(String rawCSV, Team[] completeTeamList)
	{
		String[] tokens = rawCSV.split("\\;");
		
		BalanceSheet GPEarnable = new BalanceSheet();
		BalanceSheet GoodsEarnable = new BalanceSheet();
		BalanceSheet LaborEarnable = new BalanceSheet();
		BalanceSheet InfluenceEarnable = new BalanceSheet();
		BalanceSheet MagicEarnable = new BalanceSheet();
		
		this.name = RoomUtilities.snipQuotes(tokens[0]);
		this.Description = RoomUtilities.snipQuotes(tokens[1]);
		String[] rawTeamList = tokens[2].split(",");
		for (int lcv = 0; lcv < rawTeamList.length; lcv++)
		{
			int spaceIndex = rawTeamList[lcv].indexOf(" ");
			if (spaceIndex == -1)
			{
				System.out.println("Error in Organization Constructor: no space found for quantity of team [" + rawTeamList[lcv] + "]");
			}
			else
			{
				int quantity = Integer.parseInt(rawTeamList[lcv].substring(0, spaceIndex));
				String value = rawTeamList[lcv].substring(spaceIndex);
				int location = RoomUtilities.indexOf(value, completeTeamList);
				if (location == -1)
					System.out.println("Error in Organization Constructor: entyry [" + rawTeamList[lcv] + "]");
				for (int i = 0; i <= quantity; i++)
				{
					this.teams = RoomUtilities.expand(this.teams);
					this.teams[this.teams.length-1] = new Team(completeTeamList[location]);
				}
			}	
		}
	}
	
	public void calculateBalanceSheets()
	{
		this.GPEarnable = generateBalanceSheet("GP");
		this.GoodsEarnable = generateBalanceSheet("Goods");
		this.LaborEarnable = generateBalanceSheet("Labor");
		this.InfluenceEarnable = generateBalanceSheet("Influence");
		this.MagicEarnable = generateBalanceSheet("Magic");
	}
	
	/**Determines if any of the rooms in the supplied array generate gold pieces for income*/
	public boolean isGPEarnable()
	{
		return RoomUtilities.isGPEarnable(this.teams);
	}
	/**Determines if any of the rooms in the supplied array generate goods for income*/
	public boolean isGoodsEarnable()
	{
		return RoomUtilities.isGoodsEarnable(this.teams);
	}
	/**Determines if any of the rooms in the supplied array generate labor for income*/
	public boolean isLaborEarnable()
	{
		return RoomUtilities.isLaborEarnable(this.teams);
	}
	/**Determines if any of the rooms in the supplied array generate influence for income*/
	public boolean isInfluenceEarnable()
	{
		return RoomUtilities.isInfluenceEarnable(this.teams);
	}
	/**Determines if any of the rooms in the supplied array generate magic for income*/
	public boolean isMagicEarnable()
	{
		return RoomUtilities.isMagicEarnable(this.teams);
	}
	/**Determines if any of the rooms in the supplied array generate capital for income*/
	public boolean isCapitalEarnable()
	{
		return RoomUtilities.isCapitalEarnable(this.teams);
	}
	
	/**A helper method to get less verbose output*/
	public String getBalanceSheetDescriptions()
	{
		regenerateBalanceSheets();		//Regenerate in case of changes
		String returnString = "";
		
		returnString += "\n----==Balance Sheets: Max potential earnings==----\n";
		returnString += "\n" + GPEarnable.toString();
		returnString += "\n" + GoodsEarnable.toString();
		returnString += "\n" + LaborEarnable.toString();
		returnString += "\n" + InfluenceEarnable.toString();
		returnString += "\n" + MagicEarnable.toString();
		
		return returnString;
	}
	
	/**Helper method to recreate the balance sheets, in case of changes to the building after declaration (adding rooms)*/
	private void regenerateBalanceSheets()
	{
		this.GPEarnable = generateBalanceSheet("GP");
		this.GoodsEarnable = generateBalanceSheet("Goods");
		this.LaborEarnable = generateBalanceSheet("Labor");
		this.InfluenceEarnable = generateBalanceSheet("Influence");
		this.MagicEarnable = generateBalanceSheet("Magic");
	}

	/**Generates a BalanceSheet class that provides the sum total production of the room array provided*/
	BalanceSheet generateBalanceSheet(String preferredIncome)
	{
		BalanceSheet returnSheet = new BalanceSheet();
		returnSheet.preferredIncome = preferredIncome;	//For toString purposes
		
		//Check overall potentials for capital derivation
		boolean GPEarnableOverall = isGPEarnable();
		boolean GoodsEarnableOverall = isGoodsEarnable();
		boolean InfluenceEarnableOverall = isInfluenceEarnable();
		boolean LaborEarnableOverall = isLaborEarnable();
		boolean MagicEarnableOverall = isMagicEarnable();
		
		for (int lcv = 0; lcv < teams.length; lcv++)
		{
			//Create a temporary 1-element array
			Team[] tempArray = new Team[1];
			tempArray[0] = teams[lcv];
			//Check the earning potential of this particular element
			boolean GPEarnable = RoomUtilities.isGPEarnable(tempArray);
			boolean GoodsEarnable = RoomUtilities.isGoodsEarnable(tempArray);
			boolean InfluenceEarnable = RoomUtilities.isInfluenceEarnable(tempArray);
			boolean LaborEarnable = RoomUtilities.isLaborEarnable(tempArray);
			boolean MagicEarnable = RoomUtilities.isMagicEarnable(tempArray);
			boolean CapitalEarnable = RoomUtilities.isCapitalEarnable(tempArray);
			//Logic is the same, only the variables have been changed, to protect the innocent
			//Check to see if prefferedIncome is available, if so, favor it. Capital, a special resource, can be used to produce any other type that building can produce
			if (preferredIncome == "GP")
			{
				if (GPEarnable)
					returnSheet.GP += teams[lcv].GPEarnings;
				else if (CapitalEarnable)
				{
					if (GPEarnableOverall)
						returnSheet.GP += teams[lcv].CapitalEarnings;
					else
						System.out.println("Error found: For the Room \"" + teams[lcv].Name + "\", GP not earnable for available captial on GP-only request: does this building not generate anything but capital?");
				}
				else if (LaborEarnable)
					returnSheet.Labor += teams[lcv].LaborEarnings;
				else if (MagicEarnable)
					returnSheet.Magic += teams[lcv].MagicEarnings;
				else if (InfluenceEarnable)
					returnSheet.Influence += teams[lcv].InfluenceEarnings;
				else if (GoodsEarnable)
					returnSheet.Goods += teams[lcv].GoodsEarnings;
				else
					returnSheet.GP += 0;
			}
			else if (preferredIncome == "Goods")
			{
				if (GoodsEarnable)
					returnSheet.Goods += teams[lcv].GoodsEarnings;
				else if (CapitalEarnable)
				{
					if (GoodsEarnableOverall)
						returnSheet.Goods += teams[lcv].CapitalEarnings;
					else if (GPEarnableOverall)
						returnSheet.GP += teams[lcv].CapitalEarnings;
					else
						System.out.println("Error found: For the Room \"" + teams[lcv].Name + "\", GP & Goods not earnable for available captial: does this building not generate anything but capital?");
				}
				else if (GPEarnable)
					returnSheet.GP += teams[lcv].GPEarnings;
				else if (LaborEarnable)
					returnSheet.Labor += teams[lcv].LaborEarnings;
				else if (MagicEarnable)
					returnSheet.Magic += teams[lcv].MagicEarnings;
				else if (InfluenceEarnable)
					returnSheet.Influence += teams[lcv].InfluenceEarnings;
				else
					returnSheet.Goods += 0;
			}
			else if (preferredIncome == "Labor")
			{
				if (LaborEarnable)
					returnSheet.Labor += teams[lcv].LaborEarnings;
				else if (CapitalEarnable)
				{
					if (LaborEarnableOverall)
						returnSheet.Labor += teams[lcv].CapitalEarnings;
					else if (GPEarnableOverall)
						returnSheet.GP += teams[lcv].CapitalEarnings;
					else
						System.out.println("Error found: For the Room \"" + teams[lcv].Name + "\", GP & Labor not earnable for available captial: does this building not generate anything but capital?");
				}
				else if (GPEarnable)
					returnSheet.GP += teams[lcv].GPEarnings;
				else if (MagicEarnable)
					returnSheet.Magic += teams[lcv].MagicEarnings;
				else if (InfluenceEarnable)
					returnSheet.Influence += teams[lcv].InfluenceEarnings;
				else
					returnSheet.Labor += 0;
			}
			else if (preferredIncome == "Influence")
			{
				if (InfluenceEarnable)
					returnSheet.Influence += teams[lcv].InfluenceEarnings;
				else if (CapitalEarnable)
				{
					if (InfluenceEarnableOverall)
						returnSheet.Influence += teams[lcv].CapitalEarnings;
					else if (GPEarnableOverall)
						returnSheet.GP += teams[lcv].CapitalEarnings;
					else
						System.out.println("Error found: For the Room \"" + teams[lcv].Name + "\", GP & Influence not earnable for available captial: does this building not generate anything but capital?");
				}
				else if (GPEarnable)
					returnSheet.GP += teams[lcv].GPEarnings;
				else if (GoodsEarnable)
					returnSheet.Goods += teams[lcv].GoodsEarnings;
				else if (MagicEarnable)
					returnSheet.Magic += teams[lcv].MagicEarnings;
				else if (LaborEarnable)
					returnSheet.Labor += teams[lcv].LaborEarnings;
				else
					returnSheet.Influence += 0;
			}
			else if (preferredIncome == "Magic")
			{
				if (MagicEarnable)
					returnSheet.Magic += teams[lcv].MagicEarnings;
				else if (CapitalEarnable)
				{
					if (MagicEarnableOverall)
						returnSheet.Magic += teams[lcv].CapitalEarnings;
					else if (GPEarnableOverall)
						returnSheet.GP += teams[lcv].CapitalEarnings;
					else
						System.out.println("Error found: For the Room \"" + teams[lcv].Name + "\", GP & Magic not earnable for available captial: does this building not generate anything but capital?");
				}
				else if (GPEarnable)
					returnSheet.GP += teams[lcv].GPEarnings;
				else if (GoodsEarnable)
					returnSheet.Goods += teams[lcv].GoodsEarnings;
				else if (InfluenceEarnable)
					returnSheet.Influence += teams[lcv].InfluenceEarnings;
				else if (LaborEarnable)
					returnSheet.Labor += teams[lcv].LaborEarnings;
				else
					returnSheet.Magic += 0;
			}
			else
			{
				System.out.println("Error: Invalid preferred input provided");
			}
		}
		return returnSheet;
	}
}
