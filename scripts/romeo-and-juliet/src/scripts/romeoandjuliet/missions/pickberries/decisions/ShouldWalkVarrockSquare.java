package scripts.romeoandjuliet.missions.pickberries.decisions;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldWalkVarrockSquare extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Player.getPosition().getX() < 3220;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLocation(new RSTile(3229, 3428, 0), "Walking to Varrock Square"));
        setFalseNode(new ShouldWalkVarrockMine());
    }

}
