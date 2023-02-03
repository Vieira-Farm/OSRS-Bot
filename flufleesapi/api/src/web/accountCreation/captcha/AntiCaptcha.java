package web.accountCreation.captcha;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.tribot.api.General;
import utils.gson.Exclude;
import web.HTTPRequests;

import java.io.IOException;

public class AntiCaptcha extends CaptchaSolver {

    @Exclude
    private final String CREATE_TASK = "https://api.anti-captcha.com/createTask";
    @Exclude
    private final String TASK_RESULT = "https://api.anti-captcha.com/getTaskResult";
    @Exclude
    private int captchaID;
    @Exclude
    private Gson gson;

    public AntiCaptcha (String solver, String captchaKey) {
        super(solver, captchaKey);
        this.gson = new Gson();
    }

    @Override
    public CaptchaStatus solveCaptcha() {
        JsonObject requestObject = new JsonObject();
        JsonObject task = new JsonObject();
        requestObject.addProperty("clientKey", captchaKey);
        task.addProperty("type", "NoCaptchaTaskProxyless");
        task.addProperty("websiteURL", RS_SIGNUP_URL);
        task.addProperty("websiteKey", RS_GOOGLE_KEY);

        requestObject.add("task", task);
        requestObject.addProperty("softId", 0);
        requestObject.addProperty("languagePool", "en");
        String response;
        try {
            response = HTTPRequests.postURLJSON(CREATE_TASK, requestObject, 10000);
        } catch (IOException e) {
            General.println("Error connecting to Anti-Captcha.");
            return CaptchaStatus.UNKNOWN;
        }
        JsonObject responseObject = gson.fromJson(response, JsonObject.class);
        int errorCode = responseObject.get("errorId").getAsInt();
        if (errorCode == 0) {
            captchaID = responseObject.get("taskId").getAsInt();
            return CaptchaStatus.SUCCESS;
        } else if(errorCode == 10) {
            return CaptchaStatus.ZERO_BALANCE;
        } else if (errorCode == 2) {
            return CaptchaStatus.NO_IDLE_WORKERS;
        } else if (errorCode == 1) {
            return CaptchaStatus.INVALID_KEY;
        } else {
            return CaptchaStatus.UNKNOWN;
        }
    }

    @Override
    public boolean isCaptchaSolved() {
        if (captchaID == -1) {
            return false;
        }
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("clientKey", captchaKey);
        requestObject.addProperty("taskId", captchaID);
        String response = "";
        try {
            response = HTTPRequests.postURLJSON(TASK_RESULT, requestObject, 10000);
        } catch (IOException e) {
            General.println("Error connecting to Anti-Captcha.");
            return false;
        }
        JsonObject responseObject = gson.fromJson(response, JsonObject.class);
        if (responseObject.get("status").getAsString().equals("ready")) {
            return true;
        }
        return false;
    }

    @Override
    public String getCaptchaSolution() {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("clientKey", captchaKey);
        requestObject.addProperty("taskId", captchaID);
        String response;
        try {
            response = HTTPRequests.postURLJSON(TASK_RESULT, requestObject, 10000);
        } catch (IOException e) {
            General.println("Error connecting to Anti-Captcha.");
            return "";
        }
        JsonObject responseObject = gson.fromJson(response, JsonObject.class);
        JsonObject solutionObject = responseObject.get("solution").getAsJsonObject();
        return solutionObject.get("gRecaptchaResponse").getAsString();
    }

    @Override
    public void clearCaptchaSolution() {
        captchaID = -1;
    }

    public static String getPrettyName() {
        return "AntiCaptcha";
    }
}
