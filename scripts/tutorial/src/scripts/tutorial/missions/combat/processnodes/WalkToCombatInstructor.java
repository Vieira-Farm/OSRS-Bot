package scripts.tutorial.missions.combat.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class WalkToCombatInstructor extends SuccessProcessNode {

    private final RSTile COMBAT_INSTRUCTOR_TILE = new RSTile(3105, 9508, 0);

    @Override
    public String getStatus() {
        return "Walking to Combat Instructor";
    }

    @Override
    public void successExecute() {
        Walking.walkTo(COMBAT_INSTRUCTOR_TILE);
        Timing.waitCondition(ConditionUtilities.nearTile(5, COMBAT_INSTRUCTOR_TILE), General.random(3000, 5000));
    }
}
