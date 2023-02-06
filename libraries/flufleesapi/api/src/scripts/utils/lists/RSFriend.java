package scripts.utils.lists;


import scripts.client.clientextensions.Timing;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSInterfaceComponent;
import scripts.utils.SocialLists;

import java.util.function.BooleanSupplier;

/**
 * Created by Fluffee on 18/05/2017.
 */
final public class RSFriend {
    private final RSInterfaceComponent statusComponent;
    private final RSInterfaceComponent nameComponent;
    private final String name;

    public RSFriend(RSInterfaceComponent nameComponent, RSInterfaceComponent statusComponent) {
        this.name = nameComponent.getText().replaceAll("" + (char) 160, "" + (char) 32);
        this.nameComponent = nameComponent;
        this.statusComponent = statusComponent;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return !statusComponent.getText().equals("Offline");
    }

    public boolean message(String message) {
        if (isOnline() && GameTab.open(GameTab.TABS.FRIENDS) && nameComponent.click("Message " + name)) {
            General.sleep(1100, 1200);
            Keyboard.typeString(message);
            General.sleep(300, 500);
            Keyboard.pressEnter();
            return true;
        }
        return false;
    }

    public boolean delete() {
        final int count = SocialLists.getAmount(true);
        return GameTab.open(GameTab.TABS.FRIENDS) && nameComponent.click("Delete") && Timing.waitCondition(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return SocialLists.getAmount(true) < count;
            }
        }, 1200);
    }

    public int getWorld() {
        return isOnline() ? Integer.valueOf(statusComponent.getText().substring(statusComponent.getText().length() - 3)) : -1;
    }

    public boolean hopTo() {
        final int world = getWorld();
        return world != -1 && WorldHopper.changeWorld(world);
    }

    @Override
    public String toString() {
        return name + " - " + (isOnline() ? "Online - World " + getWorld() : "Offline");
    }
}
