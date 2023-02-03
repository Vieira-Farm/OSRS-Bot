package missions.mining.decisionnodes;

import org.tribot.api2007.Game;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.reusable.nodes.TalkToGuide;
import data.Constants;

public class ShouldTalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 260 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 290 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 291 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 330;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Mining Instructor"));
        setFalseNode(new ShouldMineRock());
    }

}
