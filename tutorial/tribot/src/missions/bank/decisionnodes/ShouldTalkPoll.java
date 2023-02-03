package missions.bank.decisionnodes;

import org.tribot.api2007.Game;
import client.clientextensions.NPCChat;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.reusable.nodes.TalkToGuide;
import data.Constants;

public class ShouldTalkPoll extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 520 && NPCChat.inChat(1);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new TalkToGuide("Poll booth"));
        setFalseNode(new ShouldClosePoll());
    }

}
