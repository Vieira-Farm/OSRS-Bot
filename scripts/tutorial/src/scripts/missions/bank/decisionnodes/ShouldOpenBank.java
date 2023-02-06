package scripts.missions.bank.decisionnodes;

import scripts.data.Constants;
import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;

public class ShouldOpenBank extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 510;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Banker", new String[]{"Yes"}));
        setFalseNode(new ShouldDepositItems());
    }

}
