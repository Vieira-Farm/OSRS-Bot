package scripts.utils;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Camera;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.*;
import scripts.client.clientextensions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Fluffee on 16/05/2017.
 */
@SuppressWarnings("unused")
public class Utilities {

    /**
     * Generates a random tile within the radius of the passed tile.
     * @param tile Starting tile
     * @param radius Radius to move within
     * @return New tile within the radius of the starting tile.
     */
    public static RSTile getRandomizedTile(RSTile tile, int radius) {
        return new RSTile(tile.getX() + General.random((radius*-1), radius), tile.getY() + General.random((radius*-1), radius));
    }

    public static int addWaitTime(int newWaitTime, int numberOfWaitTimes, int oldAverage) {
        return (((numberOfWaitTimes - 1) * oldAverage) + newWaitTime) / numberOfWaitTimes;
    }

    /**
     * Generates a long value to use a seed from a base String.
     *
     * @param s String to turn into a long
     * @return long value based on the String.
     */
    public static long stringToSeed(String s) {
        if (s == null) {
            return 0;
        }
        long hash = 0;
        for (char character : s.toCharArray()) {
            hash = 31L * hash + character;
        }
        return hash;
    }

    /**
     * Returns the amount of an item you've obtained per hour.
     *
     * @param numberOfItems         Number of data obtained total.
     * @param runtimeInMilliseconds Script runtime in milliseconds
     * @return Number of data obtained per hour.
     */
    public static int getAmountPerHour(int numberOfItems, long runtimeInMilliseconds) {
        return (int) (numberOfItems * ((double) 3600000 / runtimeInMilliseconds));
    }

    /**
     * Joins a friends chat, based on the String passed as an argument. Doesn't check for confirmation in the game chat to confirm joining.
     *
     * @param friendsChat - Friends chat to join, as String
     * @return True if Friends Chat was supposedly joined, false if it hits major issues.
     */
    public static boolean joinFriendsChat(String friendsChat) {
        if (Login.getLoginState() != Login.STATE.INGAME) {
            return false;
        }
        if (GameTab.getOpen() != GameTab.TABS.CLAN) {
            Utilities.openGameTab(GameTab.TABS.CLAN);
        }
        RSInterfaceChild joinButton = Interfaces.get(7, 17);
        if (joinButton != null) {
            if (joinButton.getText().equals("Leave Chat")) {
                joinButton.click();
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return Interfaces.get(7, 17).getText().equals("Join Chat");
                }, General.random(3000, 5000));
            }
            joinButton.click();
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return !Interfaces.get(162, 32).isHidden();
            }, General.random(3000, 5000));

            if (!Interfaces.get(162, 32).isHidden()) {
                Keyboard.typeSend(friendsChat);
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return Interfaces.get(7, 17).getText().equals("Leave Chat");
                }, General.random(3000, 5000));
                return true;
            }
        }
        return false;
    }

    /**
     * Toggles Shift Dropping via the settings interface
     *
     * @param enable - True if enabling shift dropping, false if disabling
     * @return - True if successfully changed state of shfit dropping, false otherwise
     */
    public static boolean shiftDropping(boolean enable) {
        if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
            Utilities.openGameTab(GameTab.TABS.OPTIONS);
        }
        RSInterfaceComponent controls = Interfaces.get(261, 1).getChild(6);
        if (controls != null) {
            if (controls.getTextureID() != 762) {
                controls.click();
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return controls.getTextureID() != Interfaces.get(261, 1).getChild(6).getTextureID();
                }, General.random(1000, 1500));
            }
            RSInterfaceChild toggleShiftDropping = Interfaces.get(261, 67);
            if (!toggleShiftDropping.isHidden()) {
                if (enable && toggleShiftDropping.getTextureID() == 761) {
                    toggleShiftDropping.click();
                } else if (!enable && toggleShiftDropping.getTextureID() == 762) {
                    toggleShiftDropping.click();
                }
                Timing.waitCondition(() -> toggleShiftDropping.getTextureID() != Interfaces.get(261, 67).getTextureID(), General.random(1000, 1500));
                return toggleShiftDropping.getTextureID() != Interfaces.get(261, 67).getTextureID();
            }
        }
        return false;
    }

    /**
     * Toggles Escape Close via the settings interface
     *
     * @return - True if successfully changed state of escape close, false otherwise
     */
    public static boolean escapeClose() {
        if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
            Utilities.openGameTab(GameTab.TABS.OPTIONS);
        }
        RSInterface controls = Interfaces.get(261, 1,6);
        if (controls != null && !controls.isHidden() && controls.isBeingDrawn() && controls.getTextureID() != 762) {
            controls.click();
            Timing.waitCondition(() -> {
                General.sleep(300);
                return controls.getTextureID() == 762;
            }, General.random(1000, 1500));
        }
        RSInterfaceChild keybindings = Interfaces.get(261, 65);
        if (!Interfaces.isInterfaceSubstantiated(121) && keybindings != null && !keybindings.isHidden() && keybindings.isBeingDrawn()) {
            keybindings.click();
            Timing.waitCondition(() -> {
                General.sleep(300);
                return Interfaces.isInterfaceSubstantiated(121);
            }, General.random(1000, 1500));
        }
        RSInterface keybindOptions = Interfaces.get(121);
        if (Interfaces.isInterfaceSubstantiated(121) && keybindOptions != null && keybindOptions.isBeingDrawn()) {
            RSInterface escapeClose = keybindOptions.getChild(103);
            if (escapeClose != null) {
                escapeClose.click();
                Timing.waitCondition(() -> {
                    General.sleep(300);
                    return Game.getVarBitValue(4681) == 1;
                }, General.random(3000, 5000));
            }
            keybindOptions.getChild(1).getChild(11).click();
        }
        return Game.getVarBitValue(4681) == 1;
}

    public static boolean checkInventory(String itemName) {
        RSItem[] item = Inventory.find(itemName);
        if (item.length > 1) {
            return true;
        } else if (item.length > 0) {
            return item[0].getDefinition().isNoted();
        } else {
            return false;
        }
    }

    private RSObject[] getObjectOnPlane(final int plane, final int distance) {
        return Objects.find(distance, t -> t.getPosition().getPlane() == plane);
    }

    public static boolean interactObject(String objectName, String objectAction, int distance) {
        RSObject[] objects = Objects.findNearest(distance, (Filters.Objects.nameEquals(objectName)));
        if (objects.length == 0) {
            return false;
        }
        if (arrayContains(objectAction, objects[0].getDefinition().getActions())) {
            if (!objects[0].isOnScreen()) {
                Camera.turnToTile(objects[0]);
            }
            objects[0].hover();
            if (DynamicClicking.clickRSObject(objects[0], objectAction)) {
                General.sleep(1000);
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return !Player.isMoving();
                }, General.random(10000, 15000));
                General.sleep(1000, 2000);
                return true;
            }
        }
        return false;
    }

    /**
     * Toggles roofs, by clicking the interfaces.
     *
     * @param turnOff - True if turning off roofs, false if turning on
     * @return - Return true if successfully clicked button, false otherwise.
     */
    public static boolean toggleRoofs(boolean turnOff) {
        if (Login.getLoginState() != Login.STATE.INGAME) {
            return false;
        }
        if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
            Utilities.openGameTab(GameTab.TABS.OPTIONS);
        }

        RSInterfaceComponent display = Interfaces.get(261, 1).getChild(0);
        if (display != null) {
            if (display.getTextureID() != 762) {
                display.click();
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return display.getTextureID() != Interfaces.get(261, 1).getChild(6).getTextureID();
                }, General.random(1000, 1500));
            }
        }
        RSInterfaceChild advancedOptions = Interfaces.get(261, 24);
        if (advancedOptions != null) {
            advancedOptions.click();
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return Interfaces.get(60) != null;
            }, General.random(3000, 5000));

            RSInterfaceChild toggleRoofButton = Interfaces.get(60, 14);
            RSInterfaceComponent closeButton = Interfaces.get(60, 2).getChild(11);
            if (toggleRoofButton != null && closeButton != null) {
                if (turnOff && toggleRoofButton.getTextureID() == 761) {
                    toggleRoofButton.click();
                } else if (!turnOff && toggleRoofButton.getTextureID() == 762) {
                    toggleRoofButton.click();
                }
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return Interfaces.get(60, 2).getChild(11).getTextureID() != toggleRoofButton.getTextureID();
                }, General.random(1000, 1500));
                closeButton.click();
                return true;
            }
        }
        return false;
    }

    public static boolean checkInventory(int itemID) {
        RSItem[] item = Inventory.find(itemID);
        if (item.length > 1) {
            return true;
        } else if (item.length > 0) {
            return item[0].getDefinition().isNoted();
        } else {
            return false;
        }
    }

    public static boolean checkInventoryStack(String itemName, int itemNumber) {
        int item = Inventory.getCount(itemName);
        return (Integer.valueOf(item) > itemNumber);
    }

    public static boolean checkInventoryStack(int itemID, int itemNumber) {
        int item = Inventory.getCount(itemID);
        return (Integer.valueOf(item) > itemNumber);
    }

    /**
     * Opens GameTab supplied in arguments.
     *
     * @param tab (GameTab to open)
     * @return true if GameTab opens
     */
    public static boolean openGameTab(GameTab.TABS tab) {
        if (GameTab.getOpen() != tab) {
            if (GameTab.open(tab)) {
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return GameTab.getOpen() == tab;
                }, General.random(1500, 2000));
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean muteVolume() {
        if (GameTab.getOpen() != GameTab.TABS.OPTIONS) {
            openGameTab(GameTab.TABS.OPTIONS);
        }
        try {
            Interfaces.get(261).getChild(1).getChild(2).click("Audio");
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return Interfaces.get(261).getChild(1).getChild(27) != null;
            }, General.random(1500, 2000));

            RSInterfaceChild adjustMusicVolume = Interfaces.get(261).getChild(27);
            if (adjustMusicVolume != null) {
                adjustMusicVolume.click("Adjust Music Volume");
            }
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return adjustMusicVolume.getTextureID() == 687;
            }, General.random(1500, 2000));

            RSInterfaceChild adjustSoundVolume = Interfaces.get(261).getChild(33);
            if (adjustSoundVolume != null) {
                adjustSoundVolume.click("Adjust Sound Effect Volume");
            }
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return adjustSoundVolume.getTextureID() == 687;
            }, General.random(1500, 2000));

            RSInterfaceChild adjustAreaVolume = Interfaces.get(261).getChild(39);
            if (adjustAreaVolume != null) {
                adjustAreaVolume.click("Adjust Area Sound Effect Volume");
            }
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return adjustAreaVolume.getTextureID() == 687;
            }, General.random(1500, 2000));

            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static void interactObject(RSTile objectTile, int radius, String object, String action) {
        RSObject[] objects = Objects.findNearest(radius, object);
        if (objects.length != 0) {
            String actions[] = objects[0].getDefinition().getActions();
            if (actions.length != 0) {
                if (actions[0].contains(action)) {
                    RSTile currentTile = Player.getPosition();
                    objects[0].click(action);
                    Timing.waitCondition(() -> {
                        General.sleep(200, 400);
                        return !Player.isMoving() && Player.getAnimation() == -1 && Player.getPosition() != currentTile;
                    }, General.random(5000, 15000));
                }
            } else {
                //Door had no actions
                Walking.walkTo(objectTile);
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return !Player.isMoving();
                }, General.random(5000, 15000));
            }
        } else {
            //Doors not found
            Walking.walkTo(objectTile);
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return !Player.isMoving();
            }, General.random(5000, 15000));
        }
    }

    /**
     * Converts a boolean to an integer value.
     *
     * @param value - boolean to convert
     * @return 1 if boolean is true, 0 if boolean is false.
     */
    public static int booleanConversion(boolean value) {
        if (value) {
            return 1;
        }
        return 0;
    }

    /**
     * Converts an integer to a boolean value.
     *
     * @param value - integer to convert
     * @return true if int is greater than 0, false otherwise.
     */
    public static boolean integerConversion(int value) {
        if (value > 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks the actions of an interface, and returns true if the required action is found.
     *
     * @param master - Interface to check actions of
     * @param action - Action to find as string.
     * @return - True if action is found, false if not.
     */
    public static boolean checkActions(RSInterface master, String action) {
        if (master != null) {
            String[] actions = master.getActions();
            if (actions != null) {
                for (int i = 0; i < actions.length; i++) {
                    if (actions[i].contains(action)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method to calculate average average from an array list of integers
     *
     * @param times - ArrayList of integers
     * @return - The average of the integers, as an integer
     */
    public static int calculateAverage(ArrayList<Integer> times) {
        int sum = 0;
        if (!times.isEmpty()) {
            for (int holder : times) {
                sum += holder;
            }
            return sum / times.size();
        }
        return sum;
    }

    /**
     * Scans all children of an interface to find the one with the required action.
     *
     * @param interfaceMaster - Interface to scan children of.
     * @param action          - The action to look for, as String
     * @return - The child interface index if found, or -1 if not found.
     */
    public static int interfaceScanner(int interfaceMaster, String action) {
        RSInterface master = Interfaces.get(interfaceMaster);
        if (master != null) {
            RSInterfaceChild[] children = Interfaces.getChildren(interfaceMaster);
            for (int i = 0; i < children.length; i++) {
                if (children[i] == null)
                    continue;
                String[] actions = children[i].getActions();
                if (actions != null) {
                    for (int e = 0; e < actions.length; e++) {
                        if (actions[e].contains(action)) {
                            return i;
                        }
                        General.sleep(30);
                    }
                }
                General.sleep(30);
            }
            return -1;
        }
        return -1;
    }

    /**
     * Gets a random tile from an array of RSTiles, tile will be within the distance passed in the arguments.
     *
     * @param distance - Max distance away the tile can be from the RSPlayerWrapper
     * @param tiles    - Array of RSTile to find the tile in
     * @return Null if no tiles are found within the distance, or a RSTile if tiles exist within the distance.
     */
    public static RSTile getRandomTileInArray(int distance, RSTile[] tiles) {
        ArrayList<RSTile> tileArrayList = new ArrayList();
        for (RSTile tile : tiles) {
            if (tile.distanceTo(Player.getPosition()) <= distance) {
                tileArrayList.add(tile);
            }
        }
        if (tileArrayList.size() < 1) {
            return null;
        } else {
            return tileArrayList.get(General.random(0, tileArrayList.size() - 1));
        }
    }

    /**
     * Gets the index of an object in an array
     *
     * @param object      - Object to find in the array
     * @param objectArray - Array of types to search through
     * @return -1 if object is not found in the array, otherwise returns the index of the object in the array.
     */
    public static int getArrayIndex(Object object, Object[] objectArray) {
        for (int i = 0; i < objectArray.length; i++) {
            if (objectArray[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean arrayContains(Object key, Object array) {
        return Arrays.asList(array).contains(key);
    }

    /**
     * Removes all occurances of one String from another String
     *
     * @param startingString    String to remove the item from
     * @param subStringToRemove Item to remove from the String
     * @return String with all occurrences replaced
     */
    public static String removeFromString(String startingString, String subStringToRemove) {
        return startingString.replaceAll(Pattern.quote(subStringToRemove), "");
    }

    /**
     * Gets the Path to the FluffeeScripts directory, used for file writing.
     *
     * @return Path to directory as a String.
     */
    public static String getFluffeeScriptsDirectory() {
        return org.tribot.util.Util.getWorkingDirectory().getAbsolutePath() + File.separator + "FluffeeScripts" + File.separator;
    }

    /**
     * Copies the passed array into the specified length. Will copy the passed element multiple times to fill the remaining space, or cut the array to shorten.
     *
     * @param original    Starting array
     * @param arrayLength Length to make the resulting array
     * @param <T>         Generic type.
     * @return Array of passed type at the required length.
     */
    public static <T> T[] copyOfNoNulls(T[] original, T lastElement, int arrayLength) {
        T[] newArray = Arrays.copyOf(original, arrayLength);
        if (original.length < arrayLength) {
            for (int i = original.length; i < newArray.length; i++) {
                newArray[i] = newArray[i] == null ? lastElement : newArray[i];
            }
        }
        return newArray;
    }

    /**
     * Copies the passed array into the specified length. Will copy the passed element multiple times to fill the remaining space, or cut the array to shorten.
     *
     * @param original    Starting array
     * @param arrayLength Length to make the resulting array
     * @return Array of passed type at the required length.
     */
    public static int[] copyOfNoNulls(int[] original, int lastElement, int arrayLength) {
        int[] newArray = Arrays.copyOf(original, arrayLength);
        if (original.length < arrayLength) {
            for (int i = original.length; i < newArray.length; i++) {
                newArray[i] = newArray[i] == 0 ? lastElement : newArray[i];
            }
        }
        return newArray;
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static int getRandomInteger (int min, int max) {
        return (int)(min + Math.floor(Math.random()*(max + 1 - min)));
    }
}
