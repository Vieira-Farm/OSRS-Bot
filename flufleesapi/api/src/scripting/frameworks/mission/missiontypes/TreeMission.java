package scripting.frameworks.mission.missiontypes;

import scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;
import scripting.frameworks.modulardecisiontree.nodes.INode;

public interface TreeMission extends Mission {

    BaseDecisionNode getTreeRoot();

    default INode getCurrentNode() {
        return getTreeRoot().getValidNode();
    }
}