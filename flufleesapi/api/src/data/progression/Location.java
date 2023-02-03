package data.progression;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public interface Location {

    RSArea getArea();
    RSTile getCenterTile();
    String getName();

}
