using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    [Serializable()]
    public class Skill
    {
        public String Name;
        public int Value;

        public Skill()
        {
            Name = "";
            Value = 0;
        }

        public Skill(String Name, int Value)
        {
            this.Name = Name;
            this.Value = Value;
        }

        public String toString()
        {
            String returnString = "";

            returnString += this.Name + ": " + this.Value + "\n";

            return returnString;
        }
    }
}
