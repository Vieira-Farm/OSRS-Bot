package scripts.tutorial.missions.cook.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Inventory;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.InventoryUtil;

public class MakeBreadDough extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Making Bread Dough";
    }

    @Override
    public void successExecute() {
        boolean orderBoolean = General.randomBoolean();
        String firstItem = orderBoolean ? "Pot of flour" : "Bucket of water",
                secondItem = !orderBoolean ? "Pot of flour" : "Bucket of water";
        if (Inventory.getCount(firstItem) > 0 && Inventory.getCount(secondItem) > 0) {
            if (!InventoryUtil.isUsing(firstItem)) {
                InventoryUtil.useObject(firstItem);
                return;
            }
            int inventoryCount = Inventory.getAll().length;
            if (InventoryUtil.interact(secondItem, "Use " + firstItem + " -> " + secondItem)) {
                Timing.waitCondition(ConditionUtilities.inventoryCountChanged(inventoryCount), General.random(3000, 5000));
            }
        }
    }
}