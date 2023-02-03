package missions.mage.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.mage.processNodes.WalkToGuide;

public class ShouldWalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 610 &&
                NPCs.find("Magic Instructor").length < 1;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToGuide());
        setFalseNode(new ShouldTalkGuide());
    }

}
