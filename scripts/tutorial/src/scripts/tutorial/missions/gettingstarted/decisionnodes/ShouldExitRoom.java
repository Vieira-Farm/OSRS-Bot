package scripts.tutorial.missions.gettingstarted.decisionnodes;

import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.NavigateObject;
import scripts.tutorial.data.Constants;

public class ShouldExitRoom extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 10;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new NavigateObject(new RSTile(3098, 3107, 0)));
        setFalseNode(new ShouldOpenSettings());
    }

}
