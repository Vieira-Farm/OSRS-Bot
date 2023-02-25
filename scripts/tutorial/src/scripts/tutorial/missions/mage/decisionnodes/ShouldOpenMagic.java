package scripts.tutorial.missions.mage.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.OpenTab;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.mage.processNodes.CastWindStrike;

public class ShouldOpenMagic extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 630;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenTab(GameTab.TABS.MAGIC));
        setFalseNode(new CastWindStrike());
    }

}
