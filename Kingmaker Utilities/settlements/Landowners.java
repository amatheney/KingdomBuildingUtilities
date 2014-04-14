package settlements;

public class Landowners 
{
	Owner[] owners;
	Settlement derivedSettlement;
	
	public Landowners()
	{
		owners = new Owner[0];
		derivedSettlement = new Settlement();
	}
	
	public Landowners(Owner[] owners, Settlement derivedSettlement)
	{
		this.owners = owners;
		this.derivedSettlement = derivedSettlement;
		
		loadOwners();
	}
	
	//Constructor that takes a set of filenames and builds an array of owners, storing them in a settlement (also loaded from the file)
	public Landowners(String[] ownerCSVStrings, string  settlementCSV, Building[] completeBuildingList, Quality[] completeQualityList)
	{
		derivedSettlement = new Settlement(settlementCSV, completeBuildingList, completeQualityList);
		
		owners = new Owner[ownerCSVStrings.Length];
		for (int lcv = 0; lcv < owners.Length; lcv++)
		{
			owners[lcv] = new Owner(ownerCSVStrings[lcv], completeBuildingList);
		}
		
		loadOwners();
	}
	
	//Load the owners from the owner array into the settlement
	private void loadOwners()
	{
		//First, remove all buildings from the settlement that are owned
		Building[] replacementList = new Building[0];
		//Console.Out.WriteLine("At the start of loadOwners, there are " + derivedSettlement.Districts.Length + " districts.");
		for (int districtIndex = 0; districtIndex < derivedSettlement.Districts.Length; districtIndex++)
		{
			for (int buildingIndex = 0; buildingIndex < derivedSettlement.Districts[districtIndex].Buildings.Length; buildingIndex++)
			{
				if(!derivedSettlement.Districts[districtIndex].Buildings[buildingIndex].owner.Equals(""))
				{
					//Console.Out.WriteLine("The building " + derivedSettlement.Districts[districtIndex].Buildings[buildingIndex].name + " is owned by '" + derivedSettlement.Districts[districtIndex].Buildings[buildingIndex].owner + "'");
					replacementList = RoomUtilities.expand(replacementList);
					//Console.Out.WriteLine("The length of replacementList is " + (replacementList.Length) + "");
					replacementList[replacementList.Length-1] = derivedSettlement.Districts[districtIndex].Buildings[buildingIndex];
				}
			}
		}
		//Console.Out.WriteLine("At the middle of loadOwners, there are " + derivedSettlement.Districts.Length + " districts.");
		
		//Now, loop through the owners and place their buildings
		for (int lcv = 0; lcv < owners.Length; lcv++)
		{
			//Console.Out.WriteLine("Adding buildings for " + owners[lcv].OwnerName);
			//Building[] toBeAdded = owners[lcv].properties;
			//derivedSettlement.Districts = derivedSettlement.BuildDistricts(toBeAdded, derivedSettlement.Districts, "");
		}
		//Console.Out.WriteLine("At the end of loadOwners, there are " + derivedSettlement.Districts.Length + " districts.");
		
		//Regnerate all the attributes of the settlement when done
		derivedSettlement.calculateModifiers();
	}
}
