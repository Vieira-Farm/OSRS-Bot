package scripts.tutorial.missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.NavigateObject;
import scripts.tutorial.data.Constants;

public class ShouldOpenGate extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 370 && Player.getPosition().getX() <= 3094;
        //Player x position is checked as if the x is greater than 3094, we're past the gate.
    }

    @Override
    public void initializeNode() {
        setTrueNode(new NavigateObject(new RSTile(3094, 9502, 0), "Opening gate", "Gate", "Open"));
        setFalseNode(new ShouldWalkGuide());
    }

}
