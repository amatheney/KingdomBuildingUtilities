package settlements;

public class SKILL_ENUM
{
	static String[] GP_RELATED_SKILLS = new String[]{"Appraise","Bluff","Climb","Craft","Diplomacy","Disable Device","Handle Animal","Intimidate","Heal","Knowledge",
			"Linguistics","Perform","Profession","Sleight of Hand","Stealth","Ride","Survival","Swim","Spellcraft","Use Magic Device"};				
	
	static String[] GOODS_RELATED_SKILLS = new String[]{"Appraise","Bluff","Craft","Diplomacy","Disable Device","Handle Animal","Intimidate","Knowledge","Profession",
			"Sleight of Hand","Stealth"};
	
	static String[] LABOR_RELATED_SKILLS = new String[]{"Bluff","Climb","Craft","Diplomacy","Handle Animal","Intimidate","Knowledge","Profession","Ride","Survival","Swim"};
	
	static String[] INFLUENCE_RELATED_SKILLS = new String[]{"Appraise","Bluff","Craft","Diplomacy","Handle Animal","Heal","Intimidate","Knowledge","Linguistics","Perform",
			"Profession","Ride"};
	
	static String[] MAGIC_RELATED_SKILLS = new String[]{"Appraise","Craft","Diplomacy","Heal","Knowledge","Linguistics","Profession","Spellcraft","Use Magic Device"};
	
	static  boolean existsInGPSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < GP_RELATED_SKILLS.length; lcv++)
		{
			if (skillListing.equals(GP_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
	
	static  boolean existsInGoodsSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < GOODS_RELATED_SKILLS.length; lcv++)
		{
			if (skillListing.equals(GOODS_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
	
	static  boolean existsInLaborSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < LABOR_RELATED_SKILLS.length; lcv++)
		{
			if (skillListing.equals(LABOR_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
	
	static  boolean existsInInfluenceSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < INFLUENCE_RELATED_SKILLS.length; lcv++)
		{
			if (skillListing.equals(INFLUENCE_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
	
	static  boolean existsInMagicSkillList(String skillListing)
	{
		for (int lcv = 0; lcv < MAGIC_RELATED_SKILLS.length; lcv++)
		{
			if (skillListing.equals(MAGIC_RELATED_SKILLS[lcv]))
				return true;
		}
		return false;
	}
}
