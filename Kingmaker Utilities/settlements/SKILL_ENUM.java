package settlements;

public class SKILL_ENUM
{
	static string [] GP_RELATED_SKILLS = new string []{"Appraise","Bluff","Climb","Craft","Diplomacy","Disable Device","Handle Animal","Intimidate","Heal","Knowledge",
			"Linguistics","Perform","Profession","Sleight of Hand","Stealth","Ride","Survival","Swim","Spellcraft","Use Magic Device"};				
	
	static string [] GOODS_RELATED_SKILLS = new string []{"Appraise","Bluff","Craft","Diplomacy","Disable Device","Handle Animal","Intimidate","Knowledge","Profession",
			"Sleight of Hand","Stealth"};
	
	static string [] LABOR_RELATED_SKILLS = new string []{"Bluff","Climb","Craft","Diplomacy","Handle Animal","Intimidate","Knowledge","Profession","Ride","Survival","Swim"};
	
	static string [] INFLUENCE_RELATED_SKILLS = new string []{"Appraise","Bluff","Craft","Diplomacy","Handle Animal","Heal","Intimidate","Knowledge","Linguistics","Perform",
			"Profession","Ride"};
	
	static string [] MAGIC_RELATED_SKILLS = new string []{"Appraise","Craft","Diplomacy","Heal","Knowledge","Linguistics","Profession","Spellcraft","Use Magic Device"};
	
	static  bool existsInGPSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < GP_RELATED_SKILLS.Length; lcv++)
		{
			if (skillListing.Equals(GP_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
	
	static  bool existsInGoodsSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < GOODS_RELATED_SKILLS.Length; lcv++)
		{
			if (skillListing.Equals(GOODS_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
	
	static  bool existsInLaborSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < LABOR_RELATED_SKILLS.Length; lcv++)
		{
			if (skillListing.Equals(LABOR_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
	
	static  bool existsInInfluenceSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < INFLUENCE_RELATED_SKILLS.Length; lcv++)
		{
			if (skillListing.Equals(INFLUENCE_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
	
	static  bool existsInMagicSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < MAGIC_RELATED_SKILLS.Length; lcv++)
		{
			if (skillListing.Equals(MAGIC_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
}
