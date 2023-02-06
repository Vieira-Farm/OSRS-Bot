package scripts.scripting.listeners.clickContinueListener;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSInterfaceChild;
import scripts.client.clientextensions.NPCChat;
import scripts.utils.ConditionUtilities;

import java.util.concurrent.atomic.AtomicBoolean;

public class ClickContinueListener implements Runnable {

    private Thread thread;
    private boolean persistentOnly, closeWorldMap;
    private final int WORLD_MAP_INTERFACE_MASTER = 595, WORLD_MAP_CLOSE_CHILD = 37;
    private AtomicBoolean runThread = new AtomicBoolean(true);

    public static class ClickContinueBuilder {
        private boolean persistentOnly = false, closeWorldMap = false;

        public ClickContinueBuilder withPersisntentOnly() {
            this.persistentOnly = true;
            return this;
        }

        public ClickContinueBuilder withCloseWorldMap() {
            this.closeWorldMap = true;
            return this;
        }

        public ClickContinueListener build() {
            return new ClickContinueListener(this.persistentOnly, this.closeWorldMap);
        }
    }

    private ClickContinueListener(boolean persistentOnly, boolean closeWorldMap) {
        this.persistentOnly = persistentOnly;
        this.closeWorldMap = closeWorldMap;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (runThread.get()) {
            if (persistentOnly) {
                persistentLoop();
            } else {
                generalLoop();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void persistentLoop() {
        while (Login.getLoginState() != Login.STATE.INGAME && !NPCChat.clickContinuePersistentPresent() && !worldMapOpen()) {
            General.sleep(500);
        }
        if (worldMapOpen()) {
            closeWorldMap();
        } else if (NPCChat.clickContinuePersistentPresent()) {
            NPCChat.clickContinue(false, false);
        }
    }

    public void generalLoop() {
        while (Login.getLoginState() != Login.STATE.INGAME && !NPCChat.clickContinuePresentNoChat() && !worldMapOpen()) {
            General.sleep(500);
        }
        if (worldMapOpen()) {
            closeWorldMap();
        } else if (NPCChat.clickContinuePresentNoChat()) {
            NPCChat.clickContinue(false, false);
        }
    }

    public boolean worldMapOpen() {
        if (!closeWorldMap) {
            return false;
        } else {
            return Interfaces.isInterfaceSubstantiated(595, 37);
        }
    }

    public boolean closeWorldMap() {
        RSInterfaceChild closeButton = Interfaces.get(WORLD_MAP_INTERFACE_MASTER, WORLD_MAP_CLOSE_CHILD);
        if (closeButton != null && closeButton.isClickable()) {
            closeButton.click("Close");
            return Timing.waitCondition(ConditionUtilities.interfaceSubstantiated(WORLD_MAP_INTERFACE_MASTER), General.random(3000, 5000));
        }
        return false;
    }

    public void terminate() {
        runThread.set(true);
    }
}
