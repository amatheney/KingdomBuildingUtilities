using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    [Serializable()]
    public class Account
    {
        public float GP;
        public int Labor;
        public int Influence;
        public int Goods;
        public int Magic;

        public Account()
        {
            GP = 0;
            Labor = 0;
            Influence = 0;
            Goods = 0;
            Magic = 0;
        }

        public Account(float GP, int Labor, int Influence, int Goods, int Magic)
        {
            this.GP = GP;
            this.Labor = Labor;
            this.Influence = Influence;
            this.Goods = Goods;
            this.Magic = Magic;
        }

        public string getTotals()
        {
            String returnString = "";

            returnString += "         GP        : " + GP + "\n";
            returnString += "         Labor     : " + Labor + "\n";
            returnString += "         Influence : " + Influence + "\n";
            returnString += "         Goods     : " + Goods + "\n";
            returnString += "         Magic     : " + Magic + "\n";

            return returnString;
        }

        public Account combineAccounts(Account toCombine)
        {
            Account returnAccount = new Account();

            returnAccount.GP = this.GP + toCombine.GP;
            returnAccount.Labor = this.Labor + toCombine.Labor;
            returnAccount.Influence = this.Influence + toCombine.Influence;
            returnAccount.Goods = this.Goods + toCombine.Goods;
            returnAccount.Magic = this.Magic + toCombine.Magic;

            return returnAccount;
        }
    }
}
