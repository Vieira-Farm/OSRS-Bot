package scripts.tutorial.missions.survival.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.survival.processnodes.OpenInventory;

public class ShouldOpenInventory extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 30;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenInventory());
        setFalseNode(new ShouldCatchFish());
    }

}
