package missions.prayer;

import org.tribot.api2007.Game;
import scripting.frameworks.mission.missiontypes.TreeMission;
import scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;
import data.Constants;
import missions.prayer.decisionnodes.ShouldOpenBankDoor;

public class PrayerMission implements TreeMission {

    //Documentation:
    //Setting 281 == 540 when we need to open the door to leave the financial advisor
    //Setting 281 == 550 when we should talk to the prayer guide the first time
    //Setting 281 == 560 when we should open the prayer tab
    //Setting 281 == 570 when we should talk to the prayer guide the second time
    //Setting 281 == 580 when we should open the friends tab
    //Setting 281 == 600 when we should talk to the prayer guide the third time

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldOpenBankDoor();
    }

    @Override
    public String getMissionName() {
        return "Prayer mission";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 540 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 610;
    }
}
