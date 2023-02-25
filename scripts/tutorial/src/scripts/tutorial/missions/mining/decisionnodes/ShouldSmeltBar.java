package scripts.tutorial.missions.mining.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.mining.processnodes.SmeltBar;

public class ShouldSmeltBar extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 320;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new SmeltBar());
        setFalseNode(new ShouldSmithDagger());
    }

}
