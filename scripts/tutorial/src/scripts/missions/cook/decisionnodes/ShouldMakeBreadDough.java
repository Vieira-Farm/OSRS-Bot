package scripts.missions.cook.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.cook.processnodes.CookBread;
import scripts.missions.cook.processnodes.MakeBreadDough;

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