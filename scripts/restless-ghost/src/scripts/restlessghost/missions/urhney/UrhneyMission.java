package scripts.restlessghost.missions.urhney;

import org.tribot.api2007.Game;
import scripts.data.structures.bag.BagIds;
import scripts.restlessghost.missions.RestlessGhost;
import scripts.restlessghost.missions.urhney.decisions.ShouldWalkUrhney;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

import static scripts.restlessghost.data.Constants.QUEST_SETTING;

public class UrhneyMission implements TreeMission {
    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkUrhney();
    }

    @Override
    public String getMissionName() {
        return "Father Urhney";
    }

    public String getBagId() {
        return BagIds.RESTLESS_GHOST.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(QUEST_SETTING) == 1;
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(QUEST_SETTING) == 2;
    }
}
