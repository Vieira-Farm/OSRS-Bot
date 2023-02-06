package scripts.missions.mining.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.NavigateObject;
import scripts.data.Constants;
import scripts.missions.mining.processnodes.WalkGate;

public class ShouldWalkGate extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 360 &&
                Objects.find(5, "Gate").length < 1;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkGate());
        setFalseNode(new NavigateObject(
                new RSTile(3094, 9503, 0),
                "Opening gate",
                "Gate",
                "Open")
        );
    }

}
