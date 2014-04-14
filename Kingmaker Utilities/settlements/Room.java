package settlements;

public class Room {
	/**Attributes of the room*/
	String Name;
	float GPEarnings;
	float GoodsEarnings;
	float InfluenceEarnings;
	float LaborEarnings;
	float MagicEarnings;
	float CapitalEarnings;
	String Benefit;
	float GPCreate;
	float GoodsCreate;
	float InfluenceCreate;
	float LaborCreate;
	float MagicCreate;
	float DaysCreate;
	float MinSize;
	float MaxSize;
	String UpgradeFrom;
	String UpgradeTo;
	String Description;
	String SettlementMods;
	
	/**Constructor takes in a raw semicolon-seperated values line, converts it into a room class*/
	Room(String rawCSV)
	{
	    string [] tokens = rawCSV.Split(';');
	    
		this.Name = RoomUtilities.snipQuotes(tokens[0]);
		this.GPEarnings = float.Parse(tokens[1]);
		this.GoodsEarnings = float.Parse(tokens[2]);
		this.InfluenceEarnings = float.Parse(tokens[3]);
		this.LaborEarnings = float.Parse(tokens[4]);
		this.MagicEarnings = float.Parse(tokens[5]);
		this.CapitalEarnings = float.Parse(tokens[6]);
		this.Benefit = RoomUtilities.snipQuotes(tokens[7]);
		this.GPCreate = float.Parse(tokens[8]);
		this.GoodsCreate = float.Parse(tokens[9]);
		this.InfluenceCreate = float.Parse(tokens[10]);
		this.LaborCreate = float.Parse(tokens[11]);
		this.MagicCreate = float.Parse(tokens[12]);
		this.DaysCreate = float.Parse(tokens[13]);
		this.MinSize = float.Parse(tokens[14]);
		this.MaxSize = float.Parse(tokens[15]);
		this.UpgradeFrom = RoomUtilities.snipQuotes(tokens[16]);
		this.UpgradeTo = RoomUtilities.snipQuotes(tokens[17]);
		this.Description = RoomUtilities.snipQuotes(tokens[18]);
		this.SettlementMods = RoomUtilities.snipQuotes(tokens[19]);
	}
	
	/**Constructor generates a blank room*/
	Room()
	{
		this.Name = "";
		this.GPEarnings = 0;
		this.GoodsEarnings = 0;
		this.InfluenceEarnings = 0;
		this.LaborEarnings = 0;
		this.MagicEarnings = 0;
		this.CapitalEarnings = 0;
		this.Benefit = "";
		this.GPCreate = 0;
		this.GoodsCreate = 0;
		this.InfluenceCreate = 0;
		this.LaborCreate = 0;
		this.MagicCreate = 0;
		this.DaysCreate = 0;
		this.MinSize = 0;
		this.MaxSize = 0;
		this.UpgradeFrom = "";
		this.UpgradeTo = "";
		this.Description = "";
		this.SettlementMods = "";
	}
	
	/**Override method to return formatted description of the room*/
	public string  toString()
	{
		String returnString = "\n";
		
		returnString += "**Name: " + this.Name + "**\n";
		if (!(Benefit.Equals("N/A")))
			returnString += "  Benefit: " + this.Benefit + "\n";
		returnString += "  ---------Creation Costs---------\n";
		returnString += "    Create Cost (GP): " + this.GPCreate + "\n";
		returnString += "    Create Cost (Goods): " + this.GoodsCreate + "\n";
		returnString += "    Create Cost (Influence): " + this.InfluenceCreate + "\n";
		returnString += "    Create Cost (Labor): " + this.LaborCreate + "\n";
		returnString += "    Create Cost (Magic): " + this.MagicCreate + "\n";
		returnString += "  -------Earnings Potential-------\n";
		if (GPEarnings != 0)
			returnString += "    Earnings (GP): " + this.GPEarnings + "\n";
		if (GoodsEarnings != 0)
			returnString += "    Earnings (Goods): " + this.GoodsEarnings + "\n";
		if (InfluenceEarnings != 0)
			returnString += "    Earnings (Influence): " + this.InfluenceEarnings + "\n";
		if (LaborEarnings != 0)
			returnString += "    Earnings (Labor): " + this.LaborEarnings + "\n";
		if (MagicEarnings != 0)
			returnString += "    Earnings (Magic): " + this.MagicEarnings + "\n";
		if (CapitalEarnings != 0)
			returnString += "    Earnings (Capital): " + this.CapitalEarnings + "\n";
		returnString += "  Days to build: " + this.DaysCreate + "\n";
		returnString += "  Minimum Room Size: " + this.MinSize + "\n";
		returnString += "  Maximum Room Size: " + this.MaxSize + "\n";
		if (!(UpgradeFrom.Equals("N/A")))
			returnString += "  Upgrades from: " + this.UpgradeFrom + "\n";
		if (!(UpgradeTo.Equals("N/A")))
			returnString += "  Upgrades to: " + this.UpgradeTo + "\n";
		returnString += "  Description: " + this.Description + "\n";
		returnString += "  Settlement Modifiers: " + this.SettlementMods + "\n";
		
		return returnString;		
	}
	
	/**Returns a room class modified by the supplied cost percentages. Should this round up to using the ceiling, in case of non-integer values?*/
	Room getModifiedCost(float percentage)
	{
		Room returnRoom = new Room();
		
		returnRoom.Name = this.Name;
		returnRoom.GPEarnings = this.GPEarnings;
		returnRoom.GoodsEarnings = this.GoodsEarnings;
		returnRoom.InfluenceEarnings = this.InfluenceEarnings;
		returnRoom.LaborEarnings = this.LaborEarnings;
		returnRoom.MagicEarnings = this.MagicEarnings;
		returnRoom.CapitalEarnings = this.CapitalEarnings;
		returnRoom.Benefit = this.Benefit;
		returnRoom.GPCreate = this.GPCreate * percentage;
		returnRoom.GoodsCreate = this.GoodsCreate * percentage;
		returnRoom.InfluenceCreate = this.InfluenceCreate * percentage;
		returnRoom.LaborCreate = this.LaborCreate * percentage;
		returnRoom.MagicCreate = this.MagicCreate * percentage;
		returnRoom.DaysCreate = this.DaysCreate * percentage;
		returnRoom.MinSize = this.MinSize;
		returnRoom.MaxSize = this.MaxSize;
		returnRoom.UpgradeFrom = this.UpgradeFrom;
		returnRoom.UpgradeTo = this.UpgradeTo;
		returnRoom.Description = this.Description;
		returnRoom.SettlementMods = this.SettlementMods;
		
		return returnRoom;
	}

}
