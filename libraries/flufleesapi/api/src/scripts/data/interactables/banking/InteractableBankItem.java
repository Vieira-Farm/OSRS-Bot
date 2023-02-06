package scripts.data.interactables.banking;

import org.tribot.api2007.types.RSItem;
import scripts.data.interactables.InteractableItem;

public class InteractableBankItem extends InteractableItem {

    private static final String DEFAULT_ACTION = "";
    private boolean withdrawNoted = false;

    public InteractableBankItem(RSItem item) {
        super("", DEFAULT_ACTION, item.getID(), item.getStack());
        this.withdrawNoted = false;
    }

    public InteractableBankItem(String interactableName, int quantity) {
        this(interactableName, DEFAULT_ACTION, DEFAULT_ID, quantity);
    }

    public InteractableBankItem(String interactableName, String action, int itemID, int quantity) {
        super(interactableName, action, itemID, quantity);
        this.withdrawNoted = false;
    }

    public static class Builder {
        protected String interactableName;
        protected int interactableID = DEFAULT_ID, quantity;
        protected boolean withdrawNoted;

        public Builder(String interactableName, int quantity) {
            this.interactableName = interactableName;
            this.quantity = quantity;
        }

        public Builder withID(int interactableID) {
            this.interactableID = interactableID;
            return this;
        }

        public Builder withdrawNoted() {
            this.withdrawNoted = true;
            return this;
        }

        public InteractableBankItem build() {
            InteractableBankItem item  =  new InteractableBankItem(interactableName, DEFAULT_ACTION, interactableID, quantity);
            item.withdrawNoted = withdrawNoted;
            return item;
        }

    }

    public boolean isWithdrawNoted() {
        return withdrawNoted;
    }

    public void setWithdrawNoted(boolean withdrawNoted) {
        this.withdrawNoted = withdrawNoted;
    }
}
