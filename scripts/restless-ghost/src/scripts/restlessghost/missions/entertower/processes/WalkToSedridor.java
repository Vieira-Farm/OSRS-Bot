package scripts.restlessghost.missions.entertower.processes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;

public class WalkToSedridor extends SuccessProcessNode {
    @Override
    public String getStatus() {
        return "Walking to Sedridor";
    }

    @Override
    public void successExecute() {
        RSObject[] door = Objects.getAt(new RSTile(3108, 9570, 0));
        if (door.length < 1) {
            Walking.walkTo(new RSTile(3102, 9570, 0));
        } else if (!door[0].isOnScreen() || !door[0].isClickable() || !Clicking.click("Open", door[0])) {
            door[0].adjustCameraTo();
        } else {
            Timing.waitCondition(() -> door[0].getPosition().getX() != 3108, General.random(3000, 5000));
            Walking.walkTo(new RSTile(3102, 9570, 0));
        }
    }
}
