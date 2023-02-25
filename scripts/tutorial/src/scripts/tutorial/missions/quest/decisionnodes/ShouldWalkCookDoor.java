package scripts.tutorial.missions.quest.decisionnodes;

import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.quest.processnodes.WalkCookDoor;

public class ShouldWalkCookDoor extends ConstructorDecisionNode {

    public static final RSTile COOK_DOOR_TILE = new RSTile(3072, 3090, 0);

    @Override
    public boolean isValid() {
        return Constants.COOK_AREA.contains(Player.getPosition()) &&
                Objects.find(7, Filters.Objects.nameEquals("Door").and(
                        Filters.Objects.tileEquals(COOK_DOOR_TILE))).length < 1;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkCookDoor());
        setFalseNode(new ShouldLeaveCook());
    }
}
