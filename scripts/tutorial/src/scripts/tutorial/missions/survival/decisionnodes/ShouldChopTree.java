package scripts.tutorial.missions.survival.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.survival.processnodes.ChopTree;

public class ShouldChopTree extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        if (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 70) {
            return true;
        }
        boolean haveLogs = Inventory.getCount("Logs") >= 1;
        if (haveLogs) {
            return false;
        } else if (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 80) {
            return true;
        } else if ((Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 100) &&
                Objects.findNearest(8, "Fire").length < 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initializeNode() {
        setTrueNode(new ChopTree());
        setFalseNode(new ShouldMakeFire());
    }

}