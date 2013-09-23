package settlements;


public class RoomUtilities 
{
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
}
