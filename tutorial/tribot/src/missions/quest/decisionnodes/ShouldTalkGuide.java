package missions.quest.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.reusable.nodes.OpenTab;
import scripting.reusable.nodes.TalkToGuide;
import data.Constants;

public class ShouldTalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 220 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 240;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Quest Guide"));
        setFalseNode(new OpenTab("Opening quests", GameTab.TABS.QUESTS));
    }

}
