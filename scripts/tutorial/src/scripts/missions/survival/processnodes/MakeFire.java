package scripts.missions.survival.processnodes;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSPlayer;
import scripts.data.structures.ScriptVariables;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.Skilling;

public class MakeFire extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Making fire";
    }

    @Override
    public void successExecute() {
        int firemakingXp = Skills.getXP(Skills.SKILLS.FIREMAKING);
        if (Skilling.makeFireABC2("Logs", ScriptVariables.getInstance().getRandomBoolean())) {
            AntiBanSingleton.get().generateSupportingTrackerInfo(5000, false);
            RSPlayer rsPlayer = Player.getRSPlayer();
            long startTime = System.currentTimeMillis();
            while (Login.getLoginState() == Login.STATE.INGAME &&
                    (firemakingXp == Skills.getXP(Skills.SKILLS.FIREMAKING) ||
                            (rsPlayer.getOrientation() < 1500 && rsPlayer.getOrientation() > 1600))) {
                General.sleep(200, 300);
                AntiBanSingleton.get().resolveTimedActions();
            }

            AntiBanSingleton.get().setLastReactionTime(AntiBanSingleton.get().generateReactionTime((int) (System.currentTimeMillis() - startTime), true));
            AntiBanSingleton.get().generateSupportingTrackerInfo((int) (System.currentTimeMillis() - startTime), false);
            AntiBanSingleton.get().sleepReactionTime();
        }
    }
}