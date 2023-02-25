package scripts.tutorial.missions.mage.processNodes;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.Skilling;

public class CastWindStrike extends SuccessProcessNode {

    private final RSTile WIND_STRIKE_TILE = new RSTile(3139, 3091, 0);
    private final RSArea ATTACKABLE_CHICKEN_AREA = new RSArea(new RSTile(3138, 3096, 0), new RSTile(3140, 3092, 0));

    @Override
    public String getStatus() {
        return "Casting Wind Strike";
    }

    @Override
    public void successExecute() {
        RSNPC chicken = AntiBanSingleton.get().selectNextTarget(NPCs.findNearest("Chicken"));
        if (chicken == null || !Player.getPosition().equals(WIND_STRIKE_TILE)) {
            Walking.walkTo(WIND_STRIKE_TILE);
        } else if (ATTACKABLE_CHICKEN_AREA.contains(chicken.getPosition()) &&
                Skilling.castCombatSpellForAntiban("Wind Strike", chicken)) {
            long startTime = System.currentTimeMillis();
            while (Player.getRSPlayer().getInteractingIndex() != -1) {
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