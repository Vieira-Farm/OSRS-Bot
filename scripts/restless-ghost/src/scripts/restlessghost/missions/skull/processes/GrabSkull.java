package scripts.restlessghost.missions.skull.processes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.types.RSObject;
import scripts.client.clientextensions.Inventory;
import scripts.client.clientextensions.Objects;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class GrabSkull extends SuccessProcessNode {
    @Override
    public String getStatus() {
        return "Grabbing skull";
    }

    @Override
    public void successExecute() {
        int inventorySize = Inventory.getSize();
        RSObject[] altars = Objects.findNearest(7, "Altar");
        if (altars.length < 1) {
            return;
        } else if (!altars[0].isOnScreen() || !altars[0].isClickable() || !Clicking.click("Search", altars[0])) {
            altars[0].adjustCameraTo();
        } else {
            Timing.waitCondition(ConditionUtilities.inventoryCountChanged(inventorySize), General.random(3000, 5000));
        }
    }
}
