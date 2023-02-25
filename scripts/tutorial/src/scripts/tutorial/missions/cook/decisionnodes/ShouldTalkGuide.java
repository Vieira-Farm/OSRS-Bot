package scripts.tutorial.missions.cook.decisionnodes;

import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;
import scripts.tutorial.data.Constants;

public class ShouldTalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 140;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Master Chef"));
        setFalseNode(new ShouldMakeBreadDough());
    }

}
