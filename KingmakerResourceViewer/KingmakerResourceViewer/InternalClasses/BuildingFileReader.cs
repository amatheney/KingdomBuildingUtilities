using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    public class BuildingFileReader
    {
        /** Constructor. */
        public BuildingFileReader(String aFileName)
        {
            fFileName = aFileName;
        }

        /** Read the contents of the given file. */
        public Building[] read(Room[] completeRooms, FurnishingsAndTraps[] completeFurnishingList)
        {
            Building[] returnList = new Building[0];

            //log("Reading from " + fFileName);
            String[] lines = fFileName.Split(new[] { Environment.NewLine }, StringSplitOptions.RemoveEmptyEntries);
            for (int lcv = 0; lcv < lines.Length; lcv++)
            {
                Building tempElement = new Building(lines[lcv], completeRooms, completeFurnishingList);
                returnList = RoomUtilities.expand(returnList);
                returnList[returnList.Length - 1] = tempElement;
            }
            
            return returnList;
        }

        /*public string[] readTextFile()
        {
            String[] returnList = new string[0];

            log("Reading from " + fFileName);
            Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
            try
            {
                while (scanner.hasNextLine())
                {
                    String output = scanner.nextLine();
                    //String tempElement = new string (output, completeRooms);
                    returnList = RoomUtilities.expandStringArray(output, returnList);
                    returnList[returnList.Length - 1] = output;
                }
            }
            finally
            {
                scanner.close();
            }
            //log("Text read in: " + text);
            return returnList;
        }*/

        // PRIVATE 
        private String fFileName;
        private String FIXED_TEXT = "But soft! what code in yonder program breaks?";

        private void log(String aMessage)
        {
            Console.Out.WriteLine(aMessage);
        }


    }

}
