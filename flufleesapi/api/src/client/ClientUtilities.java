package client;

import org.tribot.api.General;
import org.tribot.api2007.Game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Fluffee on 03/11/17.
 */
public class ClientUtilities {

    public static final int FILE_MENU = 0, SCRIPT_MENU = 1, CLIENT_MENU = 2, VIEW_MENU = 3,
            DEBUG_MENU = 4, TOOLS_MENU = 5, HELP_MENU = 6;

    /**
     * Replaces TRiBot Cell Renderer with a custom one to allow for the printing of colours.
     *
     * @param useClientDebug      Boolean flag to indicate if you are replacing the client debug cell renderer or the system debug cell renderer
     * @param replacementRenderer Replacement ListCellRenderer.
     */
    public static void setCellRenderer(boolean useClientDebug, ListCellRenderer replacementRenderer) {
        JList list = getJList(useClientDebug);
        if (list == null) {
            General.println("Error, could not find TRiBot Frame. Cannot set cell renderer.");
            return;
        }
        list.setCellRenderer(replacementRenderer);
    }

    /**
     * Gets the JList that the TRiBot debug uses.
     *
     * @param useClientDebug boolean indicating whether or not to get the JList from the client debug.
     *                       If false, will get the JList from the bot debug.
     * @return JList object representing the debug, or null if it's not found
     */
    public static JList getJList(boolean useClientDebug) {
        return getJList(useClientDebug, findTRiBotFrame());
    }

    /**
     * Gets the JList that the TRiBot debug uses.
     *
     * @param useClientDebug boolean indicating whether or not to get the JList from the client debug.
     *                       If false, will get the JList from the bot debug.
     * @param tribotFrame JFrame object representing the TRiBot window
     * @return JList object representing the debug, or null if it's not found
     */
    public static JList getJList(boolean useClientDebug, JFrame tribotFrame) {
        int debugValue = useClientDebug ? 1 : 0;
        if (tribotFrame == null) {
            General.println("Error, could not find TRiBot Frame. Cannot getInstance list model.");
            return null;
        }
        try {
            JSplitPane tribotSplit = (JSplitPane) tribotFrame.getContentPane().getComponent(0);
            JTabbedPane debug = (JTabbedPane) tribotSplit.getComponent(0);
            JScrollPane clientDebug = (JScrollPane) debug.getComponent(debugValue);
            JViewport viewport = (JViewport) clientDebug.getComponent(0);
            return (JList) viewport.getComponent(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            General.println("Cannot getInstance list model.");
            return null;
        }
    }

    /**
     * Gets default TRiBot list cell renderer
     *
     * @param useClientDebug boolean flag to indicate if you are getting the client debug cell renderer or
     *                       the system debug cell renderer
     * @return Default cell renderer or null if not able to be found.
     */
    public static ListCellRenderer getCellRender(boolean useClientDebug) {
        JList list = getJList(useClientDebug);
        if (list == null) {
            General.println("Error, could not find TRiBot Frame. Cannot getInstance cell renderer.");
            return null;
        }
        return list.getCellRenderer();
    }

    /**
     * Resizes split pane to the percentage passed in the arguments.
     *
     * @param gameWindowPercentage Percentage to resize split pane to, must be between 0 and 1, with 1 being no debug,
     *                             and 0 being no rs window.
     * @return Return true if successful, false otherwise.
     */
    public static boolean resizeSplitPane(double gameWindowPercentage) {
        JFrame tribotFrame = findTRiBotFrame();
        if (tribotFrame == null) {
            General.println("Error, could not find TRiBot Frame. Cannot resize debug.");
            return false;
        }
        if (gameWindowPercentage < 0 || gameWindowPercentage > 1) {
            General.println("Error, percentage argument is not between 0 and 1.");
        }
        JSplitPane tribotSplit = (JSplitPane) tribotFrame.getContentPane().getComponent(0);
        tribotSplit.setDividerLocation(gameWindowPercentage);
        return true;
    }

    /**
     * Resizes split pane by moving divider to location specified in arguments.
     *
     * @param dividerLocation Location to move the divider to.
     * @return Return true if successful, false otherwise.
     */
    public static boolean resizeSplitPane(int dividerLocation) {
        JFrame tribotFrame = findTRiBotFrame();
        if (tribotFrame == null) {
            General.println("Error, could not find TRiBot Frame. Cannot resize debug.");
            return false;
        }
        if (dividerLocation < 0) {
            General.println("Error, percentage argument is less than 0.");
        }
        JSplitPane tribotSplit = (JSplitPane) tribotFrame.getContentPane().getComponent(0);
        tribotSplit.setDividerLocation(dividerLocation);
        return true;
    }

    /**
     * Gets the divider location of the split pane.
     *
     * @return Returns divider location of split pane, or -1 if an error occurs.
     */
    public static int getSplitPaneDividerLocation() {
        JFrame tribotFrame = findTRiBotFrame();
        if (tribotFrame == null) {
            General.println("Error, could not find TRiBot Frame. Cannot resize debug.");
            return -1;
        }
        JSplitPane tribotSplit = (JSplitPane) tribotFrame.getContentPane().getComponent(0);
        return tribotSplit.getDividerLocation();
    }

    /**
     * Blocks user input by clicking the option in the dropdown menu
     *
     * @return True if user input was successfully blocked, false otherwise.
     */
    public static boolean blockUserInput() {
        JMenuItem blockUserInput = getItem(CLIENT_MENU, "Block User Input");
        if (blockUserInput == null) {
            General.println("Error: Could not getInstance button.");
            return false;
        }
        blockUserInput.doClick();
        return true;
    }

    /**
     * Gets the JMenu Object representing the desired menu
     *
     * @param menuNumber int containing the index of the menu you want to grab.
     * @return JMenu object representing the menu at the index
     */
    public static JMenu getMenu(int menuNumber) {
        return getMenu(menuNumber, findTRiBotFrame());
    }

    /**
     * Gets the JMenu Object representing the desired menu
     *
     * @param menuNumber int containing the index of the menu you want to grab.
     * @param tribotFrame JFrame object representing the TRiBot window
     * @return JMenu object representing the menu at the index
     */
    public static JMenu getMenu(int menuNumber, JFrame tribotFrame) {
        JMenuBar tribotMenuBar = tribotFrame.getJMenuBar();
        if (tribotMenuBar == null)
            return null;
        return tribotMenuBar.getMenu(menuNumber);
    }

    /**
     * Gets the menu item in the corresponding menu that has the passed text.
     *
     * @param menuNumber int representing the index of the menu you want to grab.
     * @param itemText String containing the text we want to search the menu items for
     * @return JMenuItem object representing the menu item we found, or null if it's not found.
     */
    public static JMenuItem getItem(int menuNumber, String itemText) {
        return getItem(menuNumber, itemText, findTRiBotFrame());
    }

    /**
     * Gets the menu item in the corresponding menu that has the passed text.
     *
     * @param menuNumber int representing the index of the menu you want to grab.
     * @param itemText String containing the text we want to search the menu items for
     * @param tribotFrame JFrame object representing the TRiBot window
     * @return JMenuItem object representing the menu item we found, or null if it's not found.
     */
    public static JMenuItem getItem(int menuNumber, String itemText, JFrame tribotFrame) {
        if (tribotFrame == null) {
            return null;
        }
        JMenu menu = getMenu(menuNumber, tribotFrame);
        if (menu == null)
            return null;
        for (int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem item = menu.getItem(i);
            if (item.getText().equals(itemText))
                return item;
        }
        return null;
    }

    /**
     * Grabs the TRiBot JFrame by the name of the window.
     *
     * @return TRiBot JFrame or null if not found.
     */
    public static JFrame findTRiBotFrame() {
        Frame[] frames = JFrame.getFrames();
        for (Frame tempFrame : frames) {
            if (tempFrame.getTitle().contains("TRiBot Old-School - The Desktop Botting Solution")) {
                return (JFrame) tempFrame;
            }
            General.sleep(100);
        }
        General.println("Error, could not find TRiBot Frame.");
        return null;
    }

    /**
     * Mutes all sound in the TRiBot client.
     */
    public static void muteSound() {
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : infos) {
            Mixer mixer = AudioSystem.getMixer(info);
            for (Line line : mixer.getSourceLines()) {
                BooleanControl bc = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
                if (bc != null) {
                    bc.setValue(true);
                }
            }
        }
    }

    /**
     * Unmute sound in the TRiBot client.
     */
    public static void unmuteSound() {
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : infos) {
            Mixer mixer = AudioSystem.getMixer(info);
            for (Line line : mixer.getSourceLines()) {
                BooleanControl bc = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
                if (bc != null) {
                    bc.setValue(false);
                }
            }
        }
    }

}
