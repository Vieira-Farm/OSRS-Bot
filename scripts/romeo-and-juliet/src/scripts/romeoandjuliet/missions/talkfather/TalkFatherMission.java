package scripts.romeoandjuliet.missions.talkfather;

import org.tribot.api2007.Game;
import scripts.data.structures.bag.BagIds;
import scripts.romeoandjuliet.missions.RomeoAndJuliet;
import scripts.romeoandjuliet.missions.talkfather.decisions.ShouldTalkToFather;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class TalkFatherMission implements TreeMission {
    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldTalkToFather();
    }

    @Override
    public String getMissionName() {
        return "Talk to Father";
    }

    public String getBagId() {
        return BagIds.ROMEO_AND_JULIET.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 30;
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(RomeoAndJuliet.QUEST_SETTING) > 30;
    }
}
