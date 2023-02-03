package missions.combat.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;

public class CloseWornInterface extends SuccessProcessNode {

    private final int MASTER_WORN_INTERFACE = 84;
    private final int WORN_INTERFACE_CLOSE = 4;

    @Override
    public String getStatus() {
        return "Closing Worn Interface";
    }

    @Override
    public void successExecute() {
        RSInterface close = Interfaces.get(MASTER_WORN_INTERFACE, WORN_INTERFACE_CLOSE);
        if (close != null && close.click()) {
            Timing.waitCondition(ConditionUtilities.interfaceNotSubstantiated(MASTER_WORN_INTERFACE), General.random(1000, 2000));
        }
    }
}