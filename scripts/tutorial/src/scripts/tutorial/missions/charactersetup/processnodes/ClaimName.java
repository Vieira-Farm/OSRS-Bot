package scripts.tutorial.missions.charactersetup.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.tutorial.data.Constants;

public class ClaimName extends SuccessProcessNode {

    private final int USERNAME_SELECTION_MASTER_RESIZABLE = 558, USERNAME_SELECTION_CHILD_RESIZABLE = 18;

    @Override
    public String getStatus() {
        return "Claiming name";
    }

    @Override
    public void successExecute() {
        RSInterfaceChild acceptUsername = Interfaces.get(USERNAME_SELECTION_MASTER_RESIZABLE,
                USERNAME_SELECTION_CHILD_RESIZABLE);
        if (acceptUsername != null && acceptUsername.click("")) {
            Timing.waitCondition(ConditionUtilities.varbitEquals(Constants.NAME_SET_VARBIT, 1),
                    General.random(3000, 5000));
        }
    }
}