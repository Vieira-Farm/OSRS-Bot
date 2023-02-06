package scripts.scripting.frameworks.mission.missiontypes.progressive;

import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripts.scripting.frameworks.modulardecisiontree.nodes.INode;

public interface ProgressiveTreeMission extends ProgressiveMission {

    ConstructorDecisionNode getTreeRoot();

    default INode getCurrentNode() {
        return getTreeRoot().getValidNode();
    }

}
