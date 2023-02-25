package scripts.tutorial.missions.cook.processnodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;

public class OpenGate extends SuccessProcessNode {

    private final RSTile GATE_TILE = Utilities.getRandomizedTile(new RSTile(3092, 3092, 0), 2);

    @Override
    public String getStatus() {
        return "Opening gate";
    }

    @Override
    public void successExecute() {
        RSObject[] gates = Objects.find(5, "Gate");
        if (gates.length < 1) {
            if (Walking.walkTo(GATE_TILE)) {
                Timing.waitCondition(ConditionUtilities.nearTile(3, GATE_TILE), General.random(5000, 8000));
            }
            return;
        }
        if (gates[0].isClickable() && Clicking.click("Open", gates[0])) {
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return Player.getPosition().getX() < 3090;
            }, General.random(3000, 5000));
        } else {
            gates[0].adjustCameraTo();
        }
    }
}