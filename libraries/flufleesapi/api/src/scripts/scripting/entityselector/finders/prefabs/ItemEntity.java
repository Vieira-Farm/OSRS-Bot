package scripts.scripting.entityselector.finders.prefabs;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import java.util.function.Predicate;

/**
 * @author Laniax
 */
public class ItemEntity extends RSItemFinder<ItemEntity> {

    /**
     * {@inheritDoc}
     */
    public RSItem[] getResults() {

        Predicate<RSItem> filter = super.buildFilter();

        if (filter != null)
            return Inventory.find(filter); // Remarkably, the Inventory class is the only class without a #getAll(filter) method.

        return Inventory.getAll();
    }
}
