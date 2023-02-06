package scripts.missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.combat.processnodes.WalkToCombatInstructor;

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
