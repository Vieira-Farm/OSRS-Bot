package client.clientextensions;

import data.interactables.Interactable;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSObject;

public class Objects extends org.tribot.api2007.Objects {

    /**
     * Find all RSObjects near the player, within the specified radius who's name/id matches the Interactable's data.
     * The resulting array is sorted by distance.
     *
     * @param distance int representing the max distance away to look for objects
     * @param interactables Interactable[] array containing all interactables we want to look for.
     * @return RSObject[] Array of RSObject containing all objects that matched our interactables, or an empty array
     *         if we find no matches
     */
    public static RSObject[] findNearest(int distance, Interactable... interactables) {
        return findNearest(distance, isAllIdsFound(interactables) ?
                Filters.Objects.idEquals(interactables) :
                Filters.Objects.nameEquals(interactables)
        );
    }

    /**
     * Find all RSObjects near the player, within the specified radius who's name/id matches the Interactable's data.
     *
     * @param distance int representing the max distance away to look for objects
     * @param interactables Interactable[] array containing all interactables we want to look for.
     * @return RSObject[] Array of RSObject containing all objects that matched our interactables, or an empty array
     *         if we find no matches
     */
    public static RSObject[] find(int distance, Interactable... interactables) {
        return find(distance, isAllIdsFound(interactables) ?
                Filters.Objects.idEquals(interactables) :
                Filters.Objects.nameEquals(interactables)
        );
    }

    /**
     * Find all RSObjects located on a specific tile who's name/id matches the Interactable's data.
     *
     * @param position Positionable object representing the tile on which we want to search for objects
     * @param interactables Interactable[] array containing all interactables we want to look for.
     * @return RSObject[] Array of RSObject containing all objects that matched our interactables, or an empty array
     *         if we find no matches
     */
    public static boolean isAt(Positionable position, Interactable... interactables) {
        return isAt(position, isAllIdsFound(interactables) ?
                Filters.Objects.idEquals(interactables) :
                Filters.Objects.nameEquals(interactables)
        );
    }

    /**
     * Helper function to determine if all the IDs in the interactables array have been found.
     *
     * @param interactables Array of Interactable objects
     * @return true if all IDs are found, false otherwise.
     */
    private static boolean isAllIdsFound(Interactable... interactables) {
        for (Interactable interactable : interactables) {
            if (!interactable.isAllIdsFound()) {
                return false;
            }
        }
        return true;
    }
}
