package missions.bank.decisionnodes;

import data.Constants;
import missions.bank.processnodes.ClimbLadder;
import org.tribot.api2007.Player;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldClimbLadder extends ConstructorDecisionNode {
    @Override
    public boolean isValid() {
        return Constants.UNDERGROUND_AREA.contains(Player.getPosition());
    }

    @Override
    public void initializeNode() {
        setTrueNode(new ClimbLadder());
        setFalseNode(new ShouldWalkToBank());
    }
}
