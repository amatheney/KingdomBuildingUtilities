using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    /**Container class to store room augmentations, to hopefully someday include traps*/
    [Serializable()]
    public class FurnishingsAndTraps
    {
        public String Name;
        public String Benefit;
        public String Description;
        public int GPCreate;
        public int GoodsCreate;
        public int InfluenceCreate;
        public int LaborCreate;
        public int MagicCreate;
        public int DaysCreate;

        public bool trap;				//Always false, for now...
        public String associatedRoom;

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

        /**Takes a semicolon-delimited string , and parses it into into a room augmentation*/
        public FurnishingsAndTraps(String rawCSV)
        {
            String[] tokens = rawCSV.Split(';');

            this.Name = RoomUtilities.snipQuotes(tokens[0]);
            this.Benefit = RoomUtilities.snipQuotes(tokens[1]);
            this.Description = RoomUtilities.snipQuotes(tokens[2]);
            this.GPCreate = Convert.ToInt32(tokens[3]);
            this.GoodsCreate = Convert.ToInt32(tokens[4]);
            this.InfluenceCreate = Convert.ToInt32(tokens[5]);
            this.LaborCreate = Convert.ToInt32(tokens[6]);
            this.MagicCreate = Convert.ToInt32(tokens[7]);
            this.DaysCreate = Convert.ToInt32(tokens[8]);

            this.trap = false;
            this.associatedRoom = "N/A";
        }

        public string toString()
        {
            String returnString = "\n----=Custom Furnishing=----\n";
            String trapString = "";
            if (trap)
                trapString = "trap";
            if (!trap)
                trapString = "furnishing";
            if (associatedRoom.Equals("N/A"))
                returnString += "This " + trapString + " is not associated with a particular room.\n";
            if (!(associatedRoom.Equals("N/A")))
                returnString += "This " + trapString + " is associated with a " + associatedRoom + " in the building\n";
            returnString += "Name: " + Name + "\n";
            if (!(Benefit.Equals("N/A")))
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
}
