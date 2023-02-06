package scripts.client.clientextensions;

import org.tribot.api.interfaces.Positionable;

public class RSArea extends org.tribot.api2007.types.RSArea {

    public RSArea(Positionable tile1, Positionable tile2) {
        super(tile1, tile2);
    }

    public RSArea(Positionable[] tiles) {
        super(tiles);
    }

    public RSArea(Positionable tile, int radius) {
        super(tile, radius);
    }
}
