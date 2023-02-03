package missions.cook.decisionnodes;

import org.tribot.api2007.Game;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.cook.processnodes.CookBread;
import missions.cook.processnodes.MakeBreadDough;

public class ShouldMakeBreadDough extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 150;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new MakeBreadDough());
        setFalseNode(new CookBread());
    }

}