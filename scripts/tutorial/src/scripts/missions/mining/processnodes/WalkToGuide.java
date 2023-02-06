package scripts.missions.mining.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class WalkToGuide extends SuccessProcessNode {

    private final RSTile MINING_INSTRUCTOR_TILE = new RSTile(3080, 9506, 0);

    @Override
    public String getStatus() {
        return "Walking to Mining Instructor";
    }

    @Override
    public void successExecute() {
        WebWalking.walkTo(MINING_INSTRUCTOR_TILE);
        Timing.waitCondition(ConditionUtilities.nearTile(5, MINING_INSTRUCTOR_TILE), General.random(3000, 5000));
    }
}