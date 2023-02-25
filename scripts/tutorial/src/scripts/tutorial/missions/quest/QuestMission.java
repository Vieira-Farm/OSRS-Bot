package scripts.tutorial.missions.quest;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.quest.decisionnodes.ShouldWalkCookDoor;

public class QuestMission implements TreeMission {

    //Documentation
    //Setting == 170 when we need to open the cooks door to head to the quest guide
    //Setting == 180 when we need to do an emote
    //Setting == 220 when we need to talk to the quest guide the first time
    //Setting  == 230 when we need to open our quest log
    //Setting  == 240 when we need to talk to the quest guide the second time

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkCookDoor();
    }

    @Override
    public String getMissionName() {
        return "Quest mission";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 170 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 250;
    }
}
