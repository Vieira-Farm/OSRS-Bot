package scripts.scripting.frameworks.mission.missiontypes;

public interface TaskFinish {

    FinishTypes getFinishType();
    String getDescription();

    enum FinishTypes {
        SUCCESS,
        STOP_SCRIPT,
        WARNING
    }
}
