package scripts.tutorial.missions.mining.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.mining.processnodes.MineRock;

public class ShouldMineRock extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 300 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 310 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 311;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new MineRock());
        setFalseNode(new ShouldSmeltBar());
    }

}
