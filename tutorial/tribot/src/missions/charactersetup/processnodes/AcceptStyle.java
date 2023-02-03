package missions.charactersetup.processnodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;
import data.Constants;

public class AcceptStyle extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Accepting Character Style";
    }

    @Override
    public void successExecute() {
        RSInterfaceChild acceptButton = Interfaces.get(Constants.STYLE_INTERFACE_MASTER, Constants.STYLE_ACCEPT_BUTTON);
        if (acceptButton != null && Clicking.click(acceptButton)) {
            Timing.waitCondition(ConditionUtilities.settingChanged(22, 0), General.random(5000, 7000));
        }
    }
}
