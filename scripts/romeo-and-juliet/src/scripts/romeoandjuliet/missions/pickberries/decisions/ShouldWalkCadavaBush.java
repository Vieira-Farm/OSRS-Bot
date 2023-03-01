package scripts.romeoandjuliet.missions.pickberries.decisions;

import org.tribot.api2007.types.RSTile;
import scripts.client.clientextensions.Objects;
import scripts.romeoandjuliet.missions.pickberries.processes.PickCadavaBerries;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldWalkCadavaBush extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Objects.find(5, "Cadava bush").length < 1;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLocation(new RSTile(3269, 3370, 0), "Walking to Cadava Bush"));
        setFalseNode(new PickCadavaBerries());
    }

}
