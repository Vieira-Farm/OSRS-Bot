package scripts.client.clientextensions;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripts.scripting.entityselector.finders.prefabs.InterfaceEntity;
import scripts.utils.ConditionUtilities;

public class Options extends org.tribot.api2007.Options {

    private static int OPTIONS_MASTER_INTERFACE = 261;
    private static int KEYBINDS_INTERFACE = 121,
            KEYBINDS_CLOSE_MASTER = 1,
            KEYBINDS_CLOSE_COMPONENT = 11,
            ESCAPE_CLOSE_BUTTON = 103,
            ESCAPE_CLOSE_VARBIT = 4681;

    /**
     * Sets whether or not using escape to close interfaces should be enabled.
     *
     * @param enabled True if we want to enable escape close, false otherwise.
     * @return True if escape close was successfully enabled, false otherwise.
     */
    public static boolean setEscapeClose(boolean enabled) {
        if (Game.getVarBitValue(ESCAPE_CLOSE_VARBIT) == 1 == enabled) {
            return true;
        } else if (!Options.openTab(TABS.CONTROLS) || !openKeybinds()) {
            return false;
        }

        RSInterface keybindOptions = Interfaces.get(KEYBINDS_INTERFACE);
        if (keybindOptions == null) {
            return false;
        }

        RSInterface escapeClose = keybindOptions.getChild(ESCAPE_CLOSE_BUTTON);
        if (escapeClose == null) {
            return false;
        }

        escapeClose.click("Select");
        RSInterface closeButton = Interfaces.get(KEYBINDS_INTERFACE, KEYBINDS_CLOSE_MASTER, KEYBINDS_CLOSE_COMPONENT);
        if (closeButton != null) {
            closeButton.click("Close");
        }
        return Game.getVarBitValue(ESCAPE_CLOSE_VARBIT) == 1 == enabled;
    }

    /**
     * Mutes all in game sounds to 0.
     *
     * @return True if the sounds were successfully muted, false otherwise
     */
    public static boolean muteSounds() {
        return Options.openTab(TABS.AUDIO) &&
                setSound("Adjust Music Volume", 1) &&
                setSound("Adjust Sound Effect Volume", 1) &&
                setSound("Adjust Area Sound Effect Volume", 1);
    }

    /**
     * Sets the desired sound option to the desired level
     *
     * @param buttonText String containing the text indicating the sound level to adjust
     * @param desiredLevel int containing the desired level we want the sound set to
     * @return True if the sound level was set successfully, false otherwise.
     */
    private static boolean setSound(String buttonText, int desiredLevel) {
        final int BASE_TEXTURE_ID = 686;
        RSInterface[] adjustmentSliders = new InterfaceEntity().inMaster(OPTIONS_MASTER_INTERFACE)
                .actionEquals(buttonText).getResults();
        if (adjustmentSliders.length != 5) {
            return false;
        } else if (adjustmentSliders[desiredLevel-1].getTextureID() == BASE_TEXTURE_ID + desiredLevel) {
            return true;
        } else {
            adjustmentSliders[desiredLevel-1].click(buttonText);
            return Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return adjustmentSliders[desiredLevel-1].getTextureID() == BASE_TEXTURE_ID + desiredLevel;
            }, General.random(1500, 2000));
        }
    }

    /**
     * Opens the keybinds window. This is the window that allows you to set custom F keys, etc.
     *
     * @return True if the keybinds window was opened, false otherwise.
     */
    public static boolean openKeybinds() {

        final int KEYBIND_TEXTURE_ID = 761; //Used to narrow down interface search a bit further.

        InterfaceEntity keybindEntity = new InterfaceEntity().inMaster(OPTIONS_MASTER_INTERFACE)
                .textureIdEquals(KEYBIND_TEXTURE_ID)
                .actionEquals("Keybinding");
        RSInterface keybindings = keybindEntity.getFirstResult();
        if (keybindings == null) {
            return false;
        }
        keybindings.click("Keybinding");
        return Timing.waitCondition(ConditionUtilities.interfaceSubstantiated(KEYBINDS_INTERFACE), General.random(1500, 3000));
    }

}
