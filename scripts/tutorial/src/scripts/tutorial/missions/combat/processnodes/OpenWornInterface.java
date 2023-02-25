package scripts.tutorial.missions.combat.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class OpenWornInterface extends SuccessProcessNode {

    private final int EQUIP_INTERFACE = 387;
    private final int MASTER_WORN_INTERFACE = 84;
    private final int EQUIP_BUTTON = 1;

    @Override
    public String getStatus() {
        return "Opening Worn";
    }

    @Override
    public void successExecute() {
        RSInterfaceChild equip = Interfaces.get(EQUIP_INTERFACE, EQUIP_BUTTON);
        if (equip != null && equip.click()) {
            Timing.waitCondition(ConditionUtilities.interfaceSubstantiated(MASTER_WORN_INTERFACE), General.random(3000, 5000));
        }
    }
}