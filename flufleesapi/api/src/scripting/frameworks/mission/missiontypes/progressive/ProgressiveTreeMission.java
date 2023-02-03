package scripting.frameworks.mission.missiontypes.progressive;

import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.frameworks.modulardecisiontree.nodes.INode;

public interface ProgressiveTreeMission extends ProgressiveMission {

    ConstructorDecisionNode getTreeRoot();

    default INode getCurrentNode() {
        return getTreeRoot().getValidNode();
    }

}
