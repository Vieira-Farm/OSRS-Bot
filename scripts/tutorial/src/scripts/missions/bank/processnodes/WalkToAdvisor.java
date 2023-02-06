package scripts.missions.bank.processnodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;
import scripts.data.Constants;

public class WalkToAdvisor extends SuccessProcessNode {

    private final RSTile FINANCIAL_ADVISOR_TILE = Utilities.getRandomizedTile(new RSTile(3127, 3124, 0), 1);
    private final RSTile ACCIDENT_DOOR_TILE = new RSTile(3124, 3126, 0);

    @Override
    public String getStatus() {
        return "Walking to Advisor";
    }

    @Override
    public void successExecute() {
        if (!Constants.BANK_ACCIDENT_ROOM.contains(Player.getPosition())) {
            WebWalking.walkTo(FINANCIAL_ADVISOR_TILE,
                    ConditionUtilities.areaContains(Constants.BANK_INSTRUCTOR_ROOM),General.random(3000, 5000));
        } else {
            RSObject[] doors = Objects.getAt(ACCIDENT_DOOR_TILE);
            if (doors.length > 0 && Clicking.click("Open door", doors[0])) {
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return Objects.getAt(ACCIDENT_DOOR_TILE).length == 0;
                }, General.random(3000, 5000));
                Timing.waitCondition(() -> doors[0].getPosition() != ACCIDENT_DOOR_TILE, General.random(3000, 5000));
            }
        }
    }
}
