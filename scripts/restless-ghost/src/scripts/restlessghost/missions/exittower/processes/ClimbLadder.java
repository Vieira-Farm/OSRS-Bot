package scripts.restlessghost.missions.exittower.processes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class ClimbLadder extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Climbing Ladder";
    }

    @Override
    public void successExecute() {
        RSObject[] ladders = Objects.findNearest(3, "Ladder");
        if (ladders.length < 1) {
            Walking.walkTo(new RSTile(3104, 9576, 0));
        } else if (!ladders[0].isOnScreen() || !ladders[0].isClickable() || !Clicking.click("Climb-up", ladders[0])) {
            ladders[0].adjustCameraTo();
        } else {
            Timing.waitCondition(ConditionUtilities.nearTile(1, new RSTile(3105, 3162, 0)),
                    General.random(3000, 5000));
        }
    }
}