package scripts.missions.bank.decisionnodes;

import scripts.data.Constants;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.OpenTab;
import scripts.scripting.reusable.nodes.TalkToGuide;

public class ShouldTalkGuide extends ConstructorDecisionNode {

    //531 Open account settings

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 530 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) > 531;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Account Guide"));
        setFalseNode(new OpenTab(GameTab.TABS.ACCOUNT));
    }

}
