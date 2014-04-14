package settlements;

public class CentralCommand
{

	private static Room[] completeRoomList;
	private static FurnishingsAndTraps[] completeFurnishingList;
	private static Building[] completeBuildingList;
	private static Quality[] completeQualityList;
	private static Team[] completeTeamList;
	private static Organization[] completeOrganizationList;
	private static Manager[] completeManagerList;
	
	private staticString completeRoomFilename = "settlements\\roomCSV.txt";
	private staticString completeFurnishingFilename = "settlements\\FurnishingsAndTrapsCSV.txt";
	private staticString completeBuildingFilename = "settlements\\buildingsCSV.txt";
	private staticString completeQualityFilename = "settlements\\QualitiesCSV.txt";
	private staticString completeTeamFilename = "settlements\\TeamCSV.txt";
	private staticString completeOrganizationFilename = "settlements\\OrganizationCSV.txt";
	private staticString completeManagerFilename = "settlements\\ManagerCSV.txt";
	
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
			Console.Out.WriteLine("Error: " + e.Message);
		}
		Console.Out.WriteLine(completeList.Length + " room entries read");
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
			Console.Out.WriteLine("Error: " + e.Message);
		}
		Console.Out.WriteLine(completeList.Length + " furnishing/trap entries read");
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
			Console.Out.WriteLine("Error: " + e.Message + "\n" + e.toString());
		}
		Console.Out.WriteLine(completeList.Length + " quality entries read");
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
			Console.Out.WriteLine("Error: " + e.Message);
		}
		Console.Out.WriteLine(completeBuildingList.Length + " Building entries read");
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
			Console.Out.WriteLine("***An exception has occured.***");
			Console.Out.WriteLine("Error: " + e.Message);
			e.printStackTrace();
		}
		Console.Out.WriteLine(completeOwnerList.Length + " Owner entries read");
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
			//Console.Out.WriteLine("In getSettlementDB, the first settlement has " + completeSettlementList[0].Districts.Length + " districts.");
		}
		catch (Exception e)
		{
			Console.Out.WriteLine("Error: " + e.Message);
		}
		Console.Out.WriteLine(completeSettlementList.Length + " Settlement entries read");
		return completeSettlementList;
	}
	
	/**Parses a Team from a file, used to pull the compelte list of Team in*/
	private static Team[] getTeamDB(String filename)
	{
		TeamFileReader TeamReader = new TeamFileReader(filename, "UTF-8");
		Team[] completeTeam = new Team[0];
		try
		{
			completeTeamList = TeamReader.read();
		}
		catch (Exception e)
		{
			Console.Out.WriteLine("Error: " + e.Message);
		}
		Console.Out.WriteLine(completeTeamList.Length + " Team entries read");
		return completeTeamList;
	}
	
	/**Parses a Organization from a file, used to pull the compelte list of Organization in*/
	private static Organization[] getOrganizationDB(String filename)
	{
		OrganizationFileReader OrganizationReader = new OrganizationFileReader(filename, "UTF-8");
		Organization[] completeOrganizationList = new Organization[0];
		try
		{
			completeOrganizationList = OrganizationReader.read(completeTeamList);
			//Console.Out.WriteLine("In getSettlementDB, the first settlement has " + completeSettlementList[0].Districts.Length + " districts.");
		}
		catch (Exception e)
		{
			Console.Out.WriteLine("Error: " + e.Message);
		}
		Console.Out.WriteLine(completeOrganizationList.Length + " Organization entries read");
		return completeOrganizationList;
	}
	
	/**Parses a Manager from a file, used to pull the compelte list of Manager in*/
	private static Manager[] getManagerDB(String filename)
	{
		ManagerFileReader ManagerReader = new ManagerFileReader(filename, "UTF-8");
		Manager[] completeManagerList = new Manager[0];
		try
		{
			completeManagerList = ManagerReader.read();
			//Console.Out.WriteLine("In getSettlementDB, the first settlement has " + completeSettlementList[0].Districts.Length + " districts.");
		}
		catch (Exception e)
		{
			Console.Out.WriteLine("Error: " + e.Message);
		}
		Console.Out.WriteLine(completeManagerList.Length + " Manager entries read");
		return completeManagerList;
	}
	
	public CentralCommand(String ownerFilename, string  settlementFilename)
	{		
		completeRoomList = getRoomDB(completeRoomFilename);
		completeFurnishingList = getFurnishingsDB(completeFurnishingFilename);
		completeBuildingList = getBuildingDB(completeBuildingFilename, completeRoomList, completeFurnishingList);
		completeQualityList = getQualityDB(completeQualityFilename);
		completeTeamList = getTeamDB(completeTeamFilename);
		completeOrganizationList = getOrganizationDB(completeOrganizationFilename);
		completeManagerList = getManagerDB(completeManagerFilename);
		
		Owner[] owners = getOwnerDB(ownerFilename);
		Settlement[] settlement = getSettlementDB(settlementFilename);
		Console.Out.WriteLine(settlement.Length + " settlements produced");
		
		//Remove this
		/*
		for (int lcv = 0; lcv < settlement[0].Districts.Length; lcv++)
		{
			//Console.Out.WriteLine("looping through district #" + lcv);
			for (int i = 0; i < settlement[0].Districts[lcv].Buildings.Length; i++)
			{
				Console.Out.WriteLine(settlement[0].Districts[lcv].Buildings[i].name + " is owned by " + settlement[0].Districts[lcv].Buildings[i].owner);
			}
		}*/
		
		command = new Landowners(owners, settlement[0]);
		//Console.Out.WriteLine("In central command constructor, Landowers has " + command.derivedSettlement.Districts.Length + " districts in the settlement");
	}
	
	public string  basicCommandInfo()
	{
		String returnString = "\n----List of Property Owners & Basic Settlement information----";
		
		returnString += "\n----===Settlements===----";
		returnString += this.command.derivedSettlement.toString();	//Eventually this will be an array, and we're going to put kingdom information here
		
		returnString += "\n----===Landowers===----\n";
		for (int lcv = 0; lcv < this.command.owners.Length; lcv++)
		{
			returnString += this.command.owners[lcv].toString() + "\n";
		}
		
		return returnString;
	}
	
	public static void main(String[] args) 
	{
		CentralCommand base = new CentralCommand("settlements\\OwnersCSV.txt", "settlements\\settlementExample.txt");
		Console.Out.WriteLine(base.command.derivedSettlement.partialBuildingOutput());
		//Console.Out.WriteLine(base.basicCommandInfo());
	}
}
