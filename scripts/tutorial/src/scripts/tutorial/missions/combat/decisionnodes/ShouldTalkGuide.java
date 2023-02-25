package scripts.tutorial.missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.tutorial.data.Constants;

public class ShouldTalkGuide extends ConstructorDecisionNode {

    private final int MASTER_WORN_INTERFACE = 84;

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 370 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 410 &&
                        !Interfaces.isInterfaceSubstantiated(MASTER_WORN_INTERFACE) ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 470;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Combat Instructor"));
        setFalseNode(new ShouldOpenEquipment());
    }
}
