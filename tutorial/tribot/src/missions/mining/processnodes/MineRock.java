package missions.mining.processnodes;

import client.clientextensions.Inventory;
import client.clientextensions.Objects;
import client.clientextensions.Walking;
import data.Constants;
import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSObject;
import data.structures.ScriptVariables;
import scripting.antiban.AntiBanSingleton;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.Skilling;
import utils.Utilities;

public class MineRock extends SuccessProcessNode {

    private int[] rockIds = new int[]{10079, 10080};

    @Override
    public String getStatus() {
        return "Mining Rock";
    }

    @Override
    public void successExecute() {
        RSObject rock = AntiBanSingleton.get().selectNextTarget(Objects.find(7, getRockId()));
        if (rock == null) {
            Walking.walkTo(Utilities.getRandomizedTile(Constants.MINING_AREA_MIDDLE_TILE, 5));
        } else if (Skilling.mineRockForAntiban(rock)) {
            AntiBanSingleton.get().generateSupportingTrackerInfo(5000, false);
            long startTime = System.currentTimeMillis();
            while (Player.getAnimation() != -1) {
                AntiBanSingleton.get().resolveTimedActions();
                General.sleep(200, 400);
            }
            AntiBanSingleton.get().setLastReactionTime(AntiBanSingleton.get().generateReactionTime(
                    (int) (System.currentTimeMillis() - startTime), false));
            AntiBanSingleton.get().generateSupportingTrackerInfo(
                    (int) (System.currentTimeMillis() - startTime), false);
            AntiBanSingleton.get().sleepReactionTime();
        }
    }

    private int getRockId() {
        if (Skills.getXP(Skills.SKILLS.MINING) == 0) {
            return rockIds[ScriptVariables.getInstance().getRandomNumber(2)];
        } else if (Inventory.getCount("Copper ore") > 0) {
            return rockIds[1];
        } else {
            return rockIds[0];
        }
    }
}