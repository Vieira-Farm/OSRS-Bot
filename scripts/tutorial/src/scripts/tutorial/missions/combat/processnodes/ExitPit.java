package scripts.tutorial.missions.combat.processnodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.tutorial.data.Constants;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;

public class ExitPit extends SuccessProcessNode {

    private final RSTile GATE_TILE = Utilities.getRandomizedTile(new RSTile(3109, 9519, 0),1);

    @Override
    public String getStatus() {
        return "Exiting fight pit";
    }

    @Override
    public void successExecute() {
        RSObject[] gates = Objects.findNearest(4, "Gate");
        if (gates.length < 1 && WebWalking.walkTo(GATE_TILE)) {
            Timing.waitCondition(ConditionUtilities.nearTile(2, GATE_TILE), General.random(3000, 5000));
        } else if (Clicking.click("Open", gates[0])) {
            Timing.waitCondition(ConditionUtilities.areaNotContains(Constants.COMBAT_PIT_AREA), General.random(3000, 5000));
        }
    }
}