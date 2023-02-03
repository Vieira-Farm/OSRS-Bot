package scripting.frameworks.task.tasktypes;

import scripting.frameworks.mission.missiontypes.TaskFinish;
import scripting.frameworks.modulardecisiontree.nodes.INode;
import scripting.frameworks.task.Priority;

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