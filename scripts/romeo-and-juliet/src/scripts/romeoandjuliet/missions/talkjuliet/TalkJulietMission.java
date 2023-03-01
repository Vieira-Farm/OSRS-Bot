package scripts.romeoandjuliet.missions.talkjuliet;

import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import scripts.data.structures.bag.BagIds;
import scripts.romeoandjuliet.missions.RomeoAndJuliet;
import scripts.romeoandjuliet.missions.talkjuliet.decision.ShouldWalkToDownstairs;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class TalkJulietMission implements TreeMission {
    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkToDownstairs();
    }

    @Override
    public String getMissionName() {
        return "Talking to Juliet";
    }

    public String getBagId() {
        return BagIds.ROMEO_AND_JULIET.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(RomeoAndJuliet.QUEST_SETTING) < 20 ||
                (Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 50 &&
                        (Inventory.getCount("Cadava potion") > 0 ||
                                (Game.getSetting(1021) > 2048 && NPCs.find("Juliet").length > 0)));
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(RomeoAndJuliet.QUEST_SETTING) >= 20 &&
                Game.getSetting(RomeoAndJuliet.QUEST_SETTING) != 50;
    }
}
