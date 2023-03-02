package scripts.restlessghost.missions.graveyardskull.decisions;


import scripts.client.clientextensions.Inventory;
import scripts.restlessghost.missions.commonnodes.EquipGhostspeak;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldEquipAmulet extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Inventory.inventoryContains("Ghostspeak amulet");
    }

    @Override
    public void initializeNode() {
        setTrueNode(new EquipGhostspeak());
        setFalseNode(new ShouldInteractCoffin());
    }

}
