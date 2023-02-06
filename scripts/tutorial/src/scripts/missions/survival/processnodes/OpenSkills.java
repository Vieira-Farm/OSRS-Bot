package scripts.missions.survival.processnodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripts.scripting.entityselector.finders.prefabs.InterfaceEntity;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;

public class OpenSkills extends SuccessProcessNode {

    private final int INTERFACE = General.isClientResizable() ? 164 : 548;
    private final int TAB = General.isClientResizable() ? 51 : 49;
    //Interface indices change with resizable mode. In addition, ids have to be used as the game tab is not
    //a standard game tab at this time

    @Override
    public String getStatus() {
        return "Opening skills";
    }

    @Override
    public void successExecute() {
        RSInterface interfaceChild = Interfaces.get(INTERFACE, TAB);
        if (interfaceChild == null) {
            return;
        }

        if (!Utilities.checkActions(interfaceChild, "Skills")) {
            interfaceChild = new InterfaceEntity().inMaster(INTERFACE).actionEquals("Skills").getFirstResult();
            if (interfaceChild == null) {
                return;
            }
        }

        if (Clicking.click(interfaceChild)) {
            Timing.waitCondition(ConditionUtilities.tabOpened(GameTab.TABS.STATS), General.random(1000, 1250));
        }
    }
}