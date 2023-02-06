package scripts.missions.survival.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.survival.processnodes.CookFish;
import scripts.missions.survival.processnodes.MakeFire;

public class ShouldMakeFire extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        boolean hasLogs = Inventory.getCount("Logs") > 0;
        if (!hasLogs) {
            return false;
        } else if (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 80) {
            return true;
        } else if (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 100 &&
                Objects.findNearest(8, "Fire").length < 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initializeNode() {
        setTrueNode(new MakeFire());
        setFalseNode(new CookFish());
    }

}
