package missions.survival.decisionnodes;

import org.tribot.api2007.Game;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import missions.survival.processnodes.CatchFish;

public class ShouldCatchFish extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 40 ||
                (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 100);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new CatchFish());
        setFalseNode(new ShouldOpenSkills());
    }

}
