package scripts.missions.survival;

import scripts.client.clientextensions.Game;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;
import scripts.data.Constants;
import scripts.missions.survival.decisionnodes.ShouldWalkGuide;

public class SurvivalMission implements TreeMission {

    //Documentation
    //Handles working with the survival instructor
    //Setting 281 == 20 when you should walk to the survival instructor and talk to them initially
    //Setting 281 == 30 when you should open your inventory for the first time
    //Setting 281 == 40 when you should catch fish for the first time
    //Setting 281 == 50 when you should open your skills tab
    //Setting 281 == 60 when you should speak to the guide for a second time
    //Setting 281 == 70 when you should chop down a tree
    //Setting 281 == 80 when you should make a fire
    //Setting 281 == 90 || 100 when you should cook fish

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkGuide();
    }

    @Override
    public String getMissionName() {
        return "Survival";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 20 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 120;
    }


}
