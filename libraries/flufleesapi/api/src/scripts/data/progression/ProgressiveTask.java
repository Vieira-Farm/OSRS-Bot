package scripts.data.progression;

public class ProgressiveTask {

    private StopCondition stopCondition;
    private DisposalMethod disposalMethod;
    private TrainingMethod trainingMethod;
    private Location location;

    public ProgressiveTask(StopCondition stopCondition, DisposalMethod disposalMethod, TrainingMethod trainingMethod,
                           Location location) {
        this.stopCondition = stopCondition;
        this.disposalMethod = disposalMethod;
        this.trainingMethod = trainingMethod;
        this.location = location;
    }

    public DisposalMethod getDisposalMethod() {
        return disposalMethod;
    }

    public TrainingMethod getTrainingMethod() {
        return trainingMethod;
    }

    public Location getLocation() {
        return location;
    }

    public StopCondition getStopCondition() {
        return stopCondition;
    }

    public enum DisposalMethod {
        SHIFT_DROP,
        DROP,
        BANK_BOOTH,
        BANK_DEPOSIT;
    }
}
