package settlements;

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
