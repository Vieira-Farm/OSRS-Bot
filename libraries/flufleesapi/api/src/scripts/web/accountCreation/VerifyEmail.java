package scripts.web.accountCreation;

import scripts.web.HTTPRequests;

/**
 * Created by Fluffee on 22/08/17.
 */
public class VerifyEmail {

    public static String getVerificationLink (String[] emailBody) {
        for (int index = 0; index < emailBody.length; index++) {
            if (emailBody[index].contains("https://secure.runescape.com/m=email-register/submit_code.ws?address=")) {
                return emailBody[index].trim();
            }
        }
        return "";
    }

    public static boolean verifyEmail (String verificationLink) {
        String response = HTTPRequests.requestURL(verificationLink, 30000);
        if (response.contains("Congratulations")) {
            return true;
        }
        return false;
    }
}
