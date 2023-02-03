package web.pop3Mail;

/**
 * Created by Fluffee on 22/08/17.
 */

/**
 * POP3Session - Class for checking e-mail via POP3 protocol.
 */

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class POP3Session extends Object {

    /** 15 sec. socket read timeout */
    public static final int SOCKET_READ_TIMEOUT = 15 * 1000;
    protected Socket pop3Socket;
    protected BufferedReader in;
    protected PrintWriter out;
    private String host;
    private int port;
    private String userName;
    private String password;

    /**
     * Creates new POP3 session by given POP3 host and port, username and password.
     * @param host - host to connect to
     * @param port - Port to connect to on the host
     * @param userName - Username to login with
     * @param password - Password to login with
     */
    public POP3Session(String host, int port, String userName, String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Throws exception if given server response if negative. According to POP3
     * protocol, positive responses start with a '+' and negative start with '-'.
     */
    protected void checkForError(String response) throws IOException {
        if (response.charAt(0) != '+')
            throw new IOException(response);
    }

    /**
     * Gets the number of messages in your inbox
     * @return the current number of messages using the POP3 STAT command.
     */
    public int getMessageCount() throws IOException {
        // Send STAT command
        String response = doCommand("STAT");
        // The format of the response is +OK msg_count size_in_bytes
        // We take the substring from offset 4 (the start of the msg_count) and
        // go up to the first space, then convert that string to a number.
        try {
            String countStr = response.substring(4, response.indexOf(' ', 4));
            int count = (new Integer(countStr)).intValue();
            return count;
        } catch (Exception e) {
            throw new IOException("Invalid response - " + response);
        }
    }

    /**
     * Gets headers from all emails in inbox
     * @return String[] with all headers.
     * @throws IOException
     */
    public String[] getAllHeaders() throws IOException {
        int messageCount = getMessageCount();
        ArrayList<String> headers = new ArrayList<String>();
        for (int i = 1; i <= messageCount; i++) {
            headers.addAll(Arrays.asList(getHeaders(0)));
        }
        return headers.toArray(new String[headers.size()]);
    }

    /**
     * Gets the headers of the message associated with the id
     * @param messageId ID of the message to getInstance the headers of
     * @return String[] with the header contents.
     * @throws IOException
     */
    public String[] getHeaders(int messageId) {
        try {
            doCommand("TOP " + messageId + " 0");
            return getMultilineResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the body of the message associated with the id
     * @param messageId ID of the message to getInstance the body of
     * @return String[] with the body contents.
     * @throws IOException
     */
    public String[] getBody(int messageId) {
        String[] message = new String[0];
        try {
            doCommand("RETR " + messageId);
            message = getMultilineResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int index = 0;
        for (index = 0; index < message.length; index++) {
            if (message[index].equals("")) {
                break;
            }
        }
        return Arrays.copyOfRange(message, index, message.length);
    }

    /**
     * Returns size of message, based off of messageId
     * @param messageId - MessageID to getInstance the size of
     * @return - Returns size as String
     * @throws IOException
     */
    public String getSize(int messageId) throws IOException {
        String response = doCommand("LIST " + messageId);
        return response.substring(response.lastIndexOf(" ") + 1);
    }

    /**
     * Returns the message associated with the id
     * @param messageId MessageID to getInstance the message of
     * @return Message object for the messageId
     * @throws IOException
     */
    public Message getMessage(int messageId) throws IOException {
        String[] headers = getHeaders(messageId);
        String[] body = getBody(messageId);
        return new Message(headers, body);
    }

    /**
     * Deletes a particular message with DELE command.
     */
    public void deleteMessage(String messageId) throws IOException {
        doCommand("DELE " + messageId);
    }

    /**
     * Initiates a graceful exit by sending QUIT command.
     */
    public void gracefulQuit() throws IOException {
        doCommand("QUIT");
    }

    /**
     * Connects to the POP3 server and logs on it
     * with the USER and PASS commands.
     */
    public boolean connectAndAuthenticate() {
        // Make the connection
        try {
            pop3Socket = new Socket(host, port);
            pop3Socket.setSoTimeout(SOCKET_READ_TIMEOUT);
            in = new BufferedReader(new InputStreamReader(pop3Socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(pop3Socket.getOutputStream()));
            // Receive the welcome message
            String response = in.readLine();
            checkForError(response);
            // Send a USER command to authenticate
            doCommand("USER " + userName);
            // Send a PASS command to finish authentication
            doCommand("PASS " + password);
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Closes down the connection to POP3 server (if open).
     * Should be called if an exception is raised during the POP3 session.
     */
    public void close() {
        try {
            in.close();
            out.close();
            pop3Socket.close();
        } catch (Exception ex) {
            // Ignore the exception. Probably the socket is not open.
        }
    }

    /**
     * Sends a POP3 command and retrieves the response. If the response is
     * negative (begins with '-'), throws an IOException with received response.
     */
    protected String doCommand(String command) throws IOException {
        out.println(command);
        out.flush();
        String response = in.readLine();
        checkForError(response);
        return response;
    }

    /**
     * Retrieves a multi-line POP3 response. If a line contains "." by itself,
     * it is the end of the response. If a line starts with a ".", it should
     * really have two "."'s. We strip off the leading ".". If a line does not
     * start with ".", there should be at least one line more.
     */
    protected String[] getMultilineResponse() throws IOException {
        ArrayList lines = new ArrayList();
        while (true) {
            String line = in.readLine();
            if (line == null) {
                // Server closed connection
                throw new IOException("Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                // No more lines in the server response
                break;
            }
            if ((line.length() > 0) && (line.charAt(0) == '.')) {
                // The line starts with a "." - strip it off.
                line = line.substring(1);
            }
            // Add read line to the list of lines
            lines.add(line);
        }
        String response[] = new String[lines.size()];
        lines.toArray(response);
        return response;
    }

    /**
     * Returns the message ids, which are addressed to the toEmail
     * @param toEmail Email address to search to field for
     * @return int[] with ids of emails
     * @throws IOException
     */
    public int[] getMessageIds (String toEmail) {
        int messageCount = 0;
        try {
            messageCount = getMessageCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= messageCount; i++) {
            if (getToEmailAddress(i).equals(toEmail)) {
                ids.add(i);
            }
        }
        return ids.stream().mapToInt(i -> i).toArray();
    }

    public int[] getMessageIds(String toEmail, String fromEmail) {
        int messageCount = 0;
        try {
            messageCount = getMessageCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= messageCount; i++) {
            if (getToEmailAddress(i).equals(toEmail) && getFromEmailAddress(i).equals(fromEmail)) {
                ids.add(i);
            }
        }
        return ids.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Gets the email address the email was sent to
     *
     * @param messageId ID of the message you want the to email of.
     * @return The to email address as a String.
     */
    public String getToEmailAddress (int messageId) {
        String[] headers = new String[0];
            headers = getHeaders(messageId);
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].contains("Envelope-to: ")) {
                return headers[i].substring(headers[i].indexOf("Envelope-to: ") + "Envelope-to: ".length());
            }
        }
        return "";
    }

    /**
     * Gets the email address the email was sent from
     * @param messageID ID of the message you want the from email of.
     * @return The from email address as a String
     */
    public String getFromEmailAddress (int messageID) {
        String[] headers = new String[0];
            headers = getHeaders(messageID);
        for(int i = 0; i < headers.length; i++) {
            if (headers[i].contains("Reply-To: ")) {
                return headers[i].substring(headers[i].indexOf("Reply-To: ") + "Reply-To: ".length());
            }
        }
        return "";
    }

}