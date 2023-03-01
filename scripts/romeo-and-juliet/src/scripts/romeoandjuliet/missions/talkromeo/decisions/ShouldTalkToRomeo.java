package scripts.romeoandjuliet.missions.talkromeo.decisions;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldTalkToRomeo extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return NPCs.findNearest("Romeo").length > 0;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Romeo", "Talking to Romeo",
                new String[]{"Ok, thanks."}));
        setFalseNode(new WalkToLocation(new RSTile(3212, 3428, 0), "Walking to Romeo"));
    }

}
