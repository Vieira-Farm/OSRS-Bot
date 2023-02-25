package scripts.tutorial.missions.charactersetup.processnodes;

import scripts.client.clientextensions.Timing;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.charactersetup.StyleMission;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSVarBit;
import scripts.scripting.frameworks.mission.missiontypes.TaskFinish;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ProcessNode;
import scripts.utils.ConditionUtilities;

public class EnterName extends ProcessNode {

    private final int ENTER_USERNAME_INTERFACE_MASTER_RESIZABLE = 162,
            ENTER_USERNAME_INTERFACE_CHILD_RESIZABLE = 45,
            USERNAME_SELECTION_MASTER_RESIZABLE = 558,
            USERNAME_SELECTION_CHILD_RESIZABLE = 11,
            CHECKING_NAME = 2,
            NAME_AVAILABLE = 4;

    private String username;

    public EnterName(String username) {
        this.username = username;
    }

    @Override
    public String getStatus() {
        return "Entering Name";
    }

    @Override
    public TaskFinish execute() {
        RSInterfaceChild textBox = Interfaces.get(USERNAME_SELECTION_MASTER_RESIZABLE,
                USERNAME_SELECTION_CHILD_RESIZABLE);
        if (textBox.getText().isEmpty()) {
            Keyboard.typeSend(username);
            if (!Timing.waitCondition(ConditionUtilities.varbitEquals(Constants.NAME_AVAILABILITY_VARBIT, CHECKING_NAME),
                    General.random(10000, 15000))) {
                //If the varbit does not change to two, we have a problem
                return StyleMission.MissionFinishes.NAME_CHECK_FAILED;
            }
            while (RSVarBit.get(Constants.NAME_AVAILABILITY_VARBIT).getValue() == CHECKING_NAME) {
                //Sleep until the name availability varbit != 2
                General.sleep(200, 400);
            }
            if (RSVarBit.get(Constants.NAME_AVAILABILITY_VARBIT).getValue() == NAME_AVAILABLE) {
                return NodeFinishes.GENERIC_SUCCESS;
            } else {
                return StyleMission.MissionFinishes.NAME_UNAVAILABLE;
            }
        } else {
            RSInterfaceChild selectionBox = Interfaces.get(USERNAME_SELECTION_MASTER_RESIZABLE,
                    USERNAME_SELECTION_CHILD_RESIZABLE);
            if (selectionBox != null && selectionBox.click("Look up name")) {
                Timing.waitCondition(ConditionUtilities.interfaceSubstantiated(ENTER_USERNAME_INTERFACE_MASTER_RESIZABLE,
                        ENTER_USERNAME_INTERFACE_CHILD_RESIZABLE), General.random(5000, 10000));
            }
        }
        return NodeFinishes.GENERIC_SUCCESS;
    }
}