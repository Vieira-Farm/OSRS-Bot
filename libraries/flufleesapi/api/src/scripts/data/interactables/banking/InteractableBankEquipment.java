package scripts.data.interactables.banking;

import org.tribot.api2007.Equipment;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;

public class InteractableBankEquipment extends InteractableBankItem {

    private Equipment.SLOTS slot;

    private InteractableBankEquipment(String interactableName, String action, int interactableID, int quanity) {
        super(interactableName, action, interactableID, quanity);
    }

    public static class Builder extends InteractableBankItem.Builder {

        private Equipment.SLOTS slot;
        private String action;

        public Builder(String interactableName, int quantity, Equipment.SLOTS slot) {
            super(interactableName, quantity);
            this.slot = slot;
        }

        public Builder action(String action) {
            this.action = action;
            return this;
        }

        public InteractableBankEquipment build() {
            InteractableBankEquipment item = new InteractableBankEquipment(interactableName, action, interactableID, quantity);
            item.slot = this.slot;
            return item;
        }
    }

    public Equipment.SLOTS getSlot() {
        return slot;
    }

    public void setSlot(Equipment.SLOTS slot) {
        this.slot = slot;
    }

    public boolean equals(RSItem item) {
        if (item == null) {
            return false;
        } else if (isAllIdsFound()) {
            return item.getID() == this.getId();
        } else {
            RSItemDefinition itemDefinition = item.getDefinition();
            return itemDefinition != null && itemDefinition.getName().equals(this.getName());
        }
    }
}
