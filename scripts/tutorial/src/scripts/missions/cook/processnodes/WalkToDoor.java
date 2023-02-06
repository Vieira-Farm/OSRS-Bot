package scripts.missions.cook.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.types.RSTile;
import scripts.client.clientextensions.Walking;
import scripts.data.structures.ScriptVariables;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;

public class WalkToDoor extends SuccessProcessNode {

    private final RSTile CHEF_DOOR_TILE = Utilities.getRandomizedTile(new RSTile(3081, 3084, 0), 1);
    private final RSTile[] CHEF_PATH_DOOR = new RSTile[] {
            new RSTile(3088, 3091, 0), new RSTile(3089,3083,0), CHEF_DOOR_TILE
    };
    private final RSTile[] CHEF_PATH_FULL = new RSTile[] {
            new RSTile(3088,3091,0), new RSTile(3088,3074,0),
            new RSTile(3079,3069,0), new RSTile(3069,3068,0),
            new RSTile(3074,3075,0), CHEF_DOOR_TILE};

    @Override
    public String getStatus() {
        return "Walking to Entrance Door";
    }

    @Override
    public void successExecute() {
        double pathRoll = ScriptVariables.getInstance().getRandomDouble();
        if (pathRoll < 0.80) {
            org.tribot.api2007.Walking.clickTileMM(CHEF_DOOR_TILE, 1);
            Timing.waitCondition(ConditionUtilities.nearTile(2, CHEF_DOOR_TILE), General.random(4000, 6000));
        } else if (pathRoll < 0.97) {
            Walking.walkPath(org.tribot.api2007.Walking.randomizePath(CHEF_PATH_DOOR, 2, 2));
            Timing.waitCondition(ConditionUtilities.nearTile(2, CHEF_DOOR_TILE), General.random(4000, 6000));
        } else {
            Walking.walkPath(org.tribot.api2007.Walking.randomizePath(CHEF_PATH_FULL, 2, 2));
            Timing.waitCondition(ConditionUtilities.nearTile(2, CHEF_DOOR_TILE), General.random(4000, 6000));
        }
    }
}