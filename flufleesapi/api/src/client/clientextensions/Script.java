package client.clientextensions;

import client.ClientUtilities;
import org.tribot.api.General;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import scripting.antiban.AntiBanSingleton;
import scripting.painting.scriptpaint.ScriptPaint;
import scripting.swingcomponents.gui.standard.AbstractGui;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.List;

public abstract class Script extends org.tribot.script.Script implements Starting, Ending, Painting {
    protected boolean hasArguments = false, pauseWhileGUIOpen = true;
    protected AbstractGui gui = null;
    private int paintDelayMS = 600;
    private long lastPaintTime = 0;
    private List<AbstractMap.SimpleEntry<String, String>> paintFields;
    private ScriptPaint scriptPaint;

    /**
     * Gets whether or not a GUI should be shown
     *
     * @return True if the GUI should be shown, false otherwise.
     */
    private boolean shouldShowGui() {
        return this.gui != null && !this.hasArguments;
    }

    /**
     * Launches the script GUI via invokeAndWait.
     */
    private void launchGui() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                this.gui.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the list of paint fields for the script to paint. However, the field contents are only recalculated if
     * the paintDelayMS timeout has been exceeded. As the paint thread is called a ton, adding this delay ensures we
     * don't waste cycles recalculating the contents.
     *
     * TODO: Look into multithreading this. That way we can never bog down the paint thread with script calcs.
     *
     * @return List<AbstractMap.SimpleEntry<String, String>> List of map entries, each with a field name and value.
     */
    private List<AbstractMap.SimpleEntry<String, String>> getCachedPaintFields() {
        if (System.currentTimeMillis() > (lastPaintTime + paintDelayMS)) {
            paintFields = getPaintFields();
            lastPaintTime = System.currentTimeMillis();
        }
        return paintFields;
    }

    /**
     * TRiBot entry point for a script. This method is what kicks off all script logic.
     */
    @Override
    public void run() {
        this.preScriptTasks();

        if (this.shouldShowGui()) {
            this.launchGui();
        }
        while (this.gui != null && this.gui.isVisible() && this.pauseWhileGUIOpen) {
            General.sleep(500);
        }

        this.onGuiClosed();
        this.mainLoop();
    }

    /**
     * Sets up some default settings we want to apply to all scripts.
     *
     * Ensure super() is called if this method is being overridden.
     */
    @Override
    public void onStart() {
        ClientUtilities.muteSound();
        AntiBanSingleton.get().setPrintDebug(false);
        ThreadSettings.get().setClickingAPIUseDynamic(true);
    }

    /**
     * Closes the GUI on script end, if it's open.
     *
     * Ensure super() is called if this method is being overridden.
     */
    @Override
    public void onEnd() {
        if (this.gui != null && this.gui.isVisible()) {
            this.gui.dispose();
        }
    }

    /**
     * Default painting method, to auto cache paint fields for the script.
     *
     * @param graphics Graphics object passed from the TRiBot calling method
     */
    @Override
    public void onPaint(Graphics graphics) {
        if (this.scriptPaint != null) {
            this.scriptPaint.paint((Graphics2D) graphics, this.getRunningTime(), getCachedPaintFields());
        }
    }

    /**
     * Returns if this script is being run locally or on the repository.
     *
     * @return True if running locally, false otherwise.
     */
    public boolean isRunningLocal() {
        return this.getRepoID() == -1;
    }

    /**
     * Sets the value of the script paint.
     *
     * @param scriptPaint ScriptPaint object for the script to use.
     */
    public void setScriptPaint(ScriptPaint scriptPaint) {
        this.scriptPaint = scriptPaint;
    }


    /**
     * Sets the cached value of the gui for the script.
     *
     * @param gui AbstractGUi object for the script to use
     */
    public void setGui(AbstractGui gui) {
        this.gui = gui;
    }

    /**
     * Stub method
     *
     * Called when the GUI of the script is closed, if no GUI is present the method is not ran.
     */
    public void onGuiClosed() {

    }

    /**
     * The main entry point for the script, this function is only called once, so it should contain a loop
     */
    public abstract void mainLoop();

    /**
     * Gets the paint fields that the ScriptPaint object should paint to the screen.
     *
     * @return List<AbstractMap.SimpleEntry<String, String>> List of map entries, each with a field name and value.
     */
    public abstract List<AbstractMap.SimpleEntry<String, String>> getPaintFields();

    /**
     * An extendable entry point for sub classes to run methods prior to any logic in the @code{run} method being
     * executed. This allows sub classes to run code while avoiding any race conditions.
     */
    public abstract void preScriptTasks();
}
