package scripts.scripting.reusable.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;

public class WalkToLocation extends SuccessProcessNode {

    private String status = "Walking to location";
    private RSTile destination;

    public WalkToLocation(RSTile target) {
        this.destination = target;
    }

    public WalkToLocation(RSTile target, String status) {
        this(target);
        this.status = status;
    }

    public WalkToLocation(RSArea target) {
        this(target.getRandomTile());
    }

    public WalkToLocation(RSArea target, String status) {
        this(target.getRandomTile(), status);
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void successExecute() {
        WebWalking.setUseAStar(true);
        WebWalking.walkTo(destination);
        Timing.waitCondition(ConditionUtilities.nearTile(5, destination), General.random(3000, 5000));
    }
}
