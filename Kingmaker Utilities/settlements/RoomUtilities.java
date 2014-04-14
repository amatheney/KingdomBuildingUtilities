package settlements;

import settlements.Settlement.District;


public class RoomUtilities 
{
	/**Determines if any of the rooms in the supplied array generate gold pieces for income*/
	static bool isGPEarnable(Room[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isGoodsEarnable(Room[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isLaborEarnable(Room[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isInfluenceEarnable(Room[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isMagicEarnable(Room[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isCapitalEarnable(Room[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isGPEarnable(Team[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isGoodsEarnable(Team[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isLaborEarnable(Team[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isInfluenceEarnable(Team[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isMagicEarnable(Team[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
	static bool isCapitalEarnable(Team[] array)
	{
		bool found = false;
		//Loop over array
		for (int lcv = 0; lcv < array.Length; lcv++)
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
		for (int lcv = 0; lcv < array.Length; lcv++)
		{
			if (array[lcv].Name.Equals(nameOfRoom))
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
		for (int lcv = 0; lcv < array.Length; lcv++)
		{
			if (array[lcv].name.Equals(nameOfBuilding))
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
		for (int lcv = 0; lcv < array.Length; lcv++)
		{
			if (array[lcv].name.Equals(nameOfQuality))
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
		for (int lcv = 0; lcv < array.Length; lcv++)
		{
			if (array[lcv].Name.Equals(nameOfTeam))
			{
				index = lcv;
			}
		}
		
		return index;
	}
	
	/**Generate an array of Rooms that represent a building*/
	static Room[] populateBuilding(String[] listOfRooms, Room[] completeRoomList)
	{
		Room[] returnRooms = new Room[listOfRooms.Length];
		//Console.Out.WriteLine("Length of returnRooms: " + returnRooms.Length);
		//Console.Out.WriteLine("Length of ListOfRooms: " + listOfRooms.Length);
		//Console.Out.WriteLine("Name of index 20: " + completeRoomList[20].Name);
		for (int lcv = 0; lcv < listOfRooms.Length; lcv++)
		{
			int RoomListIndex = indexOf(listOfRooms[lcv], completeRoomList);
			if (RoomListIndex == -1)
			{
				Console.Out.WriteLine("ERROR: the room \"" + listOfRooms[lcv] + "\" was not found in the Room DB");
			}
			//Console.Out.WriteLine("Index of " + listOfRooms[lcv] + ": " + RoomListIndex);
			returnRooms[lcv] = completeRoomList[RoomListIndex];
		}
		
		return returnRooms;
	}
	
	/**Utility method to pull in a custom building, such as a player-created structure*/
	static Room[] populateCustomBuildingFromFile(String filename, Room[] completeRoomList)
	{
		BuildingFileReader building = new BuildingFileReader("settlements\\" + filename, "UTF-8");
		String[] roomList = new string [1];
		try
		{
			roomList = building.readTextFile();
		}
		catch (Exception e)
		{
			Console.Out.WriteLine("Error: " + e.Message);
		}
		return populateBuilding(roomList, completeRoomList);
	}
	
	/**Remove quotes from a string */
	static string  snipQuotes(String toSnip)
	{
		toSnip = toSnip.replace("\"","");//that does not work because there is no such character
		return toSnip;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Building[] expand(Building[] oldArray)
	{
		Building[] newArray = new Building[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Owner[] expand(Owner[] oldArray)
	{
		Owner[] newArray = new Owner[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Skill[] expand(Skill[] oldArray)
	{
		Skill[] newArray = new Skill[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Room[] expand(Room tempElement, Room[] oldArray)
	{
		Room[] newArray = new Room[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static FurnishingsAndTraps[] expand(FurnishingsAndTraps tempElement, FurnishingsAndTraps[] oldArray)
	{
		FurnishingsAndTraps[] newArray = new FurnishingsAndTraps[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static string [] expandStringArray(String tempElement, string [] oldArray)
	{
		String[] newArray = new string [oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static District[] expand(District[] oldArray)
	{
		District[] newArray = new District[oldArray.Length+1];
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Settlement[] expand(Settlement tempElement, Settlement[] oldArray)
	{
		Settlement[] newArray = new Settlement[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Quality[] expand(Quality[] oldArray)
	{
		Quality[] newArray = new Quality[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	static Manager[] expand(Manager[] oldArray)
	{
		Manager[] newArray = new Manager[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	public static Team[] expand(Team[] oldArray) 
	{
		Team[] newArray = new Team[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
	
	/**Expand the array by one, adding our new element to the expanded array*/
	public static Organization[] expand(Organization[] oldArray) 
	{
		Organization[] newArray = new Organization[oldArray.Length+1];
		
		Array.Copy(oldArray, 0, newArray, 0, oldArray.Length);
		
		return newArray;
	}
}
