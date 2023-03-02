package scripts.restlessghost.missions.exittower.decisions;

import org.tribot.api2007.Player;
import scripts.restlessghost.missions.exittower.processes.ClimbLadder;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldClimbLadder extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Player.getPosition().getY() > 9550; //We're still underground
    }

    @Override
    public void initializeNode() {
        setTrueNode(new ClimbLadder());
        setFalseNode(new ShouldClimbDownTower());
    }

}
