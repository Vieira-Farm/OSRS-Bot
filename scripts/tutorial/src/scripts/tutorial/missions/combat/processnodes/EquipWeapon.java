package scripts.tutorial.missions.combat.processnodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSItem;
import scripts.client.clientextensions.Inventory;
import scripts.data.structures.ScriptVariables;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.tutorial.data.Constants;
import scripts.utils.ConditionUtilities;

public class EquipWeapon extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Equipping Weapon";
    }

    @Override
    public void successExecute() {
        boolean equipInReverse = ScriptVariables.getInstance().getRandomBoolean();
        String[] itemNames = getItemNames();

        for (int i = 0; i < itemNames.length; i++) {
            i = equipInReverse ? itemNames.length - (i+1) : i;
            RSItem targetItem = Inventory.findFirst(itemNames[i]);
            if (targetItem == null)
                return;

            Clicking.click(new String[]{"Wield", "Wear", "Equip"}, targetItem);
            Timing.waitCondition(
                    ConditionUtilities.itemEquipped(targetItem, targetItem.getEquipmentSlot()),
                    General.random(3000, 5000)
            );
        }
    }

    /**
     * Gets an array containing the names of the items we want to equip
     *
     * @return String array containing the names of the items to equip
     */
    public String[] getItemNames() {
        if (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 480 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 490) {
            return new String[]{"Shortbow", "Bronze arrow"};
        } else if (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 420) {
            return new String[]{"Bronze sword", "Wooden shield"};
        } else {
            return new String[]{"Bronze dagger"};
        }
    }
}