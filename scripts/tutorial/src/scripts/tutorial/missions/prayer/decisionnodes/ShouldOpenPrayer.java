package scripts.tutorial.missions.prayer.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.OpenTab;
import scripts.tutorial.data.Constants;

public class ShouldOpenPrayer extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 560;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenTab(GameTab.TABS.PRAYERS));
        setFalseNode(new OpenTab(GameTab.TABS.FRIENDS));
    }

}
