package missions.bank.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;
import data.structures.ScriptVariables;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;
import utils.Utilities;

public class CloseBank extends SuccessProcessNode {

    private final RSTile POLL_BOOTH_TILE = new RSTile(3120, 3121, 0);

    @Override
    public String getStatus() {
        return "Closing Bank";
    }

    @Override
    public void successExecute() {
        if (ScriptVariables.getInstance().getRandomBoolean() && Banking.close()) {
            Timing.waitCondition(ConditionUtilities.bankClosed(), General.random(3000, 5000));
        } else {
            Walking.clickTileMM(Utilities.getRandomizedTile(POLL_BOOTH_TILE, 2), 1);
        }
    }
}
