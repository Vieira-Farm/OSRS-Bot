package scripts.missions.quest.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.OpenTab;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.data.Constants;

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
