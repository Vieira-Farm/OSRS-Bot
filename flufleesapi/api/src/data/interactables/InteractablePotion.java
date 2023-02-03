package data.interactables;

import java.util.Arrays;

public class InteractablePotion extends InteractableItem {
    private static final String POTION_ACTION = "Drink";
    private Interactable[] potions;
    private int defaultDose;
    private boolean allIdsFound;

    public InteractablePotion(String potionName, int defaultDose, int quantity, int... ids) {
        super(potionName, POTION_ACTION, quantity, defaultDose, ids);
        this.defaultDose = defaultDose;
        this.potions = new InteractableItem[defaultDose];
    }

    public InteractablePotion(String potionName, int quantity, int... ids) {
        super(potionName, POTION_ACTION, quantity, 4, ids);
        this.defaultDose = 4;
        this.potions = setupPotions(this.defaultDose);
    }

    private Interactable[] setupPotions(int size) {
        Interactable[] potions = new Interactable[size];
        for (int i = 0; i < potions.length; i++) {
            potions[i] = new Interactable(this.name + " (" + this.defaultDose + ")", this.action, this.getId(i));
        }
        return potions;
    }

    public String getPotionAction() {
        return POTION_ACTION;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getDefaultDose() {
        return this.defaultDose;
    }

    public Interactable[] getPotions() {
        return this.potions;
    }

    public Interactable getWithdrawPotion() {
        return this.potions[this.potions.length-1];
    }

    public Interactable getPotion(int potionDose) {
        return potions[potionDose];
    }

    public int[] getAllPotionIds() {
        return Arrays.stream(potions).mapToInt(Interactable::getId).toArray();
    }

    public String[] getAllPotionNames() {
        return Arrays.stream(potions).map(Interactable::getName).toArray(String[]::new);
    }

    public String getPotionName() {
        return this.getPotionName(this.defaultDose);
    }

    public String getPotionName(int potionDose) {
        return potions[potionDose-1].getName();
    }

    @Override
    public boolean isAllIdsFound() {
        if (!allIdsFound) {
            allIdsFound = Arrays.stream(potions).allMatch(Interactable::isAllIdsFound);
        }
        return allIdsFound;
    }
}
