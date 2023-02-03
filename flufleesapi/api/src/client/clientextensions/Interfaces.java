package client.clientextensions;

import data.interactables.InteractableInterface;
import org.tribot.api2007.types.RSInterface;

public class Interfaces extends org.tribot.api2007.Interfaces {

    /**
     * Checks whether or not the passed interface is currently substantiated. Automatically updates the interactable
     * cache if applicable.
     *
     * @param interactableInterface InteractableInterface object to check for substantiation.
     * @return True if the interface is substantiated false otherwise.
     */
    public static boolean isInterfaceSubstantiated(InteractableInterface interactableInterface) {
        if(interactableInterface.isAllIdsFound()) {
            switch(interactableInterface.getNumberIds()) {
                case 1:
                    return isInterfaceSubstantiated(interactableInterface.getIds()[0]);
                case 2:
                    return isInterfaceSubstantiated(interactableInterface.getIds()[0],
                            interactableInterface.getIds()[1]);
                default:
                    return isInterfaceSubstantiated(interactableInterface.getIds()[0],
                            interactableInterface.getIds()[1],
                            interactableInterface.getIds()[2]);
            }
        } else {
            RSInterface tempInterface = interactableInterface.getInterfaceEntity().getFirstResult();
            if (tempInterface != null) {
                interactableInterface.updateCachedIds(tempInterface);
                return tempInterface.isBeingDrawn();
            }
            return false;
        }
    }

    /**
     * Checks if the interface is valid (this can only check master interfaces). Automatically updates the
     * interactable's cached ids if applicable.
     *
     * @param interactableInterface InteractableInterface object to check for validity
     * @return True if the interface is valid, false otherwise.
     */
    public static boolean isInterfaceValid(InteractableInterface interactableInterface) {
        if (interactableInterface.isAllIdsFound()) {
            return isInterfaceValid(interactableInterface.getIds()[0]);
        } else {
            RSInterface tempInterface = interactableInterface.getInterfaceEntity().getFirstResult();
            if (tempInterface != null) {
                interactableInterface.updateCachedIds(tempInterface);
                return true;
            }
            return false;
        }
    }

    /**
     * Gets all children of the passed interface. Automatically updates the interactable's cached ids if applicable.
     *
     * @param interactableInterface InteractableInterface object representing the interface to get children of
     * @return RSInterface[] containing all children of the master, or null if there are no children/master is invalid.
     */
    public static RSInterface[] getChildren(InteractableInterface interactableInterface) {
        if(interactableInterface.isAllIdsFound()) {
            return getChildren(interactableInterface.getIds()[0]);
        } else {
            RSInterface tempInterface = interactableInterface.getInterfaceEntity().getFirstResult();
            if (tempInterface != null) {
                interactableInterface.updateCachedIds(tempInterface);
                return tempInterface.getChildren();
            }
            return null;
        }
    }

    /**
     * Gets the RSInterface object represented by the InteractableInterface. Automatically updates the interactable's
     * cached ids if applicable.
     *
     * @param interactableInterface InteractableInterface object representing the interface to get
     * @return RSInterface object representing the found interface, or null if not found.
     */
    public static RSInterface get(InteractableInterface interactableInterface) {
        if (interactableInterface.isAllIdsFound()) {
            switch (interactableInterface.getNumberIds()) {
                case 1:
                    return get(interactableInterface.getIds()[0]);
                case 2:
                    return get(interactableInterface.getIds()[0],
                            interactableInterface.getIds()[1]);
                case 3:
                default:
                    return get(interactableInterface.getIds()[0],
                            interactableInterface.getIds()[1],
                            interactableInterface.getIds()[2]);
            }
        } else {
            RSInterface tempInterface = interactableInterface.getInterfaceEntity().getFirstResult();
            if (tempInterface != null) {
                interactableInterface.updateCachedIds(tempInterface);
            }
            return tempInterface;
        }
    }
}
