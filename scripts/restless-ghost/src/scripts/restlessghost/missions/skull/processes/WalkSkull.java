package scripts.restlessghost.missions.skull.processes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.client.clientextensions.Filters;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class WalkSkull extends SuccessProcessNode {
    @Override
    public String getStatus() {
        return "Walking to Skull";
    }

    @Override
    public void successExecute() {
        if (Player.getPosition().getX() < 3111) {
            RSObject[] doors = Objects.find(5, Filters.Objects.hasAction("Open").and(Filters.Objects.tileEquals(new RSTile(3111, 9559, 0))));
            if (Player.getPosition().getY() > 9562) {
                Walking.walkTo(new RSTile(3109, 9560, 0));
                Timing.waitCondition(ConditionUtilities.nearTile(3, new RSTile(3111, 9559, 0)), General.random(3000, 5000));
            } else if (doors.length < 1) {
                Walking.walkTo(new RSTile(3115, 9566, 0));
            } else if (!doors[0].isOnScreen() || !doors[0].isClickable() || !Clicking.click("Open", doors[0])) {
                doors[0].adjustCameraTo();
            } else {
                Timing.waitCondition(() -> doors[0].getPosition().getX() != 3111, General.random(3000, 5000));
                Walking.walkTo(new RSTile(3115, 9566, 0));
            }
        } else {
            Walking.walkTo(new RSTile(3115, 9566, 0));
            Timing.waitCondition(ConditionUtilities.nearTile(5, new RSTile(3115, 9566, 0)),
                    General.random(3000, 5000));
        }
    }
}
