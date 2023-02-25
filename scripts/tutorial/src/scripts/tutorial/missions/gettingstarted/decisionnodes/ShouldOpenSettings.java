package scripts.tutorial.missions.gettingstarted.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.gettingstarted.processnodes.HandleOptions;
import scripts.tutorial.missions.gettingstarted.processnodes.OpenSettings;

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
