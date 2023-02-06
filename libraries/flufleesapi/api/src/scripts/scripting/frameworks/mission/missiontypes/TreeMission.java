package scripts.scripting.frameworks.mission.missiontypes;

import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;
import scripts.scripting.frameworks.modulardecisiontree.nodes.INode;

public interface TreeMission extends Mission {

    BaseDecisionNode getTreeRoot();

    default INode getCurrentNode() {
        return getTreeRoot().getValidNode();
    }
}