package data.interactables;

public class InteractableItem extends Interactable {

    protected int quantity;

    /**
     * Use this constructor if the object you're working with has more than one ID. Such as a tree or a monster.
     * @param interactableName Name of object
     * @param action           Action to use with the object (i.e. Wield, Wear, etc.)
     * @param quantity         Quantity of item to use/withdraw.
     * @param numberIDs        Number of possible IDs the object can have. You will need to know this prior, so the script know's when to stop searching.
     * @param interactableIDs  IDs of the object that we know starting off.
     */
    public InteractableItem(String interactableName, String action, int quantity, int numberIDs, int... interactableIDs) {
        super(interactableName, action, numberIDs, interactableIDs);
        this.quantity = quantity;
    }

    public InteractableItem(String interactableName, String action, int interactableID, int quantity) {
        super(interactableName, action, interactableID);
        this.quantity = quantity;
    }

    protected InteractableItem(Interactable interactable, int quantity) {
        super(interactable.getName(), interactable.getAction(),
                interactable.getNumberIds(), interactable.getInteractableIds());
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
