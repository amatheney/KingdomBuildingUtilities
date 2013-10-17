package settlements;

public class Manager 
{
	String Name;
	int Wage;
	Skill[] Skills;
	String Description;
	
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
		
		String[] tokens = rawCSV.split("\\;");
		this.Name = RoomUtilities.snipQuotes(tokens[0]);
		this.Wage = Integer.parseInt(tokens[1]);
		
		//Set skills
		String[] SkillStrings = RoomUtilities.snipQuotes(tokens[2]).split(",");
		this.Skills = new Skill[SkillStrings.length];
		for (int lcv = 0; lcv < SkillStrings.length; lcv++)
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
	
	public String toString()
	{
		String returnString = "";
		
		returnString += "   ---Manager: " + this.Name + "---\n";
		returnString += "      Skills: ";
		for (int lcv = 0; lcv < this.Skills.length; lcv++)
		{
			returnString += this.Skills[lcv].Name;
			if (lcv < this.Skills.length-1)
				returnString += ", ";
		}
		returnString += "\n      Wage: " + this.Wage + " gp\\day\n";
		returnString += "      " + this.Description + "\n";
		
		return returnString;
	}
}
