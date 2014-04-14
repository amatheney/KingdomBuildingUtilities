using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    /**Inner class that stores all the qualities, disadvantages, and governments of the settlement, proving an easy-to-iterate structure for calculating total modifiers*/
    public class Quality
    {
        public SettlementMods modifiers;
        public String name;
        public String description;
        public String type; 				//Advantage/Disadvantage/Government
        public double purchaseLimitPercentageModifier;
        public double baseValuePercentageModifier;
        public int magicItemModifier;
        public int spellcastingModifier;
        public String special;

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
            String[] tokens = rawCSV.Split(';');

            /*for (int lcv = 0; lcv < tokens.Length; lcv++)
            {
                Console.Out.WriteLine("Token #" + lcv + ": " + tokens[lcv]);
            }*/

            int settlementCorruptionModifier = Convert.ToInt32(tokens[3]);			//Corruption	[3]
            int settlementCrimeModifier = Convert.ToInt32(tokens[4]);				//Crime			[4]
            int settlementLawModifier = Convert.ToInt32(tokens[5]);				//Law			[5]
            int settlementLoreModifier = Convert.ToInt32(tokens[6]);				//Lore			[6]
            int settlementProductivityModifier = Convert.ToInt32(tokens[7]);		//Productivity	[7]
            int settlementSocietyModifier = Convert.ToInt32(tokens[8]);			//Society		[8]		
            int settlementDangerModifier = Convert.ToInt32(tokens[9]);			//Danger		[9]
            int settlementBaseValueModifier = 0;									//This is calculated differently for qualities.

            this.modifiers = new SettlementMods(settlementCorruptionModifier, settlementCrimeModifier, settlementLawModifier, settlementLoreModifier,
                    settlementSocietyModifier, settlementDangerModifier, settlementProductivityModifier, settlementBaseValueModifier);			//3-9

            this.name = RoomUtilities.snipQuotes(tokens[0]);						//0
            this.description = RoomUtilities.snipQuotes(tokens[1]);					//1
            this.type = RoomUtilities.snipQuotes(tokens[2]);						//2
            this.purchaseLimitPercentageModifier = (Double.Parse(tokens[11])) / 100;	//11
            this.baseValuePercentageModifier = (Double.Parse(tokens[10])) / 100;		//10
            this.magicItemModifier = Convert.ToInt32(tokens[12]);					//12
            this.spellcastingModifier = Convert.ToInt32(tokens[13]);				//13
            this.special = RoomUtilities.snipQuotes(tokens[14]);					//14
        }

        public string toString()
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
            if (!(this.special.Equals("N/A")))
                returnString += "   SPECIAL: " + this.special + "\n";

            return returnString;
        }
    }
}
