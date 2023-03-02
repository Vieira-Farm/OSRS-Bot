package scripts.restlessghost.missions.exittower.decisions;

import org.tribot.api2007.Player;
import scripts.restlessghost.missions.exittower.processes.WalkToLadder;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldWalkLadder extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Player.getPosition().getY() < 9575 && Player.getPosition().getY() > 9550;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLadder());
        setFalseNode(new ShouldClimbLadder());
    }

}
