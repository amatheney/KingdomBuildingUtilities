package settlements;

/**Container class to store the modifiers for a settlement - broken out to be more flexible and placed in buildings, settlements, disctricts and kingdoms alike.*/
public class SettlementMods
{
	int settlementCorruptionModifier;
	int settlementCrimeModifier;
	int settlementLawModifier;
	int settlementLoreModifier;
	int settlementSocietyModifier;
	int settlementDangerModifier;
	int settlementProductivityModifier;
	int settlementBaseValueModifier;
	
	/**Generic constructor*/
	public SettlementMods()
	{
		this.settlementCorruptionModifier = 0;
		this.settlementCrimeModifier = 0;
		this.settlementLawModifier = 0;
		this.settlementLoreModifier = 0;
		this.settlementSocietyModifier = 0;
		this.settlementDangerModifier = 0;
		this.settlementProductivityModifier = 0;
		this.settlementBaseValueModifier = 0;
	}
	
	/**Constructor that just takes the variables supllied and stuffs them in a class*/
	public SettlementMods(int settlementCorruptionModifier, int settlementCrimeModifier, int settlementLawModifier, int settlementLoreModifier, 
			int settlementSocietyModifier, int settlementDangerModifier, int settlementProductivityModifier, int settlementBaseValueModifier)
	{
		this.settlementCorruptionModifier = settlementCorruptionModifier;
		this.settlementCrimeModifier = settlementCrimeModifier;
		this.settlementLawModifier = settlementLawModifier;
		this.settlementLoreModifier = settlementLoreModifier;
		this.settlementSocietyModifier = settlementSocietyModifier;
		this.settlementDangerModifier = settlementDangerModifier;
		this.settlementProductivityModifier = settlementProductivityModifier;
		this.settlementBaseValueModifier = settlementBaseValueModifier;
	}
	
	public SettlementMods combineSettlementMods(SettlementMods other)
	{
		SettlementMods toBeReturned = new SettlementMods();
		
		toBeReturned.settlementCorruptionModifier = this.settlementCorruptionModifier + other.settlementCorruptionModifier;
		toBeReturned.settlementCrimeModifier = this.settlementCrimeModifier + other.settlementCrimeModifier;
		toBeReturned.settlementLawModifier = this.settlementLawModifier + other.settlementLawModifier;
		toBeReturned.settlementLoreModifier = this.settlementLoreModifier + other.settlementLoreModifier;
		toBeReturned.settlementSocietyModifier = this.settlementSocietyModifier + other.settlementSocietyModifier;
		toBeReturned.settlementDangerModifier = this.settlementDangerModifier + other.settlementDangerModifier;
		toBeReturned.settlementProductivityModifier = this.settlementProductivityModifier + other.settlementProductivityModifier;
		toBeReturned.settlementBaseValueModifier = this.settlementBaseValueModifier + other.settlementBaseValueModifier;
		
		return toBeReturned;
	}
	
	public string  toString()
	{
		String returnString = "\n----===Settlement Modifiers===----\n";
		
		returnString += "Corruption: " + settlementCorruptionModifier + "\n";
		returnString += "Crime: " + settlementCrimeModifier + "\n";
		returnString += "Law: " + settlementLawModifier + "\n";
		returnString += "Lore: " + settlementLoreModifier + "\n";
		returnString += "Society: " + settlementSocietyModifier + "\n";
		returnString += "Danger: " + settlementDangerModifier + "\n";
		returnString += "Productivity: " + settlementProductivityModifier + "\n";
		returnString += "Base Purchase Value: " + settlementBaseValueModifier + "\n";
		
		return returnString;
	}
}
