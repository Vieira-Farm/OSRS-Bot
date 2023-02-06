package scripts.missions.survival.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class WalkToGuide extends SuccessProcessNode {

    private RSTile SURVIVAL_TILE = new RSTile(3101, 3096, 0);

    @Override
    public String getStatus() {
        return "Walking to guide";
    }

    @Override
    public void successExecute() {
        Walking.walkTo(SURVIVAL_TILE);
        Timing.waitCondition(ConditionUtilities.nearTile(5, SURVIVAL_TILE), General.random(3000, 5000));
    }
}