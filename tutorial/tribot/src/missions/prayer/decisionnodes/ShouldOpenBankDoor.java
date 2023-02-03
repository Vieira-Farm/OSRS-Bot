package missions.prayer.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSTile;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.reusable.nodes.NavigateObject;
import data.Constants;

public class ShouldOpenBankDoor extends ConstructorDecisionNode {

    private final RSTile DOOR_TILE = new RSTile(3130, 3124, 0);

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) < 560 &&
                Player.getPosition().getX() < 3130 &&
                Objects.find(
                        5,
                        Filters.Objects.nameEquals("Door")
                                .and(Filters.Objects.tileEquals(DOOR_TILE))
                ).length > 0;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new NavigateObject(new RSTile(3130, 3124, 0)));
        setFalseNode(new ShouldWalkGuide());
    }
}
