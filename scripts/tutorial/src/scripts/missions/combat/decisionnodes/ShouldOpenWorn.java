package scripts.missions.combat.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.combat.processnodes.OpenWornInterface;

public class ShouldOpenWorn extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 400;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenWornInterface());
        setFalseNode(new ShouldEquipItem());
    }

}