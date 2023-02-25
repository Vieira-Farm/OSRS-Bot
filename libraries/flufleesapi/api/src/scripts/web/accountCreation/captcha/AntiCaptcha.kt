package scripts.web.accountCreation.captcha

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.tribot.api.General
import scripts.utils.gson.Exclude
import scripts.web.HTTPRequests
import java.io.IOException

class AntiCaptcha(solver: String?, captchaKey: String?) : CaptchaSolver(solver, captchaKey) {
    @Exclude
    private val CREATE_TASK = "https://api.anti-captcha.com/createTask"

    @Exclude
    private val TASK_RESULT = "https://api.anti-captcha.com/getTaskResult"

    @Exclude
    private var captchaID = 0

    @Exclude
    private val gson: Gson = Gson()

    override fun solveCaptcha(): CaptchaStatus {
        val requestObject = JsonObject()
        val task = JsonObject()
        requestObject.addProperty("clientKey", captchaKey)
        task.addProperty("type", "NoCaptchaTaskProxyless")
        task.addProperty("websiteURL", RS_SIGNUP_URL)
        task.addProperty("websiteKey", RS_GOOGLE_KEY)
        requestObject.add("task", task)
        requestObject.addProperty("softId", 0)
        requestObject.addProperty("languagePool", "en")
        val response: String = try {
            HTTPRequests.postURLJSON(CREATE_TASK, requestObject, 10000)
        } catch (e: IOException) {
            General.println("Error connecting to Anti-Captcha.")
            return CaptchaStatus.UNKNOWN
        }
        val responseObject = gson.fromJson(response, JsonObject::class.java)
        return when (responseObject["errorId"].asInt) {
            0 -> {
                captchaID = responseObject["taskId"].asInt
                CaptchaStatus.SUCCESS
            }
            10 -> {
                CaptchaStatus.ZERO_BALANCE
            }
            2 -> {
                CaptchaStatus.NO_IDLE_WORKERS
            }
            1 -> {
                CaptchaStatus.INVALID_KEY
            }
            16 -> {
                CaptchaStatus.NO_SUCH_CAPCHA_ID
            }
            else -> {
                CaptchaStatus.UNKNOWN
            }
        }
    }

    override fun isCaptchaSolved(): Boolean {
        if (captchaID == -1 || captchaID == 0) {
            return false
        }
        val requestObject = JsonObject()
        requestObject.addProperty("clientKey", captchaKey)
        requestObject.addProperty("taskId", captchaID)
        var response: String? = ""
        response = try {
            HTTPRequests.postURLJSON(TASK_RESULT, requestObject, 10000)
        } catch (e: IOException) {
            General.println("Error connecting to Anti-Captcha.")
            return false
        }
        val responseObject = gson.fromJson(response, JsonObject::class.java)
        General.println(responseObject.toString())
        return responseObject["status"].asString == "ready"
    }

    override fun getCaptchaSolution(): String {
        val requestObject = JsonObject()
        requestObject.addProperty("clientKey", captchaKey)
        requestObject.addProperty("taskId", captchaID)
        val response: String = try {
            HTTPRequests.postURLJSON(TASK_RESULT, requestObject, 10000)
        } catch (e: IOException) {
            General.println("Error connecting to Anti-Captcha.")
            return ""
        }
        val responseObject = gson.fromJson(response, JsonObject::class.java)
        val solutionObject = responseObject["solution"].asJsonObject
        return solutionObject["gRecaptchaResponse"].asString
    }

    override fun clearCaptchaSolution() {
        captchaID = -1
    }

    companion object {
        @JvmStatic
        val prettyName: String
            get() = "AntiCaptcha"
    }
}