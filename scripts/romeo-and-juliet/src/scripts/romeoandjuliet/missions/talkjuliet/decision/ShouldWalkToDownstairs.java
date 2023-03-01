package scripts.romeoandjuliet.missions.talkjuliet.decision;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldWalkToDownstairs extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Player.getPosition().getPlane() == 0 &&
                Player.getPosition().distanceTo(new RSTile(3156, 3435, 0)) > 5;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLocation(new RSTile(3159, 3435, 0), "Walk to Juliet"));
        setFalseNode(new ShouldClimbUpStairs());
    }

}
