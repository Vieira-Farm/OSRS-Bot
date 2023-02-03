package missions.bank;

import data.Constants;
import missions.bank.decisionnodes.ShouldClimbLadder;
import org.tribot.api2007.Game;
import scripting.frameworks.mission.missiontypes.TreeMission;
import scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class BankMission implements TreeMission {

    //Documentation
    //Setting 281 == 500 when we should climb the ladder to exit the underground cave
    //Setting 281 == 510 when we should talk to the banker
    //Setting 281 == 520 when we should open the poll booth
    //Setting 281 == 525 when we should walk to the financial adviser
    //Setting 281 == 530 when we should talk to the financial adviser
    //Setting 281 == 531 when we should open the account settings tab
    //Setting 281 > 531 when we should talk to the adviser again

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldClimbLadder();
    }

    @Override
    public String getMissionName() {
        return "Bank mission";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 500 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 540;
    }
}
