package missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.reusable.nodes.OpenTab;
import data.Constants;

public class ShouldOpenEquipment extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 390 ||
                (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 400 && GameTab.getOpen() != GameTab.TABS.EQUIPMENT);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenTab(GameTab.TABS.EQUIPMENT));
        setFalseNode(new ShouldOpenWorn());
    }

}
