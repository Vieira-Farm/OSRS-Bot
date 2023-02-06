package scripts.missions.gettingstarted.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.data.Constants;
import scripts.missions.gettingstarted.processnodes.HandleOptions;
import scripts.missions.gettingstarted.processnodes.OpenSettings;

public class ShouldOpenSettings extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 3;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenSettings());
        setFalseNode(new HandleOptions());
    }

}
