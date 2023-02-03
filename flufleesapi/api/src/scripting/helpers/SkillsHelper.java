package scripting.helpers;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import utils.ConditionUtilities;
import utils.ConditionUtilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;


/**
 * @author Laniax
 */
public class SkillsHelper {

    public static LinkedHashMap<SKILLS, Integer> start_skill_info = new LinkedHashMap<>();

    /**
     * Save the amount of XP of each skill so we can look back at it later (for paint, antiban etc)
     * Make sure to call this AFTER login.
     */
    public static void setStartSkills() {
        setStartSkills(SKILLS.values());
    }

    /**
     * Save the amount of XP of specific skills so we can look back at it later (for paint, antiban etc)
     * Make sure to call this AFTER login.
     *
     * @param skills - array of skills to save.
     */
    public static void setStartSkills(SKILLS[] skills) {

        // Xp can only be set when we are logged into the game.
        // Even then, it might take a few ticks before the data is available, so lets wait if necessary.

        start_skill_info.clear();

        if (Timing.waitCondition(ConditionUtilities.hitpointsLoaded(), General.random(5000, 8000))) {
            for (SKILLS skill : skills) {
                int xp = Skills.getXP(skill);
                xp = Math.max(0, xp); // make sure its above 0.
                start_skill_info.put(skill, xp);
            }
        }
    }

    /**
     * Get the list of all skills and start XP which were set by setStartSkills()
     *
     * @return the amount of xp
     */
    public static LinkedHashMap<SKILLS, Integer> getStartSkills() {
        return start_skill_info;
    }

    /**
     * Get the amount of XP a skill had when the script was started.
     *
     * @param skill
     * @return the amount of xp
     */
    public static int getStartXP(SKILLS skill) {

        return start_skill_info.containsKey(skill) ? start_skill_info.get(skill): 0;
    }

    /**
     * Get's the amount of XP we gained in a skill.
     * Make sure setStartSkills() was called on this skill.
     *
     * @param skill to check
     * @return
     */
    public static int getReceivedXP(SKILLS skill) {

        int xp = Skills.getXP(skill);

        int result = 0;

        if (xp > 0) {
            result = xp - getStartXP(skill);
        }

        return result;
    }

    /**
     * Get's the amount of XP we gained in a skill.
     * Make sure setStartSkills() was called on this skill.
     *
     * @param skill to check
     * @return
     */
    public static int getReceivedXP(SKILLS skill, long runtime) {

        int result = getReceivedXP(skill);

        int hours = (int)TimeUnit.MILLISECONDS.toHours(runtime);

        if (hours > 0) {
            if (((result / 1000) / hours) > 100) {
                // If xp is more then 100k per hour, something is up..
                return 0;
            }
        } else if ((result / 1000) > 100) {
            return 0;
        }

        return result;
    }

    /**
     * Checks if we received XP in a skill since the script started.
     *
     * @param skill to check
     * @return true if xp was gained, false otherwise.
     */
    public static boolean hasReceivedXP(SKILLS skill) {
        return getReceivedXP(skill) > 0 && start_skill_info.containsKey(skill);
    }

    /**
     * Gets all the skills who's XP is higher then when we started the script
     *
     * @return true if xp was gained, false otherwise.
     */
    public static SKILLS[] getAllSkillsWithIncrease() {

        ArrayList<SKILLS> list = new ArrayList<>();

        for (SKILLS s : SKILLS.values()) {
            if (hasReceivedXP(s)) {
                list.add(s);
            }
        }
        return list.toArray(new SKILLS[list.size()]);
    }

    /**
     * Gets the skill where we received the most XP in since we started the script.
     *
     * @return the skill, or null if none.
     */
    public static SKILLS getSkillWithMostIncrease(SKILLS defaultSkill) {

        SKILLS result = defaultSkill;
        int value = Integer.MIN_VALUE;

        for (SKILLS skill : getAllSkillsWithIncrease()) {

            int increase = getReceivedXP(skill);

            if (increase > value) {
                value = increase;
                result = skill;
            }

        }

        return result;
    }

    public static String getPrettySkillName(SKILLS skill) {
        return skill.name().substring(0, 1) + skill.name().substring(1).toLowerCase();
    }
}
