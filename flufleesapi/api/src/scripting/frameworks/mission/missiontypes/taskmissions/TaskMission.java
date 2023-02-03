package scripting.frameworks.mission.missiontypes.taskmissions;

import scripting.frameworks.mission.missiontypes.Mission;
import scripting.frameworks.modulardecisiontree.nodes.INode;
import scripting.frameworks.task.TaskSet;

public interface TaskMission extends Mission {

    TaskSet getTaskSet();

    default INode getCurrentNode() {
        return getTaskSet().getValidTask();
    }
}
