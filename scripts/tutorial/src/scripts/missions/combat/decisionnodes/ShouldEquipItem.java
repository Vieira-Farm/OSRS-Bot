package scripts.missions.combat.decisionnodes;

import scripts.data.Constants;
import scripts.missions.combat.processnodes.EquipWeapon;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldEquipItem extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 405 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 420 ||
                (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 480 &&
                        (Equipment.getCount("Bronze arrow") < 1 || Equipment.getCount("Shortbow") < 1));
    }

    @Override
    public void initializeNode() {
        setTrueNode(new EquipWeapon());
        setFalseNode(new ShouldCloseWorn());
    }

}
