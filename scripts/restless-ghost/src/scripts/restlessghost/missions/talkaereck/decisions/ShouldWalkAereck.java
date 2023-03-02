package scripts.restlessghost.missions.talkaereck.decisions;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldWalkAereck extends ConstructorDecisionNode {

    private final RSArea LUMBRIDGE_CHURCH = new RSArea(new RSTile [] {
            new RSTile(3237, 3209, 0), new RSTile(3237, 3212, 0),
            new RSTile(3239, 3212, 0), new RSTile(3239, 3216, 0),
            new RSTile(3248, 3216, 0), new RSTile(3248, 3204, 0),
            new RSTile(3239, 3204, 0), new RSTile(3239, 3209, 0) });


    @Override
    public boolean isValid() {
        return !LUMBRIDGE_CHURCH.contains(Player.getPosition());
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLocation(new RSTile(3244, 3206, 0), "Walking to Aereck"));
        setFalseNode(new TalkToGuide("Father Aereck", "Talking to Aereck",
                new String[]{"I'm looking for a quest!", "Ok, let me help then.",}));
    }

}
