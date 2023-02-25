package scripts.tutorial.missions.bank.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSTile;
import scripts.data.structures.ScriptVariables;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;

public class ClosePoll extends SuccessProcessNode {

    private final int POLL_BOOTH_MASTER_ID = 310;
    private final int POLL_BOOTH_CHILD_ID = 2;
    private final int POLL_BOOTH_COMPONENT_ID = 11;
    private final RSTile DOOR_TILE = Utilities.getRandomizedTile(new RSTile(3124, 3124, 0), 1);

    @Override
    public String getStatus() {
        return "Closing Poll Booth";
    }

    @Override
    public void successExecute() {
        if (ScriptVariables.getInstance().getRandomBoolean()) {
            RSInterface exitButton = Interfaces.get(POLL_BOOTH_MASTER_ID, POLL_BOOTH_CHILD_ID, POLL_BOOTH_COMPONENT_ID);
            if (exitButton != null && exitButton.click("Close")) {
                Timing.waitCondition(ConditionUtilities.interfaceNotSubstantiated(exitButton), General.random(3000, 5000));
            }
        } else if (Walking.walkTo(DOOR_TILE)){
            Timing.waitCondition(ConditionUtilities.nearTile(3, DOOR_TILE), General.random(3000, 5000));
        }
    }
}
