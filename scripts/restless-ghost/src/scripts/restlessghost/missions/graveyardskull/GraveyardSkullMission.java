package scripts.restlessghost.missions.graveyardskull;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.client.clientextensions.Interfaces;
import scripts.data.structures.bag.BagIds;
import scripts.restlessghost.missions.graveyardskull.decisions.ShouldWalkGraveyard;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

import static scripts.restlessghost.data.Constants.QUEST_SETTING;

public class GraveyardSkullMission implements TreeMission {

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkGraveyard();
    }

    @Override
    public String getMissionName() {
        return "Graveyard Skull Mission";
    }

    public String getBagId() {
        return BagIds.RESTLESS_GHOST.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(QUEST_SETTING) == 4 && Player.getPosition().getY() < 9550
                && Player.getPosition().getY() > 3167;
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(QUEST_SETTING) == 5 && !Interfaces.isInterfaceValid(277);
    }
}
