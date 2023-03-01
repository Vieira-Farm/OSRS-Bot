package scripts.romeoandjuliet.missions.talkjuliet.decision;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.NavigateChangePlaneObject;

public class ShouldClimbUpStairs extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Player.getPosition().getPlane() == 0;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new NavigateChangePlaneObject("Staircase", new RSTile(3156, 3435, 0),
                "Climb-up", "Climbing up stairs"));
        setFalseNode(new ShouldWalkToJuliet());
    }

}
