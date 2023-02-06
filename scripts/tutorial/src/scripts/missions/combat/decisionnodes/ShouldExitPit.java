package scripts.missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.combat.processnodes.ExitPit;

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
