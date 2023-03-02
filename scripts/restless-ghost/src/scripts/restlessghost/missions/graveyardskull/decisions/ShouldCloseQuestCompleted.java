package scripts.restlessghost.missions.graveyardskull.decisions;

import org.tribot.api2007.Interfaces;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.reusable.tasks.CloseQuestCompleted;

public class ShouldCloseQuestCompleted extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Interfaces.isInterfaceValid(277);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new CloseQuestCompleted());
        setFalseNode(new ShouldWatchCutscene());
    }

}
