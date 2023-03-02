package scripts.restlessghost.missions.commonnodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class EquipGhostspeak extends SuccessProcessNode {
    @Override
    public String getStatus() {
        return "Equipping item";
    }

    @Override
    public void successExecute() {
        RSItem[] amulets = Inventory.find("Ghostspeak amulet");
        if (amulets.length < 1) {
            return;
        }
        if (Clicking.click("Wear", amulets[0])) {
            Timing.waitCondition(ConditionUtilities.itemEquipped(amulets[0], Equipment.SLOTS.AMULET), General.random(3000, 5000));
        }
    }
}
