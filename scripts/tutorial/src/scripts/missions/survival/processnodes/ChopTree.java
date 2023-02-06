package scripts.missions.survival.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.client.clientextensions.Walking;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Skilling;

public class ChopTree extends SuccessProcessNode {

    private RSTile destination = new RSTile(3101, 3096, 0);

    @Override
    public String getStatus() {
        return "Chopping tree";
    }

    @Override
    public void successExecute() {
        RSObject nextTree = AntiBanSingleton.get().selectNextTarget( Objects.findNearest(4,"Tree"));
        if (nextTree == null) {
            if (Walking.walkTo(destination)) {
                Timing.waitCondition(ConditionUtilities.nearTile(5, destination), General.random(3000, 5000));
            }
        } else if (isTreeCuttable(nextTree.getPosition()) && Skilling.cutTreeAntiban(nextTree)) {
            AntiBanSingleton.get().generateSupportingTrackerInfo(5000, false);
            long startTime = System.currentTimeMillis();
            while (Player.getAnimation() != -1) {
                AntiBanSingleton.get().resolveTimedActions();
                General.sleep(200, 400);
            }
            AntiBanSingleton.get().setLastReactionTime(AntiBanSingleton.get().generateReactionTime(
                    (int) (System.currentTimeMillis() - startTime), true));
            AntiBanSingleton.get().generateSupportingTrackerInfo(
                    (int) (System.currentTimeMillis() - startTime), false);
            AntiBanSingleton.get().sleepReactionTime();
        }
    }

    /**
     * Verifies that the found tree is possible to cut. This is accomplished by making sure that the tree is within
     * certain x and y bounds. A tree outside those bounds would be beyond the fence in the survival area.
     *
     * @param position RSTile representing the position of the tree to verify
     * @return True if the tree is possible to cut, false otherwise
     */
    private boolean isTreeCuttable(RSTile position) {
        return position.getX() >= 3086 && position.getX() <= 3108 && position.getY() >= 3086 && position.getY() <= 3100;
        //MAX_TREE_Y = 3100, MIN_TREE_Y = 3086, MAX_TREE_X = 3108, MIN_TREE_X = 3086
    }
}