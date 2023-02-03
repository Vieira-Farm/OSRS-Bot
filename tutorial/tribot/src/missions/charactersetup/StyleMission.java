package missions.charactersetup;

import client.clientextensions.Game;
import data.Constants;
import data.Variables;
import missions.charactersetup.decisionnodes.ShouldEnterName;
import scripting.frameworks.mission.missiontypes.TaskFinish;
import scripting.frameworks.mission.missiontypes.TreeMission;
import scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class StyleMission implements TreeMission {

    //Documentation Notes:
    //Name picking master interface ID = 558
    //Box to click to enter a name, interface ID = 11. If it has text we've looked up a name, otherwise we haven't
    //Varbit 5607 == 1 when our character has a name picked, 0 when not picked
    //Setting 281 == 2 when we've completed styling our character
    //Varbit 5605 indicates status of checking. If it's 1, the has been taken, or no name entered,
    //  2 is waiting on response from server, 4 means the name is available, 5 is processing the claim

    private Variables variables;

    public StyleMission(Variables variables) {
        this.variables = variables;
    }

    @Override
    public BaseDecisionNode getTreeRoot() {
        return ShouldEnterName.create(variables);
    }

    @Override
    public String getMissionName() {
        return "Style Mission";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) < 2 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 2;
    }

    public enum MissionFinishes implements TaskFinish {
        NAME_CHECK_FAILED(TaskFinish.FinishTypes.STOP_SCRIPT, "Name checking failed"),
        NAME_UNAVAILABLE(TaskFinish.FinishTypes.WARNING, "Username unavailable"),
        ;

        private TaskFinish.FinishTypes finishType;
        private String description;

        MissionFinishes(TaskFinish.FinishTypes finishType, String description) {
            this.finishType = finishType;
            this.description = description;
        }

        public TaskFinish.FinishTypes getFinishType() {
            return finishType;
        }

        public String getDescription() {
            return description;
        }
    }
}
