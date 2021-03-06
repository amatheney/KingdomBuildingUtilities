package settlements;


/**Container class used to store all the attributes of a Building, from the rooms within to the modifiers on the settlement and kingdom in which the building resides*/
public class Building 
{
	String name;
	String owner;
	Room[] rooms;
	
	BalanceSheet GPEarnable;
	BalanceSheet GoodsEarnable;
	BalanceSheet LaborEarnable;
	BalanceSheet InfluenceEarnable;
	BalanceSheet MagicEarnable;
	
	SettlementMods SettlementModifiers;
	
	int kingdomLoyaltyModifier;
	int kingdomStabilityModifier;
	int kingdomUnrestModifier;
	int kingdomConsumptionModifier;
	int kingdomgDefenseModifier;
	int kingdomEconomyModifier;
	int kingdomFameModifier;
	
	int minorItemsProduced;
	int mediumItemsProduced;
	int majorItemsProduced;
	
	int lotSize;
	int BPCost;
	String Discount;
	String Limits;
	String upgradesFrom;
	String upgradesTo;
	String Benefit;
	String Description;
	
	FurnishingsAndTraps[] Furnishings;	//TODO: Traps
	
	/**Generic constructor*/ 
	public Building()
	{
		this.name = "";
		this.owner = "";
		this.rooms = new Room[0];
		
		this.GPEarnable = new BalanceSheet();
		this.GoodsEarnable = new BalanceSheet();
		this.LaborEarnable = new BalanceSheet();
		this.InfluenceEarnable = new BalanceSheet();
		this.MagicEarnable = new BalanceSheet();
		
		this.SettlementModifiers = new SettlementMods();
		
		this.minorItemsProduced = 0;
		this.majorItemsProduced = 0;
		this.mediumItemsProduced = 0;
		
		this.kingdomLoyaltyModifier = 0;
		this.kingdomStabilityModifier = 0;
		this.kingdomUnrestModifier = 0;
		this.kingdomConsumptionModifier = 0;
		this.kingdomgDefenseModifier = 0;
		this.kingdomEconomyModifier = 0;
		this.kingdomFameModifier = 0;
		
		this.lotSize = 0;
		this.BPCost = 0;
		this.Discount = "N/A";
		this.Limits = "N/A";
		this.upgradesFrom = "N/A";
		this.upgradesTo = "N/A";
		this.Benefit = "N/A";
		this.Description = "N/A";
		this.Furnishings = new FurnishingsAndTraps[0];
	}
	
	public Building(Building that)
	{
		this.name = that.name;
		this.owner = that.owner;
		this.rooms = that.rooms;
		
		this.GPEarnable = that.GPEarnable;
		this.GoodsEarnable = that.GoodsEarnable;
		this.LaborEarnable = that.LaborEarnable;
		this.InfluenceEarnable = that.InfluenceEarnable;
		this.MagicEarnable = that.MagicEarnable;
		
		this.SettlementModifiers = that.SettlementModifiers;
		
		this.minorItemsProduced = that.minorItemsProduced;
		this.majorItemsProduced = that.majorItemsProduced;
		this.mediumItemsProduced = that.mediumItemsProduced;
		
		this.kingdomLoyaltyModifier = that.kingdomLoyaltyModifier;
		this.kingdomStabilityModifier = that.kingdomStabilityModifier;
		this.kingdomUnrestModifier = that.kingdomUnrestModifier;
		this.kingdomConsumptionModifier = that.kingdomConsumptionModifier;
		this.kingdomgDefenseModifier = that.kingdomgDefenseModifier;
		this.kingdomEconomyModifier = that.kingdomEconomyModifier;
		this.kingdomFameModifier = that.kingdomFameModifier;
		
		this.lotSize = that.lotSize;
		this.BPCost = that.BPCost;
		this.Discount = that.Discount;
		this.Limits = that.Limits;
		this.upgradesFrom = that.upgradesFrom;
		this.upgradesTo = that.upgradesTo;
		this.Benefit = that.Benefit;
		this.Description = that.Description;
		this.Furnishings = that.Furnishings;
	}
	
	/**Constructor that takes a raw semicolon-delimited string , and populates the building with copies of rooms and furnishings found within.*/
	public Building(String rawCSV, Room[] completeRoomList, FurnishingsAndTraps[] completeFurnishingList)
	{
		this.GPEarnable = new BalanceSheet();				//Derived
		this.GoodsEarnable = new BalanceSheet();			//Derived
		this.LaborEarnable = new BalanceSheet();			//Derived
		this.InfluenceEarnable = new BalanceSheet();		//Derived
		this.MagicEarnable = new BalanceSheet();			//Derived
		
		String[] tokens = rawCSV.Split(';');
		
		this.name = RoomUtilities.snipQuotes(tokens[0]);										//BuildingName 	[0]
		this.owner = "";
		this.Benefit = RoomUtilities.snipQuotes(tokens[1]);										//Benefit 		[1]
		this.Description = RoomUtilities.snipQuotes(tokens[2]);									//Description	[2]
		String rawRooms = RoomUtilities.snipQuotes(tokens[25]);
		this.rooms = populateBuildingFromRawString(rawRooms, completeRoomList);					//Rooms			[25]
		//Console.Out.WriteLine("Now we're back in the constructor, and rooms is " + this.rooms.Length + " elements long");
		
		int settlementCorruptionModifier = Convert.ToInt32(tokens[3]);							//Corruption	[3]
		int settlementCrimeModifier  = Convert.ToInt32(tokens[4]);								//Crime			[4]
		int settlementLawModifier = Convert.ToInt32(tokens[6]);								//Law			[6]
		int settlementLoreModifier  = Convert.ToInt32(tokens[7]);								//Lore			[7]
		int settlementProductivityModifier = Convert.ToInt32(tokens[8]);						//Productivity	[8]
		int settlementSocietyModifier  = Convert.ToInt32(tokens[9]);							//Society		[9]		
		int settlementDangerModifier  = Convert.ToInt32(tokens[10]);							//Danger		[10]
		int settlementBaseValueModifier  = Convert.ToInt32(tokens[17]);						//BaseValue		[17]
		
		SettlementModifiers = new SettlementMods(settlementCorruptionModifier,settlementCrimeModifier,settlementLawModifier,settlementLoreModifier, 
				settlementSocietyModifier,settlementDangerModifier,settlementProductivityModifier,settlementBaseValueModifier);
		
		this.minorItemsProduced  = Convert.ToInt32(tokens[18]);								//MinorItems	[18]
		this.majorItemsProduced  = Convert.ToInt32(tokens[20]);								//MajorItems	[20]
		this.mediumItemsProduced  = Convert.ToInt32(tokens[19]);								//MediumItems	[19]
		
		this.kingdomLoyaltyModifier  = Convert.ToInt32(tokens[12]);							//Loyalty		[12]
		this.kingdomStabilityModifier  = Convert.ToInt32(tokens[13]);							//Stability		[13]
		this.kingdomUnrestModifier  = Convert.ToInt32(tokens[14]);								//Unrest		[14]
		this.kingdomConsumptionModifier  = Convert.ToInt32(tokens[15]);						//Consumption	[15]
		this.kingdomgDefenseModifier  = Convert.ToInt32(tokens[16]);							//Defense		[16]
		this.kingdomEconomyModifier  = Convert.ToInt32(tokens[5]);								//Economy		[5]
		this.kingdomFameModifier = Convert.ToInt32(tokens[11]);								//Fame			[11]
		
		this.lotSize  = Convert.ToInt32(tokens[27]);											//Lot Size		[27]
		this.BPCost  = Convert.ToInt32(tokens[28]);											//BP Cost		[28]
		this.Discount = RoomUtilities.snipQuotes(tokens[23]);									//Discount		[23]
		this.Limits = RoomUtilities.snipQuotes(tokens[24]);										//Limit			[24]
		this.upgradesFrom = RoomUtilities.snipQuotes(tokens[21]);								//UpgradesFrom	[21]
		this.upgradesTo = RoomUtilities.snipQuotes(tokens[22]);									//UpgradesTo	[22]
		this.Furnishings = generateFurnishings(RoomUtilities.snipQuotes(tokens[26]), 
				completeFurnishingList);														//Furnishings	[26]
		
		//Now begin properly populating the class:
		//regenerateSettlementMods();
		regenerateBalanceSheets();
	}
	
	/**Populates the furnishings in the building, using a fully-qualified list of furnishings*/
	private FurnishingsAndTraps[] generateFurnishings(String rawCSV, FurnishingsAndTraps[] completeFurnishingList)
	{
		//Create return array
		FurnishingsAndTraps[] returnList = new FurnishingsAndTraps[0];
		if (!(rawCSV.Equals("N/A")))									//Only proceed if there are items to parse through
		{
			//A tuple is a matched pair of variables - in this case, it is a Type:Associated Room matching
			String[] tuples = rawCSV.split("\\/");
			//Loop over matched pair, calling generic constructor. In this case, the format is <Type>:<Associated Room>/<Type>:<Associated Room>/etc...
			FurnishingsAndTraps toBeAdded;								//Throwaway object to populate and then add to array
			for (int lcv = 0; lcv < tuples.Length; lcv++)				//For every furnishing parsed (the /'s)
			{
				String[] tuple = rawCSV.Split(':');					//Split into types and associated rooms
				String type = tuple[0];
				String associatedRoom = tuple[1];
				//We need to remove extraneous /'s from the array, due to wierd split logic
				int indexOfSlash = associatedRoom.IndexOf("/");			
				if (indexOfSlash > 0)									//Only remove /'s if they're actually found in the string 
					associatedRoom = associatedRoom.Substring(0, indexOfSlash);
				toBeAdded = new FurnishingsAndTraps();					//Reset placeholder
				
				//Now we'll loop over the already-qualified list of furnishings				
				for (int innerlcv = 0; innerlcv < completeFurnishingList.Length; innerlcv++)
				{
					//Compare complete furnishing list with our string  name, a match means we're done.
					if (completeFurnishingList[innerlcv].Name.Equals(type))
					{
						toBeAdded = completeFurnishingList[innerlcv];	//Map over basic elements
						toBeAdded.associatedRoom = associatedRoom;		//Set associated room value
					}
				}
				if (!(toBeAdded.Name.Equals("")))		//If NOT blank (element found in completeFurnishingList
				{
					returnList = RoomUtilities.expand(toBeAdded, returnList);
					returnList[returnList.Length-1] = toBeAdded;
				}
			}
		}
		return returnList;
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
	
	/**Helper method to recreate the settlement modifiers, in case of changes to the building after declaration (adding rooms)*/
	private SettlementMods regenerateSettlementMods()
	{
		//Pull values
		int settlementCorruptionModifier = SettlementModifiers.settlementCorruptionModifier;
		int settlementCrimeModifier = SettlementModifiers.settlementCrimeModifier;
		int settlementProductivityModifier = SettlementModifiers.settlementProductivityModifier;
		int settlementLawModifier = SettlementModifiers.settlementLawModifier;
		int settlementLoreModifier = SettlementModifiers.settlementLoreModifier;
		int settlementSocietyModifier = SettlementModifiers.settlementSocietyModifier;
		int settlementDangerModifier = SettlementModifiers.settlementDangerModifier;
		int settlementBaseValueModifier = SettlementModifiers.settlementBaseValueModifier;
		
		//Now pull the settlement mods, if any
		for (int lcv = 0; lcv < rooms.Length; lcv++)
		{
			//Console.Out.WriteLine("LCV = " + lcv);
			//Console.Out.WriteLine("Rooms Length is " + rooms.Length);
			//Console.Out.WriteLine("For the following room: " + rooms[lcv].Name);
			//Console.Out.WriteLine(rooms[lcv].toString());
			
			String settlementMods = rooms[lcv].SettlementMods;
			settlementMods = RoomUtilities.snipQuotes(settlementMods);
			if (!settlementMods.Equals("N/A"))
			{
				String[] tokens = settlementMods.Split(',');
				for (int innerLCV = 0; innerLCV < tokens.Length; innerLCV++)
				{
					//Initial declarations
					int indexValue;
					String settlementModifier = "";
					String modifierValue = "";
					bool negativeValue = false;
					
					//Check for positive modifiers
					indexValue = tokens[innerLCV].IndexOf("+"); //find index of +
					if (indexValue < 0)
					{
						//That failed, so lets check for negative modifiers:
						indexValue = tokens[innerLCV].IndexOf("-"); //find index of -
						if (indexValue < 0)
						{
							Console.Out.WriteLine("Error encountered in Building Constructor: settlement modifiers cannot be parsed for the following entry: " + settlementMods);
						}
						else
						{
							negativeValue = true;
						}
					}
					
					//Now pull the values
					if (indexValue > 0)
					{
						settlementModifier = tokens[innerLCV].Substring(0, indexValue);
						modifierValue = tokens[innerLCV].Substring(indexValue);
						//remove whitespace
						settlementModifier = settlementModifier.replaceAll("\\s+","");
						modifierValue = modifierValue.replaceAll("\\s+","");
						//Now we need to set the appropriate modifier
						modifierValue = modifierValue.Substring(1); //Snip the leading symbol, we should know its sign by now.
						int modValue = Convert.ToInt32(modifierValue);
						if (negativeValue)
							modValue = modValue * -1;	//Flip if negative
						if (settlementModifier.Equals("Corruption")) 
							settlementCorruptionModifier += modValue;
						else if (settlementModifier.Equals("Crime")) 
				            settlementCrimeModifier += modValue;
						else if (settlementModifier.Equals("Productivity")) 
				            settlementProductivityModifier += modValue;
						else if (settlementModifier.Equals("Law")) 
				            settlementLawModifier += modValue;
						else if (settlementModifier.Equals("Lore")) 
				            settlementLoreModifier += modValue;
						else if (settlementModifier.Equals("Society")) 
				            settlementSocietyModifier += modValue;
						else if (settlementModifier.Equals("Danger"))
				            settlementDangerModifier += modValue;
						else
						{
							Console.Out.WriteLine("Error Encountered: settlement modifier \"" + settlementModifier + "\" could not be resolved.");
						} 
					}
				}
			}
		}
		//Return a settlement mods - a new object created with the variables calculated above
		return new SettlementMods(settlementCorruptionModifier,settlementCrimeModifier,settlementLawModifier,settlementLoreModifier, 
				settlementSocietyModifier,settlementDangerModifier,settlementProductivityModifier,settlementBaseValueModifier);
	}
	
	public void setOwnwer(String name)
	{
		this.owner = name;
	}
	
	public string  toString()
	{
		String returnString = "\n";
		
		//First, some basic info:
		returnString += "----==Building: " + this.name + "==----\n";
		if (!(owner.Equals("")))
			returnString += "Owned by " + owner + "\n";
		if (!(Benefit.Equals("N/A")))
			returnString += "Benefit: " + Benefit + "\n";
		returnString += "Description: " + Description + "\n";
		if (!(upgradesFrom.Equals("N/A")))
			returnString += "Upgrades From: " + upgradesFrom + "\n";
		if (!(upgradesTo.Equals("N/A")))
			returnString += "Upgrades To: " + upgradesTo + "\n";

		//Helper methods for selective & more cogent output later
		returnString += getBalanceSheetDescriptions();
		returnString += getSettlementDescriptions();
		returnString += getKingdomDescriptions();
		returnString += getRoomDescriptions();
		returnString += getFurnishingsDescriptions();
		
		return returnString;
	}
	
	/**A helper method to get less verbose output*/
	public string  limitedBuildingDescription()
	{
		String returnString = "\n";
		
		//First, some basic info:
		returnString += "----==Building: " + this.name + "==----\n";
		if (!(Benefit.Equals("N/A")))
			returnString += "Benefit: " + Benefit + "\n";
		returnString += "Description: " + Description + "\n";
		if (!(upgradesFrom.Equals("N/A")))
			returnString += "Upgrades From: " + upgradesFrom + "\n";
		if (!(upgradesTo.Equals("N/A")))
			returnString += "Upgrades To: " + upgradesTo + "\n";
		
		//Helper methods for selective & more cogent output later
		returnString += getBalanceSheetDescriptions();
		returnString += getSettlementDescriptions();
		returnString += getKingdomDescriptions();
		
		return returnString;
	}
	
	/**Determines if any of the rooms in the supplied array generate gold pieces for income*/
	public bool isGPEarnable()
	{
		return RoomUtilities.isGPEarnable(this.rooms);
	}
	/**Determines if any of the rooms in the supplied array generate goods for income*/
	public bool isGoodsEarnable()
	{
		return RoomUtilities.isGoodsEarnable(this.rooms);
	}
	/**Determines if any of the rooms in the supplied array generate labor for income*/
	public bool isLaborEarnable()
	{
		return RoomUtilities.isLaborEarnable(this.rooms);
	}
	/**Determines if any of the rooms in the supplied array generate influence for income*/
	public bool isInfluenceEarnable()
	{
		return RoomUtilities.isInfluenceEarnable(this.rooms);
	}
	/**Determines if any of the rooms in the supplied array generate magic for income*/
	public bool isMagicEarnable()
	{
		return RoomUtilities.isMagicEarnable(this.rooms);
	}
	/**Determines if any of the rooms in the supplied array generate capital for income*/
	public bool isCapitalEarnable()
	{
		return RoomUtilities.isCapitalEarnable(this.rooms);
	}
	
	/**A helper method to get less verbose output*/
	public string  getBalanceSheetDescriptions()
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
	
	/**A helper method to get less verbose output*/
	public string  getSettlementDescriptions()
	{
		SettlementMods tempMods = regenerateSettlementMods();		//Regenerate in case of changes, including room bonuses
		String returnString = "";
		
		returnString += "\n----==Settlement Modifiers (0 if not present)==----\n";
		if (tempMods.settlementCorruptionModifier != 0)
			returnString += "Corruption: " + tempMods.settlementCorruptionModifier + "\n";
		if (tempMods.settlementCrimeModifier != 0)
			returnString += "Crime: " + tempMods.settlementCrimeModifier + "\n";
		if (tempMods.settlementProductivityModifier != 0)
			returnString += "Economy: " + tempMods.settlementProductivityModifier + "\n";
		if (tempMods.settlementLawModifier != 0)
			returnString += "Law: " + tempMods.settlementLawModifier + "\n";
		if (tempMods.settlementLoreModifier != 0)
			returnString += "Lore: " + tempMods.settlementLoreModifier + "\n";
		if (tempMods.settlementSocietyModifier != 0)
			returnString += "Society: " + tempMods.settlementSocietyModifier + "\n";
		if (tempMods.settlementDangerModifier != 0)
			returnString += "Danger: " + tempMods.settlementDangerModifier + "\n";
		if (tempMods.settlementBaseValueModifier != 0)
			returnString += "Base Value Increase: " + tempMods.settlementBaseValueModifier + "\n";
		if (minorItemsProduced != 0)
			returnString += "Minor Magic Items Produced Here: " + minorItemsProduced + "\n";
		if (mediumItemsProduced != 0)
			returnString += "Medium Magic Items Produced Here: " + mediumItemsProduced + "\n";
		if (majorItemsProduced != 0)
			returnString += "Major Magic Items Produced Here: " + majorItemsProduced + "\n";
		if (lotSize == 0)
			returnString += "This building does not take up space in a settlement, and may occupy the same space as other buildings.\n";
		if (lotSize > 0)
			returnString += "This building takes up " + lotSize + " lots on the discrict grid.\n";
		
		return returnString;
	}
	
	/**A helper method to get less verbose output*/
	public string  getKingdomDescriptions()
	{
		String returnString = "";
		
		returnString += "\n----==Kingdom Modifiers (0 if not present)==----\n";
		returnString += "BP Cost: " + BPCost + "\n";

		if (kingdomLoyaltyModifier != 0)
			returnString += "Loyalty: " + kingdomLoyaltyModifier + "\n";
		if (kingdomStabilityModifier != 0)
			returnString += "Stability: " + kingdomStabilityModifier + "\n";
		if (kingdomUnrestModifier != 0)
			returnString += "Unrest: " + kingdomUnrestModifier + "\n";
		if (kingdomConsumptionModifier != 0)
			returnString += "Consumption: " + kingdomConsumptionModifier + " BP \n";
		if (kingdomgDefenseModifier != 0)
			returnString += "Defense: " + kingdomgDefenseModifier + "\n";
		if (kingdomEconomyModifier != 0)
			returnString += "Economy: " + kingdomEconomyModifier + "\n";
		if (kingdomFameModifier != 0)
			returnString += "Fame: " + kingdomFameModifier + "\n";
		if (!(Discount.Equals("N/A")))
			returnString += "Provides a discount for building: " + Discount + "\n";
		if (!(Limits.Equals("N/A")))
			returnString += "Limits on the creation of this building: " + Limits + "\n";
		
		return returnString;
	}
	
	/**A helper method to get less verbose output*/
	public string  getRoomDescriptions()
	{
		String returnString = "";
		
		returnString += "\n----==Detailed Room Description==----";
		//Output every room in room[]
		for (int lcv = 0; lcv < this.rooms.Length; lcv++)
		{
			returnString += this.rooms[lcv].toString();
		}
		
		return returnString;
	}
	
	/**A helper method to get less verbose output*/
	public string  getFurnishingsDescriptions()
	{
		String returnString = "";
		
		returnString += "\n----==Detailed Furnishing\\Trap Description==----\n";
		//Output every furnishing in furnishings[]
		for (int lcv = 0; lcv < this.Furnishings.Length; lcv++)
		{
			returnString += this.Furnishings[lcv].toString();
		}
		
		return returnString;
	}

	
	/**Generates a BalanceSheet class that provides the sum total production of the room array provided*/
	BalanceSheet generateBalanceSheet(String preferredIncome)
	{
		BalanceSheet returnSheet = new BalanceSheet();
		returnSheet.preferredIncome = preferredIncome;	//For toString purposes
		
		//Check overall potentials for capital derivation
		bool GPEarnableOverall = isGPEarnable();
		bool GoodsEarnableOverall = isGoodsEarnable();
		bool InfluenceEarnableOverall = isInfluenceEarnable();
		bool LaborEarnableOverall = isLaborEarnable();
		bool MagicEarnableOverall = isMagicEarnable();
		
		for (int lcv = 0; lcv < rooms.Length; lcv++)
		{
			//Create a temporary 1-element array
			Room[] tempArray = new Room[1];
			tempArray[0] = rooms[lcv];
			//Check the earning potential of this particular element
			bool GPEarnable = RoomUtilities.isGPEarnable(tempArray);
			bool GoodsEarnable = RoomUtilities.isGoodsEarnable(tempArray);
			bool InfluenceEarnable = RoomUtilities.isInfluenceEarnable(tempArray);
			bool LaborEarnable = RoomUtilities.isLaborEarnable(tempArray);
			bool MagicEarnable = RoomUtilities.isMagicEarnable(tempArray);
			bool CapitalEarnable = RoomUtilities.isCapitalEarnable(tempArray);
			//Logic is the same, only the variables have been changed, to protect the innocent
			//Check to see if prefferedIncome is available, if so, favor it. Capital, a special resource, can be used to produce any other type that building can produce
			if (preferredIncome == "GP")
			{
				if (GPEarnable)
					returnSheet.GP += rooms[lcv].GPEarnings;
				else if (CapitalEarnable)
				{
					if (GPEarnableOverall)
						returnSheet.GP += rooms[lcv].CapitalEarnings;
					else
						Console.Out.WriteLine("Error found: For the Room \"" + rooms[lcv].Name + "\", GP not earnable for available captial on GP-only request: does this building not generate anything but capital?");
				}
				else if (LaborEarnable)
					returnSheet.Labor += rooms[lcv].LaborEarnings;
				else if (MagicEarnable)
					returnSheet.Magic += rooms[lcv].MagicEarnings;
				else if (InfluenceEarnable)
					returnSheet.Influence += rooms[lcv].InfluenceEarnings;
				else if (GoodsEarnable)
					returnSheet.Goods += rooms[lcv].GoodsEarnings;
				else
					returnSheet.GP += 0;
			}
			else if (preferredIncome == "Goods")
			{
				if (GoodsEarnable)
					returnSheet.Goods += rooms[lcv].GoodsEarnings;
				else if (CapitalEarnable)
				{
					if (GoodsEarnableOverall)
						returnSheet.Goods += rooms[lcv].CapitalEarnings;
					else if (GPEarnableOverall)
						returnSheet.GP += rooms[lcv].CapitalEarnings;
					else
						Console.Out.WriteLine("Error found: For the Room \"" + rooms[lcv].Name + "\", GP & Goods not earnable for available captial: does this building not generate anything but capital?");
				}
				else if (GPEarnable)
					returnSheet.GP += rooms[lcv].GPEarnings;
				else if (LaborEarnable)
					returnSheet.Labor += rooms[lcv].LaborEarnings;
				else if (MagicEarnable)
					returnSheet.Magic += rooms[lcv].MagicEarnings;
				else if (InfluenceEarnable)
					returnSheet.Influence += rooms[lcv].InfluenceEarnings;
				else
					returnSheet.Goods += 0;
			}
			else if (preferredIncome == "Labor")
			{
				if (LaborEarnable)
					returnSheet.Labor += rooms[lcv].LaborEarnings;
				else if (CapitalEarnable)
				{
					if (LaborEarnableOverall)
						returnSheet.Labor += rooms[lcv].CapitalEarnings;
					else if (GPEarnableOverall)
						returnSheet.GP += rooms[lcv].CapitalEarnings;
					else
						Console.Out.WriteLine("Error found: For the Room \"" + rooms[lcv].Name + "\", GP & Labor not earnable for available captial: does this building not generate anything but capital?");
				}
				else if (GPEarnable)
					returnSheet.GP += rooms[lcv].GPEarnings;
				else if (MagicEarnable)
					returnSheet.Magic += rooms[lcv].MagicEarnings;
				else if (InfluenceEarnable)
					returnSheet.Influence += rooms[lcv].InfluenceEarnings;
				else
					returnSheet.Labor += 0;
			}
			else if (preferredIncome == "Influence")
			{
				if (InfluenceEarnable)
					returnSheet.Influence += rooms[lcv].InfluenceEarnings;
				else if (CapitalEarnable)
				{
					if (InfluenceEarnableOverall)
						returnSheet.Influence += rooms[lcv].CapitalEarnings;
					else if (GPEarnableOverall)
						returnSheet.GP += rooms[lcv].CapitalEarnings;
					else
						Console.Out.WriteLine("Error found: For the Room \"" + rooms[lcv].Name + "\", GP & Influence not earnable for available captial: does this building not generate anything but capital?");
				}
				else if (GPEarnable)
					returnSheet.GP += rooms[lcv].GPEarnings;
				else if (GoodsEarnable)
					returnSheet.Goods += rooms[lcv].GoodsEarnings;
				else if (MagicEarnable)
					returnSheet.Magic += rooms[lcv].MagicEarnings;
				else if (LaborEarnable)
					returnSheet.Labor += rooms[lcv].LaborEarnings;
				else
					returnSheet.Influence += 0;
			}
			else if (preferredIncome == "Magic")
			{
				if (MagicEarnable)
					returnSheet.Magic += rooms[lcv].MagicEarnings;
				else if (CapitalEarnable)
				{
					if (MagicEarnableOverall)
						returnSheet.Magic += rooms[lcv].CapitalEarnings;
					else if (GPEarnableOverall)
						returnSheet.GP += rooms[lcv].CapitalEarnings;
					else
						Console.Out.WriteLine("Error found: For the Room \"" + rooms[lcv].Name + "\", GP & Magic not earnable for available captial: does this building not generate anything but capital?");
				}
				else if (GPEarnable)
					returnSheet.GP += rooms[lcv].GPEarnings;
				else if (GoodsEarnable)
					returnSheet.Goods += rooms[lcv].GoodsEarnings;
				else if (InfluenceEarnable)
					returnSheet.Influence += rooms[lcv].InfluenceEarnings;
				else if (LaborEarnable)
					returnSheet.Labor += rooms[lcv].LaborEarnings;
				else
					returnSheet.Magic += 0;
			}
			else
			{
				Console.Out.WriteLine("Error: Invalid preferred input provided");
			}
		}
		return returnSheet;
	}
	
	/**Take in a raw slash ('/') delimited string  and parse it into a list of Room objects*/
	static Room[] populateBuildingFromRawString(String rawRoomListing, Room[] completeRoomList)
	{
		Room[] returnRooms = new Room[0];
		//Only proceed if there are rooms to parse
		if (!(rawRoomListing.Equals("N/A")))
		{
			String[] tokens = rawRoomListing.split("\\/");				//Split into array
			for (int lcv = 0; lcv < tokens.Length; lcv++)				//For every element in tokens[]
			{
				int spaceIndex = tokens[lcv].IndexOf(" "); 				//Find the white space, for special formatting
				if (spaceIndex == -1)
				{
					Console.Out.WriteLine("Error encountered in populateBuildingFromRawString: \"" + tokens[lcv] + "\" is not a valid room definition");
				}
				else
				{
					String quantityString = tokens[lcv].Substring(0, spaceIndex);
					String roomName = tokens[lcv].Substring(spaceIndex);
					//remove whitespace
					int quantity = Convert.ToInt32(quantityString);
					roomName = roomName.replaceAll("^\\s+","");			//Remove whitespace from the front of the string 
					roomName = roomName.replaceAll("\\s+$",""); 		//Remove whitepsace from the back of the string 
					//Console.Out.WriteLine("Quantity: " + quantity);
					//Console.Out.WriteLine("Room Name: " + roomName);
					//Now we have the room name, and it's quantity, so we'll progressively add rooms until we've gone through the list
					for (int i = 0; i < quantity; i++)
					{
						Room toBeAdded = new Room();
						int roomIndex = RoomUtilities.IndexOf(roomName, completeRoomList);
						if (roomIndex == -1)
						{
							Console.Out.WriteLine("Error: \"" + roomName + "\" was not found in the complete room list.");
						}
						else
						{
							toBeAdded = completeRoomList[roomIndex];
							//Expand the array
							//Console.Out.WriteLine("I'm about to add " + toBeAdded.Name + " to the room array! That will make rooms[] " + (returnRooms.Length+1) + " elements long.");
							returnRooms = RoomUtilities.expand(toBeAdded, returnRooms);
							returnRooms[returnRooms.Length-1]= toBeAdded;
											
							//Console.Out.WriteLine("Yep, " + returnRooms[returnRooms.Length-1].Name + "Is really in here");
						}
					}
				}
			}
		}
		return returnRooms;
	}
}
