package client.clientextensions;

import org.tribot.api.General;
import org.tribot.api2007.types.RSItem;
import data.interactables.Interactable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inventory extends org.tribot.api2007.Inventory {

    /**
     * Checks if inventory contains an item with one of the names passed.
     *
     * @param names Names to search inventory for
     * @return True if an item is found, false otherwise.
     */
    public static boolean inventoryContains(String... names) {
        return org.tribot.api2007.Inventory.getCount(names) > 0;
    }

    /**
     * Checks if inventory contains an item with one of the IDs passed.
     *
     * @param ids IDs to search inventory for
     * @return True if an item is found, false otherwise.
     */
    public static boolean inventoryContains(int... ids) {
        return org.tribot.api2007.Inventory.getCount(ids) > 0;
    }

    /**
     * Checks if inventory contains one of the Interactable items passed.
     *
     * @param items Interactable items to search inventory for
     * @return True if an item is found, false otherwise.
     */
    public static boolean inventoryContains(Interactable... items) {
        for (Interactable equipment : items) {
            if (equipment.isAllIdsFound() &&
                    org.tribot.api2007.Inventory.getCount(equipment.getInteractableIds()) > 0) {
                return true;
            } else if (org.tribot.api2007.Inventory.getCount(equipment.getName()) > 0) {
                return true;
            }
            General.sleep(25, 50);
        }
        return false;
    }

    /**
     * Checks if the inventory contains all of the Interactable items passed.
     *
     * @param items Interactable items to search inventory for
     * @return True if all items are found, false otherwise.
     */
    public static boolean inventoryContainsAll(Interactable... items) {
        for (Interactable equipment : items) {
            if (equipment.isAllIdsFound() &&
                    (org.tribot.api2007.Inventory.getCount(equipment.getInteractableIds()) < 1)) {
                return false;
            } else if (org.tribot.api2007.Inventory.getCount(equipment.getName()) < 1) {
                return false;
            }
            General.sleep(25, 50);
        }
        return true;
    }

    /**
     * Gets count of an interactable items in the inventory.
     *
     * @param items Items to getInstance the count of
     * @return 0 if no items are found, otherwise the number of items found.
     */
    public static int getCount(Interactable... items) {
        int count = 0;
        for (Interactable equipment : items) {
            if (equipment.isAllIdsFound()) {
                count += org.tribot.api2007.Inventory.getCount(equipment.getInteractableIds());
            } else {
                count += org.tribot.api2007.Inventory.getCount(equipment.getName());
            }
            General.sleep(25, 50);
        }
        return count;
    }

    /**
     * Gets the size (number of items) currently in the inventory.
     *
     * @return Number of items currently in the inventory.
     */
    public static int getSize() {
        return org.tribot.api2007.Inventory.getAll().length;
    }

    /**
     * Finds the first instance of an item with the specified ID in your inventory.
     *
     * @param itemIDs IDs to search the inventory for
     * @return RSItem object containing the item, or null if the item is not found.
     */
    public static RSItem findFirst(int... itemIDs) {
        RSItem[] items = find(itemIDs);
        return items.length < 1 ? null : items[0];
    }

    /**
     * Finds the first instance of an item with the specified ID in your inventory.
     *
     * @param interactableItem Interactable to search the inventory for
     * @return RSItem object containing the item, or null if the item is not found.
     */
    public static RSItem findFirst(Interactable interactableItem) {
        if (interactableItem.isAllIdsFound()) {
            return findFirst(interactableItem.getInteractableIds());
        } else {
            return findFirst(interactableItem.getName());
        }
    }

    /**
     * Finds the first instance of an item with the specified name in your inventory.
     *
     * @param itemNames Names to search the inventory for
     * @return RSItem object containing the item, or null if the item is not found.
     */
    public static RSItem findFirst(String... itemNames) {
        RSItem[] items = find(itemNames);
        return items.length < 1 ? null : items[0];
    }

    /**
     * Searches the inventory for Interactable items
     *
     * @param items Interactable items to search the inventory for
     * @return Array of RSItem containing all items found.
     */
    public static RSItem[] find(Interactable... items) {
        List<RSItem> foundItems = new ArrayList<>();
        RSItem[] tempItems;
        for (Interactable item : items) {
            if (item.isAllIdsFound()) {
                tempItems = find(item.getInteractableIds());
                if (tempItems.length > 0) {
                    foundItems.addAll(Arrays.asList(tempItems));
                }
            } else {
                tempItems = find(item.getName());
                if (tempItems.length > 0) {
                    foundItems.addAll(Arrays.asList(tempItems));
                }
            }
            General.sleep(25, 50);
        }
        return foundItems.toArray(new RSItem[foundItems.size()]);
    }

}
