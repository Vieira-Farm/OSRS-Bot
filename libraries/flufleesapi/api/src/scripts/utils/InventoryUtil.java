package scripts.utils;

import scripts.client.clientextensions.Game;
import scripts.client.clientextensions.Inventory;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.types.RSItem;

/**
 *
 * @author Spencer
 */
public class InventoryUtil {

    public static boolean interact(String itemName, String option) {
        Utilities.openGameTab(GameTab.TABS.INVENTORY);
        RSItem[] items = Inventory.find(itemName);
        if (items.length < 1)
            return false;
        return items[0].click(option);
    }

    public static boolean useObject(String itemName) {
        if (isUsing(itemName)) {
            return true;
        }
        Utilities.openGameTab(GameTab.TABS.INVENTORY);
        RSItem[] items = Inventory.find(itemName);
        if (items.length < 1)
            return false;
        return items[0].click("Use " + itemName);
    }


    public static RSItem getFirst(String item) {
        RSItem[] items = Inventory.find(item);
        if (items != null && items.length > 0)
            return items[0];
        return null;
    }

    public static boolean isUsing(String item) {
        return Game.isUptext(item);
    }

    public static int getCount(String... items) {
        int count = 0;
        for (String item : items) {
            count += Inventory.getCount(item);
        }

        return count;
    }

    public static int getCount(int... items) {
        int count = 0;
        for (int item : items) {
            count += Inventory.getCount(item);
        }

        return count;
    }
}

