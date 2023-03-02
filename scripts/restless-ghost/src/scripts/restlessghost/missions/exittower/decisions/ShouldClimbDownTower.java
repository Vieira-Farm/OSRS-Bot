package scripts.restlessghost.missions.exittower.decisions;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.restlessghost.missions.exittower.processes.ExitTower;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.NavigateChangePlaneObject;

public class ShouldClimbDownTower extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        RSTile playerPosition = Player.getPosition();
        return playerPosition.getPlane() > 0 && playerPosition.getX() > 3100 && playerPosition.getY() < 3165;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new NavigateChangePlaneObject("Staircase", "Climb-down"));
        setFalseNode(new ExitTower());
    }

}
