package missions.bank.decisionnodes;

import client.clientextensions.NPCChat;
import data.Constants;
import missions.bank.processnodes.OpenPoll;
import org.tribot.api2007.Game;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldOpenPoll extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 520 &&
                !NPCChat.inChat(1);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new OpenPoll());
        setFalseNode(new ShouldTalkPoll());
    }

}
