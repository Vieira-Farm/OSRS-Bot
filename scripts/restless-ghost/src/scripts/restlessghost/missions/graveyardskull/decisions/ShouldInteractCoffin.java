package scripts.restlessghost.missions.graveyardskull.decisions;

import org.tribot.api2007.Player;
import scripts.client.clientextensions.Filters;
import scripts.client.clientextensions.Objects;
import scripts.restlessghost.missions.commonnodes.InteractCoffin;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

import static scripts.restlessghost.data.Constants.GRAVEYARD;

public class ShouldInteractCoffin extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return GRAVEYARD.contains(Player.getPosition()) &&
                Objects.find(5, Filters.Objects.nameEquals("Coffin")
                .and(Filters.Objects.hasAction("Open"))).length > 0;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new InteractCoffin());
        setFalseNode(new ShouldCloseQuestCompleted());
    }

}
