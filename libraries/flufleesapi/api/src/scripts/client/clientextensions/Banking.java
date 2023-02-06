package scripts.client.clientextensions;


import scripts.data.interactables.InteractableItem;

public class Banking extends org.tribot.api2007.Banking {

    /**
     * Deposits the InteractableItem in the bank
     *
     * @param item InteractableItem representing the item to deposit
     * @return True if the item was deposited, false otherwise
     */
    public static boolean deposit(InteractableItem item) {
       return deposit(item, item.getQuantity());
    }

    /**
     * Deposits the passed quantity amount of the InteractableItem
     *
     * @param item InteractableItem representing the item to deposit
     * @param quantity int representing the amount of the item to deposit
     * @return True if the item was deposited, false otherwise
     */
    public static boolean deposit(InteractableItem item, int quantity) {
        if (item.getId() != InteractableItem.DEFAULT_ID) {
            return org.tribot.api2007.Banking.deposit(quantity, item.getId());
        } else {
            return org.tribot.api2007.Banking.deposit(quantity, item.getName());
        }
    }

    /**
     * Withdraws the InteractableItem from the bank
     *
     * @param item InteractableItem representing the item to withdraw
     * @return True if the item was withdrawn, false otherwise
     */
    public static boolean withdraw(InteractableItem item) {
       return withdraw(item, item.getQuantity());
    }

    /**
     * Withdraws the InteractableItem from the bank
     *
     * @param item InteractableItem representing the item to withdraw
     * @param quantity int representing the amount of the item to withdraw
     * @return True if the item was withdrawn, false otherwise
     */
    public static boolean withdraw(InteractableItem item, int quantity) {
        if (item.getId() != InteractableItem.DEFAULT_ID) {
            return org.tribot.api2007.Banking.withdraw(quantity, item.getId());
        } else {
            return org.tribot.api2007.Banking.withdraw(quantity, item.getName());
        }
    }
}
