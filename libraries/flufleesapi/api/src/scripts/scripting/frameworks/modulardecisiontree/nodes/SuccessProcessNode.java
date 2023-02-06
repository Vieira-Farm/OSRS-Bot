package scripts.scripting.frameworks.modulardecisiontree.nodes;

import scripts.scripting.frameworks.mission.missiontypes.TaskFinish;

public abstract class SuccessProcessNode extends ProcessNode {

    public abstract void successExecute();

    @Override
    public TaskFinish execute() {
        successExecute();
        return NodeFinishes.GENERIC_SUCCESS;
    }
}
