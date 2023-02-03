package data.accounts;

import com.allatori.annotations.DoNotRename;
import com.google.gson.*;
import utils.gson.Exclude;

public class AccountDetails {

    @DoNotRename
    private String email, password, username;
    @DoNotRename
    private boolean isAccountCreated;
    @DoNotRename
    private int accountCreationIndex;

    @Exclude
    private AccountCreationDetails accountCreationDetails;

    public AccountDetails(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = "";
        this.accountCreationDetails = null;
        this.isAccountCreated = true;
        this.accountCreationIndex = 1;
    }

    public AccountDetails(String email, String password, boolean isAccountCreated) {
        this.email = email;
        this.password = password;
        this.username = "";
        this.accountCreationDetails = isAccountCreated ? null : AccountCreationDetails.randomFactory(1);
        this.isAccountCreated = isAccountCreated;
        this.accountCreationIndex = 1;
    }

    public AccountDetails(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.accountCreationDetails = AccountCreationDetails.randomFactory(1);
        this.isAccountCreated = false;
        this.accountCreationIndex = 1;
    }

    public AccountDetails(String email, String password, String username, boolean isAccountCreated) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.accountCreationDetails = isAccountCreated ? null : AccountCreationDetails.randomFactory(1);
        this.isAccountCreated = isAccountCreated;
        this.accountCreationIndex = 1;
    }

    public AccountDetails(String email, String password, AccountCreationDetails accountCreationDetails) {
        this.email = email;
        this.password = password;
        this.username = "";
        this.accountCreationDetails = accountCreationDetails;
        this.isAccountCreated = false;
        this.accountCreationIndex = accountCreationDetails.getCreationIndex();
    }

    public AccountDetails(String email, String password, String username,
                          AccountCreationDetails accountCreationDetails) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.accountCreationDetails = accountCreationDetails;
        this.isAccountCreated = false;
        this.accountCreationIndex = accountCreationDetails.getCreationIndex();
    }

    private AccountDetails(String email, String password, String username, boolean isAccountCreated,
                           AccountCreationDetails accountCreationDetails) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.accountCreationDetails = accountCreationDetails;
        this.isAccountCreated = isAccountCreated;
        this.accountCreationIndex = accountCreationDetails.getCreationIndex();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AccountCreationDetails getAccountCreationDetails() {
        return accountCreationDetails;
    }

    public void setAccountCreationDetails(AccountCreationDetails accountCreationDetails) {
        this.accountCreationDetails = accountCreationDetails;
    }

    public boolean isAccountCreated() {
        return isAccountCreated;
    }

    public void setAccountCreated(boolean accountCreated) {
        isAccountCreated = accountCreated;
    }

    public static AccountDetails[] factoryGenerateMultipleAccounts(String email, String password, String username, int numberAccounts) {
        AccountDetails[] accountDetailsList = new AccountDetails[numberAccounts];
        for (int i = 0; i < accountDetailsList.length; i++) {
            accountDetailsList[i] = new AccountDetails(email, password, username, AccountCreationDetails.randomFactory(i));
        }
        return accountDetailsList;
    }

    @Override
    public String toString() {
        return "<html>Email: " + email + "<br />Password: " + password + "</html>";
    }

    /**
     * Custon JsonDeserializer to allow the proxy to be created correctly. As GSON doesn't do well with creating a
     * Proxy object. As such we need to invoke our parameterized constructor instead of the default and
     * using reflection.
     */
    public static class AccountDetailsDeserializer implements JsonDeserializer<AccountDetails> {
        @Override
        public AccountDetails deserialize(JsonElement jsonElement, java.lang.reflect.Type type,
                                         JsonDeserializationContext jsonDeserializationContext)
                throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("username")) {
                return new AccountDetails(
                        jsonObject.get("email").getAsString(),
                        jsonObject.get("password").getAsString(),
                        jsonObject.get("username").getAsString()
                        );
            } else {
                return new AccountDetails(
                        jsonObject.get("email").getAsString(),
                        jsonObject.get("password").getAsString()
                );
            }
        }
    }
}
