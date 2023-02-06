package scripts.missions.mining.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.mining.processnodes.SmithDagger;

public class ShouldSmithDagger extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 340 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 350;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new SmithDagger());
        setFalseNode(new ShouldWalkGate());
    }

}
