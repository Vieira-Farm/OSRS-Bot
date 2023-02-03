package missions.survival.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSNPC;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.survival.processnodes.WalkToGuide;

public class ShouldWalkGuide extends ConstructorDecisionNode {

    //Documentation

    @Override
    public boolean isValid() {
        RSNPC[] npcs = NPCs.findNearest("Survival Expert");
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 20 &&
                (npcs.length < 1 || npcs[0].getPosition().distanceTo(Player.getPosition()) > 5);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WalkToGuide());
        setFalseNode(new ShouldTalkGuide());
    }
}
