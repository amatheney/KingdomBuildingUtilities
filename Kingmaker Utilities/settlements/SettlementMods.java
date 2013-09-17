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
}
