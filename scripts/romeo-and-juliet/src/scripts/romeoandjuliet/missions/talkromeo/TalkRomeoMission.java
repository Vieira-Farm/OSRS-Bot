package scripts.romeoandjuliet.missions.talkromeo;

import scripts.client.clientextensions.Game;
import scripts.data.structures.bag.BagIds;
import scripts.romeoandjuliet.missions.RomeoAndJuliet;
import scripts.romeoandjuliet.missions.talkromeo.decisions.ShouldWaitForChat;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class TalkRomeoMission implements TreeMission {
    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWaitForChat();
    }

    @Override
    public String getMissionName() {
        return "Talk to Romeo";
    }

    public String getBagId() {
        return BagIds.ROMEO_AND_JULIET.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 20 ||
                Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 60;
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(RomeoAndJuliet.QUEST_SETTING) > 20 &&
                Game.getSetting(RomeoAndJuliet.QUEST_SETTING) != 60;
    }
}
