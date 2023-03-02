package scripts.restlessghost.missions.entertower.decisions;

import org.tribot.api2007.Player;
import scripts.restlessghost.missions.entertower.processes.EnterTower;
import scripts.restlessghost.missions.entertower.processes.WalkToSedridor;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldEnterTower extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Player.getPosition().getY() < 9550;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new EnterTower());
        setFalseNode(new WalkToSedridor());
    }

}
