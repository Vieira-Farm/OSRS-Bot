package scripts.game.minigames;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;
import org.tribot.api2007.types.RSVarBit;
import scripts.utils.ConditionUtilities;

import java.awt.*;

public class MinigameUtilities {

    public enum MINIGAMES {
        BARBARIAN_ASSAULT("Barbarian Assault"),
        BLAST_FURNACE("Blast Furnace"),
        BURTHORPE_GAMES_ROOM("Burthorpe Games Room"),
        CASTLE_WARS("Castle Wars"),
        CLAN_WARS("Clan Wars"),
        DAGANNOTH_KINGS("Dagannoth Kings"),
        FISHING_TRAWLER("Fishing Trawler"),
        GOD_WARS("God Wars"),
        LAST_MAN_STANDING("Last Man Standing"),
        NIGHTMARE_ZONE("Nightmare Zone"),
        PEST_CONTROL("Pest Control"),
        PLAYER_OWNED_HOUSES("RSPlayerWrapper Owned Houses"),
        RAT_PITS("Rat Pits"),
        SHADES_OF_MORTTON("Shades of Mort'ton"),
        SHIELD_OF_ARRAV("Shield of Arrav"),
        TITHE_FARM("Tithe Farm"),
        TROUBLE_BREWING("Trouble Brewing"),
        TZHAAR_FIGHT_PIT("TzHaar Fight Pit"),
        VOLCANIC_MINE("Volcanic Mine");

        private String minigameName;

        MINIGAMES(String minigameName) {
            this.minigameName = minigameName;
        }

        public String getMinigameName() {
            return minigameName;
        }
    }

    public static final int MINIGAME_TELEPORT_ANIMATION = 4847;

    private static final int MINIGAME_VARBIT = 3217;
    private static final int ACHIEVEMENT_DIARIES_VARBIT = 3612;
    private static final int KOUREND_VARBIT = 618;

    private static final int QUESTS_TAB_MASTER_INTERFACE = 399;
    private static final int QUESTS_VIEW_MINIGAMES_INTERFACE_FIXED = 2;
    private static final int QUESTS_VIEW_MINIGAMES_INTERFACE_RESIZABLE = 4;

    private static final int ACHIEVEMENT_DIARIES_TAB_MASTER_INTERFACE = 259;
    private static final int ACHIEVEMENT_DIARIES_VIEW_MINIGAMES_INTERFACE_FIXED = 2;
    private static final int ACHIEVEMENT_DIARIES_VIEW_MINIGAMES_INTERFACE_RESIZABLE = 8;

    private static final int KOUREND_TAB_MASTER_INTERFACE = 245;
    private static final int KOUREND_VIEW_MINIGAMES_INTERFACE_FIXED = 4;
    private static final int KOUREND_VIEW_MINIGAMES_INTERFACE_RESIZABLE = 6;

    private static final int MINIGAME_TAB_MASTER_INTERFACE = 76;
    private static final int MINIGAME_LIST_DROPDOWN_FIXED = 11;
    private static final int MINIGAME_LIST_DROPDOWN_RESIZABLE = 13;
    private static final int MINIGAME_LIST_FIXED = 21;
    private static final int MINIGAME_LIST_RESIZABLE = 23;
    private static final int MINIGAME_TELEPORT_BUTTON = 31;


    /**
     * Determines if the minigame tab inside the quests tab is open.
     * @return True if tab opened, false otherwise.
     */
    public static boolean minigameTabOpen() {
        return RSVarBit.get(MINIGAME_VARBIT).getValue() == 1;
    }

    /**
     * Determines if the achievement diaries tab inside the quests tab is open.
     * @return True if tab opened, false otherwise.
     */
    private static boolean achievementDiariesTabOpen() {
        return RSVarBit.get(ACHIEVEMENT_DIARIES_VARBIT).getValue() == 1;
    }

    /**
     * Determines if the achievement diaries tab inside the quests tab is open.
     * @return True if tab opened, false otherwise.
     */
    private static boolean kourendTabOpen() {
        return RSVarBit.get(KOUREND_VARBIT).getValue() == 1;
    }

    /**
     * Determines if the achievement diaries tab inside the quests tab is open.
     * @return True if tab opened, false otherwise.
     */
    private static boolean questsTabOpen() {
        return !achievementDiariesTabOpen() && !minigameTabOpen() && !kourendTabOpen();
    }

    /**
     * Gets the master tab interface for the quests game tab. As the master interface changes based upon which inner tab is opened.
     * @return The integer value representing the master interface index.
     */
    private static int getTabMasterInterface() {
        if (achievementDiariesTabOpen())
            return ACHIEVEMENT_DIARIES_TAB_MASTER_INTERFACE;
        if (kourendTabOpen())
            return KOUREND_TAB_MASTER_INTERFACE;
        if (minigameTabOpen())
            return MINIGAME_TAB_MASTER_INTERFACE;
        return QUESTS_TAB_MASTER_INTERFACE;
    }

    /**
     * Gets the child interface id for the View Minigame button. As the child interface changes based upon which inner tab is opened.
     * @return The integer value representing the master interface index.
     */
    private static int getViewMinigameChildInterface() {
        if (General.isClientResizable()) {
            if (achievementDiariesTabOpen()) {
                return ACHIEVEMENT_DIARIES_VIEW_MINIGAMES_INTERFACE_RESIZABLE;
            } else if (kourendTabOpen()) {
                return KOUREND_VIEW_MINIGAMES_INTERFACE_RESIZABLE;
            } else {
                return QUESTS_VIEW_MINIGAMES_INTERFACE_RESIZABLE;
            }
        } else {
            if (achievementDiariesTabOpen()) {
                return ACHIEVEMENT_DIARIES_VIEW_MINIGAMES_INTERFACE_FIXED;
            } else if (kourendTabOpen()) {
                return KOUREND_VIEW_MINIGAMES_INTERFACE_FIXED;
            } else {
                return QUESTS_VIEW_MINIGAMES_INTERFACE_FIXED;
            }
        }

    }

    /**
     * Opens the inner minigame tab, if it is not already opened.
     * @return True if minigame tab opened, false otherwise.
     */
    public static boolean openMinigameTab(){
        if (!GameTab.getOpen().equals(GameTab.TABS.QUESTS)) {
            GameTab.open(GameTab.TABS.QUESTS);
            Timing.waitCondition(ConditionUtilities.tabOpened(GameTab.TABS.QUESTS), General.random(3000, 5000));
        }
        if (GameTab.getOpen().equals(GameTab.TABS.QUESTS) && !minigameTabOpen()) {
            RSInterfaceChild minigameTabButton = Interfaces.get(getTabMasterInterface(), getViewMinigameChildInterface());
            if (minigameTabButton == null || minigameTabButton.isHidden() || !minigameTabButton.isBeingDrawn())
                return false;
            Clicking.click("View Minigames", minigameTabButton);
            Timing.waitCondition(ConditionUtilities.varbitEquals(MINIGAME_VARBIT, 1), General.random(3000, 5000));
        }
        return minigameTabOpen();
    }

    /**
     * Casts teleport for the specified minigame. Does not check if requirements are met to use the teleport, does not wait for teleport to complete.
     * @param minigame Minigame to teleport to.
     * @return True if teleport begins, false if it does not.
     */
    public static boolean castMinigameTeleport(MINIGAMES minigame) {
        if (!selectMiningame(minigame))
            return false;
        RSInterfaceChild teleportButton = Interfaces.get(MINIGAME_TAB_MASTER_INTERFACE, MINIGAME_TELEPORT_BUTTON);
        if (teleportButton == null || teleportButton.isHidden() || !teleportButton.isBeingDrawn())
            return false;
        Clicking.click(teleportButton);
        return Timing.waitCondition(() -> {
            General.sleep(200, 400);
            return Player.getAnimation() == MINIGAME_TELEPORT_ANIMATION;
        }, General.random(3000, 5000));
    }

    public static boolean selectMiningame(MINIGAMES minigame) {
        RSInterfaceChild minigameDropdown = Interfaces.get(MINIGAME_TAB_MASTER_INTERFACE, General.isClientResizable() ? MINIGAME_LIST_DROPDOWN_RESIZABLE : MINIGAME_LIST_DROPDOWN_FIXED),
                minigameList = Interfaces.get(MINIGAME_TAB_MASTER_INTERFACE, General.isClientResizable() ? MINIGAME_LIST_RESIZABLE : MINIGAME_LIST_FIXED);
        if (GameTab.getOpen() != GameTab.TABS.QUESTS) {
            GameTab.open(GameTab.TABS.QUESTS);
            if (!Timing.waitCondition(ConditionUtilities.tabOpened(GameTab.TABS.QUESTS), General.random(1000, 2000)))
                return false;
        }
        if (minigameDropdown != null) {
            String name = minigameDropdown.getText();
            if (name != null && name.equals(minigame.getMinigameName())) {
                return true;
            }
        }
        if (!openMinigameTab() || minigameList == null)
            return false;
        else {
            Clicking.click(minigameDropdown);
            Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return !minigameList.isHidden();
            }, General.random(3000, 5000));
        }
        RSInterfaceComponent minigameComponent = getMinigameComponent(minigame);
        if (minigameComponent == null || !scrollToMinigame(minigameList, minigameComponent))
            return false;
        Clicking.click(minigameComponent);
        Timing.waitCondition(() -> {
            General.sleep(200, 400);
            return minigameList.isHidden();
        }, General.random(3000, 5000));
        return minigameDropdown.getText().equals(minigame.getMinigameName());
    }

    private static RSInterfaceComponent getMinigameComponent(MINIGAMES minigame) {
        RSInterfaceChild minigameList = Interfaces.get(MINIGAME_TAB_MASTER_INTERFACE, General.isClientResizable() ? MINIGAME_LIST_RESIZABLE : MINIGAME_LIST_FIXED);
        if (minigameList == null || minigameList.isHidden())
            return null;
        for (RSInterfaceComponent component : minigameList.getChildren()) {
            if (component == null)
                continue;
            String text = component.getText();
            if (text == null || !text.equals(minigame.getMinigameName()))
                continue;
            return component;
        }
        return null;
    }

    private static boolean scrollToMinigame (RSInterfaceChild minigameList, RSInterfaceComponent minigameComponent) {
        if (minigameList.isHidden())
            return false;
        Rectangle listRectangle = minigameList.getAbsoluteBounds(), componentRectangle = minigameComponent.getAbsoluteBounds();
        if (listRectangle == null || componentRectangle == null)
            return false;
        if (!listRectangle.contains(componentRectangle.getLocation())) {
            if (!listRectangle.contains(Mouse.getPos())) {
                Mouse.moveBox(listRectangle);
            }
            Mouse.scroll(listRectangle.getY() > componentRectangle.getY(), General.random(3, 5));
        }
        Mouse.moveBox(componentRectangle);
        return true;
    }
}
