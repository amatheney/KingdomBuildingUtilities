package settlements;


public class Settlement 
{
	String Name;
	String Alignment;
	String Size;
	int Population;
	SettlementMods Modifiers;
	Quality[] Qualities;
	String[] notableNPCs;
	int PurchaseLimit;
	int Spellcasting;
	int MinorItems;
	int MediumItems;
	int MajorItems;
	District[] Districts;
	
	public Settlement()
	{
		this.Name = "";
		this.Alignment = "";
		this.Size = "";
		this.Population = 0;
		this.Modifiers = new SettlementMods();
		this.Qualities = new Quality[0];
		this.notableNPCs  = null;
		this.PurchaseLimit = 0;
		this.Spellcasting = 0;
		this.MinorItems = 0;
		this.MediumItems = 0;
		this.MajorItems = 0;
		this.Districts = new District[0];
	}
	
	/*Constructor That builds a settlement out of a raw string*/
	public Settlement(String rawCSV, Building[] completeBuildingList, Room[] completeRoomList, FurnishingsAndTraps[] completeFurnishingList)
	{
		//initializing
		this.Districts = new District[0];
		this.Modifiers = new SettlementMods();
		this.Qualities = new Quality[0];
		
		String[] tokens = rawCSV.split("\\;");
		
		for (int tokenLCV = 0; tokenLCV < tokens.length; tokenLCV++)
		{
			//System.out.println("Token being processed: " + tokens[tokenLCV]);
			String[] tempSplit = tokens[tokenLCV].split("\\=");
			String propertyName = tempSplit[0];
			String propertyValue = tempSplit[1];
			int DistrictIndex = propertyName.indexOf("District:");
			
			if (propertyName.equals("Name"))			//Name of settlement
				this.Name = RoomUtilities.snipQuotes(propertyValue);
			if (propertyName.equals("Alignment"))		//Alignment (2 characters long)
				this.Alignment = RoomUtilities.snipQuotes(propertyValue);
			if (propertyName.equals("Population"))		//Population
				this.Population = Integer.parseInt(propertyValue);
			if (propertyName.equals("Qualities"))		//Qualities - needs to be fleshed out
				this.Qualities = DeriveQualities(RoomUtilities.snipQuotes(propertyValue));
			if (propertyName.equals("NotableNPCs"))		//Contains a list of NPCs that needs to be parsed further
			{
				propertyValue = RoomUtilities.snipQuotes(propertyValue);
				String[] NPCSplit = propertyValue.split("\\,");
				
				for (int NPCLCV = 0; NPCLCV < NPCSplit.length; NPCLCV++)
				{
					NPCSplit[NPCLCV] = RoomUtilities.snipQuotes(NPCSplit[NPCLCV]);
				}
				
				this.notableNPCs = NPCSplit; 
			}
			if (DistrictIndex == 0)						//Lots of work to parse in a district's buildings
			{
				District toBeAdded = new District();
				
				//Pull the list of buildings, qualify them, then add them to the building
				propertyValue = RoomUtilities.snipQuotes(propertyValue);
				String[] districtBuildingList = propertyValue.split("\\,");
				for (int buildingLCV = 0; buildingLCV < districtBuildingList.length; buildingLCV++)
				{
					Building tempBuilding = new Building();
					tempBuilding = completeBuildingList[RoomUtilities.indexOf(districtBuildingList[buildingLCV], completeBuildingList)];
					toBeAdded.placeBuilding(tempBuilding);
				}
				
				//Now pull the name, if supplied
				int colonIndex = propertyName.indexOf(":");
				String districtName = "N/A";
				if (colonIndex > 0)
				{
					districtName = propertyName.substring(colonIndex+1);
				}
				if (!(districtName.equals("N/A")))
					toBeAdded.nameOfDistrict = districtName;
				
				//Finally, add this district to the list
				this.Districts = expand(this.Districts);
				this.Districts[this.Districts.length-1] = toBeAdded;
				//System.out.println("Districts is now " + this.Districts.length + " elements long");
			}			
		}
		
		calculateModifiers();
	}
	
	private void calculateModifiers()
	{
		//Reset first, we're going to completely re-calculate these values
		this.Modifiers = new SettlementMods();
		this.PurchaseLimit = 0;
		this.Spellcasting = 0;
		this.MinorItems = 0;
		this.MediumItems = 0;
		this.MajorItems = 0;
		
		//Loop over qualities and combine where found (Populate this)
		/*for (int lcv = 0; lcv < this.Qualities.length; lcv++)
		{
			this.Modifiers = this.Modifiers.combineSettlementMods(this.Qualities[lcv].modifiers);
		}*/
		
		//Loop over districts and combine where found
		for (int lcv = 0; lcv < this.Districts.length; lcv++)
		{
			this.Modifiers = this.Modifiers.combineSettlementMods(this.Districts[lcv].getTotalModifiers());
			this.MinorItems += this.Districts[lcv].getMinorItemsProduced();
			this.MediumItems += this.Districts[lcv].getMediumItemsProduced();
			this.MajorItems += this.Districts[lcv].getMajorItemsProduced();
		}
		
		//Apply some final modifiers based off size
		this.Size = getSize(this.Population);
		if (this.Size.equals("Thorp"))
		{
			SettlementMods mutators = new SettlementMods(-4,-4,-4,-4,-4,-10,-4,50);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 1;
			this.PurchaseLimit = 500;
			this.MinorItems += 4;
			this.MediumItems += 0;
			this.MajorItems += 0;
		}
		else if (this.Size.equals("Hamlet"))
		{
			SettlementMods mutators = new SettlementMods(-2,-2,-2,-2,-2,-5,-2,200);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 2;
			this.PurchaseLimit = 1000;
			this.MinorItems += 6;
			this.MediumItems += 0;
			this.MajorItems += 0;
		}
		else if (this.Size.equals("Village"))
		{
			SettlementMods mutators = new SettlementMods(-1,-1,-1,-1,-1,0,-1,500);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 3;
			this.PurchaseLimit = 2500;
			this.MinorItems += 8;
			this.MediumItems += 4;
			this.MajorItems += 0;
		}
		else if (this.Size.equals("Small Town"))
		{
			SettlementMods mutators = new SettlementMods(0,0,0,0,0,0,0,1000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 4;
			this.PurchaseLimit = 5000;
			this.MinorItems += 12;
			this.MediumItems += 6;
			this.MajorItems += 0;
		}
		else if (this.Size.equals("Large Town"))
		{
			SettlementMods mutators = new SettlementMods(0,0,0,0,0,5,0,2000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 5;
			this.PurchaseLimit = 10000;
			this.MinorItems += 12;
			this.MediumItems += 8;
			this.MajorItems += 4;
		}
		else if (this.Size.equals("Small City"))
		{
			SettlementMods mutators = new SettlementMods(1,1,1,1,1,5,1,4000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 6;
			this.PurchaseLimit = 25000;
			this.MinorItems += 16;
			this.MediumItems += 12;
			this.MajorItems += 6;
		}
		else if (this.Size.equals("Large City"))
		{
			SettlementMods mutators = new SettlementMods(2,2,2,2,2,10,2,8000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 7;
			this.PurchaseLimit = 50000;
			this.MinorItems += 16;
			this.MediumItems += 12;
			this.MajorItems += 8;
		}
		else if (this.Size.equals("Metropolis"))
		{
			SettlementMods mutators = new SettlementMods(4,4,4,4,4,10,4,16000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 8;
			this.PurchaseLimit = 100000;
			this.MinorItems = -1;
			this.MediumItems += 16;
			this.MajorItems += 12;
		}
		
		//Some final modifiers for alignment
		String lawOrChaos = this.Alignment.substring(0, 1);
		String goodOrEvil = this.Alignment.substring(1);
		if (lawOrChaos.equals("N"))
			this.Modifiers.settlementLoreModifier += 1;
		if (lawOrChaos.equals("L"))
			this.Modifiers.settlementLawModifier += 1;
		if (lawOrChaos.equals("C"))
			this.Modifiers.settlementCrimeModifier += 1;
		if (goodOrEvil.equals("N"))
			this.Modifiers.settlementLoreModifier += 1;
		if (goodOrEvil.equals("E"))
			this.Modifiers.settlementCorruptionModifier += 1;
		if (goodOrEvil.equals("G"))
			this.Modifiers.settlementSocietyModifier += 1;
	}
	
	public String toString()
	{
		String returnString = "";
		
		//A bit of alignment formatting
		String alignment = this.Alignment;
		if (alignment.equals("NN"))
			alignment = "N";
		
		returnString += "\n||||----____ The " + this.Size + " of " + this.Name + "____----||||\n";
		returnString += "   " + alignment + " " + this.Size + " (" + this.Population + ")\n";
		returnString += "   Spellcasting up to level " + this.Spellcasting + " is available\n";
		returnString += "   Vendors will purchase items up to " + this.PurchaseLimit + "gp\n";
		returnString += "   75% of items " + this.Modifiers.settlementBaseValueModifier + " gp or lower are available for sale\n";
		returnString += "   In addition, there are " + this.MinorItems + " minor items, " + this.MediumItems + " medium items, and " + this.MajorItems + " major items for sale.\n";
		returnString += this.Modifiers.toString();
		returnString += "\n--==Notable NPCs==--\n";
		for (int lcv = 0; lcv < this.notableNPCs.length; lcv++)
		{
			returnString += this.notableNPCs[lcv] + "\n";
		}
		returnString += "\n-----=====Districts=====-----\n";
		for (int lcv = 0; lcv < this.Districts.length; lcv++)
		{
			returnString += this.Districts[lcv].nameOfDistrict + ": " + this.Districts[lcv].remainingLotsAvailable() + " lots free\n";
		}
				
		return returnString;
	}
	
	public String completeBuildingOutput()
	{
		String returnString = toString();
		
		returnString += "\n-----=====Districts (Complete Building Output)=====-----\n";
		for (int lcv = 0; lcv < this.Districts.length; lcv++)
		{
			returnString += "\n" + this.Districts[lcv].toString();
		}
		
		return returnString;
	}
	
	public int estimatePopulation()
	{
		int newPopulation = 0;
		if (this.Population <= 0)
		{
			for (int lcv = 0; lcv < this.Districts.length; lcv++)
			{
				newPopulation += Districts[lcv].currentlyOccupiedLots() * 250;
			}
		}
		return newPopulation;
	}
	
	public String getSize(int population)
	{
		if (population < 0)
			population = estimatePopulation();
		if (population < 21)
			return "Thorp";
		else if (population >= 21 && population <= 60)
			return  "Hamlet";
		else if (population >= 61 && population <= 200)
			return "Village";
		else if (population >= 201 && population <= 2000)
			return "Small Town";
		else if (population >= 2001 && population <= 5000)
			return "Large Town";
		else if (population >= 5001 && population <= 10000)
			return "Small City";
		else if (population >= 10001 && population <= 25000)
			return "Large City";
		else if (population >= 25001)
			return "Metropolis";
		else
			return "Invalid";
	}
	
	private Quality[] DeriveQualities(String qualityListing)
	{
		//FLESH THIS OUT
		return null;
	}
	
	private District[] BuildDistricts(Building[] Buildings, District[] Districts)
	{
		District toBeAdded = new District();						//Container for this iterations building allocations
		Building[] remainder = toBeAdded.populateLots(Buildings);	//Place what buildings you can in this lot, and then return the remainder
		Districts = expand(Districts);								//Expand the supplied districts array to hold the new district
		Districts[Districts.length-1] = toBeAdded;					//Place this district container in the main array
		if (remainder != null)										//If there were buildings returned:
			Districts = BuildDistricts(remainder, Districts);			//Recursivly call this method, passing in the remainders and the constructed array
		return Districts;											//Otherwise, return what we have built so far
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	private District[] expand(District[] oldArray)
	{
		District[] newArray = new District[oldArray.length+1];
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Inner class that stores all the qualities, disadvantages, and governments of the settlement, proving an easy-to-iterate structure for calculating total modifiers*/
	private class Quality
	{
		SettlementMods modifiers;
		String name;
		String description;
		
		/**Generic constructor*/
		private Quality()
		{
			this.modifiers = new SettlementMods();
			this.name = "";
			this.description = "";
		}
	}
	
	/**Inner class that stores a district's worth of buildings - typically a settlement only contains a single district*/
	public class District
	{
		final int BLOCK_SIZE = 4;			//The size of a block, in lots
		final int NUMBER_OF_BLOCKS = 9;		//The number of blocks in a district
		final int LOT_SIZE = 750;			//The size of a single lot, in feet
		
		Building[] Buildings;
		String nameOfDistrict;
		
		/**Generic Constructor*/
		private District()
		{
			this.nameOfDistrict = "";
			this.Buildings = new Building[0];
		}
		
		public SettlementMods getTotalModifiers() 
		{
			SettlementMods toBeReturned = new SettlementMods();
			
			for (int lcv = 0; lcv < this.Buildings.length; lcv++)
			{
				toBeReturned = toBeReturned.combineSettlementMods(this.Buildings[lcv].SettlementModifiers);
			}
			
			return toBeReturned;
		}

		/**Qualified Constructor*/
		private District(String nameOfDistrict)
		{
			this.nameOfDistrict = nameOfDistrict;
			this.Buildings = new Building[0];
		}
		
		private Building[] populateLots (Building[] Buildings)
		{
			Building[] remainder = new Building[0];
			
			for (int lcv = 0; lcv < Buildings.length; lcv++)
			{
				boolean success = placeBuilding(Buildings[lcv]);
				if (success == false)
				{
					remainder = new Building[Buildings.length-lcv];
					int indexToGo = Buildings.length-lcv-1;
					String output = "Debug String: \n\nBuildings has " + Buildings.length + " elements\nLCV is " + lcv + "\nremainder is ";
					output += remainder.length + " elements long\nI'm going to loop from 0 to " + indexToGo + " now";
					System.out.println(output);
					
					for (int innerLCV = 0; innerLCV < Buildings.length-lcv; innerLCV++)
					{
						int buildingIndex = lcv+innerLCV;

						remainder[innerLCV] = Buildings[buildingIndex];
						System.out.println("I'm placing element #" + buildingIndex + " from Buildings into position #" + innerLCV + " in remainder");
					}
					return remainder;
				}
			}
			
			return null;
		}
		
		/**Returns the maximum lots of a district*/
		private int maxLotsAvailable()
		{
			return BLOCK_SIZE * NUMBER_OF_BLOCKS;
		}
		
		/**Returns the currently occupied lots (the occupied size) of a disctict*/
		private int currentlyOccupiedLots()
		{
			int currentLots = 0;
			for (int lcv = 0; lcv < Buildings.length; lcv++)
			{
				currentLots += Buildings[lcv].lotSize;
			}
			return currentLots;
		}
		
		/**Returns the remaining lots (the unoccupied size) of a district*/
		private int remainingLotsAvailable()
		{
			return maxLotsAvailable() - currentlyOccupiedLots();
		}
		
		/**Returns true if this building was able to be added to the array, and false otherwise*/
		private boolean placeBuilding(Building toBeAdded)
		{
			int remainingSpace = remainingLotsAvailable();
			if (remainingSpace > toBeAdded.lotSize)
			{
				this.Buildings = expand(this.Buildings);
				Buildings[Buildings.length-1] = toBeAdded;
				return true;
			}
			return false;
		}
		
		/**Expand the array by one, adding our new element to the expanded array*/
		private Building[] expand(Building[] oldArray)
		{
			Building[] newArray = new Building[oldArray.length+1];
			System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
			
			return newArray;
		}
		
		/**Retuns the first instace of a building found in the building array*/
		private int indexOf(String buildingName)
		{
			for (int lcv = 0; lcv < Buildings.length; lcv++)
			{
				if (Buildings[lcv].name.equals(buildingName))
					return lcv;
			}
			return -1;
		}
		
		public String toString()
		{
			String returnString = "";
			if (nameOfDistrict.equals(""))
				returnString += "\n-----===Buildings===-----\n";
			else
				returnString += "\n-----===" + nameOfDistrict + " District===-----\n";
			for (int lcv = 0; lcv < Buildings.length; lcv++)
			{
				returnString += Buildings[lcv].toString();
			}
			return returnString;
		}
		
		public int getMinorItemsProduced()
		{
			int returnValue = 0;
			
			for (int lcv = 0; lcv < Buildings.length; lcv++)
			{
				returnValue += Buildings[lcv].minorItemsProduced;
			}
			
			return returnValue;
		}
		
		public int getMediumItemsProduced()
		{
			int returnValue = 0;
			
			for (int lcv = 0; lcv < Buildings.length; lcv++)
			{
				returnValue += Buildings[lcv].mediumItemsProduced;
			}
			
			return returnValue;
		}
		
		public int getMajorItemsProduced()
		{
			int returnValue = 0;
			
			for (int lcv = 0; lcv < Buildings.length; lcv++)
			{
				returnValue += Buildings[lcv].majorItemsProduced;
			}
			
			return returnValue;
		}
	}
}
