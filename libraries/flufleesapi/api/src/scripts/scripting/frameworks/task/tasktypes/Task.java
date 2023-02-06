package scripts.scripting.frameworks.task.tasktypes;

import scripts.scripting.frameworks.mission.missiontypes.TaskFinish;
import scripts.scripting.frameworks.modulardecisiontree.nodes.INode;
import scripts.scripting.frameworks.task.Priority;

/**
 * @author Encoded
 */
public abstract class Task implements INode {

    public abstract Priority priority();
    public abstract boolean isValid();
    public abstract TaskFinish execute();
    public abstract String getStatus();

    public INode getValidNode() {
        return this;
    }
}