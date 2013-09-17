package settlements;

/**Container class to store room augmentations, to hopefully someday include traps*/
public class FurnishingsAndTraps 
{
	String Name;
	String Benefit;
	String Description;
	int GPCreate;
	int GoodsCreate;
	int InfluenceCreate;
	int LaborCreate;
	int MagicCreate;
	int DaysCreate;
	
	boolean trap;				//Always false, for now...
	String associatedRoom;
	
	/**Generic constructor*/
	public FurnishingsAndTraps()
	{
		this.Name = "";
		this.Benefit = "";
		this.Description = "";
		this.GPCreate = 0;
		this.GoodsCreate = 0;
		this.InfluenceCreate = 0;
		this.LaborCreate = 0;
		this.MagicCreate = 0;
		this.DaysCreate = 0;
		
		this.trap = false;
		this.associatedRoom = "N/A";
	}
	
	/**Takes a semicolon-delimited string, and parses it into into a room augmentation*/
	public FurnishingsAndTraps(String rawCSV)
	{
		String[] tokens = rawCSV.split("\\;");
		
		this.Name = RoomUtilities.snipQuotes(tokens[0]);
		this.Benefit = RoomUtilities.snipQuotes(tokens[1]);
		this.Description = RoomUtilities.snipQuotes(tokens[2]);
		this.GPCreate = Integer.parseInt(tokens[3]);
		this.GoodsCreate = Integer.parseInt(tokens[4]);
		this.InfluenceCreate = Integer.parseInt(tokens[5]);
		this.LaborCreate = Integer.parseInt(tokens[6]);
		this.MagicCreate = Integer.parseInt(tokens[7]);
		this.DaysCreate = Integer.parseInt(tokens[8]);
		
		this.trap = false;
		this.associatedRoom = "N/A";
	}
	
	public String toString()
	{
		String returnString = "\n----=Custom Furnishing=----\n";
		String trapString = "";
		if (trap)
			trapString = "trap";
		if (!trap)
			trapString = "furnishing";
		if (associatedRoom.equals("N/A"))
			returnString += "This " + trapString + " is not associated with a particular room.\n";
		if (!(associatedRoom.equals("N/A")))
			returnString += "This " + trapString + " is associated with a " + associatedRoom + " in the building\n";
		returnString += "Name: " + Name + "\n";
		if (!(Benefit.equals("N/A")))
			returnString += "Benefit: " + Benefit + "\n";
		returnString += "Description: " + Description + "\n";
		returnString += "---Creation Costs---\n";
		if (GPCreate != 0)
			returnString += "  Creation Cost (GP): " + GPCreate + "gp\n";
		if (GoodsCreate != 0)
			returnString += "  Creation Cost (Goods): " + GoodsCreate + "\n";
		if (InfluenceCreate != 0)
			returnString += "  Creation Cost (Influence): " + InfluenceCreate + "\n";
		if (LaborCreate != 0)
			returnString += "  Creation Cost (Labor): " + LaborCreate + "\n";
		if (MagicCreate != 0)
			returnString += "  Creation Cost (Magic): " + MagicCreate + "\n";
		returnString += "Days to create: " + DaysCreate + " days\n";
		
		return returnString;
	}

	
}