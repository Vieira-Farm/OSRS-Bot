package scripts.client.clientextensions;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import scripts.utils.ConditionUtilities;

import java.util.function.BooleanSupplier;

public class Walking extends org.tribot.api2007.Walking {

    /**
     * Walks a path of tiles using the minimap to click the tiles. This allows you to walk a full path of tiles without
     * TRiBot trying to take the shortest path
     *
     * @param path Positionable[] array containing the tiles you want the bot to walk
     * @return True if the end tile in the path was successfully reached, false otherwise.
     */
    public static boolean walkPath(Positionable[] path) {
        return walkPath(path, null);
    }

    /**
     * Walks a path of tiles using the minimap to click the tiles. This allows you to walk a full path of tiles without
     * TRiBot trying to take the shortest path
     *
     * @param path Positionable[] array containing the tiles you want the bot to walk
     * @param stoppingCondition BooleanSupplier containing a condition we want to check as we walk. This allows us to
     *                          break out of the walking early
     * @return True if the end tile in the path was successfully reached, false otherwise.
     */
    public static boolean walkPath(Positionable[] path, BooleanSupplier stoppingCondition) {
        for (Positionable position : path) {
            if (Player.getPosition().distanceTo(position.getPosition()) <= 3) {
                continue;
            }
            org.tribot.api2007.Walking.clickTileMM(position, 1);
            Timing.waitCondition(ConditionUtilities.nearTile(3, position.getPosition()), General.random(5000, 8000));
            if (stoppingCondition != null && stoppingCondition.getAsBoolean())
                break;
        }
        return Player.getPosition().distanceTo(path[path.length-1].getPosition()) < 3;
    }

    /**
     * Walks a path of tiles using the screen to click the tiles. This allows you to walk a full path of tiles without
     * TRiBot trying to take the shortest path
     *
     * @param path Positionable[] array containing the tiles you want the bot to walk
     * @return True if the end tile in the path was successfully reached, false otherwise.
     */
    public static boolean walkScreenPath(Positionable[] path) {
        return walkScreenPath(path, null);
    }

    /**
     * Walks a path of tiles using the screen to click the tiles. This allows you to walk a full path of tiles without
     * TRiBot trying to take the shortest path
     *
     * @param path Positionable[] array containing the tiles you want the bot to walk
     * @param stoppingCondition BooleanSupplier containing a condition we want to check as we walk. This allows us to
     *                          break out of the walking early
     * @return True if the end tile in the path was successfully reached, false otherwise.
     */
    public static boolean walkScreenPath(Positionable[] path, BooleanSupplier stoppingCondition) {
        for (Positionable position : path) {
            if (Player.getPosition().distanceTo(position.getPosition()) <= 3) {
                continue;
            }
            org.tribot.api2007.Walking.clickTileMS(position, 1);
            Timing.waitCondition(ConditionUtilities.nearTile(3, position.getPosition()), General.random(5000, 8000));
            if (stoppingCondition != null && stoppingCondition.getAsBoolean()) {
                break;
            }
        }
        return Player.getPosition().distanceTo(path[path.length-1].getPosition()) < 3;
    }


}
