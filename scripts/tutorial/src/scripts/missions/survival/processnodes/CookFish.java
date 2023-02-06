package scripts.missions.survival.processnodes;

import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.Skilling;

public class CookFish extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Cooking fish";
    }

    @Override
    public void successExecute() {
        RSObject fire = AntiBanSingleton.get().selectNextTarget(Objects.findNearest(8, "Fire"));
        if (fire != null && Skilling.cookFireForAntiban("Raw shrimps", fire)) {
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