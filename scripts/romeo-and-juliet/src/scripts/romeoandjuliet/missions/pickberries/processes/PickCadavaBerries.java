package scripts.romeoandjuliet.missions.pickberries.processes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.types.RSObject;
import scripts.client.clientextensions.Filters;
import scripts.client.clientextensions.Inventory;
import scripts.client.clientextensions.Objects;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class PickCadavaBerries extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Picking Cadava Berries";
    }

    @Override
    public void successExecute() {
        int inventoryCount = Inventory.getSize();
        RSObject[] bushes = Objects.findNearest(5, Filters.Objects.nameEquals("Cadava bush").and
                (Filters.Objects.modelLengthGreaterThan(1500)));
        if (bushes.length < 1) {
            return;
        } else if (!bushes[0].isOnScreen() || !bushes[0].isClickable()) {
            bushes[0].adjustCameraTo();
        } else if (Clicking.click("Pick-from", bushes[0])) {
            Timing.waitCondition(ConditionUtilities.inventoryCountChanged(inventoryCount), General.random(3000, 5000));
        }
    }
}