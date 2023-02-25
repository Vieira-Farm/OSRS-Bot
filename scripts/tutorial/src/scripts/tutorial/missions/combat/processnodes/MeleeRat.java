package scripts.tutorial.missions.combat.processnodes;

import org.tribot.api.General;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.Skilling;

public class MeleeRat extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Meleeing Rat";
    }

    @Override
    public void successExecute() {
        RSNPC rat = AntiBanSingleton.get().selectNextTarget(NPCs.findNearest("Giant rat"));
        if (rat != null && Skilling.meleeMonsterForAntiban(rat)) {
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