package scripts.missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.combat.processnodes.CloseWornInterface;

public class ShouldCloseWorn extends ConstructorDecisionNode {

    private final int MASTER_WORN_INTERFACE = 84;

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 410 &&
                Interfaces.isInterfaceSubstantiated(MASTER_WORN_INTERFACE);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new CloseWornInterface());
        setFalseNode(new ShouldOpenCombatStyle());
    }

}