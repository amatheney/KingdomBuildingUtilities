using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    [Serializable()]
    public class Manager
    {
        public String Name;
        public int Wage;
        public Skill[] Skills;
        public String Description;

        public Manager()
        {
            Name = "";
            Wage = 0;
            Skills = new Skill[0];
            Description = "";
        }

        public Manager(String rawCSV)
        {
            //Abbot;4;Diplomacy,Heal,Knowledge;An Abbot is divine spellcaster...
            Name = "";
            Wage = 0;
            Skills = new Skill[0];
            Description = "";

            String[] tokens = rawCSV.Split(';');
            this.Name = RoomUtilities.snipQuotes(tokens[0]);
            this.Wage = Convert.ToInt32(tokens[1]);

            //Set skills
            String[] SkillStrings = RoomUtilities.snipQuotes(tokens[2]).Split(',');
            this.Skills = new Skill[SkillStrings.Length];
            for (int lcv = 0; lcv < SkillStrings.Length; lcv++)
            {
                this.Skills[lcv] = new Skill(SkillStrings[lcv], 10);
            }

            this.Description = RoomUtilities.snipQuotes(tokens[3]);
        }

        public Manager(Manager input)
        {
            this.Name = input.Name;
            this.Wage = input.Wage;
            this.Skills = input.Skills;
            this.Description = input.Description;
        }

        public Manager(Owner input)
        {
            this.Name = input.OwnerName;
            this.Wage = 0;
            this.Skills = input.skills;
            this.Description = "Personally managed by " + input.OwnerName + " (" + input.PlayerName + ")";
        }

        public string toString()
        {
            String returnString = "";

            returnString += "   ---Manager: " + this.Name + "---\n";
            returnString += "      Skills: ";
            for (int lcv = 0; lcv < this.Skills.Length; lcv++)
            {
                returnString += this.Skills[lcv].Name;
                if (lcv < this.Skills.Length - 1)
                    returnString += ", ";
            }
            returnString += "\n      Wage: " + this.Wage + " gp\\day\n";
            returnString += "      " + this.Description + "\n";

            return returnString;
        }
    }

}
