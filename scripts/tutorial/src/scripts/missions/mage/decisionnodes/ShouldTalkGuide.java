package scripts.missions.mage.decisionnodes;

import scripts.client.clientextensions.Interfaces;
import scripts.data.Constants;
import org.tribot.api2007.Game;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.nodes.TalkToGuide;

import static scripts.missions.mage.processNodes.SelectedMainWorlds.SELECT_CHOOSE_BEGINNING_INTERFACE;

public class ShouldTalkGuide extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 620 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 640 ||
                Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 660 ||
                (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 670 &&
                        !Interfaces.isInterfaceSubstantiated(SELECT_CHOOSE_BEGINNING_INTERFACE));
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Magic Instructor",
                new String[]{"Yes.", "No, I'm not planning to do that.", "I'm good, thanks."}));
        if (Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) < 670) {
            setFalseNode(new ShouldOpenMagic());
        } else {
            setFalseNode(new ShouldChooseBeginning());
        }
    }

}
