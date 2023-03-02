package scripts.restlessghost.missions.graveyardskull.decisions;

import org.tribot.api2007.Player;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.WalkToLocation;

import static scripts.restlessghost.data.Constants.GRAVEYARD;

public class ShouldWalkGraveyard extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return !GRAVEYARD.contains(Player.getPosition());
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLocation(GRAVEYARD.getRandomTile(), "Walk Graveyard"));
        setFalseNode(new ShouldEquipAmulet());
    }

}
