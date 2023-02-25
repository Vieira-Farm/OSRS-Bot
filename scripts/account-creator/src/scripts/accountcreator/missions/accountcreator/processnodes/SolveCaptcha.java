package scripts.accountcreator.missions.accountcreator.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.web.accountCreation.captcha.CaptchaSolver;

import static scripts.web.accountCreation.captcha.CaptchaSolver.CaptchaStatus.SUCCESS;

public class SolveCaptcha extends SuccessProcessNode {

    private CaptchaSolver captchaSolver;

    public SolveCaptcha(CaptchaSolver captchaSolver) {
        this.captchaSolver = captchaSolver;
    }

    @Override
    public String getStatus() {
        return "Solving Captcha";
    }

    @Override
    public void successExecute() {
        CaptchaSolver.CaptchaStatus submission = captchaSolver.solveCaptcha();
        if (submission != SUCCESS) {
            handleError(submission);
            return;
        }
        Timing.waitCondition(() -> {
            General.sleep(10000);
            return captchaSolver.isCaptchaSolved();
        }, 120000);
    }

    private void handleError(CaptchaSolver.CaptchaStatus submission) {
        switch (submission) {
            case NO_IDLE_WORKERS:
                General.println("Error: No idle workers, sleeping for 30 seconds and retrying");
                General.sleep(30000);
                break;
            case INVALID_KEY:
                General.println("Error: captcha Key is invalid");
                break;
            case UNKNOWN:
                General.println("Unknown error, please send Fluffee your settings.");
                break;
            case ZERO_BALANCE:
                General.println("Error: Your captcha account as no money, please add more money to your account.");
                break;
            case NO_SUCH_CAPCHA_ID:
                General.println("Error: No Captcha ID.");
                break;
        }
    }
}
