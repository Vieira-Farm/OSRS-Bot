package scripts.data.skills.crafting;

import scripts.data.interactables.Interactable;
import scripts.data.interactables.InteractableItem;

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
