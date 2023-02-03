package missions.prayer.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;
import utils.Utilities;
import data.Constants;

public class WalkToGuide extends SuccessProcessNode {

    private final RSTile PRAYER_INSIDE_TILE = Utilities.getRandomizedTile(new RSTile(3123, 3106, 0), 2),
    OUTSIDE_DOOR = Utilities.getRandomizedTile(new RSTile(3137, 3118, 0), 3);

    @Override
    public String getStatus() {
        return "Walking to guide";
    }

    @Override
    public void successExecute() {
        if (Constants.OUTSIDE_BANK_GUIDE_AREA.contains(Player.getPosition()) && Walking.walkTo(OUTSIDE_DOOR)) {
            Timing.waitCondition(ConditionUtilities.areaNotContains(Constants.OUTSIDE_BANK_GUIDE_AREA),
                    General.random(3000, 5000));
        } else if (WebWalking.walkTo(PRAYER_INSIDE_TILE)) {
            Timing.waitCondition(ConditionUtilities.nearTile(3, PRAYER_INSIDE_TILE), General.random(3000, 5000));
        }
    }
}