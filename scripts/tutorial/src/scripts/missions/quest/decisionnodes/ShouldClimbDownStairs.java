package scripts.missions.quest.decisionnodes;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.NavigateChangePlaneObject;

public class ShouldClimbDownStairs extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        RSTile playerPositon = Player.getPosition();
        return playerPositon.getPlane() == 1 && playerPositon.getX() < 3089 && playerPositon.getY() > 3118;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new NavigateChangePlaneObject(
                "Staircase",
                new RSTile(3083, 3124, 1),
                "Climb-down",
                "Climb down stairs")
        );
        setFalseNode(new ShouldOpenDoor());
    }

}
