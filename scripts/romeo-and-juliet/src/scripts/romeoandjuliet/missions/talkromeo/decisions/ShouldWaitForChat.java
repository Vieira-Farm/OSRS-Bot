package scripts.romeoandjuliet.missions.talkromeo.decisions;


import scripts.client.clientextensions.Game;
import scripts.client.clientextensions.NPCChat;
import scripts.romeoandjuliet.sharednodes.WatchCutscene;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldWaitForChat extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getVarBitValue(6719) > 0 && !NPCChat.inChat(1);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WatchCutscene());
        setFalseNode(new ShouldTalkToRomeo());
    }

}
