package utils;

import client.clientextensions.Timing;
import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;
import utils.lists.RSFriend;
import utils.lists.RSIgnore;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

/**
 * Created by Fluffee on 18/05/2017.
 * Recreated from AlphaDog's Friends API, modified to fit both the ignore list and friends list. Also updated the ID's for the interfaces.
 * General methods were credit of AlphaDog
 */
public class SocialLists {
    private final static int FRIEND_PARENT_WIDGET = 429;
    private final static int FRIEND_CONTAINER = 8;
    private final static int IGNORE_PARENT_WIDGET = 432;
    private final static int IGNORE_CONTAINER = 7;
    private final static int ADD_IGNORE = 10;
    private final static int DELETE_IGNORE = 12;
    private final static int ADD_FRIENDS = 11;
    private final static int DELETE_FRIENDS = 13;

    /**
     * Gets all the Players that are on your ignore list.
     *
     * @return RSIgnore array with all your ignore list members
     */
    public static RSIgnore[] getAllIgnore() {
        if (GameTab.getOpen() != GameTab.TABS.FRIENDS) { //Friends are not loaded yet
            Utilities.openGameTab(GameTab.TABS.FRIENDS);
        }

        final RSInterfaceChild container = Interfaces.get(FRIEND_PARENT_WIDGET, FRIEND_CONTAINER);
        final ArrayList<RSIgnore> players = new ArrayList<>();

        if (container != null) {
            RSInterfaceComponent[] children = container.getChildren();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    if (children[i].getFontID() != -1 && !children[i].isHidden()) {
                        RSInterfaceComponent nameInterface = children[i];
                        players.add(new RSIgnore(nameInterface));
                    }
                }
            }
        }
        return players.toArray(new RSIgnore[players.size()]);
    }

    /**
     * Gets all the Players that are on your friends list.
     *
     * @return RSFriend array with all your friend list members
     */
    public static RSFriend[] getAllFriends() {
        if (GameTab.getOpen() != GameTab.TABS.FRIENDS) { //Friends are not loaded yet
            Utilities.openGameTab(GameTab.TABS.FRIENDS);
        }

        final RSInterfaceChild container = Interfaces.get(FRIEND_PARENT_WIDGET, FRIEND_CONTAINER);
        final ArrayList<RSFriend> players = new ArrayList<>();

        if (container != null) {
            RSInterfaceComponent[] children = container.getChildren();
            if (children != null) {
                for (int i = 0; i < children.length; i = i + 5) { //Magic numbers for how many interfaces each player occupies
                    if (children[i].getActions().length > 0 && children[i].getFontID() != -1 && !children[i].isHidden()) {
                        RSInterfaceComponent nameInterface = children[i];
                        RSInterfaceComponent statusInterface = children[i + 2]; //Jump to status interface
                        if (statusInterface != null) {
                            players.add(new RSFriend(nameInterface, statusInterface));
                        }
                    }
                }
            }
        }
        return players.toArray(new RSFriend[players.size()]);
    }

    /**
     * Gets all the players that are either online or offline on your friends list.
     *
     * @param online - true if you want to getInstance all your online friends, false if you want to getInstance all your offline friends
     * @return Array with all your friends list members
     */
    public static RSFriend[] getAllFriends(boolean online) {
        final ArrayList<RSFriend> friends = new ArrayList<>();
        for (RSFriend friend : getAllFriends()) {
            if (friend.isOnline() == online) {
                friends.add(friend);
            }
        }
        return friends.toArray(new RSFriend[friends.size()]);
    }

    /**
     * Gets the RSIgnore that matches the specified name
     * Note: Will return null if player is not found.
     * Note: The name doesn't have to be case sensitive
     *
     * @return RSIgnore that matches the specified name
     */
    public static RSIgnore getIgnore(String name) {
        for (RSIgnore ignore : getAllIgnore()) {
            if (ignore.getName().equalsIgnoreCase(name)) {
                return ignore;
            }
        }
        return null;
    }

    /**
     * Gets the RSFriend that matches the specified name
     * Note: Will return null if player is not found.
     * Note: The name doesn't have to be case sensitive
     *
     * @return RSFriend that matches the specified name
     */
    public static RSFriend getFriend(String name) {
        for (RSFriend friend : getAllFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                return friend;
            }
        }
        return null;
    }

    /**
     * Gets the number of players on either your friends or ignore list
     *
     * @param friends - True if getting number of players on friends list, false if getting number of ignore list.
     * @return amount of players on your desired list
     */
    public static int getAmount(boolean friends) {
        if (friends) {
            return getAllFriends().length;
        } else {
            return getAllIgnore().length;
        }
    }

    /**
     * Adds a player to your desired list
     *
     * @param name    - the name of the player you want to add
     * @param friends - True if adding to friends list, false if adding to ignore.
     * @return true if the player is successfully added
     */
    public static boolean addPlayer(String name, boolean friends) {
        RSInterfaceChild button = null;
        if (friends) {
            if (SocialLists.getFriend(name) != null) {
                return true;
            } else {
                Utilities.openGameTab(GameTab.TABS.FRIENDS);
                button = Interfaces.get(FRIEND_PARENT_WIDGET, ADD_FRIENDS);
            }
        } else {
            if (SocialLists.getIgnore(name) != null) {
                return true;
            } else {
                Utilities.openGameTab(GameTab.TABS.IGNORE);
                button = Interfaces.get(IGNORE_PARENT_WIDGET, ADD_IGNORE);
            }
        }
        final int count = getAmount(friends);
        if (button != null) {
            return clickButton(button, name, friends) && Timing.waitCondition(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    General.sleep(200, 400);
                    return getAmount(friends) > count;
                }
            }, 3000);
        }
        return false;
    }

    /**
     * Deletes a player on your desired list
     *
     * @param name    - the name of the player you want to delete
     * @param friends - True if adding to friends list, false if adding to ignore.
     * @return true if the player is successfully deleted
     */
    public static boolean deletePlayer(String name, boolean friends) {
        RSInterfaceChild button = null;
        if (friends) {
            if (SocialLists.getFriend(name) == null) {
                return true;
            } else {
                Utilities.openGameTab(GameTab.TABS.FRIENDS);
                button = Interfaces.get(FRIEND_PARENT_WIDGET, DELETE_FRIENDS);
            }
        } else {
            if (SocialLists.getIgnore(name) == null) {
                return true;
            } else {
                Utilities.openGameTab(GameTab.TABS.IGNORE);
                button = Interfaces.get(IGNORE_PARENT_WIDGET, DELETE_IGNORE);
            }
        }
        final int count = getAmount(friends);
        if (button != null) {
            return clickButton(button, name, friends) && Timing.waitCondition(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    General.sleep(200, 400);
                    return getAmount(friends) < count;
                }
            }, 3000);
        }
        return false;
    }

    /**
     * Clicks the specified button and enters the friend name afterwards
     *
     * @param button  - the interface child to click
     * @param name    - the friend name to type afterwards
     * @param friends - True if opening friends list, false if opening ignore.
     * @return true if the button is successfully clicked, false otherwise
     */
    private static boolean clickButton(RSInterfaceChild button, String name, boolean friends) {
        GameTab.TABS tabToOpen = null;
        if (friends) {
            tabToOpen = GameTab.TABS.FRIENDS;
        } else {
            tabToOpen = GameTab.TABS.IGNORE;
        }
        if (button != null && GameTab.open(tabToOpen) && Clicking.click(button)) {
            General.sleep(1100, 1200);
            Keyboard.typeString(name);
            General.sleep(300, 500);
            Keyboard.pressEnter();
            return true;
        }
        return false;
    }
}
