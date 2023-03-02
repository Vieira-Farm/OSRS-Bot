package scripts.restlessghost.missions.graveyardskull.decisions;

import scripts.client.clientextensions.Game;
import scripts.restlessghost.missions.commonnodes.WatchCutscene;
import scripts.restlessghost.missions.graveyardskull.processes.PlaceSkull;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldWatchCutscene extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Game.getVarBitValue(6719) > 0;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new WatchCutscene());
        setFalseNode(new PlaceSkull());
    }

}
