using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KingmakerResourceViewer
{
    [Serializable()]
    public class SKILL_ENUM
    {
        public static String[] GP_RELATED_SKILLS = new String[]{"Appraise","Bluff","Climb","Craft","Diplomacy","Disable Device","Handle Animal","Intimidate","Heal","Knowledge",
			"Linguistics","Perform","Profession","Sleight of Hand","Stealth","Ride","Survival","Swim","Spellcraft","Use Magic Device"};

        public static String[] GOODS_RELATED_SKILLS = new String[]{"Appraise","Bluff","Craft","Diplomacy","Disable Device","Handle Animal","Intimidate","Knowledge","Profession",
			"Sleight of Hand","Stealth"};

        public static String[] LABOR_RELATED_SKILLS = new String[] { "Bluff", "Climb", "Craft", "Diplomacy", "Handle Animal", "Intimidate", "Knowledge", "Profession", "Ride", "Survival", "Swim" };

        public static String[] INFLUENCE_RELATED_SKILLS = new String[]{"Appraise","Bluff","Craft","Diplomacy","Handle Animal","Heal","Intimidate","Knowledge","Linguistics","Perform",
			"Profession","Ride"};

        public static String[] MAGIC_RELATED_SKILLS = new String[] { "Appraise", "Craft", "Diplomacy", "Heal", "Knowledge", "Linguistics", "Profession", "Spellcraft", "Use Magic Device" };

        public static bool existsInGPSkillList(String skillListing)
        {
            for (int lcv = 0; lcv < GP_RELATED_SKILLS.Length; lcv++)
            {
                if (skillListing.Equals(GP_RELATED_SKILLS[lcv]))
                    return true;
            }
            return false;
        }

        public static bool existsInGoodsSkillList(String skillListing)
        {
            for (int lcv = 0; lcv < GOODS_RELATED_SKILLS.Length; lcv++)
            {
                if (skillListing.Equals(GOODS_RELATED_SKILLS[lcv]))
                    return true;
            }
            return false;
        }

        public static bool existsInLaborSkillList(String skillListing)
        {
            for (int lcv = 0; lcv < LABOR_RELATED_SKILLS.Length; lcv++)
            {
                if (skillListing.Equals(LABOR_RELATED_SKILLS[lcv]))
                    return true;
            }
            return false;
        }

        public static bool existsInInfluenceSkillList(String skillListing)
        {
            for (int lcv = 0; lcv < INFLUENCE_RELATED_SKILLS.Length; lcv++)
            {
                if (skillListing.Equals(INFLUENCE_RELATED_SKILLS[lcv]))
                    return true;
            }
            return false;
        }

        public static bool existsInMagicSkillList(String skillListing)
        {
            for (int lcv = 0; lcv < MAGIC_RELATED_SKILLS.Length; lcv++)
            {
                if (skillListing.Equals(MAGIC_RELATED_SKILLS[lcv]))
                    return true;
            }
            return false;
        }
    }
}
