package settlements;

/**Stores the potential earnings of a room, building, or collection of buildings. Pivots off 'preferredIncome' - an identifier specifiying the ideal currency to generate*/
public class BalanceSheet 
{
	float GP;					//Maximum gold pieces that can be earned
	float Goods;				//Maximum goods capital that can be earned
	float Influence;			//Maximum influence capital that can be earned				
	float Labor;				//Maximum labor capital that can be earned
	float Magic;				//Maximum magic capital that can be earned
	String preferredIncome;  	//Always in caps
	
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
	
	public string  toString()
	{
		String returnString = "";
		returnString += "-------=== Balance Sheet: Max " + this.preferredIncome + " ===-------\n";
		returnString += "   GP / Goods / Influence / Labor / Magic\n";
		//returnString += " " + this.GP + " / " + this.Goods + " / " + this.Influence + " / " + this.Labor + " / " + this.Magic + "     (Total: " + total() + ")\n";
		returnString += "  " + this.GP + "  /  " + this.Goods + "  /  " + this.Influence + "  /  " + this.Labor + "  /  " + this.Magic + "\n";
		return returnString;
	}
}
