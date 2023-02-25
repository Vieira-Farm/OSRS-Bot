package scripts.accountcreator.missions.accountcreator;

import scripts.accountcreator.data.Variables;
import scripts.data.accounts.AccountDetails;
import scripts.data.network.ConnectionSettings;
import scripts.accountcreator.missions.accountcreator.decisionnodes.ShouldSolveCaptcha;
import scripts.scripting.frameworks.mission.missiontypes.TaskFinish;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;
import scripts.web.accountCreation.captcha.CaptchaSolver;

public class AccountCreator implements TreeMission {

    private Variables variables;

    public AccountCreator(AccountDetails accountDetails,
                          ConnectionSettings connectionSettings,
                          CaptchaSolver captchaSolver) {
        try {
            variables = new Variables(this.getClass(), accountDetails, connectionSettings, captchaSolver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getMissionName() {
        return "Account Creation";
    }

    @Override
    public boolean isMissionValid() {
        return !variables.isCreationCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return variables.isCreationCompleted();
    }

    @Override
    public BaseDecisionNode getTreeRoot() {
        return ShouldSolveCaptcha.create(this.variables);
    }

    @Override
    public MissionGameState getMissionGameState() {
        return MissionGameState.OUT_OF_GAME;
    }

    @Override
    public boolean shouldStopExecution(TaskFinish childTaskFinish) {
        if (childTaskFinish.getFinishType() == TaskFinish.FinishTypes.WARNING) {
            variables.setCreationCompleted(false);
        }
        return TreeMission.super.shouldStopExecution(childTaskFinish);
    }

    public enum MissionFinishes implements TaskFinish {
        TIME_OUT(
                FinishTypes.SUCCESS,
                "Your proxy or internet connection timed out during creation, we'll try again."
        ),
        RETRY_ACCOUNT(
                FinishTypes.SUCCESS,
                "There was an error on the RS side, we'll try to create the account again."
        ),
        ADDITIONAL_SECURITY_CHECK(
                FinishTypes.SUCCESS,
                "RS decided to add an extra security check to avoid robot."
        ),
        ACCOUNT_CREATED(
                FinishTypes.SUCCESS,
                "Account created successfully!"
        ),
        EMAIL_ADDRESS_TAKEN(
                FinishTypes.WARNING,
                "The account's email address was already taken, skipping account."
        ),
        IP_BANNED(
                FinishTypes.STOP_SCRIPT,
                "Your IP has been banned from creating accounts."
        ),
        SCAM_PASSWORD(
                FinishTypes.WARNING,
                "The password supplied was a common scam password, skipping account"
        ),
        PASSWORD_LENGTH(
                FinishTypes.WARNING,
                "The password supplied was not between 5 and 20 characters, skipping account"
        ),
        INVALID_PASSWORD(
                FinishTypes.WARNING,
                "The password supplied contained characters that RS does not support, skipping account"
        ),
        INVALID_EMAIL_DOMAIN(
                FinishTypes.WARNING,
                "The email domain being used is not allowed by RS, skipping account"
        ),
        SKIPPING_ACCOUNT(
                FinishTypes.WARNING,
                "Account has been skipped, there was an unfixable error with" +
                " it's information"),
        UNKNOWN_ERROR(
                FinishTypes.STOP_SCRIPT,
                "An unknown error during creation occurred, please send the above error to Fluffee."),
        ;

        private FinishTypes finishType;
        private String description;

        MissionFinishes(FinishTypes finishType, String description) {
            this.finishType = finishType;
            this.description = description;
        }

        @Override
        public FinishTypes getFinishType() {
            return this.finishType;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
    }
}
