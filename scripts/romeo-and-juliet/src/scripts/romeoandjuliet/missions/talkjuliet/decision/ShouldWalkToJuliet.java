package scripts.romeoandjuliet.missions.talkjuliet.decision;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.scripting.reusable.nodes.WalkToLocation;

public class ShouldWalkToJuliet extends ConstructorDecisionNode {
    @Override
    public boolean isValid() {
        return NPCs.findNearest("Juliet").length < 1 || (Player.getPosition().getY() < 4000 &&
                Player.getPosition().getY() > 3426);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToLocation(new RSTile(3157, 3426, 1), "Walking to Juliet"));
        setFalseNode(new TalkToGuide("Juliet", "Talking to Juliet",
                new String[]{"Yes I've met him.", "Certainly, I'll do so straight away."}));
    }
}
