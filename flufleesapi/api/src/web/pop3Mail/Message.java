package web.pop3Mail;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Fluffee on 22/08/17.
 */

public class Message {

    //TODO Add separate to fields, subject, etc.
    private Map<String, LinkedList<String>> headers;

    private String body;
    private String[] bodyArray, headersArray;

    protected Message(Map<String, LinkedList<String>> headers, String body) {
        this.headers = Collections.unmodifiableMap(headers);
        this.body = body;
    }

    public Message (String[] headers, String[] body) {
        this.headersArray = headers;
        this.bodyArray = body;
    }

    public Map<String, LinkedList<String>> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public String[] getHeadersArray() {
        return headersArray;
    }

    public String[] getBodyArray() {
        return bodyArray;
    }


}
