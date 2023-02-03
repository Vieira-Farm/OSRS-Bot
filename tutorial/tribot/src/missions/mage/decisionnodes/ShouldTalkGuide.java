package missions.mage.decisionnodes;

import data.Constants;
import org.tribot.api2007.Game;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.reusable.nodes.TalkToGuide;

public class ShouldTalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 620 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 640 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 660 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 670;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Magic Instructor",
                new String[]{"Yes.", "No, I'm not planning to do that.", "I'm good, thanks."}));
        setFalseNode(new ShouldOpenMagic());
    }

}
