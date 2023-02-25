package scripts.tutorial.missions.mage;

import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.mage.decisionnodes.ShouldLeavePrayer;
import org.tribot.api2007.Game;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class MageMission implements TreeMission {

    //Documentation:
    //Setting 281 == 610 when you need to leave the prayer guide
    //Setting 281 == 620 when you need to talk to the magic guide for the first time
    //Setting 281 == 630 when you should open the magic tab
    //Setting 281 == 640 when you should talk tot he magic guide for the second time
    //Setting 281 == 650 when you should cast wind strike
    //Setting 281 == 660 || 670 when you should talk to the magic guide for the last time

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldLeavePrayer();
    }

    @Override
    public String getMissionName() {
        return "Mage mission";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 610 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 700;
    }
}
