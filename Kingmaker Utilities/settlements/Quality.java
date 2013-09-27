package settlements;

/**Inner class that stores all the qualities, disadvantages, and governments of the settlement, proving an easy-to-iterate structure for calculating total modifiers*/
public class Quality
{
	SettlementMods modifiers;
	String name;
	String description;
	String type; 				//Advantage/Disadvantage/Government
	double purchaseLimitPercentageModifier;
	double baseValuePercentageModifier;
	int magicItemModifier;
	int spellcastingModifier;
	String special;
	
	/**Generic constructor*/
	public Quality()
	{
		this.modifiers = new SettlementMods();
		this.name = "";
		this.description = "";
		this.type = "Advantage";
		this.purchaseLimitPercentageModifier = 0.0d;
		this.baseValuePercentageModifier = 0.0d;
		this.magicItemModifier = 0;
		this.spellcastingModifier = 0;
		this.special = "";
	}
	
	/**Generic constructor*/
	public Quality(String rawCSV)
	{
		String[] tokens = rawCSV.split("\\;");
		
		/*for (int lcv = 0; lcv < tokens.length; lcv++)
		{
			System.out.println("Token #" + lcv + ": " + tokens[lcv]);
		}*/
		
		int settlementCorruptionModifier = Integer.parseInt(tokens[3]);			//Corruption	[3]
		int settlementCrimeModifier  = Integer.parseInt(tokens[4]);				//Crime			[4]
		int settlementLawModifier = Integer.parseInt(tokens[5]);				//Law			[5]
		int settlementLoreModifier  = Integer.parseInt(tokens[6]);				//Lore			[6]
		int settlementProductivityModifier = Integer.parseInt(tokens[7]);		//Productivity	[7]
		int settlementSocietyModifier  = Integer.parseInt(tokens[8]);			//Society		[8]		
		int settlementDangerModifier  = Integer.parseInt(tokens[9]);			//Danger		[9]
		int settlementBaseValueModifier  = 0;									//This is calculated differently for qualities.
		
		this.modifiers = new SettlementMods(settlementCorruptionModifier,settlementCrimeModifier,settlementLawModifier,settlementLoreModifier, 
				settlementSocietyModifier,settlementDangerModifier,settlementProductivityModifier,settlementBaseValueModifier);			//3-9
		
		this.name = RoomUtilities.snipQuotes(tokens[0]);						//0
		this.description = RoomUtilities.snipQuotes(tokens[1]);					//1
		this.type = RoomUtilities.snipQuotes(tokens[2]);						//2
		this.purchaseLimitPercentageModifier = (Double.parseDouble(tokens[11]))/100;	//11
		this.baseValuePercentageModifier = (Double.parseDouble(tokens[10]))/100;		//10
		this.magicItemModifier = Integer.parseInt(tokens[12]);					//12
		this.spellcastingModifier = Integer.parseInt(tokens[13]);				//13
		this.special = RoomUtilities.snipQuotes(tokens[14]);					//14
	}
	
	public String toString()
	{
		String returnString = "";
		
		returnString += "   Quality: " + this.name + " (" + this.type + ") \n";
		returnString += this.description + "\n";
		//returnString += this.modifiers.toString();
		if (this.purchaseLimitPercentageModifier > 0.0d)
			returnString += "   **Purchase limit modified by " + this.purchaseLimitPercentageModifier + "\n";
		if (this.baseValuePercentageModifier > 0.0d)
			returnString += "   **Base value limit modified by " + this.baseValuePercentageModifier + "\n";
		if (this.magicItemModifier > 0)
			returnString += "   **Quantitiy of highest magic item level produce by settlement modified by " + this.magicItemModifier + "\n";
		if (this.spellcastingModifier > 0)
			returnString += "   **Highest spellcasting level in town modified by " + this.spellcastingModifier + "\n";
		if (!(this.special.equals("N/A")))
			returnString += "   SPECIAL: " + this.special + "\n";
			
		return returnString;
	}
}