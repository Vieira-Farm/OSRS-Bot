package scripts.missions.mage.processNodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;
import scripts.data.Constants;

public class LeavePrayer extends SuccessProcessNode {

    private final RSTile DOOR_TILE = new RSTile(3122, 3102, 0);
    private final RSTile MAGIC_TILE = new RSTile(3128, 3093, 0);

    @Override
    public String getStatus() {
        return "Leaving prayer guide";
    }

    @Override
    public void successExecute() {
        if (Constants.PRAYER_AREA.contains(Player.getPosition())) {
            RSObject[] doors = Objects.getAt(DOOR_TILE, Filters.Objects.nameEquals("Door"));
            if (doors.length == 0) {
                return;
            }
            if (doors[0].isClickable() &&  Clicking.click("Open door", doors[0])) {
                Timing.waitCondition(ConditionUtilities.nearTile(1, DOOR_TILE), General.random(3000, 5000));
            } else {
                doors[0].adjustCameraTo();
            }
        } else if (Walking.blindWalkTo(Utilities.getRandomizedTile(MAGIC_TILE, 5))) {
            Timing.waitCondition(ConditionUtilities.nearTile(5, MAGIC_TILE), General.random(3000, 5000));
        }
    }
}