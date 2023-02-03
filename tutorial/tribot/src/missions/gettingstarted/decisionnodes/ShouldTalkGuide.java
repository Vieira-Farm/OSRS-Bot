package missions.gettingstarted.decisionnodes;

import org.tribot.api2007.Game;
import data.structures.ScriptVariables;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.reusable.nodes.TalkToGuide;
import data.Constants;

public class ShouldTalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 2 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 7;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Gielinor Guide", new String[]{
                Constants.ACCOUNT_AGE_OPTIONS[ScriptVariables.getInstance().getRandomNumber(3)]}));
        setFalseNode(new ShouldExitRoom());
    }
}
