package scripts.romeoandjuliet.missions.pickberries;

import org.tribot.api2007.Game;
import scripts.client.clientextensions.Inventory;
import scripts.data.structures.bag.BagIds;
import scripts.romeoandjuliet.missions.RomeoAndJuliet;
import scripts.romeoandjuliet.missions.pickberries.decisions.ShouldWalkVarrockSquare;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class PickBerriesMission implements TreeMission {
    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldWalkVarrockSquare();
    }

    @Override
    public String getMissionName() {
        return "Pick berries";
    }

    public String getBagId() {
        return BagIds.ROMEO_AND_JULIET.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(RomeoAndJuliet.QUEST_SETTING) == 50 &&
                Inventory.getCount("Cadava berries", "Cadava potion") < 1 &&
                Game.getSetting(1021) <= 2048;
    }

    @Override
    public boolean isMissionCompleted() {
        return Inventory.getCount("Cadava berries") > 0;
    }
}
