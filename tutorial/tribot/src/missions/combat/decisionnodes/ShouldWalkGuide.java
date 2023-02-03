package missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.combat.processnodes.WalkToCombatInstructor;

public class ShouldWalkGuide extends ConstructorDecisionNode {
    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 370 &&
                NPCs.find("Combat Instructor").length < 1;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToCombatInstructor());
        setFalseNode(new ShouldTalkGuide());
    }

}
