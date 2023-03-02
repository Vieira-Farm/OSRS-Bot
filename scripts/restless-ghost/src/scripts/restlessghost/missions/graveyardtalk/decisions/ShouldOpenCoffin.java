package scripts.restlessghost.missions.graveyardtalk.decisions;

import org.tribot.api2007.NPCs;
import scripts.restlessghost.missions.commonnodes.InteractCoffin;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;

public class ShouldOpenCoffin extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return NPCs.findNearest("Restless Ghost").length < 1;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new InteractCoffin());
        setFalseNode(new TalkToGuide("Restless Ghost", "Talking to Ghost",
                new String[]{"Yep, now tell me what the problem is."}));
    }

}
