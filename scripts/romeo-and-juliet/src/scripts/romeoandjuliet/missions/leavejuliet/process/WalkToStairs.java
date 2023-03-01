package scripts.romeoandjuliet.missions.leavejuliet.process;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.client.clientextensions.Filters;
import scripts.client.clientextensions.Objects;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class WalkToStairs extends SuccessProcessNode {

    private final RSTile stairDoorTile = new RSTile(3157, 3430, 1);
    private final RSTile patioDoorTile = new RSTile(3158, 3426, 1);
    private final RSTile stairsTile = new RSTile(3156, 3435, 1);

    @Override
    public String getStatus() {
        return "Walking to stairs";
    }

    @Override
    public void successExecute() {
        RSTile playerPosition = Player.getPosition();
        RSObject[] stairDoors = Objects.find(12, Filters.Objects.tileEquals(stairDoorTile).and(Filters.Objects.nameEquals("Door")));
        RSObject[] patioDoors = Objects.find(12, Filters.Objects.tileEquals(patioDoorTile).and(Filters.Objects.nameEquals("Door")));

        if (playerPosition.getY() < 3427 && patioDoors.length > 0) { //on patio
            if (patioDoors[0].isOnScreen() && patioDoors[0].isClickable() && DynamicClicking.clickRSObject(patioDoors[0], "Open")) {
                Timing.waitCondition(ConditionUtilities.nearTile(1, patioDoorTile), General.random(1000, 2000));
            } else {
                patioDoors[0].adjustCameraTo();
            }
        } else if (playerPosition.getY() < 3431 && stairDoors.length > 0) { //inside patio, outside stairs
            if (stairDoors[0].isOnScreen() && stairDoors[0].isClickable() && DynamicClicking.clickRSObject(stairDoors[0], "Open")) {
                Timing.waitCondition(ConditionUtilities.nearTile(1, stairDoorTile), General.random(1000, 2000));
            } else {
                stairDoors[0].adjustCameraTo();
            }
        } else {
            WebWalking.walkTo(stairsTile);
            Timing.waitCondition(ConditionUtilities.nearTile(3, stairsTile), General.random(1000, 2000));
        }
    }
}
