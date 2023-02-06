package scripts.missions.prayer.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.data.Constants;

public class ShouldTalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 550 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 570 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 600;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Brother Brace"));
        setFalseNode(new ShouldOpenPrayer());
    }

}
