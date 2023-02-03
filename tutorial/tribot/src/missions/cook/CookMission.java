package missions.cook;

import org.tribot.api2007.Game;
import scripting.frameworks.mission.missiontypes.TreeMission;
import scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;
import data.Constants;
import missions.cook.decisionnodes.ShouldOpenGate;

public class CookMission implements TreeMission {

    //Documentation
    //Setting 281 == 120 when you should open the gate to walk to the cook
    //Setting 281 == 130 when you've opened the gate. However, this shouldn't be used, as you can go back through the
    //gate by accident. Then the bot could get stuck
    //Setting 281 == 140 when you should talk to the cook
    //Setting 281 == 150 when you should make bread dough
    //Setting 281 == 160 when you should cook the bread dough
    //Setting 281 == ??? when you burn the bread
    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldOpenGate();
    }

    @Override
    public String getMissionName() {
        return "Cook mission";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 120 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 170;
    }
}
