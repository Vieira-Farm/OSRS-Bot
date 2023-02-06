package scripts.missions.bank.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.bank.processnodes.WalkToBank;

public class ShouldWalkToBank extends ConstructorDecisionNode {
    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) < 525 &&
                !Constants.BANK_AREA.contains(Player.getPosition());
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToBank());
        setFalseNode(new ShouldOpenBank());
    }
}
