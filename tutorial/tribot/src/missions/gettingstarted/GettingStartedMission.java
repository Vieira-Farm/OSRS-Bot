package missions.gettingstarted;

import client.clientextensions.Game;
import data.Constants;
import missions.gettingstarted.decisionnodes.ShouldTalkGuide;
import scripting.frameworks.mission.missiontypes.TreeMission;
import scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class GettingStartedMission implements TreeMission {

    //Documentation
    //Setting 281 equals 2 when you have to talk to the guide for the first time
    //Setting 281 equals 3 when you have to open setting
    //Setting 281 equals 7 after you've opened settings. At this point you can adjust your options, or talk to the guide
    //to continue
    //Setting 281 equals 10 after you've finished talking to the guide and can leave via the door
    //Setting 281 equals 20 once you've left the guide house and opened the d9oor

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldTalkGuide();
    }

    @Override
    public String getMissionName() {
        return "Gielinor Guide";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 2 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 20;
    }
}
