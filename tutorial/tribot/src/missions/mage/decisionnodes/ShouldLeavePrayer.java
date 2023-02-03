package missions.mage.decisionnodes;

import org.tribot.api2007.Player;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.mage.processNodes.LeavePrayer;

public class ShouldLeavePrayer extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Constants.PRAYER_AREA.contains(Player.getPosition()) ||
                Player.getPosition().getX() == 3122 && Player.getPosition().getY() > 3097;

        //TODO: Figure out what these x and y values represent.
    }

    @Override
    public void initializeNode() {
        setTrueNode(new LeavePrayer());
        setFalseNode(new ShouldWalkGuide());
    }
}
