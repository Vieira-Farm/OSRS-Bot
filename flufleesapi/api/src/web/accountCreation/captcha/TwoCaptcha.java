package web.accountCreation.captcha;

import utils.gson.Exclude;
import web.HTTPRequests;

public class TwoCaptcha extends CaptchaSolver {

    @Exclude
    private String captchaID = "";

    protected TwoCaptcha(String solver, String captchaKey) {
        super(solver, captchaKey);
    }

    public CaptchaStatus solveCaptcha() {
        String response = HTTPRequests.requestURL("http://2captcha.com/in.php?key=" + captchaKey +
                "&method=userrecaptcha&googlekey=" + RS_GOOGLE_KEY + "&pageurl=" + RS_SIGNUP_URL, 30000);
        if (response.equals("ERROR_WRONG_USER_KEY") || response.equals("ERROR_KEY_DOES_NOT_EXIST")) {
            return CaptchaStatus.INVALID_KEY;
        } else if (response.equals("ERROR_ZERO_BALANCE")) {
            return CaptchaStatus.ZERO_BALANCE;
        } else if (response.startsWith("OK")) {
            captchaID = response.substring(3);
            return CaptchaStatus.SUCCESS;
        } else if (response.startsWith("IOExce")) {
            return CaptchaStatus.NO_IDLE_WORKERS;
        } else {
            return CaptchaStatus.UNKNOWN;
        }
    }

    public boolean isCaptchaSolved() {
        if (captchaID.isEmpty()) {
            return false;
        }
        return !HTTPRequests.requestURL("http://2captcha.com/res.php?key=" + captchaKey + "&action=get&id=" +
                captchaID, 30000).startsWith("CAPCHA");
    }

    public String getCaptchaSolution() {
        String solution = HTTPRequests.requestURL("http://2captcha.com/res.php?key=" + captchaKey +
                "&action=get&id=" + captchaID, 30000);
        return solution.length() < 4 ? "" : solution.substring(3);
    }

    @Override
    public void clearCaptchaSolution() {
        captchaID = "";
    }

    @Override
    public String toString() {
        return "2Captcha";
    }

    public static String getPrettyName() {
        return "2Captcha";
    }
}
