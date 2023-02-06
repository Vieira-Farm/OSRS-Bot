package scripts.missions.mining.processnodes;

import org.tribot.api.General;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.Skilling;

public class SmithDagger extends SuccessProcessNode {

    private RSTile ANVIL_TILE = new RSTile(3082, 9500, 0);

    @Override
    public String getStatus() {
        return "Smithing Dagger";
    }

    @Override
    public void successExecute() {
        RSObject anvil = AntiBanSingleton.get().selectNextTarget( Objects.find(7,"Anvil"));
        if (anvil == null) {
            Walking.walkTo(ANVIL_TILE);
        } else if (Skilling.useAnvilForAntiban(anvil) && Skilling.smithItemForAntiban(Skilling.SmithingItems.DAGGER, 1)) {
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