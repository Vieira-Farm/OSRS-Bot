package scripts.missions.quest.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Walking;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.missions.quest.decisionnodes.ShouldWalkCookDoor;

public class WalkCookDoor extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Walking to Cook Door";
    }

    @Override
    public void successExecute() {
        if (Walking.walkTo(ShouldWalkCookDoor.COOK_DOOR_TILE)) {
            Timing.waitCondition(ConditionUtilities.nearTile(1, ShouldWalkCookDoor.COOK_DOOR_TILE),
                    General.random(3000, 5000));
        }
    }
}