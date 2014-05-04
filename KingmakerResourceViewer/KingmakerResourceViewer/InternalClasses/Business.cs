using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    [Serializable()]
    public class Business
    {
        Owner owner;
        Manager manager;
        String name;
        Organization[] personnel;
        Building[] properties;

        Random RNG;

        public Business()
        {
            this.properties = new Building[0];
            this.personnel = new Organization[0];
            this.owner = new Owner();
            this.manager = new Manager();
            name = "<Undefined>";

            RNG = new Random();
        }

        public Business(Business clone)
        {
            this.owner = clone.owner;
            this.manager = clone.manager;
            this.personnel = clone.personnel;
            this.name = clone.name;
            assignBuildings(clone.properties);
        }

        public Business(Owner owner, Manager manager, Organization[] personnel, Building[] properties)
        {
            this.owner = owner;
            this.manager = manager;
            this.personnel = personnel;
            assignBuildings(properties);
            name = "<None>";
        }

        public Business(Owner owner, String name, Manager manager, Organization[] personnel, Building[] properties)
        {
            this.owner = owner;
            this.manager = manager;
            this.personnel = personnel;
            assignBuildings(properties);
            this.name = name;
        }

        public Business(Owner owner, Organization[] personnel, Building[] properties)
        {
            this.owner = owner;
            this.manager = null;
            this.personnel = personnel;
            assignBuildings(properties);
        }

        public Business(Owner owner, String name, Organization[] personnel, Building[] properties)
        {
            this.owner = owner;
            this.manager = null;
            this.personnel = personnel;
            assignBuildings(properties);
            this.name = name;
        }

        public void addBuilding(Building toBeAdded)
        {
            this.properties = RoomUtilities.expand(this.properties);
            this.properties[this.properties.Length - 1] = toBeAdded;
        }

        public Boolean removeBuilding(Building toBeRemoved)
        {
            int found = indexOf(toBeRemoved);
            if (found != -1)
            {
                Building[] reBuilt = new Building[0];

                for (int i = 0; i < properties.Length; i++)
                {
                    if (found != i)
                    {
                        reBuilt = RoomUtilities.expand(reBuilt);
                        reBuilt[reBuilt.Length - 1] = this.properties[i];
                        Console.WriteLine("Writing " + this.properties[i].name + " to reBuilt, which now has a length of " + reBuilt.Length + ".");
                    }
                }

                //Once the array has been re-built, write over the existing properties array
                this.properties = reBuilt;

                return true;

            }
            else
                return false;
        }

        public void addOrganization(Organization toBeAdded)
        {
            this.personnel = RoomUtilities.expand(this.personnel);
            this.personnel[this.personnel.Length - 1] = toBeAdded;
        }

        public Boolean removeOrganization(Organization toBeRemoved)
        {
            int found = indexOf(toBeRemoved);
            if (found != -1)
            {
                Organization[] reBuilt = new Organization[0];

                for (int i = 0; i < personnel.Length; i++)
                {
                    if (found != i)
                    {
                        reBuilt = RoomUtilities.expand(reBuilt);
                        reBuilt[reBuilt.Length - 1] = this.personnel[i];
                        Console.WriteLine("Writing " + this.personnel[i].name + " to reBuilt, which now has a length of " + reBuilt.Length + ".");
                    }
                }

                //Once the array has been re-built, write over the existing properties array
                this.personnel = reBuilt;

                return true;

            }
            else
                return false;
        }

        public int indexOf(Building compared)
        {
            for (int i = 0; i < properties.Length; i++)
            {
                Building item = properties[i];    
            
            
                if (item.name.Equals(compared.name))
                {
                    //Added security to protect against identical buildings in the same organization
                    if (item.rooms.Length == compared.rooms.Length)
                    {
                        return i;
                    }
                }
            }

            return -1;
        }

        public int indexOf(Organization compared)
        {
            for (int i = 0; i < personnel.Length; i++)
            {
                Organization item = personnel[i];


                if (item.name.Equals(compared.name))
                {
                    //Added security to protect against identical buildings in the same organization
                    if (item.teams.Length == compared.teams.Length)
                    {
                        return i;
                    }
                }
            }

            return -1;
        }

        public Building[] getBuildings()
        {
            return this.properties;
        }

        public Organization[] getOrganizations()
        {
            return this.personnel;
        }

        public Owner getOwner()
        {
            return this.owner;
        }

        public void setGPAccount(float GP)
        {
            this.owner.balance.GP = GP;
        }

        public void setGoodsAccount(int Goods)
        {
            this.owner.balance.Goods = Goods;
        }

        public void setLaborAccount(int Labor)
        {
            this.owner.balance.Labor = Labor;
        }

        public void setInfluenceAccount(int Influence)
        {
            this.owner.balance.Influence = Influence;
        }

        public void setMagicAccount(int Magic)
        {
            this.owner.balance.Magic = Magic;
        }

        public String getName()
        {
            return this.name;
        }

        public void setProperties(Building[] toBeSet)
        {
            this.properties = toBeSet;
        }

        public void setPersonnel(Organization[] toBeSet)
        {
            this.personnel = toBeSet;
        }

        public void setManager(Manager toBeSet)
        {
            this.manager = toBeSet;
        }

        public void setOwner(Owner toBeSet)
        {
            this.owner = toBeSet;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public void assignBuildings(Building[] properties)
        {
            this.properties = properties;
            for (int lcv = 0; lcv < properties.Length; lcv++)
            {
                this.properties[lcv] = new Building(properties[lcv]);
            }
            assignOwner();
        }

        public void assignOwner()
        {
            for (int lcv = 0; lcv < properties.Length; lcv++)
            {
                properties[lcv].owner = owner.OwnerName;
            }
        }

        /*
         * 		//Split buildings, build array, buildings are fully-qualified copies
                String[] rawBuildingList = tokens[7].Split(',');
                //Console.Out.WriteLine("---RawBuildingList is " + rawBuildingList.Length + " elements long");
                for (int lcv = 0; lcv < rawBuildingList.Length; lcv++)
                {
                    this.properties = RoomUtilities.expand(this.properties);
                    Building toBeAdded = new Building(completeBuildingList[RoomUtilities.IndexOf(rawBuildingList[lcv], completeBuildingList)]);
                    //Set the owner
                    toBeAdded.owner = OwnerName;
                    //Console.Out.WriteLine("Data integrity has been lost, maybe: " + completeBuildingList[RoomUtilities.IndexOf(rawBuildingList[lcv], completeBuildingList)].owner);
                    this.properties[this.properties.Length-1] = toBeAdded;		                
                }
                */

        public BalanceSheet consolidateBuildingBalanceSheets(string preferredIncome)
        {
            BalanceSheet toBeReturned = new BalanceSheet();

            foreach (Building item in this.properties)
            {
                toBeReturned = RoomUtilities.combineBalanceSheet(toBeReturned, item.generateBalanceSheet(preferredIncome));
            }

            return toBeReturned;
        }

        public BalanceSheet consolidateOrganizationBalanceSheets(string preferredIncome)
        {
            BalanceSheet toBeReturned = new BalanceSheet();

            foreach (Organization item in this.personnel)
            {
                toBeReturned = RoomUtilities.combineBalanceSheet(toBeReturned, item.generateBalanceSheet(preferredIncome));
            }

            return toBeReturned;
        }

        public BalanceSheet consolidateBusinessBalanceSheets(string preferredIncome)
        {
            return RoomUtilities.combineBalanceSheet(consolidateBuildingBalanceSheets(preferredIncome), consolidateOrganizationBalanceSheets(preferredIncome));
        }
        
        public string getHoldings()
        {
            String returnString = "\n";

            returnString += "---------===" + this.owner.OwnerName + " (" + this.owner.PlayerName + ")===---------\n";
            returnString += "   ----Personal Assets, Capital and Employees----\n";
            returnString += "      ---Liquid Assets---\n";
            returnString += this.owner.balance.getTotals();
            returnString += "      ---Capital Assets---\n";
            for (int lcv = 0; lcv < properties.Length; lcv++)
            {
                returnString += "         " + properties[lcv].name + "\n";
            }
            if (manager != null)
                returnString += "      ---Manager: " + manager.Name + "---\n";
            for (int lcv = 0; lcv < personnel.Length; lcv++)
            {
                returnString += "      ---Organization: " + personnel[lcv].name + "---\n";
                for (int teamLCV = 0; teamLCV < personnel[lcv].teams.Length; teamLCV++)
                {
                    returnString += "         " + personnel[lcv].teams[teamLCV].Name + "\n";
                }
            }

            return returnString;
        }

        public string getSynopsis()
        {
            String returnString = "";

            returnString += "|||   " + this.name + "   |||\r\n";
            returnString += "Owned by: " + this.owner.OwnerName + " (" + this.owner.PlayerName + ")\r\n";
            
            returnString += "\r\n---Capital Assets---\r\n";
            for (int lcv = 0; lcv < properties.Length; lcv++)
            {
                returnString += "   " + properties[lcv].name + "\r\n";
            }
            if (manager != null && !manager.Name.Equals(""))
                returnString += "\r\n---Manager: " + manager.Name + "---\r\n";

            returnString += "\r\n---Employees:---\r\n";
            for (int lcv = 0; lcv < personnel.Length; lcv++)
            {
                returnString += "   " + personnel[lcv].name + "\r\n";
                /*for (int teamLCV = 0; teamLCV < personnel[lcv].teams.Length; teamLCV++)
                {
                    returnString += "   " + personnel[lcv].teams[teamLCV].Name + "\r\n";
                }*/
            }

            return returnString;
        }

        private int getUsableSkillValue(bool UseManager, string earningsDesired)
        {
            int returnValue = 0;

            Skill[] validList;
            if (UseManager)
                validList = manager.Skills;
            else
                validList = owner.skills;

            for (int lcv = 0; lcv < validList.Length; lcv++)
            {
                if (earningsDesired.Equals("GP"))
                {
                    if (SKILL_ENUM.existsInGPSkillList(validList[lcv].Name))
                    {
                        if (validList[lcv].Value > returnValue)
                            returnValue = validList[lcv].Value;
                    }
                }
                if (earningsDesired.Equals("GOODS"))
                {
                    if (SKILL_ENUM.existsInGoodsSkillList(validList[lcv].Name))
                    {
                        if (validList[lcv].Value > returnValue)
                            returnValue = validList[lcv].Value;
                    }
                }
                if (earningsDesired.Equals("LABOR"))
                {
                    if (SKILL_ENUM.existsInLaborSkillList(validList[lcv].Name))
                    {
                        if (validList[lcv].Value > returnValue)
                            returnValue = validList[lcv].Value;
                    }
                }
                if (earningsDesired.Equals("INFLUENCE"))
                {
                    if (SKILL_ENUM.existsInInfluenceSkillList(validList[lcv].Name))
                    {
                        if (validList[lcv].Value > returnValue)
                            returnValue = validList[lcv].Value;
                    }
                }
                if (earningsDesired.Equals("MAGIC"))
                {
                    if (SKILL_ENUM.existsInMagicSkillList(validList[lcv].Name))
                    {
                        if (validList[lcv].Value > returnValue)
                            returnValue = validList[lcv].Value;
                    }
                }
            }

            return returnValue;
        }

        public Account generateDailyIncome(String PreferredIncome, bool UseManager)
        {
            Account returnAccount = new Account();

            for (int lcv = 0; lcv < this.properties.Length; lcv++)
            {
                BalanceSheet tempTotals = this.properties[lcv].generateBalanceSheet(PreferredIncome);
                if (this.properties[lcv].isGPEarnable())
                {
                    int GPd20 = RNG.Next(20) + 1 + getUsableSkillValue(UseManager, "GP");
                    float result = (GPd20 + tempTotals.GP) / 10;
                    returnAccount.GP += result;
                }
                if (this.properties[lcv].isLaborEarnable())
                {
                    int Labord20 = RNG.Next(20) + 1 + getUsableSkillValue(UseManager, "LABOR");
                    returnAccount.Labor += (int)(Labord20 + tempTotals.Labor) / 10;
                }
                if (this.properties[lcv].isInfluenceEarnable())
                {
                    int Influenced20 = RNG.Next(20) + 1 + getUsableSkillValue(UseManager, "INFLUENCE");
                    returnAccount.Influence += (int)(Influenced20 + tempTotals.Influence) / 10;
                }
                if (this.properties[lcv].isGoodsEarnable())
                {
                    int Goodsd20 = RNG.Next(20) + 1 + getUsableSkillValue(UseManager, "GOODS");
                    returnAccount.Goods += (int)(Goodsd20 + tempTotals.Goods) / 10;
                }
                if (this.properties[lcv].isMagicEarnable())
                {
                    int Magicd20 = RNG.Next(20) + 1 + getUsableSkillValue(UseManager, "MAGIC");
                    returnAccount.Magic += (int)(Magicd20 + tempTotals.Magic) / 10;
                }
            }
            //Assign the profits / losses to this owner
            owner.balance.combineAccounts(returnAccount);
            return returnAccount;
        }

        /**A workweek in pathfinder is 6 days - no one works 7 days a week, nor is a business open 7 days a week*/
        public Account generateMultiDayIncome(String PreferredIncome, int numDays, bool attended)
        {
            Account returnAccount = new Account();
            for (int lcv = 0; lcv < numDays; lcv++)
            {
                if (lcv % 7 != 0)
                {
                    if (lcv > 7)
                        returnAccount = returnAccount.combineAccounts(generateDailyIncome(PreferredIncome, true));
                    else
                        returnAccount = returnAccount.combineAccounts(generateDailyIncome(PreferredIncome, false));
                }
            }

            int numWeeks = numDays / 7;
            if (!attended && numWeeks > 0 && manager == null)
            {
                //First week suffers no penalty
                for (int lcv = 0; lcv < numWeeks; lcv++)
                {
                    returnAccount.GP -= 1;
                    returnAccount.Goods -= 1;
                    returnAccount.Labor -= 1;
                    returnAccount.Influence -= 1;
                    returnAccount.Magic -= 1;
                }
                //Can't lose money on the income phase
                if (returnAccount.GP < 0)
                    returnAccount.GP = 0;
                if (returnAccount.Goods < 0)
                    returnAccount.Goods = 0;
                if (returnAccount.Labor < 0)
                    returnAccount.Labor = 0;
                if (returnAccount.Influence < 0)
                    returnAccount.Influence = 0;
                if (returnAccount.Magic < 0)
                    returnAccount.Magic = 0;
            }
            //		Assign the profits / losses to this owner
            owner.balance.combineAccounts(returnAccount);
            return returnAccount;
        }

        public string earningsReport(String PreferredIncome, int numDays, bool attended)
        {
            String returnString = "";

            returnString += "\nGenerating income for " + numDays + ", favoring production of " + PreferredIncome + "...\n";
            if (!attended && manager == null)
                returnString += "**Warning: You are not attending this business, and income after the first week will be penalized\n";
            Account temp = generateMultiDayIncome(PreferredIncome, numDays, attended);
            returnString += "---Incomes Generated---\n";
            returnString += "   GP: " + temp.GP;
            returnString += "\n   Goods: " + temp.Goods;
            returnString += "\n   Influence: " + temp.Influence;
            returnString += "\n   Labor: " + temp.Labor;
            returnString += "\n   Magic: " + temp.Magic;

            return returnString;
        }
    }

}
