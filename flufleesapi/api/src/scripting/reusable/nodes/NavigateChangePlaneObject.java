package scripting.reusable.nodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;

public class NavigateChangePlaneObject extends SuccessProcessNode {

    private String name, action = "", status = "Navigating object";
    private RSTile location;

    public NavigateChangePlaneObject(String name, String action) {
        this.name = name;
        this.action = action;
    }

    public NavigateChangePlaneObject(String name, RSTile location) {
        this.name = name;
        this.location = location;
    }

    public NavigateChangePlaneObject(String name, RSTile location, String action) {
        this(name, location);
        this.action = action;
    }

    public NavigateChangePlaneObject(String name, String status, RSTile location) {
        this(name, location);
        this.status = status;
    }

    public NavigateChangePlaneObject(String name, RSTile location, String action, String status) {
        this (name, location, action);
        this.status = status;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void successExecute() {
        int startingPlane = Player.getPosition().getPlane();
        RSObject[] objects;
        if (location != null) {
            objects = Objects.find(7, Filters.Objects.nameEquals(name).and(Filters.Objects.tileEquals(location)));
        } else {
            objects = Objects.find(7, Filters.Objects.nameEquals(name).and(Filters.Objects.actionsEquals(action)));
        }
        if (objects.length < 1)
            return;
        if (objects[0].isOnScreen() && objects[0].isClickable() && Clicking.click(action, objects[0])) {
            Timing.waitCondition(ConditionUtilities.changedPlane(startingPlane), General.random(3000, 5000));
        } else {
            objects[0].adjustCameraTo();
        }
    }
}
