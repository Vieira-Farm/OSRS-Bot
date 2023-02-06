package scripts.data.network;

import com.allatori.annotations.DoNotRename;
import com.google.gson.*;
import scripts.utils.gson.Exclude;
import scripts.web.BasicAuthInterceptor;
import scripts.web.UserAgents;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class ConnectionSettings {

    @Exclude
    private String acceptCriteria, acceptLanguage, userAgent;
    @DoNotRename
    private String proxyUsername, proxyPassword, ipAddress;
    @DoNotRename
    private int proxyPort;
    @DoNotRename
    private Proxy proxy;
    @DoNotRename
    private BasicAuthInterceptor interceptor;
    @DoNotRename
    private boolean proxyCreation;

    public ConnectionSettings(String ipAddress, int proxyPort) {
        this(ipAddress, proxyPort, "", "");
    }

    public ConnectionSettings(String ipAddress, int proxyPort, String proxyUsername, String proxyPassword) {
        this.proxy = null;
        if (ipAddress != null && !ipAddress.isEmpty() && proxyPort > 0) {
            this.proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(ipAddress, proxyPort));
        }
        this.ipAddress = ipAddress;
        this.proxyPort = proxyPort;
        this.proxyCreation = proxy != null;
        init(proxyUsername, proxyPassword);
    }

    public ConnectionSettings(Proxy proxy) {
        this(proxy, "", "");
    }

    public ConnectionSettings(Proxy proxy, String proxyPassword, String proxyUsername) {
        this.proxyPort = proxy != null ? ((InetSocketAddress) proxy.address()).getPort() : -1;
        this.ipAddress = proxy != null ? ((InetSocketAddress) proxy.address()).getAddress().toString() : "";
        this.proxy = proxy;
        this.proxyCreation = proxy != null;
        init(proxyUsername, proxyPassword);
    }

    private void init(String proxyUsername, String proxyPassword) {
        this.proxyPassword = proxyPassword;
        this.proxyUsername = proxyUsername;
        this.interceptor = new BasicAuthInterceptor(proxyUsername, proxyPassword);
        this.acceptCriteria = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
        this.acceptLanguage = "en-CA,en-US;q=0.7,en;q=0.3";
        this.userAgent = UserAgents.getRandomUserAgent();
    }

    public String getAcceptCriteria() {
        return acceptCriteria;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void newUserAgent() {
        this.userAgent = UserAgents.getRandomUserAgent();
    }

    public Proxy getProxy() {
        return proxy;
    }

    public boolean isProxyCreation() {
        return proxyCreation;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public BasicAuthInterceptor getInterceptor() {
        return interceptor;
    }

    /**
     * Custon JsonDeserializer to allow the ConnectionSettings to be created correctly. As GSON doesn't do well with creating a
     * Proxy object. As such we need to invoke our parameterized constructor instead of the default and
     * using reflection.
     */
    public static class ConnectionSettingsDeserializer implements JsonDeserializer<ConnectionSettings> {
        @Override
        public ConnectionSettings deserialize(JsonElement jsonElement, java.lang.reflect.Type type,
                                              JsonDeserializationContext jsonDeserializationContext)
                throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new ConnectionSettings(
                    jsonObject.has("ipAddress") ? jsonObject.get("ipAddress").getAsString() : "",
                    jsonObject.has("proxyPort") ? jsonObject.get("proxyPort").getAsInt() : -1,
                    jsonObject.has("proxyUsername") ? jsonObject.get("proxyUsername").getAsString() : "",
                    jsonObject.has("proxyPassword") ? jsonObject.get("proxyPassword").getAsString() : ""
            );
        }
    }
}
