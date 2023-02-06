package scripts.missions.bank.processnodes;

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

public class ClimbLadder extends SuccessProcessNode {

    private final RSTile UNDERGROUND_LADDER_TILE =
            Utilities.getRandomizedTile(new RSTile(3111, 9525, 0), 5);
    private final RSTile[] LADDER_PATH = new RSTile[] {
            new RSTile(3105, 9508, 0),
            new RSTile(3111, 9513, 0),
            new RSTile(3111, 9522),
            new RSTile(3111, 9525, 0)
    };
    @Override
    public String getStatus() {
        return "Climbing ladder";
    }

    @Override
    public void successExecute() {
        RSObject[] ladders = Objects.findNearest(10, "Ladder");
        if ((ladders.length < 1 || !ladders[0].isClickable()) &&
                Walking.walkPath(Walking.randomizePath(LADDER_PATH, 1, 1))) {
            Timing.waitCondition(ConditionUtilities.nearTile(5, UNDERGROUND_LADDER_TILE), General.random(3000, 5000));
        } else {
            Clicking.click("Climb-up", ladders[0]);
            Timing.waitCondition(ConditionUtilities.changedTile(Player.getPosition()), General.random(3000, 5000));
        }
    }
}
