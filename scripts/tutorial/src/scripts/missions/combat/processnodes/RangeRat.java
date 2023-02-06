package scripts.missions.combat.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Skilling;

public class RangeRat extends SuccessProcessNode {

    private final RSTile RANGING_TILE = new RSTile(3107, 9523, 0);

    @Override
    public String getStatus() {
        return "Ranging Rat";
    }

    @Override
    public void successExecute() {
        RSNPC rat = AntiBanSingleton.get().selectNextTarget(NPCs.findNearest("Giant rat"));
        if (rat == null && WebWalking.walkTo(RANGING_TILE)) {
            Timing.waitCondition(ConditionUtilities.nearTile(5, RANGING_TILE), General.random(3000, 5000));
        } else if (Skilling.rangeMonsterSafeSpotForAntiban(rat)) {
            AntiBanSingleton.get().generateSupportingTrackerInfo(5000, true);
            long startTime = System.currentTimeMillis();
            while (Player.getRSPlayer().getInteractingCharacter() != null) {
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