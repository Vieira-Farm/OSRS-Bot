package scripts.restlessghost.missions.urhney.decisions;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldWalkUrhney extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return NPCs.findNearest("Father Urhney").length < 1;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLocation(new RSTile(3147, 3175, 0), "Walking Urhney"));
        setFalseNode(new TalkToGuide("Father Urhney", "Talking Urhney",
                new String[]{"Father Aereck sent me to talk to you.", "He's got a ghost haunting his graveyard."}));
    }

}
