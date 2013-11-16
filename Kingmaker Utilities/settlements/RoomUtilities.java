package settlements;

import settlements.Settlement.District;


public class RoomUtilities 
{
	/**Determines if any of the rooms in the supplied array generate gold pieces for income*/
	static boolean isGPEarnable(Room[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].GPEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate goods for income*/
	static boolean isGoodsEarnable(Room[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].GoodsEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate labor for income*/
	static boolean isLaborEarnable(Room[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].LaborEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate influence for income*/
	static boolean isInfluenceEarnable(Room[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].InfluenceEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate magic for income*/
	static boolean isMagicEarnable(Room[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].MagicEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate capital for income*/
	static boolean isCapitalEarnable(Room[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].CapitalEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	
	/**Determines if any of the rooms in the supplied array generate gold pieces for income*/
	static boolean isGPEarnable(Team[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].GPEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate goods for income*/
	static boolean isGoodsEarnable(Team[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].GoodsEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate labor for income*/
	static boolean isLaborEarnable(Team[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].LaborEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate influence for income*/
	static boolean isInfluenceEarnable(Team[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].InfluenceEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate magic for income*/
	static boolean isMagicEarnable(Team[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].MagicEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	/**Determines if any of the rooms in the supplied array generate capital for income*/
	static boolean isCapitalEarnable(Team[] array)
	{
		boolean found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].CapitalEarnings > 0)
			{
				found = true;
				return found;
			}
		}
		
		return found;
	}
	
	/**Return the integrer index of the room with the given name within the supplied array*/
	static int indexOf(String nameOfRoom, Room[] array)
	{
		int index = -1;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].Name.equals(nameOfRoom))
			{
				index = lcv;
			}
		}
		
		return index;
	}
	
	/**Return the integrer index of the building with the given name within the supplied array*/
	static int indexOf(String nameOfBuilding, Building[] array)
	{
		int index = -1;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].name.equals(nameOfBuilding))
			{
				index = lcv;
			}
		}
		
		return index;
	}
	
	/**Return the integrer index of the Quality with the given name within the supplied array*/
	static int indexOf(String nameOfQuality, Quality[] array)
	{
		int index = -1;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].name.equals(nameOfQuality))
			{
				index = lcv;
			}
		}
		
		return index;
	}
	
	/**Return the integrer index of the Team with the given name within the supplied array*/
	static int indexOf(String nameOfTeam, Team[] array)
	{
		int index = -1;
		//Loop over array
		for (int lcv = 0; lcv < array.length; lcv++)
		{
			if (array[lcv].Name.equals(nameOfTeam))
			{
				index = lcv;
			}
		}
		
		return index;
	}
	
	/**Generate an array of Rooms that represent a building*/
	static Room[] populateBuilding(String[] listOfRooms, Room[] completeRoomList)
	{
		Room[] returnRooms = new Room[listOfRooms.length];
		//System.out.println("Length of returnRooms: " + returnRooms.length);
		//System.out.println("Length of ListOfRooms: " + listOfRooms.length);
		//System.out.println("Name of index 20: " + completeRoomList[20].Name);
		for (int lcv = 0; lcv < listOfRooms.length; lcv++)
		{
			int RoomListIndex = indexOf(listOfRooms[lcv], completeRoomList);
			if (RoomListIndex == -1)
			{
				System.out.println("ERROR: the room \"" + listOfRooms[lcv] + "\" was not found in the Room DB");
			}
			//System.out.println("Index of " + listOfRooms[lcv] + ": " + RoomListIndex);
			returnRooms[lcv] = completeRoomList[RoomListIndex];
		}
		
		return returnRooms;
	}
	
	/**Utility method to pull in a custom building, such as a player-created structure*/
	static Room[] populateCustomBuildingFromFile(String filename, Room[] completeRoomList)
	{
		BuildingFileReader building = new BuildingFileReader("settlements\\" + filename, "UTF-8");
		String[] roomList = new String[1];
		try
		{
			roomList = building.readTextFile();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		return populateBuilding(roomList, completeRoomList);
	}
	
	/**Remove quotes from a string*/
	static String snipQuotes(String toSnip)
	{
		toSnip = toSnip.replace("\"","");//that does not work because there is no such character
		return toSnip;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Building[] expand(Building[] oldArray)
	{
		Building[] newArray = new Building[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Owner[] expand(Owner[] oldArray)
	{
		Owner[] newArray = new Owner[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Skill[] expand(Skill[] oldArray)
	{
		Skill[] newArray = new Skill[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Room[] expand(Room tempElement, Room[] oldArray)
	{
		Room[] newArray = new Room[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static FurnishingsAndTraps[] expand(FurnishingsAndTraps tempElement, FurnishingsAndTraps[] oldArray)
	{
		FurnishingsAndTraps[] newArray = new FurnishingsAndTraps[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static String[] expandStringArray(String tempElement, String[] oldArray)
	{
		String[] newArray = new String[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static District[] expand(District[] oldArray)
	{
		District[] newArray = new District[oldArray.length+1];
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Settlement[] expand(Settlement tempElement, Settlement[] oldArray)
	{
		Settlement[] newArray = new Settlement[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Quality[] expand(Quality[] oldArray)
	{
		Quality[] newArray = new Quality[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Manager[] expand(Manager[] oldArray)
	{
		Manager[] newArray = new Manager[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	public static Team[] expand(Team[] oldArray) 
	{
		Team[] newArray = new Team[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	public static Organization[] expand(Organization[] oldArray) 
	{
		Organization[] newArray = new Organization[oldArray.length+1];
		
		System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
		
		return newArray;
	}
}
