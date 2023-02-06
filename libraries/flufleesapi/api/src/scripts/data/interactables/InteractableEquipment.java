package scripts.data.interactables;

import org.tribot.api2007.Equipment;

public class InteractableEquipment extends InteractableItem {

    private Equipment.SLOTS slot;

    public InteractableEquipment(String interactableName, int interactableID, String action, Equipment.SLOTS slot) {
        super(interactableName, action, interactableID, 1);
        this.slot = slot;
    }

    public InteractableEquipment(String interactableName, int numberIDs, String action, Equipment.SLOTS slot, int... interactableIDs) {
        super(interactableName, action, 1, numberIDs, interactableIDs);
        this.slot = slot;
    }

    public InteractableEquipment(String interactableName, int quantity, int interactableID, String action, Equipment.SLOTS slot) {
        super(interactableName, action, interactableID, quantity);
        this.slot = slot;
    }

    public InteractableEquipment(String interactableName, int quantity, int numberIDs, String action, Equipment.SLOTS slot, int... interactableIDs) {
        super(interactableName, action, quantity, numberIDs, interactableIDs);
        this.slot = slot;
    }

    private InteractableEquipment(InteractableItem interactableItem, Equipment.SLOTS slot) {
        super(interactableItem.getName(), interactableItem.getAction(), interactableItem.getQuantity(),
                interactableItem.getNumberIds(), interactableItem.getInteractableIds());
        this.slot = slot;
    }

    public Equipment.SLOTS getSlot() {
        return slot;
    }

}
