package scripts.restlessghost.missions.entertower.decisions;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldWalkToTower extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Player.getPosition().getY() > 3168;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLocation(new RSTile(3109, 3168, 0)));
        setFalseNode(new ShouldClimbDownTower());
    }

}
