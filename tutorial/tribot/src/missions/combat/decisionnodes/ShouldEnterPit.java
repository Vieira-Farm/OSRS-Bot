package missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.combat.processnodes.EnterPit;

public class ShouldEnterPit extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return !Constants.COMBAT_PIT_AREA.contains(Player.getPosition()) &&
                (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 440 &&
                        Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) <= 460);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new EnterPit());
        setFalseNode(new ShouldMeleeRat());
    }

}
