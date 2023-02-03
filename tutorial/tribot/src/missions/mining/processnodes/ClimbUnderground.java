package missions.mining.processnodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;
import data.Constants;

public class ClimbUnderground extends SuccessProcessNode {

    public static final RSTile LADDER_TILE = new RSTile(3088, 3119, 0);

    @Override
    public String getStatus() {
        return "Climbing underground";
    }

    @Override
    public void successExecute() {
        RSObject[] objects = Objects.find(10, Filters.Objects.nameEquals("Ladder")
                .and(Filters.Objects.tileEquals(LADDER_TILE)));
        if (objects.length < 1) {
            return;
        }
        if (objects[0].isClickable() && objects[0].isOnScreen() && DynamicClicking.clickRSObject(objects[0], "Climb-down")) {
            if (Timing.waitCondition(ConditionUtilities.stoppedMoving(), General.random(3000, 5000))) {
                Timing.waitCondition(ConditionUtilities.areaNotContains(Constants.QUEST_GUIDE_AREA), General.random(3000, 5000));
            }
        } else if (objects[0].adjustCameraTo()) {
            Walking.blindWalkTo(objects[0].getPosition());
        }
    }
}