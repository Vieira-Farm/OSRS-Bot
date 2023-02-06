package scripts.missions.mining.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.mining.processnodes.ClimbUnderground;

public class ShouldClimbUnderground extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 250 &&
                Constants.QUEST_GUIDE_AREA.contains(Player.getPosition());
    }

    @Override
    public void initializeNode() {
        setTrueNode(new ClimbUnderground());
        setFalseNode(new ShouldWalkGuide());
    }

}
