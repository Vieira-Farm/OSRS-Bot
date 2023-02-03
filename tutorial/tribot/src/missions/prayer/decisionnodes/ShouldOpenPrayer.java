package missions.prayer.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.reusable.nodes.OpenTab;
import data.Constants;

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
