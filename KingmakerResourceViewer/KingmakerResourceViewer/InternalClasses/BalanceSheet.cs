using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    /**Stores the potential earnings of a room, building, or collection of buildings. Pivots off 'preferredIncome' - an identifier specifiying the ideal currency to generate*/
    [Serializable()]
    public class BalanceSheet
    {
        public float GP;					//Maximum gold pieces that can be earned
        public float Goods;				//Maximum goods capital that can be earned
        public float Influence;			//Maximum influence capital that can be earned				
        public float Labor;				//Maximum labor capital that can be earned
        public float Magic;				//Maximum magic capital that can be earned
        public String preferredIncome;  	//Always in caps

        /**Generic constructor. Assumes GP is the preferred return*/
        public BalanceSheet()
        {
            this.GP = 0;
            this.Goods = 0;
            this.Influence = 0;
            this.Labor = 0;
            this.Magic = 0;
            preferredIncome = "GP";
        }

        /**Debugging method that checks the total units in this balance sheet - used to compare multiple balance sheets to one another, all of which should have the same total.*/
        public float total()
        {
            return this.GP + this.Goods + this.Influence + this.Labor + this.Magic;
        }

        public string toString()
        {
            String returnString = "";
            returnString += "-------=== Balance Sheet: Max " + this.preferredIncome + " ===-------\n";
            returnString += "   GP / Goods / Influence / Labor / Magic\n";
            //returnString += " " + this.GP + " / " + this.Goods + " / " + this.Influence + " / " + this.Labor + " / " + this.Magic + "     (Total: " + total() + ")\n";
            returnString += "  " + this.GP + "  /  " + this.Goods + "  /  " + this.Influence + "  /  " + this.Labor + "  /  " + this.Magic + "\n";
            return returnString;
        }

        public string toStringForTextBox()
        {
            String returnString = "";
            returnString += "-------=== Balance Sheet: Max " + this.preferredIncome + " ===-------\r\n";
            returnString += "   GP / Goods / Influence / Labor / Magic\r\n";
            //returnString += " " + this.GP + " / " + this.Goods + " / " + this.Influence + " / " + this.Labor + " / " + this.Magic + "     (Total: " + total() + ")\n";
            returnString += "  " + this.GP + "  /  " + this.Goods + "  /  " + this.Influence + "  /  " + this.Labor + "  /  " + this.Magic + "\r\n";
            return returnString;
        }
    }

}
