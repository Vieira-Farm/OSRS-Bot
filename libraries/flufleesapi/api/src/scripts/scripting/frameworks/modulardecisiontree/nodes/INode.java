package scripts.scripting.frameworks.modulardecisiontree.nodes;


import scripts.scripting.frameworks.mission.missiontypes.TaskFinish;

public interface INode {
    boolean isValid();

    INode getValidNode();

    String getStatus();

    TaskFinish execute();

    enum NodeFinishes implements TaskFinish {
        GENERIC_SUCCESS(FinishTypes.SUCCESS, "Node was successful"),
        NO_VALID_MISSIONS(FinishTypes.STOP_SCRIPT, "No valid missions or nodes found."),
        ;

        private FinishTypes finishType;
        private String finishDescription;

        NodeFinishes(FinishTypes finishType, String finishDescription) {
            this.finishType = finishType;
            this.finishDescription = finishDescription;
        }

        @Override
        public FinishTypes getFinishType() {
            return this.finishType;
        }

        @Override
        public String getDescription() {
            return this.finishDescription;
        }
    }
}
