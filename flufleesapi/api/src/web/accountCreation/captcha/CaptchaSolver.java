package web.accountCreation.captcha;

import com.allatori.annotations.DoNotRename;
import com.google.gson.*;

import java.lang.reflect.Type;

@SuppressWarnings("unused")
public abstract class CaptchaSolver {

    static final String RS_GOOGLE_KEY = "6Lcsv3oUAAAAAGFhlKrkRb029OHio098bbeyi_Hv";
    static final String RS_SIGNUP_URL = "https://secure.runescape.com/m=account-creation/create_account";
    @DoNotRename
    String captchaKey;
    @DoNotRename
    private String solverName;

    CaptchaSolver(String solver, String captchaKey) {
        this.solverName = solver;
        this.captchaKey = captchaKey;
    }

    public String getCaptchaKey() {
        return this.captchaKey;
    }

    public static CaptchaSolver getCaptchaSolver(String solver, String captchaKey) {
        if (solver.equalsIgnoreCase(TwoCaptcha.getPrettyName())) {
            return new TwoCaptcha(solver, captchaKey);
        } else if (solver.equals(AntiCaptcha.getPrettyName())) {
            return new AntiCaptcha(solver, captchaKey);
        } else {
            return null;
        }
    }

    public static class CaptchaSolverDeserializer implements JsonDeserializer {

        @Override
        public CaptchaSolver deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String solverName = jsonObject.get("solverName").getAsString();
            if (solverName.equals("2Captcha")) {
                return new TwoCaptcha(
                        solverName,
                        jsonObject.get("captchaKey").getAsString()
                );
            } else if (solverName.equals("AntiCaptcha")) {
                return new AntiCaptcha(
                        solverName,
                        jsonObject.get("captchaKey").getAsString()
                );
            }
            return null;
        }
    }

    public abstract CaptchaStatus solveCaptcha();
    public abstract boolean isCaptchaSolved();
    public abstract String getCaptchaSolution();
    public abstract void clearCaptchaSolution();

    public enum CaptchaStatus {
        INVALID_KEY,
        NO_IDLE_WORKERS,
        SUCCESS,
        UNKNOWN,
        ZERO_BALANCE,
        ;
    }
}
