package scripts.restlessghost.missions.skull;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.data.structures.bag.BagIds;
import scripts.restlessghost.missions.RestlessGhost;
import scripts.restlessghost.missions.skull.decisions.ShouldWalkToSkull;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

import static scripts.restlessghost.data.Constants.QUEST_SETTING;

public class SkullMission implements TreeMission {

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkToSkull();
    }

    @Override
    public String getMissionName() {
        return "Get skull";
    }

    public String getBagId() {
        return BagIds.RESTLESS_GHOST.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(QUEST_SETTING) == 3 && Player.getPosition().getX() > 3102
                && Player.getPosition().getY() > 9555;
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(QUEST_SETTING) == 4;
    }
}
