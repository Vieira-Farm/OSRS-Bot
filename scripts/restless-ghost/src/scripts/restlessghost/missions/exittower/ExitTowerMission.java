package scripts.restlessghost.missions.exittower;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.data.structures.bag.BagIds;
import scripts.restlessghost.missions.RestlessGhost;
import scripts.restlessghost.missions.exittower.decisions.ShouldWalkLadder;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

import static scripts.restlessghost.data.Constants.QUEST_SETTING;

public class ExitTowerMission implements TreeMission {

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkLadder();
    }

    @Override
    public String getMissionName() {
        return "Exit tower.";
    }

    public String getBagId() {
        return BagIds.RESTLESS_GHOST.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(QUEST_SETTING) == 4 &&
                (Player.getPosition().getY() > 9550 || Player.getPosition().getY() < 3167);
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(QUEST_SETTING) == 4
                && Player.getPosition().getY() < 9550 && Player.getPosition().getY() > 3167;
    }
}
