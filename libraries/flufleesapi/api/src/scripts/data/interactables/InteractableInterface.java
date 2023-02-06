package scripts.data.interactables;

import org.tribot.api2007.types.RSInterface;
import scripts.scripting.entityselector.finders.prefabs.InterfaceEntity;

public class InteractableInterface {

    private InterfaceEntity interfaceEntity;
    private int[] interfaceIds;
    private boolean allIdsFound;

    public InteractableInterface(InterfaceEntity interfaceEntity, int numberOfIds, int defaultId) {
        this.interfaceEntity = interfaceEntity;
        this.interfaceIds = new int[numberOfIds];
        for (int i = 0; i < numberOfIds; i++) {
            this.interfaceIds[i] = defaultId;
        }
    }

    public boolean isAllIdsFound() {
        return this.allIdsFound;
    }

    public InterfaceEntity getInterfaceEntity() {
        return this.interfaceEntity;
    }

    public int[] getIds() {
        return this.interfaceIds;
    }

    public int getNumberIds() {
        return this.interfaceIds.length;
    }

    public void updateCachedIds(RSInterface rsInterface) {

    }
}
