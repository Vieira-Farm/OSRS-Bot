package scripts.client.clientextensions;

import scripts.data.interactables.Interactable;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;

import java.util.function.Predicate;

/**
 * Collection of extended Filters to allow for filtering over Interactable objects
 */
public class Filters extends org.tribot.api2007.ext.Filters {

    public static class Objects extends org.tribot.api2007.ext.Filters.Objects {

        /**
         * Returns the nameEquals or idEquals filter based on whether or not the full array of interactables
         * has all their ids found
         *
         * @param interactables Array of interactable object we want to filter
         * @return Predicate<RSObject> that can filter all the RSObjects in the area around the player
         */
        public static Predicate<RSObject> interactableEquals(final Interactable... interactables) {
            if (interactables == null || interactables.length == 0) {
                return (rsObject) -> false;
            }
            boolean allIdsFound = true;

            for (Interactable interactable : interactables) {
                allIdsFound = interactable.isAllIdsFound();
            }

            return allIdsFound ? idEquals(interactables) : nameEquals(interactables);
        }

        /**
         * Returns a predicate that filters objects based on whether or not their name matches the interactable's name
         *
         * @param interactables Array of interactable object we want to filter
         * @return Predicate<RSObject> that can filter all the RSObjects in the area around the player
         */
        public static Predicate<RSObject> nameEquals(final Interactable... interactables) {
            if (interactables == null || interactables.length == 0) {
                return (rsObject) -> false;
            }
            return (rsObject) -> {
                if (rsObject == null) {
                    return false;
                }
                RSObjectDefinition objectDefinition;
                String objectName;
                if (((objectDefinition = rsObject.getDefinition()) == null) ||
                        ((objectName = objectDefinition.getName()) == null)) {
                    return false;
                }
                for (Interactable interactable : interactables) {
                    if (interactable.getName().equals(objectName)) {
                        interactable.setId(rsObject.getID());
                    }
                    return objectName.equals(interactable.getName());
                }
                return false;
            };
        }

        /**
         * Returns a predicate that filters objects based on whether or not their id matches the interactable's id
         *
         * @param interactables Array of interactable object we want to filter
         * @return Predicate<RSObject> that can filter all the RSObjects in the area around the player
         */
        public static Predicate<RSObject> idEquals(final Interactable... interactables) {
            if (interactables == null || interactables.length == 0) {
                return rsObject -> false;
            }
            return rsObject -> {
                if (rsObject != null) {
                    for (Interactable interactable : interactables) {
                        return rsObject.getID() == interactable.getId();
                    }
                }
                return false;
            };
        }

        /**
         * Returns a predicate that filters objects based on whether or not their model length exceeds the passed
         * model length
         *
         * @param modelLength Max number of points the model can have
         * @return Predicate<RSObject> that can filter all the RSObjects in the area around the player
         */
        public static Predicate<RSObject> modelLengthGreaterThan(int modelLength) {
            return rsObject -> {
                if (rsObject == null) {
                    return false;
                }
                RSModel model = rsObject.getModel();
                return model != null && model.getPoints().length > modelLength;
            };
        }

        /**
         * Returns a predicate that filters objects based on whether or not their actions contain a specific action
         *
         * @param action String containing the action we want to search for
         * @return Predicate<RSObject> that can filter all the RSObjects in the area around the player
         */
        public static Predicate<RSObject> hasAction(String action) {
            return rsObject -> {
                if (rsObject == null) {
                    return false;
                }
                RSObjectDefinition definition = rsObject.getDefinition();
                if (definition == null) {
                    return false;
                }
                for (String definitionAction : definition.getActions()) {
                    if (definitionAction.contains(action)) {
                        return true;
                    }
                }
                return false;
            };
        }
    }
}
