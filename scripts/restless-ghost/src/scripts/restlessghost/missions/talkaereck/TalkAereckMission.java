package scripts.restlessghost.missions.talkaereck;

import org.tribot.api2007.Game;
import scripts.data.structures.bag.BagIds;
import scripts.restlessghost.missions.RestlessGhost;
import scripts.restlessghost.missions.talkaereck.decisions.ShouldWalkAereck;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

import static scripts.restlessghost.data.Constants.QUEST_SETTING;

public class TalkAereckMission implements TreeMission {

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkAereck();
    }

    @Override
    public String getMissionName() {
        return "Talking Aereck";
    }

    public String getBagId() {
        return BagIds.RESTLESS_GHOST.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(QUEST_SETTING) < 1;
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(QUEST_SETTING) == 1;
    }
}
