package scripts.web;

import com.google.gson.JsonObject;
import org.tribot.api.General;

import java.io.*;
import java.net.*;

/**
 * Created by Fluffee on 10/08/17.
 */

public class HTTPRequests {

    /**
     * Sends a getInstance request to a page, and returns the response.
     *
     * @param urlString web URL
     * @param proxy     Proxy to request the page with.
     * @param timeout   Timeout value, how long to wait before killing request
     * @return a String with the data response.
     */
    public static String requestURL(String urlString, Proxy proxy, int timeout) {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection(proxy);
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder output = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
                output.append(inputLine);
            in.close();
            return output.toString();
        } catch (IOException e) {
            General.println("IOExcept Req: " + e.toString());
        }
        return "";
    }

    /**
     * Sends a getInstance request to a page, and returns the response.
     *
     * @param urlString web URL
     * @param proxy     Proxy to request the page with.
     * @param timeout   Timeout value, how long to wait before killing request
     * @return a String with the data response.
     */
    public static String requestURL(String urlString, Proxy proxy, int timeout, String accept, String acceptEncoding, String userAgent, String acceptLanguage) {
        try {
            URL address = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) address.openConnection(proxy);
            httpConnection.addRequestProperty("Accept", accept);
            httpConnection.addRequestProperty("Accept-Encoding", acceptEncoding);
            httpConnection.addRequestProperty("User-Agent", userAgent);
            httpConnection.addRequestProperty("Accept-Language", acceptLanguage);
            httpConnection.addRequestProperty("Connection", "keep-alive");
            httpConnection.setConnectTimeout(timeout);
            httpConnection.setReadTimeout(timeout);

            BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            StringBuilder output = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
                output.append(inputLine);
            in.close();
            return output.toString();
        } catch (NullPointerException e) {
            General.println("Hit an error");
            return "";
        } catch (IOException e) {
            General.println("IOExcept Req: " + e.toString());
        }
        return "";
    }

    public static HttpURLConnection requestURLConnection(String urlString, int timeout, String accept, String acceptEncoding, String userAgent, String acceptLanguage) {
        try {
            URL address = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) address.openConnection();
            httpConnection.setConnectTimeout(timeout);
            httpConnection.setReadTimeout(timeout);
            httpConnection.addRequestProperty("Accept", accept);
            httpConnection.addRequestProperty("Accept-Encoding", acceptEncoding);
            httpConnection.addRequestProperty("User-Agent", userAgent);
            httpConnection.addRequestProperty("Accept-Language", acceptLanguage);
            httpConnection.addRequestProperty("Connection", "keep-alive");

            return httpConnection;
        } catch (IOException e) {
            General.println("IOExcept Req: " + e.toString());
            return null;
        }
    }

    /**
     * Sends a getInstance request to a page, and returns the response.
     *
     * @param timeout Timeout value, how long to wait before killing request
     * @return String with the data response.
     */
    public static String requestURL(String urlString, int timeout, String accept, String acceptEncoding, String userAgent, String acceptLanguage) {
        try {
            HttpURLConnection httpConnection = requestURLConnection(urlString, timeout, accept, acceptEncoding, userAgent, acceptLanguage);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            StringBuilder output = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
                output.append(inputLine);
            in.close();
            return output.toString();
        } catch (NullPointerException e) {
            General.println("Hit an error");
            return "";
        } catch (IOException e) {
            General.println("IOExcept Req: " + e.toString());
            return "";
        }
    }

    public static HttpURLConnection requestURLConnection(String urlString, int timeout) {
        try {
            URL address = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) address.openConnection();
            httpConnection.setConnectTimeout(timeout);
            httpConnection.setReadTimeout(timeout);
            httpConnection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpConnection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
            httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0");
            httpConnection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
            httpConnection.addRequestProperty("Connection", "keep-alive");
            return httpConnection;
        } catch (IOException exception) {
            return null;
        }
    }

    /**
     * Sends a getInstance request to a page, and returns the response.
     *
     * @param timeout Timeout value, how long to wait before killing request
     * @return String with the data response.
     */
    public static String requestURL(String urlString, int timeout) {
        try {
            HttpURLConnection httpConnection = requestURLConnection(urlString, timeout);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            StringBuilder output = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
                output.append(inputLine);
            in.close();
            return output.toString();
        } catch (NullPointerException e) {
            General.println("Hit an error");
            return "";
        } catch (IOException e) {
            return "IOException";
        }
    }

    /**
     * Returns a String that contains the data response to the requested page
     *
     * @param timeout Timeout value, how long to wait before killing request
     * @return a String with the data response.
     */
    public static String postURL(String urlString, String parameters, int timeout, Proxy proxy, String accept, String userAgent, String acceptLanguage) throws IOException {
        URL address = new URL(urlString);
        HttpURLConnection httpConnection = (HttpURLConnection) address.openConnection(proxy);
        httpConnection.setRequestMethod("POST");
        httpConnection.addRequestProperty("Accept", accept);
        httpConnection.addRequestProperty("User-Agent", userAgent);
        httpConnection.addRequestProperty("Accept-Language", acceptLanguage);
        httpConnection.addRequestProperty("Connection", "keep-alive");
        httpConnection.setDoOutput(true);
        httpConnection.setConnectTimeout(timeout);
        httpConnection.setReadTimeout(timeout);

        DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();
        httpConnection.disconnect();

        BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        String result = response.toString();
        in.close();
        return result;
    }

    /**
     * Returns a String that contains the data response to the requested page
     *
     * @param timeout Timeout value, how long to wait before killing request
     * @return a String with the data response.
     */
    public static String postURL(String urlString, String parameters, int timeout, Proxy proxy) throws IOException {
        URL address = new URL(urlString);
        HttpURLConnection httpConnection = (HttpURLConnection) address.openConnection(proxy);
        httpConnection.setRequestMethod("POST");
        httpConnection.setDoOutput(true);
        httpConnection.setConnectTimeout(timeout);
        httpConnection.setReadTimeout(timeout);

        DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();
        httpConnection.disconnect();

        BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        String result = response.toString();
        in.close();
        return result;
    }

    /**
     * Returns a String that contains the data response to the requested page
     *
     * @param timeout Timeout value, how long to wait before killing request
     * @return a String with the data response.
     */
    public static String postURLJSON(String urlString, JsonObject object, int timeout, Proxy proxy) throws IOException {
        URL address = new URL(urlString);
        HttpURLConnection httpConnection = (HttpURLConnection) address.openConnection(proxy);
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);
        httpConnection.setConnectTimeout(timeout);
        httpConnection.setReadTimeout(timeout);

        OutputStreamWriter wr = new OutputStreamWriter(httpConnection.getOutputStream());
        wr.write(object.toString());
        wr.flush();
        wr.close();
        httpConnection.disconnect();

        BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        String result = response.toString();
        in.close();
        return result;
    }

    /**
     * Returns a String that contains the data response to the requested page
     *
     * @param timeout Timeout value, how long to wait before killing request
     * @return a String with the data response.
     */
    public static String postURLJSON(String urlString, JsonObject object, int timeout) throws IOException {
        URL address = new URL(urlString);
        HttpURLConnection httpConnection = (HttpURLConnection) address.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);
        httpConnection.setConnectTimeout(timeout);
        httpConnection.setReadTimeout(timeout);

        OutputStreamWriter wr = new OutputStreamWriter(httpConnection.getOutputStream());
        wr.write(object.toString());
        wr.flush();
        wr.close();
        httpConnection.disconnect();

        BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        String result = response.toString();
        in.close();
        return result;
    }

    /**
     * Returns a String that contains the data response to the requested page
     *
     * @param timeout Timeout value, how long to wait before killing request
     * @return a String with the data response.
     */
    public static String postURL(String urlString, String parameters, int timeout, String accept, String userAgent, String acceptLanguage) throws IOException {
        URL address = new URL(urlString);
        HttpURLConnection httpConnection = (HttpURLConnection) address.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.addRequestProperty("Accept", accept);
        httpConnection.addRequestProperty("User-Agent", userAgent);
        httpConnection.addRequestProperty("Accept-Language", acceptLanguage);
        httpConnection.addRequestProperty("Connection", "keep-alive");
        httpConnection.setDoOutput(true);
        httpConnection.setConnectTimeout(timeout);
        httpConnection.setReadTimeout(timeout);

        DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();
        httpConnection.disconnect();

        BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        String result = response.toString();
        in.close();
        return result;
    }

    /**
     * Returns a String that contains the data response to the requested page
     *
     * @param timeout Timeout value, how long to wait before killing request
     * @return a String with the data response.
     */
    public static String postURL(String urlString, String parameters, int timeout) throws IOException {
        URL address = new URL(urlString);
        HttpURLConnection httpConnection = (HttpURLConnection) address.openConnection();
        httpConnection.setRequestMethod("POST");
        httpConnection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpConnection.addRequestProperty("Accept-Encoding", "gzip, deflate, br");
        httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:62.0) Gecko/20100101 Firefox/62.0");
        httpConnection.addRequestProperty("Accept-Language", "en-US,en;q=0.5");
        httpConnection.addRequestProperty("Connection", "keep-alive");
        httpConnection.setDoOutput(true);
        httpConnection.setConnectTimeout(timeout);
        httpConnection.setReadTimeout(timeout);

        DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
        wr.writeBytes(parameters);
        wr.flush();
        wr.close();
        httpConnection.disconnect();

        BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        String result = response.toString();
        in.close();
        return result;
    }

    /**
     * Downloads a file from a URL
     *
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public static void downloadFile(String fileURL, String saveDir, String fileName) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode != HttpURLConnection.HTTP_OK)
            return;

        // opens input stream from the HTTP connection
        InputStream inputStream = httpConn.getInputStream();
        String saveFilePath = saveDir + File.separator + fileName;

        // opens an output stream to save into file
        FileOutputStream outputStream = new FileOutputStream(saveFilePath);

        int bytesRead = -1;
        byte[] buffer = new byte[4096];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        httpConn.disconnect();
    }
}
