package settlements;

public class Account
{
	float GP;
	int Labor;
	int Influence;
	int Goods;
	int Magic;
	
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
	
	public String getTotals()
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
