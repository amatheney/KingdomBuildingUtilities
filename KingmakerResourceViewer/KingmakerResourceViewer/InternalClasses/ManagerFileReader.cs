using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    [Serializable()]
    public class ManagerFileReader
    {
        /** Constructor. */
        public ManagerFileReader(String aFileName)
        {
            fFileName = aFileName;
        }

        /** Read the contents of the given file. */
        public Manager[] read()
        {
            Manager[] returnList = new Manager[0];

            //log("Reading from " + fFileName);
            String[] lines = fFileName.Split(new[] { Environment.NewLine }, StringSplitOptions.RemoveEmptyEntries);
            for (int lcv = 0; lcv < lines.Length; lcv++)
            {
                Manager tempElement = new Manager(lines[lcv]);
                returnList = RoomUtilities.expand(returnList);
                returnList[returnList.Length - 1] = tempElement;
            }  
            return returnList;
        }

        // PRIVATE 
        private String fFileName;
        private String FIXED_TEXT = "But soft! what code in yonder program breaks?";

        private void log(String aMessage)
        {
            Console.Out.WriteLine(aMessage);
        }


    }
}
