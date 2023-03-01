package scripts.romeoandjuliet.missions.talkfather.decisions;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldTalkToFather extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return NPCs.findNearest("Father Lawrence").length > 0;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Father Lawrence", "Talking to Father Lawrence"));
        setFalseNode(new WalkToLocation(new RSTile(3254, 3484, 0), "Walking to Father Lawrence"));
    }

}
