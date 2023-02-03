package utils;

import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

/**
 * Created by Fluffee on 28/11/17.
 */
public class MyWalking {

    public static boolean[][] getCollisionMap() {
        final int[][] flags = PathFinding.getCollisionData();
        final RSTile pLoc = Player.getPosition().toLocalTile();
        boolean[][] map = new boolean[104][104];
        visitTile(map, flags, pLoc.getX(), pLoc.getY());
        return map;
    }

    private static void visitTile(final boolean[][] map, final int[][] flags, final int x, final int y) {
        map[x][y] = true;
        if (y > 0 && !map[x][y - 1] // south
                && (flags[x][y - 1] & 0x1280102) == 0) {
            visitTile(map, flags, x, y - 1);
        }
        if (x > 0 && !map[x - 1][y] // west
                && (flags[x - 1][y] & 0x1280108) == 0) {
            visitTile(map, flags, x - 1, y);
        }
        if (y < 103 && !map[x][y + 1] // north
                && (flags[x][y + 1] & 0x1280120) == 0) {
            visitTile(map, flags, x, y + 1);
        }
        if (x < 103 && !map[x + 1][y] // east
                && (flags[x + 1][y] & 0x1280180) == 0) {
            visitTile(map, flags, x + 1, y);
        }
    }

    public static boolean isNPCReachable(RSNPC npc) {
        return isTileReachable(npc.getPosition());
    }

    public static boolean isObjectReachable(RSObject object) {
        return isTileReachable(object.getPosition());
    }

    public static boolean isGroundItemReachable(RSGroundItem groundItem) {
        return isTileReachable(groundItem.getPosition());
    }

    public static boolean isTileReachable(RSTile tile) {
        tile = tile.getType() != RSTile.TYPES.WORLD ? tile.toWorldTile() : tile;
        RSTile playerPosition = Player.getPosition();
        if (playerPosition.getX() == tile.getX() && playerPosition.getY() == tile.getY()){
            return true;
        }
        final boolean[][] map = MyWalking.getCollisionMap();
        tile = tile.getType() != RSTile.TYPES.LOCAL ? tile.toLocalTile() : tile;
        try {
            return map[tile.getX()][tile.getY()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public static RSNPC[] getReachableNPCS(String... names) {
        final boolean[][] map = MyWalking.getCollisionMap();
        RSNPC[] npcs = NPCs.find(Filters.NPCs.nameContains(names).and(t -> isNPCReachable(t)));
        return npcs;
    }

    public static RSObject[] getReachableObjects(int distance, String... names) {
        final boolean[][] map = MyWalking.getCollisionMap();
        RSObject[] objects = Objects.findNearest(distance, Filters.Objects.nameContains(names).and(t -> isObjectReachable(t)));
        return objects;
    }

}
