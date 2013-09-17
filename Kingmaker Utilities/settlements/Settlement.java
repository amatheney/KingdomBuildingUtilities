package settlements;

public class Settlement 
{
	String Name;
	String Alignment;
	String Size;
	int Population;
	SettlementMods Modifiers;
	Quality[] Qualities;
	String notableNPCs;
	int BaseValue;
	int PurchaseLimit;
	int Spellcasting;
	int MinorItems;
	int MediumItems;
	int MajorItems;
	District[] Districts;
	
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
		
		/**Qualified Constructor*/
		private District(String nameOfDistrict, Building[] buildings)
		{
			this.nameOfDistrict = nameOfDistrict;
			this.Buildings = buildings;
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
	}
}
