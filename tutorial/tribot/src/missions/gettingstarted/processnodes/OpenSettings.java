package missions.gettingstarted.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripting.entityselector.finders.prefabs.InterfaceEntity;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;
import utils.Utilities;

public class OpenSettings extends SuccessProcessNode {

    private final int INTERFACE = General.isClientResizable() ? 164 : 548, TAB = General.isClientResizable() ? 38 : 35;

    @Override
    public String getStatus() {
        return "Opening Settings";
    }

    @Override
    public void successExecute() {
        RSInterface interfaceChild = Interfaces.get(INTERFACE, TAB);
        if (interfaceChild == null) {
            return;
        }
        if (!Utilities.checkActions(interfaceChild, "Options")) {
            interfaceChild = new InterfaceEntity().inMaster(INTERFACE).actionEquals("Options").getFirstResult();
            if (interfaceChild == null) {
                return;
            }
        }
        if (interfaceChild.click()) {
            Timing.waitCondition(ConditionUtilities.tabOpened(GameTab.TABS.OPTIONS), General.random(1000, 1250));
        }
    }
}