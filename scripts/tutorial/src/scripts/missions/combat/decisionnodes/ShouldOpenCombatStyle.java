package scripts.missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.OpenTab;
import scripts.data.Constants;

public class ShouldOpenCombatStyle extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 430;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenTab(GameTab.TABS.COMBAT));
        setFalseNode(new ShouldEnterPit());
    }

}
