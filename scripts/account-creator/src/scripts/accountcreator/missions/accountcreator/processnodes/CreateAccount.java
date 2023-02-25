package scripts.accountcreator.missions.accountcreator.processnodes;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.tribot.api.General;
import scripts.accountcreator.data.Variables;
import scripts.data.accounts.AccountDetails;
import scripts.data.network.ConnectionSettings;
import scripts.accountcreator.missions.accountcreator.AccountCreator;
import scripts.scripting.frameworks.mission.missiontypes.TaskFinish;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ProcessNode;
import scripts.web.UserAgents;

import java.io.IOException;

public class CreateAccount extends ProcessNode {

    private Variables variables;

    public CreateAccount(Variables variables) {
        this.variables = variables;
    }

    private String url = "https://secure.runescape.com/m=account-creation/create_account";

    @Override
    public String getStatus() {
        return "Creating Account";
    }

    @Override
    public TaskFinish execute() {
        populateCookies(variables.httpClient);
        String responseBody = "";

        try {
            responseBody = sendAccountCreationRequest(variables.accountDetails, variables.connectionSettings);
        } catch (IOException e) {
            General.println(AccountCreator.MissionFinishes.IP_BANNED.getDescription());
            return AccountCreator.MissionFinishes.IP_BANNED;
        }

        AccountCreator.MissionFinishes missionFinish = getMissionFinish(responseBody);
        //TIME_OUT and RETRY_ACCOUNT both require the same account to be tried again, so we don't want to mark the
        //account as created
        variables.setCreationCompleted(
                missionFinish != AccountCreator.MissionFinishes.TIME_OUT &&
                        missionFinish != AccountCreator.MissionFinishes.RETRY_ACCOUNT &&
                        missionFinish != AccountCreator.MissionFinishes.ADDITIONAL_SECURITY_CHECK
        );

        if (missionFinish == AccountCreator.MissionFinishes.UNKNOWN_ERROR) {
            General.println(responseBody);
        }

        General.println(missionFinish.getDescription());
        return missionFinish;
    }

    /**
     * Sends a GET request to the RuneScape website to populate the required cookies to create an account
     *
     * @param httpClient OkHttpClient object used to send the request
     * @return None, the cookies are stored inside the httpClient's cookie jar
     */
    public void populateCookies(OkHttpClient httpClient) {
        Request getSignupPage = new Request.Builder()
                .get()
                .url("https://secure.runescape.com/m=account-creation/create_account?theme=oldschool")
                .header("User-agent", UserAgents.getRandomUserAgent())
                .build();
        try {
            httpClient.newCall(getSignupPage).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the POST request to create a RuneScape account
     *
     * @param accountDetails     AccountDetails object containing the details of the account to create
     * @param connectionSettings ConnectionSettings object containing the headers to use for the creation request
     * @return String containing the body of the response
     * @throws IOException Thrown if an error occurs on creation
     */
    public String sendAccountCreationRequest(AccountDetails accountDetails, ConnectionSettings connectionSettings) throws IOException {
        RequestBody creationBody = new FormBody.Builder()
                .add("theme", "oldschool")
                .add("theme", "oldschool")
                .add("email1", accountDetails.getEmail())
                .add("onlyOneEmail", "1")
                .add("password1", accountDetails.getPassword())
                .add("onlyOnePassword", "1")
                .add("day", Integer.toString(accountDetails.getAccountCreationDetails().getCreationDay()))
                .add("month", Integer.toString(accountDetails.getAccountCreationDetails().getCreationMonth()))
                .add("year", Integer.toString(accountDetails.getAccountCreationDetails().getCreationYear()))
                .add("create-submit", "create")
                .add("g-recaptcha-response", variables.captchaSolver.getCaptchaSolution())
                .build();

        Request creationRequest = new Request.Builder()
                .post(creationBody)
                .url(url)
                .addHeader("User-Agent", connectionSettings.getUserAgent())
                .addHeader("Accept", connectionSettings.getAcceptCriteria())
                .addHeader("Accept-Encoding", "deflate, br")
                .addHeader("Accept-Language", connectionSettings.getAcceptLanguage())
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Connection", "keep-alive")
                .addHeader("Host", "secure.runescape.com")
                .addHeader("Pragma", "no-cache")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .build();

        return variables.httpClient.newCall(creationRequest).execute().body().string();
    }

    /**
     * Parses the account creation POST request response body to figure out how the creation attempt went
     *
     * @param responseBody String containing the response body from the account creation post request
     * @return MissionFinish value representing the way the creation went
     */
    public AccountCreator.MissionFinishes getMissionFinish(String responseBody) {
        variables.captchaSolver.clearCaptchaSolution();
        if (responseBody.isEmpty()) {
            return AccountCreator.MissionFinishes.TIME_OUT;
        } else if (responseBody.contains(">" + variables.accountDetails.getEmail())) {
            return AccountCreator.MissionFinishes.RETRY_ACCOUNT;
        } else {
            variables.setCreationCompleted(true);
        }

        if (responseBody.contains("Account Created")) {
            return AccountCreator.MissionFinishes.ACCOUNT_CREATED;
        } else if (responseBody.contains("Passwords must be between 5 and 20 characters.")) {
            return AccountCreator.MissionFinishes.PASSWORD_LENGTH;
        } else if (responseBody.contains("Your password contains invalid characters.")) {
            return AccountCreator.MissionFinishes.INVALID_PASSWORD;
        } else if (responseBody.contains("The email address is not valid.")) {
            return AccountCreator.MissionFinishes.SKIPPING_ACCOUNT;
        } else if (responseBody.contains("Your password is a common scam password.")) {
            return AccountCreator.MissionFinishes.SCAM_PASSWORD;
        } else if (responseBody.contains("This email address has already been used to play.")) {
            return AccountCreator.MissionFinishes.EMAIL_ADDRESS_TAKEN;
        } else if (responseBody.contains("An error has occurred and it has not been possible to create your account")) {
            return AccountCreator.MissionFinishes.IP_BANNED;
        } else if (responseBody.contains("Please stand by, while we are checking your browser.")) {
            return AccountCreator.MissionFinishes.ADDITIONAL_SECURITY_CHECK;
        } else {
            return AccountCreator.MissionFinishes.UNKNOWN_ERROR;
        }
    }
}
