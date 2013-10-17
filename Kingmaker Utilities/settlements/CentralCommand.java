package settlements;

public class CentralCommand 
{

	private static Room[] completeRoomList;
	private static FurnishingsAndTraps[] completeFurnishingList;
	private static Building[] completeBuildingList;
	private static Quality[] completeQualityList;
	
	private static final String completeRoomFilename = "settlements\\roomCSV.txt";
	private static final String completeFurnishingFilename = "settlements\\FurnishingsAndTrapsCSV.txt";
	private static final String completeBuildingFilename = "settlements\\buildingsCSV.txt";
	private static final String completeQualityFilename = "settlements\\QualitiesCSV.txt";
	
	private Landowners command;
	
	/**Parses rooms from a file, used to pull the complete list of rooms in*/
	private static Room[] getRoomDB(String filename)
	{
		RoomFileReader roomBuilder = new RoomFileReader(filename, "UTF-8");
		Room[] completeList = new Room[1];
		try
		{
			completeList = roomBuilder.read();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		System.out.println(completeList.length + " room entries read");
		return completeList;
	}
	
	/**Parses furnishings from a file, used to pull the compelte list of augmentations in*/
	private static FurnishingsAndTraps[] getFurnishingsDB(String filename)
	{
		FurnishingsAndTrapsFileReader FurnishingBuilding = new FurnishingsAndTrapsFileReader(filename, "UTF-8");
		FurnishingsAndTraps[] completeList = new FurnishingsAndTraps[1];
		try
		{
			completeList = FurnishingBuilding.read();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		System.out.println(completeList.length + " furnishing/trap entries read");
		return completeList;
	}
	
	/**Parses furnishings from a file, used to pull the compelte list of augmentations in*/
	private static Quality[] getQualityDB(String filename)
	{
		QualityFileReader QualityReader = new QualityFileReader(filename, "UTF-8");
		Quality[] completeList = new Quality[1];
		try
		{
			completeList = QualityReader.read();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage() + "\n" + e.toString());
		}
		System.out.println(completeList.length + " quality entries read");
		return completeList;
	}
	
	/**Parses Buildings from a file, used to pull the compelte list of Buildings in*/
	private static Building[] getBuildingDB(String filename, Room[] completeList, FurnishingsAndTraps[] completeFurnishingList)
	{
		BuildingFileReader buildingBuilder = new BuildingFileReader(filename, "UTF-8");
		Building[] completeBuildingList = new Building[1];
		try
		{
			completeBuildingList = buildingBuilder.read(completeList, completeFurnishingList);
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		System.out.println(completeBuildingList.length + " Building entries read");
		return completeBuildingList;
	}
	
	/**Parses Owners from a file, used to pull the compelte list of Owners in*/
	private static Owner[] getOwnerDB(String filename)
	{
		OwnerFileReader OwnerReader = new OwnerFileReader(filename, "UTF-8");
		Owner[] completeOwnerList = new Owner[1];
		try
		{
			completeOwnerList = OwnerReader.read(completeBuildingList);
		}
		catch (Exception e)
		{
			System.out.println("***An exception has occured.***");
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println(completeOwnerList.length + " Owner entries read");
		return completeOwnerList;
	}
	
	/**Parses a Settlement from a file, used to pull the compelte list of Settlements in*/
	private static Settlement[] getSettlementDB(String filename)
	{
		SettlementFileReader SettlementReader = new SettlementFileReader(filename, "UTF-8");
		Settlement[] completeSettlementList = new Settlement[0];
		try
		{
			completeSettlementList = SettlementReader.read(completeBuildingList, completeQualityList);
			//System.out.println("In getSettlementDB, the first settlement has " + completeSettlementList[0].Districts.length + " districts.");
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		System.out.println(completeSettlementList.length + " Settlement entries read");
		return completeSettlementList;
	}
	
	public CentralCommand(String ownerFilename, String settlementFilename)
	{		
		completeRoomList = getRoomDB(completeRoomFilename);
		completeFurnishingList = getFurnishingsDB(completeFurnishingFilename);
		completeBuildingList = getBuildingDB(completeBuildingFilename, completeRoomList, completeFurnishingList);
		completeQualityList = getQualityDB(completeQualityFilename);
		
		Owner[] owners = getOwnerDB(ownerFilename);
		Settlement[] settlement = getSettlementDB(settlementFilename);
		System.out.println(settlement.length + " settlements produced");
		
		//Remove this
		/*
		for (int lcv = 0; lcv < settlement[0].Districts.length; lcv++)
		{
			//System.out.println("looping through district #" + lcv);
			for (int i = 0; i < settlement[0].Districts[lcv].Buildings.length; i++)
			{
				System.out.println(settlement[0].Districts[lcv].Buildings[i].name + " is owned by " + settlement[0].Districts[lcv].Buildings[i].owner);
			}
		}*/
		
		command = new Landowners(owners, settlement[0]);
		//System.out.println("In central command constructor, Landowers has " + command.derivedSettlement.Districts.length + " districts in the settlement");
	}
	
	public String basicCommandInfo()
	{
		String returnString = "\n----List of Property Owners & Basic Settlement information----";
		
		returnString += "\n----===Settlements===----";
		returnString += this.command.derivedSettlement.toString();	//Eventually this will be an array, and we're going to put kingdom information here
		
		returnString += "\n----===Landowers===----\n";
		for (int lcv = 0; lcv < this.command.owners.length; lcv++)
		{
			returnString += this.command.owners[lcv].toString() + "\n";
		}
		
		return returnString;
	}
	
	public static void main(String[] args) 
	{
		CentralCommand base = new CentralCommand("settlements\\OwnersCSV.txt", "settlements\\settlementExample.txt");
		System.out.println(base.command.derivedSettlement.partialBuildingOutput());
		//System.out.println(base.basicCommandInfo());
	}
}
