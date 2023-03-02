package scripts.restlessghost.missions.skull.decisions;


import scripts.client.clientextensions.Objects;
import scripts.restlessghost.missions.skull.processes.WalkSkull;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldWalkToSkull extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Objects.find(7, "Altar").length < 1;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkSkull());
        setFalseNode(new ShouldGrabSkull());
    }

}
