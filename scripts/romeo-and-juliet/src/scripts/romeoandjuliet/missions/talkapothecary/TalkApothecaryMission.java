package scripts.romeoandjuliet.missions.talkapothecary;

import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import scripts.data.structures.bag.BagIds;
import scripts.romeoandjuliet.missions.RomeoAndJuliet;
import scripts.romeoandjuliet.missions.talkapothecary.decisions.ShouldTalkToApothecary;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class TalkApothecaryMission implements TreeMission {
    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldTalkToApothecary();
    }

    @Override
    public String getMissionName() {
        return "Talk to Apothecary";
    }

    public String getBagId() {
        return BagIds.ROMEO_AND_JULIET.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 40 ||
                (Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 50 && Inventory.getCount("Cadava berries") > 0);
    }

    @Override
    public boolean isMissionCompleted() {
        return (Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 50 &&
                (Inventory.getCount("Cadava potion") > 0 || Inventory.getCount("Cadava berries") < 1));
    }
}
