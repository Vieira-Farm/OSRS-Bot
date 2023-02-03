package data.skills.crafting;

import data.interactables.Interactable;
import data.interactables.InteractableItem;

public interface CraftingItem {

    static InteractableItem makeMaterial(CraftingMaterial material, int quantity) {
        return new InteractableItem(material.getMaterial().getName(),
                material.getMaterial().getAction(),
                material.getMaterial().getId(),
                quantity);
    }

    int getLevelRequired();
    double getXpGained();
    CraftingMethods getMethod();
    Interactable getProduct();
    InteractableItem[] getMaterials();
}
