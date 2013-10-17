package settlements;

public class Team 
{
	/**Attributes of the Team*/
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
	
	public Team()
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
	}
	
	public Team(Team input)
	{
		this.Name = input.Name;
		this.GPEarnings = input.GPEarnings;
		this.GoodsEarnings = input.GoodsEarnings;
		this.InfluenceEarnings = input.InfluenceEarnings; 
		this.LaborEarnings = input.LaborEarnings;
		this.MagicEarnings = input.MagicEarnings;
		this.CapitalEarnings = input.CapitalEarnings;
		this.Benefit = input.Benefit;
		this.GPCreate = input.GPCreate;
		this.GoodsCreate = input.GoodsCreate; 
		this.InfluenceCreate = input.InfluenceCreate; 
		this.LaborCreate = input.LaborCreate;
		this.MagicCreate = input.MagicCreate;
		this.DaysCreate = input.DaysCreate;
		this.MinSize = input.MinSize;
		this.MaxSize = input.MaxSize;
		this.UpgradeFrom = input.UpgradeFrom;
		this.UpgradeTo = input.UpgradeTo;
		this.Description = input.Description;
	}
	
	public Team(String rawCSV)
	{	
		String[] tokens = rawCSV.split("\\;");
		
		this.Name = RoomUtilities.snipQuotes(tokens[0]);
		this.GPEarnings = Integer.parseInt(tokens[1]);
		this.GoodsEarnings = Integer.parseInt(tokens[2]);
		this.InfluenceEarnings = Integer.parseInt(tokens[3]); 
		this.LaborEarnings = Integer.parseInt(tokens[4]);
		this.MagicEarnings = Integer.parseInt(tokens[5]);
		this.CapitalEarnings = Integer.parseInt(tokens[6]);
		this.Benefit = RoomUtilities.snipQuotes(tokens[7]);
		this.GPCreate = Integer.parseInt(tokens[8]);
		this.GoodsCreate = Integer.parseInt(tokens[9]); 
		this.InfluenceCreate = Integer.parseInt(tokens[10]); 
		this.LaborCreate = Integer.parseInt(tokens[11]);
		this.MagicCreate = Integer.parseInt(tokens[12]);
		this.DaysCreate = Integer.parseInt(tokens[13]);
		this.MinSize = Integer.parseInt(tokens[14]);
		this.MaxSize = Integer.parseInt(tokens[15]);
		this.UpgradeFrom = RoomUtilities.snipQuotes(tokens[16]);
		this.UpgradeTo = RoomUtilities.snipQuotes(tokens[17]);
		this.Description = RoomUtilities.snipQuotes(tokens[18]);
	}
	
	/**Override method to return formatted description of the room*/
	public String toString()
	{
		String returnString = "\n";
		
		returnString += "**Name: " + this.Name + "**\n";
		if (!(Benefit.equals("N/A")))
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
		if (!(UpgradeFrom.equals("N/A")))
			returnString += "  Upgrades from: " + this.UpgradeFrom + "\n";
		if (!(UpgradeTo.equals("N/A")))
			returnString += "  Upgrades to: " + this.UpgradeTo + "\n";
		returnString += "  Description: " + this.Description + "\n";
		
		return returnString;		
	}
}