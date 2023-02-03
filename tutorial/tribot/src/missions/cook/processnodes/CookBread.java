package missions.cook.processnodes;

import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import scripting.antiban.AntiBanSingleton;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.Skilling;

public class CookBread extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Cooking Bread";
    }

    @Override
    public void successExecute() {
        RSObject range = AntiBanSingleton.get().selectNextTarget(
                Objects.findNearest(8, "Range"));
        if (range != null && Skilling.cookRangeForAntiban("Bread dough", range)) {
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
}