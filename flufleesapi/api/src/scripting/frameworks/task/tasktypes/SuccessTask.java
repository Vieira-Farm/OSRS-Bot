package scripting.frameworks.task.tasktypes;

import scripting.frameworks.mission.missiontypes.TaskFinish;

public abstract class SuccessTask extends Task {
    public abstract void successExecute();

    public TaskFinish execute() {
        successExecute();
        return NodeFinishes.GENERIC_SUCCESS;
    }
}
