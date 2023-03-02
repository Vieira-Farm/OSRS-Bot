package scripts.restlessghost.missions.exittower.processes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;

public class ExitTower extends SuccessProcessNode {
    @Override
    public String getStatus() {
        return "Exiting tower";
    }

    @Override
    public void successExecute() {
        if (Player.getPosition().getX() < 3106) {
            RSObject[] door = Objects.getAt(new RSTile(3107, 3162, 0));
            if (door.length < 1) {
                Walking.walkTo(new RSTile(3109, 3165, 0));
            } else if (!door[0].isOnScreen() || !door[0].isClickable() || !Clicking.click("Open", door[0])) {
                door[0].adjustCameraTo();
            } else {
                Timing.waitCondition(() -> door[0].getPosition().getX() != 3107, General.random(3000, 5000));
                Walking.walkTo(new RSTile(3109, 3165, 0));
            }
        } else {
            RSObject[] door = Objects.getAt(new RSTile(3109, 3167, 0));
            if (door.length < 1) {
                Walking.walkTo(new RSTile(3109, 3170, 0));
            } else if (!door[0].isOnScreen() || !door[0].isClickable() || !Clicking.click("Open", door[0])) {
                door[0].adjustCameraTo();
            } else {
                Timing.waitCondition(() -> door[0].getPosition().getY() != 3167, General.random(3000, 5000));
                Walking.walkTo(new RSTile(3109, 3170, 0));
            }
        }
    }
}
