package scripts.tutorial.missions.quest.decisionnodes;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.NavigateObject;
import scripts.tutorial.data.Constants;

public class ShouldLeaveCook extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Constants.COOK_AREA.contains(Player.getPosition());
    }

    @Override
    public void initializeNode() {
        setTrueNode(new NavigateObject(
                new RSTile(3072, 3090, 0))
        );
        setFalseNode(new ShouldClimbDownStairs());
    }

}
