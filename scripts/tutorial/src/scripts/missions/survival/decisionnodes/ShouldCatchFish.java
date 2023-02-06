package scripts.missions.survival.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.survival.processnodes.CatchFish;

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
