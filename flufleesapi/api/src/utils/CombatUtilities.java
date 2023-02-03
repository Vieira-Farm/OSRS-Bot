package utils;

import org.tribot.api.Clicking;
import org.tribot.api.Timing;
import org.tribot.api2007.Combat;
import org.tribot.api2007.types.RSItem;

public class CombatUtilities {

    /**
     * Eats food by clicking the passed RSItem in the inventory
     *
     * @param food RSItem representing the food to eat
     * @return True if the food was eaten, false otherwise.
     */
    public static boolean eat(RSItem food) {
        if (food == null) {
            return false;
        }
        String[] actions = food.getDefinition().getActions();
        for (String action : actions) {
            if (action.contains("Eat") || action.contains("Drink")) {
                return eat(food, action);
            }
        }
        return false;
    }

    /**
     * Eats food by clicking the passed option on the RSItem in the inventory
     *
     * @param food RSItem representing the food to eat
     * @param option String containing the option to click on the food
     * @return True if the food was eaten, false otherwise.
     */
    public static boolean eat(RSItem food, String option) {
        if (option == null || food == null) {
            return false;
        }
        int startingHP = Combat.getHP();
        if (Clicking.click(option, food)) {
            return Timing.waitCondition(ConditionUtilities.ateFood(startingHP), 3000);
        }
        return false;
    }
}
