package settlements;

public class Skill
{
	String Name;
	int Value;
	
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
	
	public string  toString()
	{
		String returnString = "";
		
		returnString += this.Name + ": " + this.Value + "\n";
		
		return returnString;
	}
}
