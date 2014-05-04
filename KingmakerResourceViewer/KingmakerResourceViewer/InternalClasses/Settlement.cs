using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    [Serializable()]
    public class Settlement 
    {
        public String Name;
        public String Alignment;
        public String Size;
        public int Population;
        public SettlementMods Modifiers;
        public Quality[] Qualities;
        public String[] notableNPCs;
        public int PurchaseLimit;
        public int Spellcasting;
        public int MinorItems;
        public int MediumItems;
        public int MajorItems;
        public District[] Districts;
	
	public Settlement()
	{
		this.Name = "";
		this.Alignment = "NN";
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
	
	/*Constructor That builds a settlement out of a raw string */
	public Settlement(String rawCSV, Building[] completeBuildingList, Quality[] completeQualities)
	{
		//initializing
		this.Districts = new District[0];
		this.Modifiers = new SettlementMods();
		this.Qualities = new Quality[0];

        String[] tokens = rawCSV.Split(';');
		
		for (int tokenLCV = 0; tokenLCV < tokens.Length; tokenLCV++)
		{
			//Console.Out.WriteLine("Token being processed: " + tokens[tokenLCV]);
			String[] tempSplit = tokens[tokenLCV].Split('=');
			String propertyName = tempSplit[0];
			String propertyValue = tempSplit[1];
			int DistrictIndex = propertyName.IndexOf("District:");
			
			if (propertyName.Equals("Name"))			//Name of settlement
				this.Name = RoomUtilities.snipQuotes(propertyValue);
			if (propertyName.Equals("Alignment"))		//Alignment (2 characters long)
				this.Alignment = RoomUtilities.snipQuotes(propertyValue);
			if (propertyName.Equals("Population"))		//Population
				this.Population = Convert.ToInt32(propertyValue);
			if (propertyName.Equals("Qualities"))		//Qualities - needs to be fleshed out
				this.Qualities = DeriveQualities(RoomUtilities.snipQuotes(propertyValue), completeQualities);
			if (propertyName.Equals("NotableNPCs"))		//Contains a list of NPCs that needs to be parsed further
			{
				propertyValue = RoomUtilities.snipQuotes(propertyValue);
				String[] NPCSplit = propertyValue.Split(',');
				
				for (int NPCLCV = 0; NPCLCV < NPCSplit.Length; NPCLCV++)
				{
					NPCSplit[NPCLCV] = RoomUtilities.snipQuotes(NPCSplit[NPCLCV]);
				}
				
				this.notableNPCs = NPCSplit; 
			}
			if (DistrictIndex == 0)						//Lots of work to parse in a district's buildings
			{
				
				//Pull the list of buildings, qualify them, then add them to the building
				propertyValue = RoomUtilities.snipQuotes(propertyValue);
				String[] districtBuildingList = propertyValue.Split(',');
				Building[] toBeAdded = new Building[0];
				//Console.Out.WriteLine(districtBuildingList.Length + " buildings found in settlement " + this.Name);
				for (int buildingLCV = 0; buildingLCV < districtBuildingList.Length; buildingLCV++)
				{
					Building tempBuilding = new Building();
					tempBuilding = completeBuildingList[RoomUtilities.indexOf(districtBuildingList[buildingLCV], completeBuildingList)];
					toBeAdded = RoomUtilities.expand(toBeAdded);
					toBeAdded[toBeAdded.Length-1] = tempBuilding;
				}
				
				//Now pull the name, if supplied
				int colonIndex = propertyName.IndexOf(":");
				String districtName = "";
				if (colonIndex > 0)
				{
					districtName = propertyName.Substring(colonIndex+1);
				}
				
				this.Districts = BuildDistricts(toBeAdded, this.Districts, districtName);
				
				for (int lcv = 0; lcv < this.Districts.Length; lcv++)
				{
					if (this.Districts[lcv].nameOfDistrict.Equals("Unnamed Rollover District"))
						this.Districts[lcv].nameOfDistrict = districtName;
				}	
			}			
		}
		Console.Out.WriteLine("---" + this.Districts.Length + " District(s) built.");
		
		calculateModifiers();
	}
	
	public void calculateModifiers()
	{
		//Reset first, we're going to completely re-calculate these values
		//Console.Out.WriteLine("---Calculating settlement modifiers...");
		this.Modifiers = new SettlementMods();
		this.PurchaseLimit = 0;
		this.Spellcasting = 0;
		this.MinorItems = 0;
		this.MediumItems = 0;
		this.MajorItems = 0;
		
		//Loop over qualities and combine where found (Populate this)
		/*for (int lcv = 0; lcv < this.Qualities.Length; lcv++)
		{
			this.Modifiers = this.Modifiers.combineSettlementMods(this.Qualities[lcv].modifiers);
		}*/
		
		//Loop over districts and combine where found
		//Console.Out.WriteLine("---Totaling magic items and settlement mods...");
		for (int lcv = 0; lcv < this.Districts.Length; lcv++)
		{
			this.Modifiers = this.Modifiers.combineSettlementMods(this.Districts[lcv].getTotalModifiers());
			this.MinorItems += this.Districts[lcv].getMinorItemsProduced();
			this.MediumItems += this.Districts[lcv].getMediumItemsProduced();
			this.MajorItems += this.Districts[lcv].getMajorItemsProduced();
		}

        this.Population = estimatePopulation();

		//Apply somemodifiers based off size
		//Console.Out.WriteLine("---Calculating population size...");
		this.Size = getSize(this.Population);
		if (this.Size.Equals("Thorp"))
		{
			SettlementMods mutators = new SettlementMods(-4,-4,-4,-4,-4,-10,-4,50);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 1;
			this.PurchaseLimit = 500;
			this.MinorItems += 4;
			this.MediumItems += 0;
			this.MajorItems += 0;
		}
		else if (this.Size.Equals("Hamlet"))
		{
			SettlementMods mutators = new SettlementMods(-2,-2,-2,-2,-2,-5,-2,200);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 2;
			this.PurchaseLimit = 1000;
			this.MinorItems += 6;
			this.MediumItems += 0;
			this.MajorItems += 0;
		}
		else if (this.Size.Equals("Village"))
		{
			SettlementMods mutators = new SettlementMods(-1,-1,-1,-1,-1,0,-1,500);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 3;
			this.PurchaseLimit = 2500;
			this.MinorItems += 8;
			this.MediumItems += 4;
			this.MajorItems += 0;
		}
		else if (this.Size.Equals("Small Town"))
		{
			SettlementMods mutators = new SettlementMods(0,0,0,0,0,0,0,1000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 4;
			this.PurchaseLimit = 5000;
			this.MinorItems += 12;
			this.MediumItems += 6;
			this.MajorItems += 0;
		}
		else if (this.Size.Equals("Large Town"))
		{
			SettlementMods mutators = new SettlementMods(0,0,0,0,0,5,0,2000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 5;
			this.PurchaseLimit = 10000;
			this.MinorItems += 12;
			this.MediumItems += 8;
			this.MajorItems += 4;
		}
		else if (this.Size.Equals("Small City"))
		{
			SettlementMods mutators = new SettlementMods(1,1,1,1,1,5,1,4000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 6;
			this.PurchaseLimit = 25000;
			this.MinorItems += 16;
			this.MediumItems += 12;
			this.MajorItems += 6;
		}
		else if (this.Size.Equals("Large City"))
		{
			SettlementMods mutators = new SettlementMods(2,2,2,2,2,10,2,8000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 7;
			this.PurchaseLimit = 50000;
			this.MinorItems += 16;
			this.MediumItems += 12;
			this.MajorItems += 8;
		}
		else if (this.Size.Equals("Metropolis"))
		{
			SettlementMods mutators = new SettlementMods(4,4,4,4,4,10,4,16000);
			this.Modifiers = this.Modifiers.combineSettlementMods(mutators);
			this.Spellcasting = 8;
			this.PurchaseLimit = 100000;
			this.MinorItems = -1;
			this.MediumItems += 16;
			this.MajorItems += 12;
		}
		
		//Somemodifiers for alignment
		//Console.Out.WriteLine("---Applying alignment modifiers...");
		String lawOrChaos = this.Alignment.Substring(0, 1);
		String goodOrEvil = this.Alignment.Substring(1);
		if (lawOrChaos.Equals("N"))
			this.Modifiers.settlementLoreModifier += 1;
		if (lawOrChaos.Equals("L"))
			this.Modifiers.settlementLawModifier += 1;
		if (lawOrChaos.Equals("C"))
			this.Modifiers.settlementCrimeModifier += 1;
		if (goodOrEvil.Equals("N"))
			this.Modifiers.settlementLoreModifier += 1;
		if (goodOrEvil.Equals("E"))
			this.Modifiers.settlementCorruptionModifier += 1;
		if (goodOrEvil.Equals("G"))
			this.Modifiers.settlementSocietyModifier += 1;
		
		//Adjustment for settlement qualities
		if (this.Qualities != null)
		{
			//Console.Out.WriteLine("---Consolidating Qualities... [" +this.Qualities.Length + "] to resolve");
			for (int lcv = 0; lcv < this.Qualities.Length; lcv++)
			{
				//Console.Out.WriteLine("---|---Attempting to parse " + this.Qualities[lcv].name);
				this.Modifiers = this.Modifiers.combineSettlementMods(this.Qualities[lcv].modifiers);
				if (this.Qualities[lcv].baseValuePercentageModifier > 0)
					this.Modifiers.settlementBaseValueModifier =(int)(this.Modifiers.settlementBaseValueModifier * this.Qualities[lcv].baseValuePercentageModifier);
				if (this.Qualities[lcv].purchaseLimitPercentageModifier > 0)
					this.PurchaseLimit =(int)(this.PurchaseLimit * this.Qualities[lcv].purchaseLimitPercentageModifier);
				this.Spellcasting += this.Qualities[lcv].spellcastingModifier;
				if (this.Qualities[lcv].magicItemModifier > 0)
				{
					if (this.MajorItems > 0)
						this.MajorItems += this.Qualities[lcv].magicItemModifier;
					else if (this.MediumItems > 0)
						this.MediumItems += this.Qualities[lcv].magicItemModifier;
					else if (this.MinorItems > 0)
						this.MinorItems += this.Qualities[lcv].magicItemModifier;
					else
						this.MinorItems = 0;
					
					if (this.MajorItems < 0)
						this.MajorItems = 0;
					if (this.MediumItems < 0)
						this.MediumItems = 0;
					if (this.MinorItems < 0)
						this.MinorItems = 0;
					
				}
			}
		}
		else
			Console.Out.WriteLine("WARNING: no qualities found when regenerating settlement modifiers");
		//Console.Out.WriteLine("---Done regenerating settlement.");
	}
	
	public string  toString()
	{
		String returnString = "";
		
		//A bit of alignment formatting
		String alignment = this.Alignment;
		if (alignment.Equals("NN"))
			alignment = "N";
		
		returnString += "\n||||----____ The " + this.Size + " of " + this.Name + "____----||||\n";
		returnString += "   " + alignment + " " + this.Size + " (" + this.Population + ")\n";
		returnString += "   Spellcasting up to level " + this.Spellcasting + " is available\n";
		returnString += "   Vendors will purchase items up to " + this.PurchaseLimit + "gp\n";
		returnString += "   75% of items " + this.Modifiers.settlementBaseValueModifier + " gp or lower are available for sale\n";
		returnString += "   In addition, there are " + this.MinorItems + " minor items, " + this.MediumItems + " medium items, and " + this.MajorItems + " major items for sale.\n";
		returnString += this.Modifiers.toString();
		returnString += "\n--==Qualities (Non special bonuses already included in above modifiers)==--\n";
		if (this.Qualities != null)
		{
			for (int lcv = 0; lcv < this.Qualities.Length; lcv++)
			{
				returnString += this.Qualities[lcv].toString() + "\n";
			}
		}
        if (this.notableNPCs != null)
        {
            returnString += "\n--==Notable NPCs==--\n";
            for (int lcv = 0; lcv < this.notableNPCs.Length; lcv++)
            {
                returnString += this.notableNPCs[lcv] + "\n";
            }
        }
		returnString += "\n-----=====Districts=====-----\n";
		for (int lcv = 0; lcv < this.Districts.Length; lcv++)
		{
			if (this.Districts[lcv].nameOfDistrict.Equals(""))
				returnString += "<Rollover District>";
			else
				returnString += this.Districts[lcv].nameOfDistrict;
			returnString += ": " + this.Districts[lcv].remainingLotsAvailable() + " lots free\n";
		}
				
		return returnString;
	}
	
	public string  partialBuildingOutput()
	{
		String returnString = toString();
		
		returnString += "\n-----=====Districts (Partial Building Output)=====-----\n";
		for (int lcv = 0; lcv < this.Districts.Length; lcv++)
		{
			returnString += "   ---" + this.Districts[lcv].nameOfDistrict + "---   \n";
			for (int i = 0; i < this.Districts[lcv].Buildings.Length; i++)
			{
				returnString += this.Districts[lcv].Buildings[i].name;
				if (this.Districts[lcv].Buildings[i].owner.Equals(""))
					returnString += " (government property)\n"; 
				else
					returnString += ", owned by " + this.Districts[lcv].Buildings[i].owner + "\n";
						
			}
		}
		
		return returnString;
	}
	
	public string  completeBuildingOutput()
	{
		String returnString = toString();
		
		returnString += "\n-----=====Districts (Complete Building Output)=====-----\n";
		for (int lcv = 0; lcv < this.Districts.Length; lcv++)
		{
			returnString += "\n" + this.Districts[lcv].toString();
		}
		
		return returnString;
	}
	
	public int estimatePopulation()
	{
		int newPopulation = 0;
		
		for (int lcv = 0; lcv < this.Districts.Length; lcv++)
		{
			newPopulation += Districts[lcv].currentlyOccupiedLots() * 250;
		}
		
		return newPopulation;
	}
	
	public string  getSize(int population)
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
	
	private Quality[] DeriveQualities(String qualityListing, Quality[] completeQualities)
	{
		//Console.Out.WriteLine("***Derive Qualities: parsing '" + qualityListing + "'");
		if (qualityListing.Equals("N/A"))
			return null;
		String[] tokens = qualityListing.Split(',');
		Quality[] returnList = new Quality[0];
		
		for (int lcv = 0; lcv < tokens.Length; lcv++)
		{
			//Console.Out.WriteLine("Token being parsed: '" + tokens[lcv] + "'");
			Quality toBeAdded = new Quality();
			toBeAdded = completeQualities[RoomUtilities.indexOf(tokens[lcv], completeQualities)];
			returnList = RoomUtilities.expand(returnList);
			returnList[returnList.Length-1] = toBeAdded;
		}
		
		return returnList;
	}
	
	public District[] BuildDistricts(Building[] Buildings, District[] Districts, string  districtName)
	{
		//Console.Out.WriteLine("At the start of BuildDistricts, there are " + Districts.Length + " districts");
		Building[] remainder = Buildings;
		bool nameApplied = false;
		bool iterationRequired = true;
		
		if (Districts.Length == 0)
		{
			Districts = new District[1];
			Districts[0] = new District();
			//Console.Out.WriteLine("Before the loop, I had to add a new district. The new length is " + Districts.Length);
		}
		else if (!(districtName.Equals("")))
		{
			Districts = District.expand(Districts);
			Districts[Districts.Length-1] = new District(districtName);
			nameApplied = true;
			//Console.Out.WriteLine("I set the last district in the list to " + districtName);
		}
		
		for (int districtLCV = 0; districtLCV < Districts.Length; districtLCV++)
		{
			//Console.Out.WriteLine("At the start of the loop, there are " + Districts[districtLCV].remainingLotsAvailable() + " lots available in the " + Districts[districtLCV].nameOfDistrict + " district.");
			//If the district is unnamed, and a string  has been supplied, and we have not yet named a district in this iteration:
			//Console.Out.WriteLine(Districts[districtLCV].nameOfDistrict + " is being looped over");
			if (Districts[districtLCV].nameOfDistrict.Equals("") && !(districtName.Equals("")) && nameApplied == false)
			{
				Districts[districtLCV].nameOfDistrict = districtName;
				nameApplied = true;
				//Console.Out.WriteLine("I set district #" + districtLCV + " to " + districtName);
			}
			
			if (Districts[districtLCV].nameOfDistrict.Equals(districtName) || districtName.Equals("") && iterationRequired)
			{
				iterationRequired = false;
				for (int buildingLCV = 0; buildingLCV < remainder.Length; buildingLCV++)
				{
					//Console.Out.WriteLine(remainder.Length-buildingLCV + " buildings to place");
					//Console.Out.WriteLine("Right now, I'm trying to add a " + Buildings[buildingLCV].name + ", owned by " + Buildings[buildingLCV].owner + " to district #" + districtLCV);
					if (!Districts[districtLCV].placeBuilding(Buildings[buildingLCV]))
					{
						//Console.Out.WriteLine("...But the district was full!");
						//If we've entered here, this district is full. This building, and all after it, become the only elements in remainder,
						//and we're going to break out of the loop prematurely.
						remainder = new Building[0];
						for (int lcv = 0; lcv < Buildings.Length-buildingLCV; lcv++)
						{
							remainder = RoomUtilities.expand(remainder);
							remainder[lcv] = Buildings[buildingLCV+lcv];
						}
						buildingLCV = remainder.Length+1;
						Districts = District.expand(Districts);
						Districts[Districts.Length-1] = new District();
						districtName = "";
						//Console.Out.WriteLine("I had to add a new district. The new length is " + Districts.Length);
						iterationRequired = true;
					}
					//else
						//districtLCV = Districts.Length;
				}
			}
			//else
				//Console.Out.WriteLine("I'm skipping putting buildings in '" + Districts[districtLCV].nameOfDistrict + "' district because the name didn't match '" + districtName + "'" + " (" + Districts[districtLCV].Equals(districtName) + ")");
				//Console.Out.WriteLine("At the end of the loop, there are " + Districts[districtLCV].remainingLotsAvailable() + " lots available in the " + Districts[districtLCV].nameOfDistrict + " district.");
				
		}
		return Districts;
	}

    public void BuildDistricts(Building[] Buildings, string districtName)
    {
        District[] Districts = new District[0];

        //Console.Out.WriteLine("At the start of BuildDistricts, there are " + Districts.Length + " districts");
        Building[] remainder = Buildings;
        bool nameApplied = false;
        bool iterationRequired = true;

        if (Districts.Length == 0)
        {
            Districts = new District[1];
            Districts[0] = new District();
            //Console.Out.WriteLine("Before the loop, I had to add a new district. The new length is " + Districts.Length);
        }
        else if (!(districtName.Equals("")))
        {
            Districts = District.expand(Districts);
            Districts[Districts.Length - 1] = new District(districtName);
            nameApplied = true;
            //Console.Out.WriteLine("I set the last district in the list to " + districtName);
        }

        for (int districtLCV = 0; districtLCV < Districts.Length; districtLCV++)
        {
            //Console.Out.WriteLine("At the start of the loop, there are " + Districts[districtLCV].remainingLotsAvailable() + " lots available in the " + Districts[districtLCV].nameOfDistrict + " district.");
            //If the district is unnamed, and a string  has been supplied, and we have not yet named a district in this iteration:
            //Console.Out.WriteLine(Districts[districtLCV].nameOfDistrict + " is being looped over");
            if (Districts[districtLCV].nameOfDistrict.Equals("") && !(districtName.Equals("")) && nameApplied == false)
            {
                Districts[districtLCV].nameOfDistrict = districtName;
                nameApplied = true;
                //Console.Out.WriteLine("I set district #" + districtLCV + " to " + districtName);
            }

            if (Districts[districtLCV].nameOfDistrict.Equals(districtName) || districtName.Equals("") && iterationRequired)
            {
                iterationRequired = false;
                for (int buildingLCV = 0; buildingLCV < remainder.Length; buildingLCV++)
                {
                    //Console.Out.WriteLine(remainder.Length-buildingLCV + " buildings to place");
                    //Console.Out.WriteLine("Right now, I'm trying to add a " + Buildings[buildingLCV].name + ", owned by " + Buildings[buildingLCV].owner + " to district #" + districtLCV);
                    if (!Districts[districtLCV].placeBuilding(Buildings[buildingLCV]))
                    {
                        //Console.Out.WriteLine("...But the district was full!");
                        //If we've entered here, this district is full. This building, and all after it, become the only elements in remainder,
                        //and we're going to break out of the loop prematurely.
                        remainder = new Building[0];
                        for (int lcv = 0; lcv < Buildings.Length - buildingLCV; lcv++)
                        {
                            remainder = RoomUtilities.expand(remainder);
                            remainder[lcv] = Buildings[buildingLCV + lcv];
                        }
                        buildingLCV = remainder.Length + 1;
                        Districts = District.expand(Districts);
                        Districts[Districts.Length - 1] = new District();
                        districtName = "";
                        //Console.Out.WriteLine("I had to add a new district. The new length is " + Districts.Length);
                        iterationRequired = true;
                    }
                    //else
                    //districtLCV = Districts.Length;
                }
            }
            //else
            //Console.Out.WriteLine("I'm skipping putting buildings in '" + Districts[districtLCV].nameOfDistrict + "' district because the name didn't match '" + districtName + "'" + " (" + Districts[districtLCV].Equals(districtName) + ")");
            //Console.Out.WriteLine("At the end of the loop, there are " + Districts[districtLCV].remainingLotsAvailable() + " lots available in the " + Districts[districtLCV].nameOfDistrict + " district.");

        }
        this.Districts = Districts;

        calculateModifiers();
    }

    public KingdomMods calculateKingdomModifiers()
    {

    }
	
	/**Inner class that stores a district's worth of buildings - typically a settlement only contains a single district*/
    [Serializable()]
    public class District
	{
        public int BLOCK_SIZE = 4;			//The size of a block, in lots
        public int NUMBER_OF_BLOCKS = 9;    //The number of blocks in a district
        public int LOT_SIZE = 750;			//The size of a single lot, in feet

        public Building[] Buildings;
        public String nameOfDistrict;
		
		/**Generic Constructor*/
        public District()
		{
			this.nameOfDistrict = "";
			this.Buildings = new Building[0];
		}
		
		public SettlementMods getTotalModifiers() 
		{
			SettlementMods toBeReturned = new SettlementMods();
			
			for (int lcv = 0; lcv < this.Buildings.Length; lcv++)
			{
				toBeReturned = toBeReturned.combineSettlementMods(this.Buildings[lcv].SettlementModifiers);
			}
			
			return toBeReturned;
		}

		/**Qualified Constructor*/
        public District(String nameOfDistrict)
		{
			this.nameOfDistrict = nameOfDistrict;
			this.Buildings = new Building[0];
		}

        public Building[] populateLots(Building[] Buildings)
		{
			Building[] remainder = new Building[0];
			
			for (int lcv = 0; lcv < Buildings.Length; lcv++)
			{
				bool success = placeBuilding(Buildings[lcv]);
				if (success == false)
				{
					remainder = new Building[Buildings.Length-lcv];
					int indexToGo = Buildings.Length-lcv-1;
					String output = "A new district is being created to house the excess buildings. \nDebug Information: \nBuildings has " + Buildings.Length + " elements\nLCV is " + lcv + "\nremainder is ";
					output += remainder.Length + " elements long\nI'm going to loop from 0 to " + indexToGo + " now";
					Console.Out.WriteLine(output);
					
					for (int innerLCV = 0; innerLCV < Buildings.Length-lcv; innerLCV++)
					{
						int buildingIndex = lcv+innerLCV;

						remainder[innerLCV] = Buildings[buildingIndex];
						Console.Out.WriteLine("I'm placing element #" + buildingIndex + " from Buildings into position #" + innerLCV + " in remainder");
					}
					return remainder;
				}
			}
			
			return null;
		}
		
		/**Returns the maximum lots of a district*/
        public int maxLotsAvailable()
		{
			return BLOCK_SIZE * NUMBER_OF_BLOCKS;
		}
		
		/**Returns the currently occupied lots (the occupied size) of a disctict*/
        public int currentlyOccupiedLots()
		{
			int currentLots = 0;
			for (int lcv = 0; lcv < Buildings.Length; lcv++)
			{
				currentLots += Buildings[lcv].lotSize;
			}
			return currentLots;
		}
		
		/**Returns the remaining lots (the unoccupied size) of a district*/
        public int remainingLotsAvailable()
		{
			return maxLotsAvailable() - currentlyOccupiedLots();
		}
		
		/**Returns true if this building was able to be added to the array, and false otherwise*/
        public bool placeBuilding(Building toBeAdded)
		{
			int remainingSpace = remainingLotsAvailable();
			if (remainingSpace > toBeAdded.lotSize)
			{
				this.Buildings = expand(this.Buildings);
				Buildings[Buildings.Length-1] = toBeAdded;
				return true;
			}
			Console.Out.WriteLine("WARNING: a " + toBeAdded.name + " cannot be placed in this district (no more space)");
			return false;
		}
		
		/**Expand the array by one, adding our new element to the expanded array*/
        public Building[] expand(Building[] oldArray)
		{
			Building[] newArray = new Building[oldArray.Length+1];
			Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
			
			return newArray;
		}
		
		/**Retuns the first instace of a building found in the building array*/
        public int indexOf(String buildingName)
		{
			for (int lcv = 0; lcv < Buildings.Length; lcv++)
			{
				if (Buildings[lcv].name.Equals(buildingName))
					return lcv;
			}
			return -1;
		}

        public string toString()
		{
			String returnString = "";
			if (nameOfDistrict.Equals(""))
				returnString += "\n-----===Buildings===-----\n";
			else
				returnString += "\n-----===" + nameOfDistrict + " District===-----\n";
			for (int lcv = 0; lcv < Buildings.Length; lcv++)
			{
				returnString += Buildings[lcv].toString();
			}
			return returnString;
		}
		
		public int getMinorItemsProduced()
		{
			int returnValue = 0;
			
			for (int lcv = 0; lcv < Buildings.Length; lcv++)
			{
				returnValue += Buildings[lcv].minorItemsProduced;
			}
			
			return returnValue;
		}
		
		public int getMediumItemsProduced()
		{
			int returnValue = 0;
			
			for (int lcv = 0; lcv < Buildings.Length; lcv++)
			{
				returnValue += Buildings[lcv].mediumItemsProduced;
			}
			
			return returnValue;
		}
		
		public int getMajorItemsProduced()
		{
			int returnValue = 0;
			
			for (int lcv = 0; lcv < Buildings.Length; lcv++)
			{
				returnValue += Buildings[lcv].majorItemsProduced;
			}
			
			return returnValue;
		}

        /**Expand the array by one, adding our new element to the expanded array*/
        public static District[] expand(District[] oldArray)
        {
            District[] newArray = new District[oldArray.Length + 1];
            Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);

            return newArray;
        }
	}
}
}
