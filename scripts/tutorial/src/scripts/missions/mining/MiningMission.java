package scripts.missions.mining;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;
import scripts.data.Constants;
import scripts.missions.mining.decisionnodes.ShouldClimbUnderground;

public class MiningMission implements TreeMission {

    //Documentation
    //Setting 281 == 250 when we need to climb underground
    //Setting 281 == 260 when we need to talk to the guide the first time
    //Setting 281 == 270 when we should prospect the first rock
    //Setting 281 == 280 || 281 when we should talk to the guide after prospecting a rock
    //Setting 281 == 290 || 291 when we should prospect our second rock
    //Setting 281 == 300 || 310 || 311 when we should mine a rock. 310 and 311, are for the second rocks. Should
    //use the setting value to indicate what rock to mine
    //Setting 281 == 320 when we should smelt a bronze bar
    //Setting 281 == 330 when we should talk to the guide about smithing a dagger
    //Setting 281 == 340 || 350 when we should smith a dagger
    //Setting 281 == 360 when we should open the gate to the combat instructor

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldClimbUnderground();
    }

    @Override
    public String getMissionName() {
        return "Mining mission";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 250 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 370;
    }
}
