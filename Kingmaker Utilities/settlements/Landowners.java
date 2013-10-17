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
	public Landowners(String[] ownerCSVStrings, String settlementCSV, Building[] completeBuildingList, Quality[] completeQualityList)
	{
		derivedSettlement = new Settlement(settlementCSV, completeBuildingList, completeQualityList);
		
		owners = new Owner[ownerCSVStrings.length];
		for (int lcv = 0; lcv < owners.length; lcv++)
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
		//System.out.println("At the start of loadOwners, there are " + derivedSettlement.Districts.length + " districts.");
		for (int districtIndex = 0; districtIndex < derivedSettlement.Districts.length; districtIndex++)
		{
			for (int buildingIndex = 0; buildingIndex < derivedSettlement.Districts[districtIndex].Buildings.length; buildingIndex++)
			{
				if(!derivedSettlement.Districts[districtIndex].Buildings[buildingIndex].owner.equals(""))
				{
					//System.out.println("The building " + derivedSettlement.Districts[districtIndex].Buildings[buildingIndex].name + " is owned by '" + derivedSettlement.Districts[districtIndex].Buildings[buildingIndex].owner + "'");
					replacementList = RoomUtilities.expand(replacementList);
					//System.out.println("The length of replacementList is " + (replacementList.length) + "");
					replacementList[replacementList.length-1] = derivedSettlement.Districts[districtIndex].Buildings[buildingIndex];
				}
			}
		}
		//System.out.println("At the middle of loadOwners, there are " + derivedSettlement.Districts.length + " districts.");
		
		//Now, loop through the owners and place their buildings
		for (int lcv = 0; lcv < owners.length; lcv++)
		{
			//System.out.println("Adding buildings for " + owners[lcv].OwnerName);
			Building[] toBeAdded = owners[lcv].properties;
			derivedSettlement.Districts = derivedSettlement.BuildDistricts(toBeAdded, derivedSettlement.Districts, "");
		}
		//System.out.println("At the end of loadOwners, there are " + derivedSettlement.Districts.length + " districts.");
		
		//Regnerate all the attributes of the settlement when done
		derivedSettlement.calculateModifiers();
	}
}
