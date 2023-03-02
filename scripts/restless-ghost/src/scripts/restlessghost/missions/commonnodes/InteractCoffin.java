package scripts.restlessghost.missions.commonnodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSObject;
import scripts.client.clientextensions.Objects;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;

public class InteractCoffin extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Interact Coffin";
    }

    @Override
    public void successExecute() {
        RSObject[] coffins = Objects.findNearest(8, "Coffin");
        if (coffins.length < 1) {
            return;
        }
        if (!coffins[0].isClickable() || !coffins[0].isOnScreen() || !DynamicClicking.clickRSObject(coffins[0], "Open", "Close")) {
            coffins[0].adjustCameraTo();
        } else {
            Timing.waitCondition(() -> NPCs.findNearest("Restless Ghost").length > 0, General.random(3000, 5000));
        }
    }
}