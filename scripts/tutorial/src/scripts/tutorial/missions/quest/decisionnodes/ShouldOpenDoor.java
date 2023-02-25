package scripts.tutorial.missions.quest.decisionnodes;

import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.NavigateObject;
import scripts.tutorial.data.Constants;

public class ShouldOpenDoor extends ConstructorDecisionNode {

    private final RSTile DOOR_TILE = new RSTile(3086, 3126, 0);

    @Override
    public boolean isValid() {
        return !Constants.QUEST_GUIDE_AREA.contains(Player.getPosition()) &&
                Objects.find(7, Filters.Objects.nameEquals("Door")
                        .and(Filters.Objects.tileEquals(DOOR_TILE))).length > 0;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new NavigateObject(new RSTile(3086, 3126, 0)));
        setFalseNode(new ShouldWalkGuide());
    }

}
