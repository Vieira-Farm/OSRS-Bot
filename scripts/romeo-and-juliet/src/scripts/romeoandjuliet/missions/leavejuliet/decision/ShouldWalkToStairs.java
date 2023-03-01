package scripts.romeoandjuliet.missions.leavejuliet.decision;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.romeoandjuliet.missions.leavejuliet.process.WalkToStairs;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.NavigateChangePlaneObject;

public class ShouldWalkToStairs extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Player.getPosition().getPlane() == 1 && Player.getPosition().distanceTo(new RSTile(3156, 3435, 1)) > 3;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToStairs());
        setFalseNode(new NavigateChangePlaneObject("Staircase", new RSTile(3156, 3435, 1),
                "Climb-down", "Climbing down stairs"));
    }
}
