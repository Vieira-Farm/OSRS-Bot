package missions.quest.decisionnodes;

import org.tribot.api2007.Player;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.quest.processnodes.WalkToGuide;

public class ShouldWalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return !Constants.QUEST_GUIDE_AREA.contains(Player.getPosition());
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToGuide());
        setFalseNode(new ShouldTalkGuide());
    }

}
