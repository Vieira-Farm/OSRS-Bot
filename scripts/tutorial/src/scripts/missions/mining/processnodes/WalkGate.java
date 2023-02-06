package scripts.missions.mining.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;

public class WalkGate extends SuccessProcessNode {

    public static final RSTile GATE_TILE = Utilities.getRandomizedTile(new RSTile(3093, 9502, 0), 1);

    @Override
    public String getStatus() {
        return "Walking to gate";
    }

    @Override
    public void successExecute() {
        WebWalking.walkTo(GATE_TILE);
        Timing.waitCondition(ConditionUtilities.nearTile(2, GATE_TILE), General.random(3000, 5000));
    }
}