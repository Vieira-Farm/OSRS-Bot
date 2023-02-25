package scripts.tutorial.missions.cook.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.cook.processnodes.CookBread;
import scripts.tutorial.missions.cook.processnodes.MakeBreadDough;

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