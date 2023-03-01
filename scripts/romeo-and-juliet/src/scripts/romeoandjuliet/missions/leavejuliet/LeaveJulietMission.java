package scripts.romeoandjuliet.missions.leavejuliet;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.data.structures.bag.BagIds;
import scripts.romeoandjuliet.missions.RomeoAndJuliet;
import scripts.romeoandjuliet.missions.leavejuliet.decision.ShouldWalkToStairs;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class LeaveJulietMission implements TreeMission {
    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkToStairs();
    }

    @Override
    public String getMissionName() {
        return "Leaving Juliet";
    }

    public String getBagId() {
        return BagIds.ROMEO_AND_JULIET.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Player.getPosition().getPlane() == 1 &&
                (Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 20 ||
                        Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 60);
    }

    @Override
    public boolean isMissionCompleted() {
        return Player.getPosition().getPlane() != 1 &&
                (Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 20 ||
                        Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 60);
    }
}
