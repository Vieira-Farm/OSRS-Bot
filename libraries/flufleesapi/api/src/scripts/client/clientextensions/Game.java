package scripts.client.clientextensions;

import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.types.RSVarBit;

public class Game extends org.tribot.api2007.Game {

    /**
     * Gets the value of the varbit
     *
     * @param index int containing the index of the varbit to get the value of
     * @return int containing the value of the varbit, or -1 if the varbit is not found.
     */
    public static int getVarBitValue(int index) {
        RSVarBit varbit = RSVarBit.get(index);
        return varbit == null ? -1 : varbit.getValue();
    }

    /**
     * Checks if the player's current position is inside the passed bounds
     *
     * @param lowerBound RSTile representing the tile the player must be above
     * @param upperBound RSTile representing the tile the player must be below
     * @param checkingTile RSTile representing the tile we're checking to see if it's in the bounds
     * @return True if the player is inside the bounds, false otherwise.
     */
    public static boolean tileIsInBounds(RSTile lowerBound, RSTile upperBound, RSTile checkingTile) {
        if (checkingTile.getX() < lowerBound.getX() || checkingTile.getX() > upperBound.getX()) {
            return false;
        }
        if (checkingTile.getY() < lowerBound.getY() || checkingTile.getY() > upperBound.getY()) {
            return false;
        }

        return checkingTile.getPlane() >= lowerBound.getPlane() &&
                checkingTile.getPlane() <= upperBound.getPlane();
    }
}
