package scripts.client.clientextensions;

import scripts.data.interactables.Interactable;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;

public class Equipment extends org.tribot.api2007.Equipment {

    private static final int CACHED_ITEM_INDEX = 94;

    /**
     * Gets the count of the item from the equipped items list
     *
     * @param item Interactable item to get the count of
     * @return int representing the count of the item, or -1 if the item is not found
     */
    public static int getCount(Interactable item) {
        return item.isAllIdsFound() ? getCount(item.getId()) : getCount(item.getName());
    }

    /**
     * Gets the count of the item with the same name
     *
     * @param name String containing the name of the item we want the count of
     * @return The count of the item as an int or -1 if it's not found
     */
    public static int getCount(String name) {
        RSItem[] equippedItems = getItems();
        for (RSItem item : equippedItems) {
            RSItemDefinition itemDefinition = item.getDefinition();
            if (itemDefinition != null && itemDefinition.getName().equals(name)) {
                return item.getStack();
            }
        }
        return -1;
    }

    /**
     * Gets the count of the item with the id passed
     *
     * @param id int representing the id of the item we want to get the count of
     * @return The count of the item as an int, or -1 if it's not found.
     */
    public static int getCount(int id) {
        RSItem[] equippedItems = getItems();
        for (RSItem item : equippedItems) {
            if (item.getID() == id) {
                return item.getStack();
            }
        }
        return -1;
    }

    /**
     * Gets all equipped items by polling the game cache
     *
     * @return RSItem[] containing all items that you have currently equipped
     */
    public static RSItem[] getItems() {
        return Game.getCachedItems(CACHED_ITEM_INDEX);
    }

    /**
     * Gets the item in the slot
     *
     * @param slot SLOTS item representing the slot we want to look for an item in
     * @return RSItem representing the item that is in the slot, or null if not defined.
     */
    public static RSItem getItem(SLOTS slot) {
        RSItem[] equippedItems = getItems();
        for (RSItem item : equippedItems) {
            if (item.getEquipmentSlot() == slot) {
                return item;
            }
        }
        return null;
    }
}
