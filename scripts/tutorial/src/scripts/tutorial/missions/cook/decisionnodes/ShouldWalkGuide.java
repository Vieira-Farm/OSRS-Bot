package scripts.tutorial.missions.cook.decisionnodes;

import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.cook.processnodes.WalkToDoor;

public class ShouldWalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return !Constants.COOK_AREA.contains(Player.getPosition()) && Objects.findNearest(7, "Door").length < 1;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToDoor());
        setFalseNode(new ShouldOpenDoor());
    }

}
