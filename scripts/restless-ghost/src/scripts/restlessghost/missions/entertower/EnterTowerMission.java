package scripts.restlessghost.missions.entertower;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.data.structures.bag.BagIds;
import scripts.restlessghost.missions.entertower.decisions.ShouldWalkToTower;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

import static scripts.restlessghost.data.Constants.QUEST_SETTING;

public class EnterTowerMission implements TreeMission {

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkToTower();
    }

    @Override
    public String getMissionName() {
        return "Entering Tower";
    }

    public String getBagId() {
        return BagIds.RESTLESS_GHOST.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(QUEST_SETTING) == 3 && Player.getPosition().getY() < 9565;
    }

    @Override
    public boolean isMissionCompleted() {
        return Player.getPosition().getX() < 3107
                && Player.getPosition().getY() > 9565;
    }
}
