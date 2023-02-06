package scripts.missions.cook.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ModularDecisionNode;
import scripts.data.Constants;
import scripts.missions.cook.processnodes.OpenGate;

public class ShouldOpenGate extends ModularDecisionNode {

    //If our x position is greater than 3090, we're still inside the survival area.
    @Override
    public boolean isValid() {
        return Player.getPosition().getX() >= 3090;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenGate());
        setFalseNode(new ShouldWalkGuide());
    }

    @Override
    public boolean shouldRemove() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 170;
    }


}
