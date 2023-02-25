package scripts.tutorial.missions.bank.decisionnodes;

import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.bank.processnodes.ClosePoll;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldClosePoll extends ConstructorDecisionNode {

    private final int POLL_BOOTH_MASTER_ID = 310;

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 525 &&
                Interfaces.isInterfaceSubstantiated(POLL_BOOTH_MASTER_ID);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new ClosePoll());
        setFalseNode(new ShouldWalkToGuide());
    }

}
