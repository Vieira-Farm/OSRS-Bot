package scripts.client.clientextensions;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;
import org.tribot.api2007.types.RSInterfaceMaster;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.function.BooleanSupplier;

public class NPCChat extends org.tribot.api2007.NPCChat {

    //Various timing measures to try and create realistic chat response times.
    private static final double MEAN_SECONDS_PER_WORD = 0.336086956522,
        STD_DEV_SECONDS_PER_WORD = 0.1,
        MIN_SECONDS_PER_WORD = 0.15,
        MAX_SECONDS_PER_WORD = 0.666666667;

    private static final int
        SELECT_AN_OPTION_CHILD_WINDOW = 1,
        NAME_CHILD = 2,
        RECEIVED_ITEMS_INTERFACE = 11,
        CHATBOX_HIDDEN_CHILD = 45,
        CHATBOX_INTERFACE = 162,
        ITEM_ACTION_INTERFACE_WINDOW = 193,
        PLAYER_TALKING_INTERFACE_WINDOW = 217,
        SELECT_AN_OPTION_INTERFACE_WINDOW = 219,
        SINGLE_OPTION_DIALOGUE_WINDOW = 229,
        NPC_TALKING_INTERFACE_WINDOW = 231,
        LEVEL_UP_INTERFACE_WINDOW = 233,
        PERSISTENT_CLICK_CONTINUE_FONT_ID = 496,
        IN_CHAT_INTERFACE = 559;

    /**
     * Checks if there is a click continue dialog present including chat dialogs
     *
     * @return boolean indicating whether or not a click continue dialog is present.
     */
    public static boolean clickContinuePresent() {
        return Login.getLoginState() == Login.STATE.INGAME && //Interfaces can be valid while not logged in.
            (
                Interfaces.isInterfaceSubstantiated(RECEIVED_ITEMS_INTERFACE) ||
                Interfaces.isInterfaceSubstantiated(ITEM_ACTION_INTERFACE_WINDOW) ||
                Interfaces.isInterfaceSubstantiated(PLAYER_TALKING_INTERFACE_WINDOW) ||
                Interfaces.isInterfaceSubstantiated(SINGLE_OPTION_DIALOGUE_WINDOW) ||
                Interfaces.isInterfaceSubstantiated(NPC_TALKING_INTERFACE_WINDOW) ||
                Interfaces.isInterfaceSubstantiated(LEVEL_UP_INTERFACE_WINDOW)
            );
    }

    /**
     * Checks if there is a click continue dialog present, but doesn't check for chat dialogs
     *
     * @return boolean indicating whether or not a non-chat click continue dialog is present.
     */
    public static boolean clickContinuePresentNoChat() {
        return Login.getLoginState() == Login.STATE.INGAME && //Interfaces can be valid while not logged in.
            (
                Interfaces.isInterfaceSubstantiated(RECEIVED_ITEMS_INTERFACE) ||
                Interfaces.isInterfaceSubstantiated(ITEM_ACTION_INTERFACE_WINDOW) ||
                Interfaces.isInterfaceSubstantiated(SINGLE_OPTION_DIALOGUE_WINDOW) ||
                Interfaces.isInterfaceSubstantiated(CHATBOX_INTERFACE, CHATBOX_HIDDEN_CHILD) ||
                Interfaces.isInterfaceSubstantiated(LEVEL_UP_INTERFACE_WINDOW)
            );
    }

    /**
     * Checks if the persistent click continue dialog is present.
     *
     * @return boolean indicating whether or not the persistent click continue dialog is currently present
     */
    public static boolean clickContinuePersistentPresent() {
        return Login.getLoginState() == Login.STATE.INGAME && //Interfaces can be valid while not logged in.
                Interfaces.isInterfaceSubstantiated(CHATBOX_INTERFACE, CHATBOX_HIDDEN_CHILD);
    }

    /**
     * Gets the master interface which is currently being used for chat
     *
     * @return RSInterfaceMaster object representing the interface currently open for chat, or null if none are open.
     */
    private static RSInterfaceMaster getChatInterface() {
        if (Interfaces.isInterfaceSubstantiated(RECEIVED_ITEMS_INTERFACE)) {
            return Interfaces.get(RECEIVED_ITEMS_INTERFACE);
        }
        if (Interfaces.isInterfaceSubstantiated(ITEM_ACTION_INTERFACE_WINDOW)) {
            return Interfaces.get(ITEM_ACTION_INTERFACE_WINDOW);
        }
        if (Interfaces.isInterfaceSubstantiated(PLAYER_TALKING_INTERFACE_WINDOW)) {
            return Interfaces.get(PLAYER_TALKING_INTERFACE_WINDOW);
        }
        if (Interfaces.isInterfaceSubstantiated(SINGLE_OPTION_DIALOGUE_WINDOW)) {
            return Interfaces.get(SINGLE_OPTION_DIALOGUE_WINDOW);
        }
        if (Interfaces.isInterfaceSubstantiated(NPC_TALKING_INTERFACE_WINDOW)) {
            return Interfaces.get(NPC_TALKING_INTERFACE_WINDOW);
        }
        if (Interfaces.isInterfaceSubstantiated(LEVEL_UP_INTERFACE_WINDOW)) {
            return Interfaces.get(LEVEL_UP_INTERFACE_WINDOW);
        }
        return null;
    }

    /**
     * Gets the RSInterface associated with the click continue text
     *
     * @return RSInterface object which contains the click continue
     */
    public static RSInterface getClickContinue() {
        if (Interfaces.isInterfaceSubstantiated(CHATBOX_INTERFACE, CHATBOX_HIDDEN_CHILD)) {
            return Interfaces.get(CHATBOX_INTERFACE, CHATBOX_HIDDEN_CHILD);
        } else {
            return findClickContinueChild(getChatInterface());
        }
    }

    /**
     * Gets all interface components that contain selectable options
     *
     * @return RSInterfaceComponent[] containing all possible options that can be selected
     */
    public static RSInterfaceComponent[] getSelectOptionInterfaces() {
        if (!Interfaces.isInterfaceSubstantiated(SELECT_AN_OPTION_INTERFACE_WINDOW))
            return null;
        RSInterfaceComponent[] options = Interfaces.get(SELECT_AN_OPTION_INTERFACE_WINDOW).getComponents();
        if (options == null)
            return null;
        return Arrays.copyOfRange(options, 1, options.length); //The 0 component is invalid.
    }

    /**
     * Selects an option from the currently displayed options
     *
     * @param wait boolean indicating whether or not we should wait after the option is clicked
     * @param options String[] containing all options we could select. The first option will be selected.
     * @return boolean indicating whether or not the option was successfully selected.
     */
    public static boolean selectOption(boolean wait, String... options) {
        return selectOption(wait, false, options);
    }

    /**
     * Selects an option from the currently displayed options
     *
     * @param wait boolean indicating whether or not we should wait after the option is clicked
     * @param keyboard boolean indicating whether or not the keyboard should be used to select an option
     * @param options String[] containing all options we could select. The first option will be selected.
     * @return boolean indicating whether or not the option was successfully selected.
     */
    public static boolean selectOption(boolean wait, boolean keyboard, String... options) {
        RSInterfaceChild optionMaster = Interfaces.get(SELECT_AN_OPTION_INTERFACE_WINDOW, SELECT_AN_OPTION_CHILD_WINDOW);
        if (optionMaster == null) {
            return false;
        }
        for (String option : options) {
            int chatOptionNumber = getOptionComponentNumber(optionMaster, option);
            if (chatOptionNumber == -1) {
                continue;
            }
            RSInterfaceComponent optionToSelect = optionMaster.getChild(chatOptionNumber);
            if (keyboard) {
                Keyboard.typeKeys(Integer.toString(chatOptionNumber).charAt(0));
            } else {
                optionToSelect.click();
            }
            return (wait ? Timing.waitCondition(isNotBeingDrawn(optionToSelect), General.random(1000, 2000)) : true);
        }
        return false;
    }

    /**
     * Gets the index of the option component that contains the specified text
     *
     * @param optionMaster RSInterface object representing the interface to look within
     * @param optionText String containing the text to search the options for
     * @return int representing the index of the component containing the text, or -1 if the text is not found
     */
    private static int getOptionComponentNumber(RSInterface optionMaster, String optionText) {
        RSInterface[] options = optionMaster.getChildren();
        if (options == null) {
            return -1;
        }
        for (RSInterface option : options) {
            if (option.getText().contains(optionText)) {
                return option.getIndex();
            }
        }
        return -1;
    }

    /**
     * Gets the interface used for displaying the select option dialog
     *
     * @return
     */
    public static RSInterfaceMaster getSelectOptionInterface() {
        return Interfaces.get(SELECT_AN_OPTION_INTERFACE_WINDOW);
    }

    /**
     * Gets the message contained in the current chat interface
     *
     * @return String containing the message displayed
     */
    public static String getMessage() {
        return getMessage(getChatInterface());
    }

    /**
     * Gets the message in the interface. This is the message that the NPC or your character is saying
     *
     * @param chatInterface RSInterface representing the interface which contains the message
     * @return String containing the message from the chatbox
     */
    public static String getMessage(RSInterface chatInterface) {
        if (chatInterface == null)
            return null;
        RSInterface message = findMessage(chatInterface);
        if (message == null)
            return null;
        return message.getText();
    }

    /**
     * Gets the options currently displayed in the chatbox
     *
     * @return String[] of options displayed in the chatbox
     */
    public static String[] getOptions() {
        if (!Interfaces.isInterfaceSubstantiated(SELECT_AN_OPTION_INTERFACE_WINDOW, SELECT_AN_OPTION_CHILD_WINDOW))
            return null;
        RSInterfaceComponent[] options = Interfaces.get(SELECT_AN_OPTION_INTERFACE_WINDOW, SELECT_AN_OPTION_CHILD_WINDOW).getChildren();
        if (options == null)
            return null;
        return Arrays.asList(options).subList(1, options.length).stream().map(RSInterface::getText).toArray(String[]::new);
    }

    /**
     * Clicks continue in the chat by  clicking on the text
     *
     * @param wait boolean indicating whether or not we should wait for the interface to disappear
     * @return True if the continue was successfully clicked. False otherwise
     */
    public static boolean clickContinue(boolean wait) {
        return clickContinue(wait, false);
    }

    /**
     * Clicks continue in the chat by pressing the spacebar or by clicking on the text
     *
     * @param wait boolean indicating whether or not we should wait for the interface to disappear
     * @param spacebar boolean indicating whether or not the spacebar should be used to continue the chat
     * @return True if the continue was successfully clicked. False otherwise
     */
    public static boolean clickContinue(boolean wait, boolean spacebar) {
        RSInterface clickContinue = getClickContinue();
        if (clickContinue == null) {
            return false;
        }
        spacebar = clickContinue.getFontID() == PERSISTENT_CLICK_CONTINUE_FONT_ID ? false : spacebar;
        if (spacebar) {
            Keyboard.pressKeys(Keyboard.getKeyCode(' '));
        } else {
            Rectangle clickContinueBounds = clickContinue.getAbsoluteBounds();
            if (clickContinueBounds != null && !clickContinueBounds.contains(Mouse.getPos())) {
                Mouse.moveBox(clickContinue.getAbsoluteBounds());
            }
            Clicking.click(clickContinue);
        }
        if (!wait)
            return true;
        else
            return Timing.waitCondition(textChanged(clickContinue.getParent(), getMessage(clickContinue.getParent())),
                    General.random(1000, 2000));
    }

    /**
     * Gets the name of the person currently talking. Whether that be the name of the NPC or the name of our player
     *
     * @return String containing the name of the person currently talking, or null if not found.
     */
    public static String getName() {
        if (Interfaces.isInterfaceSubstantiated(PLAYER_TALKING_INTERFACE_WINDOW)) {
            RSInterfaceChild nameInterface = Interfaces.get(PLAYER_TALKING_INTERFACE_WINDOW, NAME_CHILD);
            if (nameInterface != null) {
                return nameInterface.getText();
            }
        } else if (Interfaces.isInterfaceSubstantiated(NPC_TALKING_INTERFACE_WINDOW)) {
            RSInterfaceChild nameInterface = Interfaces.get(NPC_TALKING_INTERFACE_WINDOW, NAME_CHILD);
            if (nameInterface != null) {
                return nameInterface.getText();
            }
        }
        return null;
    }

    /**
     * Gets whether or not we are currently in chat with an NPC.
     * Used to ensure that we don't end the chat prematurely during the times between answering an option dialog
     * and heading back to the click continue dialog.
     *
     * @param numberOfTries int representing the number of times we want to try and confirm we are in a chat.
     * @return boolean indicating whether or not we are currently in shat
     */
    public static boolean inChat(int numberOfTries) {
        if (Login.getLoginState() != Login.STATE.INGAME) {
            return false;
        }
        RSInterfaceChild hiddenInterface = Interfaces.get(CHATBOX_INTERFACE, IN_CHAT_INTERFACE);
        while (numberOfTries > 0) {
            if (hiddenInterface != null && !hiddenInterface.isHidden())
                return true;
            General.sleep(300, 600);
            numberOfTries--;
        }
        return false;
    }

    /**
     * Finds the click continue interface inside the master interface associated with the id
     *
     * @param interfaceMasterID int representing the id of the master interface to look within
     * @return RSInterface object representing the click continue interface
     */
    public static RSInterface findClickContinue(int interfaceMasterID) {
        return findClickContinue(Interfaces.get(interfaceMasterID));
    }

    /**
     * Finds the click continue interface inside the passed master
     *
     * @param masterInterface RSInterfaceMaster object to look for a click continue inside
     * @return
     */
    public static RSInterface findClickContinue(RSInterfaceMaster masterInterface) {
        if (masterInterface != null) {
            RSInterfaceChild[] children = masterInterface.getChildren();
            if (children == null)
                return null;
            for (RSInterfaceChild child : children) {
                if (child.getConfig() == 1)
                    return child;
            }
        }
        return null;
    }

    /**
     * Searches through the found master interface to find the message that is being displayed
     *
     * @param interfaceMasterID int representing the id of the master interface to look within
     * @return RSInterface containing the found message
     */
    public static RSInterface findMessage(int interfaceMasterID) {
        return findMessage(Interfaces.get(interfaceMasterID));
    }

    /**
     * Searches through the master interface to find the message being displayed
     *
     * @param masterInterface RSInterface representing the interface to look within
     * @return RSInterface containing the displayed message, or null if one is not found.
     */
    public static RSInterface findMessage(RSInterface masterInterface) {
        if (masterInterface == null) {
            return null;
        }
        RSInterface[] children = masterInterface.getChildren();
        if (children == null) {
            return null;
        }
        for (RSInterface child : children) {
            if (child.getTextColour() == 0 && child.getFontID() != -1) {
                return child;
            }
        }
        return null;
    }

    /**
     * Finds a child interface within the master interface that contains the click here to continue text
     *
     * @param masterInterfaceId int representing the id of the master interface to look within
     * @return RSInterface representing the click continue interface, or null if interface is not found.
     */
    public static RSInterface findClickContinueChild(int masterInterfaceId) {
        return findClickContinueChild(Interfaces.get(masterInterfaceId));
    }

    /**
     * Finds a child interface within the master interface that contains the click here to continue text
     *
     * @param masterInterface RSInterfaceMaster object representing the interface to look within
     * @return RSInterface representing the click continue interface, or null if interface is not found.
     */
    private static RSInterface findClickContinueChild(RSInterfaceMaster masterInterface) {
        final String TEXT_TO_FIND = "Click here to continue";
        if (masterInterface == null) {
            return null;
        }
        RSInterfaceChild[] children = masterInterface.getChildren();
        if (children == null) {
            return null;
        }
        for (RSInterfaceChild child : children) {
            if (masterInterface.getIndex() == ITEM_ACTION_INTERFACE_WINDOW) {
                RSInterfaceComponent[] components = child.getChildren();
                if (components == null) {
                    continue;
                }
                for (RSInterfaceComponent component : components) {
                    if (!component.getText().isEmpty() && component.getText().contains(TEXT_TO_FIND)) {
                        return component;
                    }
                }
            } else if (!child.getText().isEmpty() && child.getText().contains(TEXT_TO_FIND)) {
                return child;
            }
        }
        return null;
    }

    /**
     * Holds spacebar until the clickContinue interface is no longer present
     */
    public static void holdSpaceForChat() {
        Keyboard.holdKey(' ', KeyEvent.VK_SPACE, () -> {
            General.sleep(200, 400);
            return !clickContinuePresent();
        });
    }

    /**
     * Gets an estimated read time to read all the messages provided. Used to delay clicking okay on messages.
     *
     * @param messages String[] containing the messages to get the read time for.
     * @return long containing the total read time for all messages
     */
    public static long getReadWaitTime(String... messages) {
        if (messages == null)
            return 1000;
        long readingTime = 0;
        for (String message : messages) {
            if (message == null) {
                continue;
            }
            int words = 0;
            words += message.split(" ").length;
            readingTime += (long) (words * 1000 *
                    General.randomSD(MIN_SECONDS_PER_WORD, MAX_SECONDS_PER_WORD,
                            MEAN_SECONDS_PER_WORD, STD_DEV_SECONDS_PER_WORD));
        }
        return readingTime;
    }

    /**
     * Gets a BooleanSupplier which will return true if the interface passed is not being drawn.
     *
     * @param rsInterface RSInterface object to check drawing of
     * @return BooleanSupplier representing a check for the drawn status of the interface
     */
    private static BooleanSupplier isNotBeingDrawn(RSInterface rsInterface) {
        return () -> {
            General.sleep(200, 400);
            return !rsInterface.isBeingDrawn();
        };
    }

    /**
     * Gets a BooleanSupplier which will return true if the text in the interface has changed
     *
     * @param rsInterface RSInterface object to check text of
     * @return BooleanSupplier representing a check for the change status of the text
     */
    private static BooleanSupplier textChanged(RSInterface rsInterface, String startText) {
        return () -> {
            General.sleep(200, 400);
            return !rsInterface.isBeingDrawn() || !rsInterface.getText().equals(startText);
        };
    }

}
