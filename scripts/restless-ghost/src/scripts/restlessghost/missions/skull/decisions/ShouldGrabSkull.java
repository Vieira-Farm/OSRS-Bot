package scripts.restlessghost.missions.skull.decisions;

import org.tribot.api2007.types.RSTile;
import scripts.client.clientextensions.Inventory;
import scripts.restlessghost.missions.skull.processes.GrabSkull;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldGrabSkull extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return !Inventory.inventoryContains("Ghosts's skull");
    }

    @Override
    public void initializeNode() {
        setTrueNode(new GrabSkull());
        setFalseNode(new WalkToLocation(new RSTile(3112, 9559, 0), "Leave skull"));
    }

}
