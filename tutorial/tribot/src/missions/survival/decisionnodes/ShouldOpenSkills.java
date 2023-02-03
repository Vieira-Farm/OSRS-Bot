package missions.survival.decisionnodes;

import org.tribot.api2007.Game;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.survival.processnodes.OpenSkills;

public class ShouldOpenSkills extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 50;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenSkills());
        setFalseNode(new ShouldChopTree());
    }

}
