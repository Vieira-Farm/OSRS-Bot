package scripts.romeoandjuliet.missions.pickberries.decisions;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldWalkVarrockMine extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Player.getPosition().getY() > 3381;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLocation(new RSTile(3291,3381,0), "Walking to Varrock Mine"));
        setFalseNode(new ShouldWalkCadavaBush());
    }

}
