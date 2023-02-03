package scripting.reusable.nodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;

public class NavigateObject extends SuccessProcessNode {

    private RSTile objectTile = null;
    private String objectName = "Door", objectAction = "Open";
    private String status = "Opening door";

    public NavigateObject(RSTile objectTile, String status, String objectName, String objectAction) {
        this.objectTile  = objectTile;
        this.status = status;
        this.objectName = objectName;
        this.objectAction = objectAction;
    }

    public NavigateObject(RSTile objectTile, String status) {
        this.objectTile  = objectTile;
        this.status = status;
    }

    public NavigateObject(RSTile objectTile) {
        this.objectTile = objectTile;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void successExecute() {
        RSObject[] objects = Objects.find(7, Filters.Objects.nameEquals(objectName)
                .and(Filters.Objects.tileEquals(objectTile)));
        if (objects.length < 1)
            return;
        if (objects[0].isOnScreen() && objects[0].isClickable() && Clicking.click(objectAction, objects[0])) {
            Timing.waitCondition(ConditionUtilities.stoppedMoving(), General.random(3000, 5000));
        } else {
            objects[0].adjustCameraTo();
        }
    }
}