package scripts.tutorial.missions.combat.decisionnodes;

import scripts.tutorial.data.Constants;
import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.tutorial.missions.combat.processnodes.MeleeRat;
import scripts.tutorial.missions.combat.processnodes.RangeRat;

public class ShouldMeleeRat extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 450 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 460;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new MeleeRat());
        setFalseNode(new RangeRat());
    }

}
