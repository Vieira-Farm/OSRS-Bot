package scripts.tutorial.missions.mage.processNodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class WalkToGuide extends SuccessProcessNode {

    private final RSTile MAGE_TILE = new RSTile(3141, 3088, 0);

    @Override
    public String getStatus() {
        return "Walking to Mage";
    }

    @Override
    public void successExecute() {
        if (WebWalking.walkTo(MAGE_TILE)) {
            Timing.waitCondition(ConditionUtilities.nearTile(5, MAGE_TILE), General.random(3000, 5000));
        }
    }
}