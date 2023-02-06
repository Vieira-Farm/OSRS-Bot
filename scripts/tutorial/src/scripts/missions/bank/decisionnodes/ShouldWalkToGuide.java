package scripts.missions.bank.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.bank.processnodes.WalkToAdvisor;

public class ShouldWalkToGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return !Constants.BANK_INSTRUCTOR_ROOM.contains(Player.getPosition()) &&
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 525;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToAdvisor());
        setFalseNode(new ShouldTalkGuide());
    }

}
