package scripts.utils.lists;

import scripts.client.clientextensions.Timing;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.types.RSInterfaceComponent;
import scripts.utils.SocialLists;
import java.util.function.BooleanSupplier;

/**
 * Created by Fluffee on 18/05/2017.
 */
final public class RSIgnore {
    private final RSInterfaceComponent nameComponent;
    private final String name;

    public RSIgnore(RSInterfaceComponent nameComponent) {
        this.name = nameComponent.getText().replaceAll("" + (char) 160, "" + (char) 32);
        this.nameComponent = nameComponent;
    }

    public String getName() {
        return name;
    }

    public boolean delete() {
        final int count = SocialLists.getAmount(false);
        return GameTab.open(GameTab.TABS.FRIENDS) && nameComponent.click("Delete") && Timing.waitCondition(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return SocialLists.getAmount(false) < count;
            }
        }, 1200);
    }

    @Override
    public String toString() {
        return name;
    }
}