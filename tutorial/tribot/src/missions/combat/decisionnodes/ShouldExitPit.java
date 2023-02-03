package missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.combat.processnodes.ExitPit;

public class ShouldExitPit extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) < 440 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) > 460) &&
                Constants.COMBAT_PIT_AREA.contains(Player.getPosition());
    }

    @Override
    public void initializeNode() {
        setTrueNode(new ExitPit());
        setFalseNode(new ShouldOpenGate());
    }
}
