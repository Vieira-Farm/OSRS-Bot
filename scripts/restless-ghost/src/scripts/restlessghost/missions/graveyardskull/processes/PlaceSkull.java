package scripts.restlessghost.missions.graveyardskull.processes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import scripts.client.clientextensions.Filters;
import scripts.client.clientextensions.Game;
import scripts.client.clientextensions.Inventory;
import scripts.client.clientextensions.Objects;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class PlaceSkull extends SuccessProcessNode {
    @Override
    public String getStatus() {
        return "Placing Skull";
    }

    @Override
    public void successExecute() {
        if (Game.getItemSelectionState() == 1 && Game.getSelectedItemName().equals("Ghost's skull")) {
            RSObject[] coffins = Objects.find(5, Filters.Objects.nameEquals("Coffin")
                    .and(Filters.Objects.hasAction("Close")));
            if (coffins.length < 1) {
                return;
            } else if (!coffins[0].isClickable() || !coffins[0].isOnScreen() || !Clicking.click("Use Ghost's skull -> Coffin", coffins[0])) {
                coffins[0].adjustCameraTo();
            } else {
                Timing.waitCondition(ConditionUtilities.settingDoesNotEqual(1021, 2048),
                        General.random(3000, 5000));
            }
        } else {
            RSItem[] skulls = Inventory.find("Ghost's skull");
            if (skulls.length < 1) {
                return;
            }
            Clicking.click("Use", skulls[0]);
            Timing.waitCondition(() -> Game.getItemSelectionState() == 1, General.random(3000, 5000));
        }
    }
}
