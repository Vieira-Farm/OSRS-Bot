package scripts.restlessghost.missions.exittower.processes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.client.clientextensions.Filters;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;

public class WalkToLadder extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Walk to ladder";
    }

    @Override
    public void successExecute() {
        RSObject[] doors = Objects.find(5, Filters.Objects.hasAction("Open").and(Filters.Objects.tileEquals(new RSTile(3111, 9559, 0))));
        if (doors.length < 1) {
            Walking.walkTo(new RSTile(3104, 9576, 0));
        } else if (!doors[0].isOnScreen() || !doors[0].isClickable() || !Clicking.click("Open", doors[0])) {
            doors[0].adjustCameraTo();
        } else {
            Timing.waitCondition(() -> doors[0].getPosition().getX() != 3111, General.random(3000, 5000));
            Walking.walkTo(new RSTile(3104, 9576, 0));
        }
    }
}