package scripts.missions.gettingstarted.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripts.scripting.entityselector.finders.prefabs.InterfaceEntity;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;

public class OpenSettings extends SuccessProcessNode {

    private final int INTERFACE = General.isClientResizable() ? 164 : 548, TAB = General.isClientResizable() ? 40 : 35;

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
        if (!Utilities.checkActions(interfaceChild, "Settings")) {
            interfaceChild = new InterfaceEntity().inMaster(INTERFACE).actionEquals("Settings").getFirstResult();
            if (interfaceChild == null) {
                return;
            }
        }
        if (interfaceChild.click()) {
            Timing.waitCondition(ConditionUtilities.tabOpened(GameTab.TABS.OPTIONS), General.random(1000, 1250));
        }
    }
}