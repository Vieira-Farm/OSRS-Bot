package scripts.tutorial.missions.mining.processnodes;

import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.Skilling;

public class SmeltBar extends SuccessProcessNode {

    private RSTile FURNACE_TILE = new RSTile(3079, 9498, 0);

    @Override
    public String getStatus() {
        return "Smelting Bar";
    }

    @Override
    public void successExecute() {
        RSObject furnace = AntiBanSingleton.get().selectNextTarget(Objects.find(7,"Furnace"));
        if (furnace == null) {
            Walking.walkTo(FURNACE_TILE);
        } else if (Skilling.smeltBarTutorial(Skilling.Bars.BRONZE, furnace)) {
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
}