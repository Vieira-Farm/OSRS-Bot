package missions.bank.decisionnodes;

import data.Constants;
import missions.bank.processnodes.ClosePoll;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

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
