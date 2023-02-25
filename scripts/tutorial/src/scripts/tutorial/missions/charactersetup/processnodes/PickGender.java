package scripts.tutorial.missions.charactersetup.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.tutorial.data.Constants;
import scripts.tutorial.data.Variables;

public class PickGender extends SuccessProcessNode {

    private Variables variables;

    public PickGender(Variables variables) {
        this.variables = variables;
    }

    private final int FEMALE_ID = 139;

    @Override
    public String getStatus() {
        return "Pick Gender";
    }

    @Override
    public void successExecute() {
        Timing.waitCondition(ConditionUtilities.interfaceSubstantiated(Constants.STYLE_INTERFACE_MASTER), General.random(5000, 7000));
        boolean isMale = General.randomBoolean();
        if (isMale) {
            variables.setPickedGender(true);
            return;
        }
        RSInterfaceChild button = Interfaces.get(Constants.STYLE_INTERFACE_MASTER, FEMALE_ID);
        if (button == null) {
            return;
        }
        variables.setPickedGender(button.click("Female"));
    }
}
