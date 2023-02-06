package scripts.missions.bank.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;
import scripts.data.Constants;

public class WalkToBank extends SuccessProcessNode {

    private final RSTile BANK_TILE = Utilities.getRandomizedTile(new RSTile(3121, 3121, 0), 2);

    @Override
    public String getStatus() {
        return "Walking to Bank";
    }

    @Override
    public void successExecute() {
        WebWalking.walkTo(BANK_TILE);
        Timing.waitCondition(ConditionUtilities.areaContains(Constants.BANK_AREA), General.random(10000, 15000));
    }
}
