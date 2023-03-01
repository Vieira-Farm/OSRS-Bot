package scripts.romeoandjuliet.missions.talkapothecary.decisions;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldTalkToApothecary extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return NPCs.findNearest("Apothecary").length > 0;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Apothecary", "Talking to Apothecary",
                new String[]{"Talk about something else.", "Talk about Romeo & Juliet.", "Ok, thanks."}));
        setFalseNode(new WalkToLocation(new RSTile(3196, 3404, 0), "Walking to Apothecary"));
    }

}
