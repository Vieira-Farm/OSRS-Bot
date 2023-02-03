package web;

public class UserAgents {

    /**
     * Generates a random user agent based on a random number generated.
     *
     * @return String containing the user agent generated.
     */
    public static String getRandomUserAgent() {
        switch (getRandomInt(1, 4)) {
            case 1:
                return getChromeAgent();
            case 2:
                return getFirefoxAgent();
            case 3:
                return getOperaAgent();
            case 4:
                return getInternetExplorerAgent();
            default:
                return "";
        }
    }

    /**
     * Generates a random user agent for the Chrome web browser
     *
     * @return String containing the Chrome user agent
     */
    public static String getChromeAgent() {
        String template = "Mozilla/5.0 (%s) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/%d.0.%d.%d Safari/537.36";
        return String.format(template, getToken(), getRandomInt(27, 75),
                getRandomInt(1000, 9999), getRandomInt(0, 999));
    }

    /**
     * Generates a random user agent for the Firefox web browser
     *
     * @return String containing the Firefox user agent
     */
    public static String getFirefoxAgent() {
        String template = "Mozilla/5.0 (%s; rv:%d.0) Gecko/20100101 Firefox/%d.0";
        int version = getRandomInt(44, 66);
        return String.format(template, getToken(), version, version);
    }

    /**
     * Generates a random user agent for the Opera web browser
     *
     * @return String containing the Opera user agent
     */
    public static String getOperaAgent() {
        String template = getChromeAgent() + " OPR/%d.0.%d.%d";
        return String.format(template, getRandomInt(45, 60), getRandomInt(1000, 9999), getRandomInt(0, 999));
    }

    /**
     * Generates a random user agent for the Internet Explorer web browser
     *
     * @return String containing the Internet Explorer user agent
     */
    public static String getInternetExplorerAgent() {
        String template = "Mozilla/5.0 (%s%s; Trident/%d.0; rv:%d:0) like Gecko";
        int version = getRandomInt(8, 11);
        int subVersion = 4;
        switch (version) {
            case 9:
                subVersion = 5;
                break;
            case 10:
                subVersion = 6;
                break;
            case 11:
                subVersion = 7;
                break;
        }
        int msiVersion = subVersion + 3;
        String compatibleString = subVersion == 7 ? "" : "compatible; MSIE " + msiVersion + ".0; ";
        //Only subversion 7 needs the compatibility string.

        return String.format(template, compatibleString, getWindowsToken(), subVersion, version);
    }

    /**
     * Generates a random token to be used in the user agent. The token tells the host what OS you are running.
     *
     * @return String containing the generated token
     */
    private static String getToken() {
        switch (getRandomInt(1, 3)) {
            case 1:
                return getWindowsToken();
            case 2:
                return getLinuxToken();
            case 3:
                return getMacToken();
            default:
                return "";
        }
    }

    /**
     * Generates a random windows token
     *
     * @return String containing the generated windows token
     */
    private static String getWindowsToken() {
        int kernelVersion = getRandomInt(4, 7);
        int kernelSubversion = getRandomInt(0, 2);
        String bitType = getRandomInt(0, 1) == 1 ? "" : "WOW64";
        if (kernelVersion == 7) { //Kernel version 7 is used as a sentinel during random generation for Windows 10.
            kernelVersion = 10;
            kernelSubversion = 0;
            bitType = bitType.isEmpty() ? "" : "Win64; x64"; //If the bit type is empty, we're on a 32 bit OS
        }
        return String.format("Windows NT %d.%d; %s", kernelVersion, kernelSubversion, bitType);
    }

    /**
     * Generates a random linux token
     *
     * @return String containing the generated linux token
     */
    private static String getLinuxToken() {
        String bitType = getRandomInt(0, 1) == 0 ? "i686" : "x86_64";
        return String.format("X11; Linux %s", bitType);
    }

    /**
     * Generates a random Macintosh token
     *
     * @return String containing the generated Macintosh token
     */
    private static String getMacToken() {
        return String.format("Macintosh; Intel Mac OS X 10_%d_%d", getRandomInt(8, 14), getRandomInt(0, 9));
    }

    /**
     * Gets a random integer within the specified range. Bounds are inclusive
     *
     * @param min int representing the minimum value to generate
     * @param max int representing the maximum value to generate
     * @return int containing the random number
     */
    private static int getRandomInt(int min, int max) {
        return (int) (min + Math.floor(Math.random() * (max + 1 - min)));
    }

}
