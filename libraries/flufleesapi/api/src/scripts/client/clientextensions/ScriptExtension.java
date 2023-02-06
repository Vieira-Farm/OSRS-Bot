package scripts.client.clientextensions;

import scripts.client.ClientUtilities;
import org.tribot.api.General;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.listeners.inventoryListener.InventoryListener;
import scripts.scripting.listeners.inventoryListener.InventoryObserver;
import scripts.scripting.painting.scriptpaint.ScriptPaint;
import scripts.scripting.swingcomponents.gui.standard.AbstractGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

public abstract class ScriptExtension extends Script implements Painting, Arguments,
                                                                EventBlockingOverride, Starting, Ending,
                                                                MessageListening07, InventoryListener {

    protected boolean hasArguments = false, pauseWhileGUIOpen = true;
    protected final ScriptManifest MANIFEST = this.getClass().getAnnotation(ScriptManifest.class);
    protected BufferedImage icon = null;
    protected AbstractGui gui = null;
    protected InventoryObserver observer = null;
    protected int paintDelayMS = 500;
    protected long lastPaintTime = 0;
    protected List<AbstractMap.SimpleEntry<String, String>> paintFields;

    public Thread thread;

    private boolean quitting = false;
    private boolean usingInventoryObserver = false;

    /**
     * Returns if the script is quitting. IE, finishing the last loop and disposing of any objects.
     * @return
     */
    public boolean isQuitting() {
        return this.quitting;
    }

    /**
     * Sets if the script is quitting. IE, finishing the last loop and disposing of any objects.
     */
    public void setQuitting(boolean qutting) {
        this.quitting = qutting;
    }

    /**
     * Returns if this script is being run locally or on the repository.
     * @return
     */
    public boolean isLocal() {
        return this.getRepoID() == -1;
    }

    /**
     * This method is called once when the script starts and we are logged ingame, just before the paint/gui shows.
     */
    public void onScriptStart() {
    }

    /**
     * This method is called when the GUI closes when starting the script.
     * This is never called if you do not have a GUI.
     */
    public void onGUIClosed() {}

    public abstract void mainLoop();

    /**
     * Return a JavaFX gui, it will automatically be shown and the script will wait until it closes.
     * Return null if you don't want a GUI.
     *
     * @return
     */
    public abstract AbstractGui getGUI();


    /**
     * Checks if an inventory observer is running.
     *
     * @return
     */
    public boolean isUsingInventoryObserver() {
        return this.usingInventoryObserver;
    }

    /**
     * Sets if we want to use a inventory observer or not. This will enable the #inventoryItemAdded and #inventoryItemRemoved functions to getInstance called.
     * @param state
     */
    public void setInventoryObserverState(boolean state) {

        if (state) {
            if (this.observer == null) {
                this.observer = new InventoryObserver();
                this.observer.addListener(this);
            }
            this.observer.start();
            this.usingInventoryObserver = true;

        } else if (this.observer != null) {
            this.observer.end();

            this.usingInventoryObserver = false;
        }
    }

    /**
     * This is the entry point of a tribot script. You shouldn't have to change anything here.
     */
    @Override
    public void run() {
        this.thread = Thread.currentThread();
        try {
            SwingUtilities.invokeAndWait(() -> {
                this.gui = this.getGUI();
                if (this.gui != null && !this.hasArguments) {
                    this.gui.setVisible(true);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        this.onScriptStart();

        if (!this.hasArguments && this.gui != null) {
            while (this.gui.isVisible() && this.pauseWhileGUIOpen) {
                General.sleep(300);
            }

            if (this.isQuitting()) { // a gui is able to quit the script.
                return;
            }
        }

        this.onGUIClosed();
        this.mainLoop();
    }

    @Override
    public void onStart() {
        ClientUtilities.muteSound();
        AntiBanSingleton.get().setPrintDebug(false);
        ThreadSettings.get().setClickingAPIUseDynamic(true);
    }

    /**
     * This method is called once when the script stops completely. (ie. user clicked the stop button or the script queue ran out.)
     * Please call super() when overriding this method.
     */
    @Override
    public void onEnd() {
        this.setInventoryObserverState(false);
        if (this.gui != null) {
            this.gui.dispose();
        }
    }

    public EventBlockingOverride.OVERRIDE_RETURN overrideMouseEvent(MouseEvent e) {
        return EventBlockingOverride.OVERRIDE_RETURN.PROCESS;
    }

    public List<AbstractMap.SimpleEntry<String, String>> cachePaintFields() {
        if (System.currentTimeMillis() > lastPaintTime + paintDelayMS) {
            paintFields = getPaintFields();
            lastPaintTime = System.currentTimeMillis();
        }
        return paintFields;
    }

    @Override
    public void onPaint(Graphics graphics) {
        getScriptPaint().paint((Graphics2D) graphics, this.getRunningTime(), cachePaintFields());
    }

    public abstract ScriptPaint getScriptPaint();
    public abstract List<AbstractMap.SimpleEntry<String, String>> getPaintFields();

    public void mouseReleased(Point point, int button, boolean is_bot) {}
    public void mouseDragged(Point point, int button, boolean is_bot) {}
    public OVERRIDE_RETURN overrideKeyEvent(KeyEvent e) {return OVERRIDE_RETURN.SEND;}
    public void passArguments(HashMap<String, String> hashMap) {}
    public void keyTyped(int keycode, boolean is_bot) {}
    public void keyReleased(int keycode, boolean is_bot) {}
    public void keyPressed(int keycode, boolean is_bot) {}
    public void serverMessageReceived(String s) {}
    public void clanMessageReceived(String name, String msg) {}
    public void playerMessageReceived(String name, String msg) {}
    public void personalMessageReceived(String name, String msg) {}
    public void duelRequestReceived(String name, String msg) {}
    public void tradeRequestReceived(String s) {}

}


