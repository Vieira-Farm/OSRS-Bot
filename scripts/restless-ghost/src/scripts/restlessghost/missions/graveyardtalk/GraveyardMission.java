package scripts.restlessghost.missions.graveyardtalk;

import org.tribot.api2007.Game;
import scripts.data.structures.bag.BagIds;
import scripts.restlessghost.missions.graveyardtalk.decisions.ShouldWalkGraveyard;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

import static scripts.restlessghost.data.Constants.QUEST_SETTING;

public class GraveyardMission implements TreeMission {

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkGraveyard();
    }

    @Override
    public String getMissionName() {
        return "Graveyard Chat Mission";
    }

    public String getBagId() {
        return BagIds.RESTLESS_GHOST.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(QUEST_SETTING) == 2;
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(QUEST_SETTING) == 3;
    }
}
