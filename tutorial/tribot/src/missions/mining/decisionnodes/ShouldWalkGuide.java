package missions.mining.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.mining.processnodes.WalkToGuide;

public class ShouldWalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 250 || Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 260 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 290) && !Constants.MINING_AREA.contains(Player.getPosition());
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToGuide());
        setFalseNode(new ShouldTalkGuide());
    }
}
