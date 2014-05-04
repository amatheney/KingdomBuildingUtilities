using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    [Serializable()]
    public class Team
    {
        /**Attributes of the Team*/
        public String Name;
        public float GPEarnings;
        public float GoodsEarnings;
        public float InfluenceEarnings;
        public float LaborEarnings;
        public float MagicEarnings;
        public float CapitalEarnings;
        public String Benefit;
        public float GPCreate;
        public float GoodsCreate;
        public float InfluenceCreate;
        public float LaborCreate;
        public float MagicCreate;
        public float DaysCreate;
        public float MinSize;
        public float MaxSize;
        public String UpgradeFrom;
        public String UpgradeTo;
        public String Description;

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
            String[] tokens = rawCSV.Split(';');

            this.Name = RoomUtilities.snipQuotes(tokens[0]);
            this.GPEarnings = Convert.ToInt32(tokens[1]);
            this.GoodsEarnings = Convert.ToInt32(tokens[2]);
            this.InfluenceEarnings = Convert.ToInt32(tokens[3]);
            this.LaborEarnings = Convert.ToInt32(tokens[4]);
            this.MagicEarnings = Convert.ToInt32(tokens[5]);
            this.CapitalEarnings = Convert.ToInt32(tokens[6]);
            this.Benefit = RoomUtilities.snipQuotes(tokens[7]);
            this.GPCreate = Convert.ToInt32(tokens[8]);
            this.GoodsCreate = Convert.ToInt32(tokens[9]);
            this.InfluenceCreate = Convert.ToInt32(tokens[10]);
            this.LaborCreate = Convert.ToInt32(tokens[11]);
            this.MagicCreate = Convert.ToInt32(tokens[12]);
            this.DaysCreate = Convert.ToInt32(tokens[13]);
            this.MinSize = Convert.ToInt32(tokens[14]);
            this.MaxSize = Convert.ToInt32(tokens[15]);
            this.UpgradeFrom = RoomUtilities.snipQuotes(tokens[16]);
            this.UpgradeTo = RoomUtilities.snipQuotes(tokens[17]);
            this.Description = RoomUtilities.snipQuotes(tokens[18]);
        }

        /**Override method to return formatted description of the room*/
        public String toString()
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

            return returnString;
        }

        //Textbox-friendly output
        public String toStringForTextBox()
        {
            String returnString = "\n";

            returnString += "**Name: " + this.Name + "**\r\n";
            if (!(Benefit.Equals("N/A")))
                returnString += "  Benefit: " + this.Benefit + "\r\n";
            returnString += "  ---------Creation Costs---------\r\n";
            returnString += "    Create Cost (GP): " + this.GPCreate + "\r\n";
            returnString += "    Create Cost (Goods): " + this.GoodsCreate + "\r\n";
            returnString += "    Create Cost (Influence): " + this.InfluenceCreate + "\r\n";
            returnString += "    Create Cost (Labor): " + this.LaborCreate + "\r\n";
            returnString += "    Create Cost (Magic): " + this.MagicCreate + "\r\n";
            returnString += "  -------Earnings Potential-------\r\n";
            if (GPEarnings != 0)
                returnString += "    Earnings (GP): " + this.GPEarnings + "\r\n";
            if (GoodsEarnings != 0)
                returnString += "    Earnings (Goods): " + this.GoodsEarnings + "\r\n";
            if (InfluenceEarnings != 0)
                returnString += "    Earnings (Influence): " + this.InfluenceEarnings + "\r\n";
            if (LaborEarnings != 0)
                returnString += "    Earnings (Labor): " + this.LaborEarnings + "\r\n";
            if (MagicEarnings != 0)
                returnString += "    Earnings (Magic): " + this.MagicEarnings + "\r\n";
            if (CapitalEarnings != 0)
                returnString += "    Earnings (Capital): " + this.CapitalEarnings + "\r\n";
            returnString += "  Days to build: " + this.DaysCreate + "\r\n";
            returnString += "  Minimum Room Size: " + this.MinSize + "\r\n";
            returnString += "  Maximum Room Size: " + this.MaxSize + "\r\n";
            if (!(UpgradeFrom.Equals("N/A")))
                returnString += "  Upgrades from: " + this.UpgradeFrom + "\r\n";
            if (!(UpgradeTo.Equals("N/A")))
                returnString += "  Upgrades to: " + this.UpgradeTo + "\r\n";
            returnString += "  Description: " + this.Description + "\r\n";

            return returnString;
        }

        //Textbox-friendly output
        public String LimtedDescriptionForTextBox()
        {
            String returnString = "\n";

            returnString += "**Name: " + this.Name + "**\r\n";
            if (!(Benefit.Equals("N/A")))
                returnString += "  Benefit: " + this.Benefit + "\r\n";
            if (!(UpgradeFrom.Equals("N/A")))
                returnString += "  Upgrades from: " + this.UpgradeFrom + "\r\n";
            if (!(UpgradeTo.Equals("N/A")))
                returnString += "  Upgrades to: " + this.UpgradeTo + "\r\n";
            returnString += "\r\n  Description: " + this.Description + "\r\n";

            return returnString;
        }

        public String conciseTeamOutput()
        {
            String returnString = "";

            returnString += "Name: " + this.Name;
            returnString += " || Earnings: ";
            returnString += this.GPEarnings + " GP / ";
            returnString += this.GoodsEarnings + " Goods / ";
            returnString += this.InfluenceEarnings + " Influence / ";
            returnString += this.LaborEarnings + " Labor / ";
            returnString += this.MagicEarnings + " Magic / ";
            returnString += this.CapitalEarnings + " Capital / ";

            return returnString;
        }
    }
}
