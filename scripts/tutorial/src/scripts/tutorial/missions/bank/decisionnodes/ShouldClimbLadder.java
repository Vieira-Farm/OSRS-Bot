package scripts.tutorial.missions.bank.decisionnodes;

import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.bank.processnodes.ClimbLadder;
import org.tribot.api2007.Player;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

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
