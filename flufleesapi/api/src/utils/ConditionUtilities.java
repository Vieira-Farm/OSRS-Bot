package utils;

import client.clientextensions.*;
import client.clientextensions.Banking;
import client.clientextensions.Combat;
import client.clientextensions.Equipment;
import client.clientextensions.Game;
import client.clientextensions.Interfaces;
import client.clientextensions.Inventory;
import data.interactables.InteractableInterface;
import org.tribot.api.General;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.types.RSVarBit;

import java.util.function.BooleanSupplier;

/**
 * Created by Fluffee on 24/08/17.
 */
@SuppressWarnings("unused")
public class ConditionUtilities {

    public static BooleanSupplier isInTrade() {
        return () -> {
            General.sleep(200, 400);
            return Trading.getWindowState() == Trading.WINDOW_STATE.FIRST_WINDOW;
        };
    }

    public static BooleanSupplier worldHopComplete(int startingWorldNumber) {
        return () -> {
            General.sleep(200, 400);
            return WorldHopper.getWorld() != startingWorldNumber && Game.getSetting(281) == 1000;
        };
    }

    public static BooleanSupplier interfaceSubstantiated(int interfaceNumber) {
        return () -> {
            General.sleep(200, 400);
            return Interfaces.isInterfaceSubstantiated(interfaceNumber);
        };
    }

    public static BooleanSupplier interfaceSubstantiated(int interfaceNumber, int childNumber) {
        return () -> {
            General.sleep(200, 400);
            return Interfaces.isInterfaceSubstantiated(interfaceNumber, childNumber);
        };
    }

    public static BooleanSupplier interfaceSubstantiated(InteractableInterface interactableInterface) {
        return () -> {
            General.sleep(200, 400);
            return Interfaces.isInterfaceSubstantiated(interactableInterface);
        };
    }

    public static BooleanSupplier interfaceSubstantiated(RSInterface rsInterface) {
        return () -> {
            General.sleep(200, 400);
            return Interfaces.isInterfaceSubstantiated(rsInterface);
        };
    }

    public static BooleanSupplier interfaceNotSubstantiated(int interfaceNumber) {
        return () -> {
            General.sleep(200, 400);
            return !Interfaces.isInterfaceSubstantiated(interfaceNumber);
        };
    }

    public static BooleanSupplier interfaceNotSubstantiated(int interfaceNumber, int childNumber) {
        return () -> {
            General.sleep(200, 400);
            return !Interfaces.isInterfaceSubstantiated(interfaceNumber, childNumber);
        };
    }

    public static BooleanSupplier interfaceNotSubstantiated(RSInterface rsInterface) {
        return () -> {
            General.sleep(200, 400);
            return !Interfaces.isInterfaceSubstantiated(rsInterface);
        };
    }

    public static BooleanSupplier levelChanged(Skills.SKILLS skill, int startingLevel) {
        return () -> {
            General.sleep(200, 400);
            return skill.getCurrentLevel() > startingLevel;
        };
    }

    public static BooleanSupplier ateFood(int startingHP) {
        return () -> {
            General.sleep(200, 400);
            return Combat.getHP() > startingHP;
        };
    }

    public static BooleanSupplier hitpointsLoaded() {
        return () -> {
            General.sleep(200, 400);
            return Skills.getXP(Skills.SKILLS.HITPOINTS) >= 1000;
        };

    }

    public static BooleanSupplier inBank() {
        return () -> {
            General.sleep(200, 400);
            return Banking.isInBank();
        };
    }

    public static BooleanSupplier varbitEquals(int varbitID, int newValue) {
        return () -> {
            General.sleep(200, 400);
            return RSVarBit.get(varbitID).getValue() == newValue;
        };
    }

    public static BooleanSupplier varbitNotEquals(int varbitID, int oldValue) {
        return () -> {
            General.sleep(200, 400);
            return RSVarBit.get(varbitID).getValue() != oldValue;
        };
    }

    public static BooleanSupplier settingChanged(int settingID, int newValue) {
        return () -> {
            General.sleep(200, 400);
            return Game.getSetting(settingID) == newValue;
        };
    }

    public static BooleanSupplier settingDoesNotEqual(int settingId, int oldValue) {
        return () -> {
            General.sleep(200, 400);
            return Game.getSetting(settingId) != oldValue;
        };
    }

    public static BooleanSupplier areaNotContains (RSArea area) {
        return () -> {
            General.sleep(200, 400);
            return !area.contains(Player.getPosition());
        };
    }

    public static BooleanSupplier areaContains (RSArea area) {
        return () -> {
            General.sleep(200, 400);
            return area.contains(Player.getPosition());
        };
    }

    public static BooleanSupplier loginStateChanged (Login.STATE currentState) {
        return () -> {
            General.sleep(200, 400);
            return Login.getLoginState() != currentState;
        };
    }

    public static BooleanSupplier stoppedAnimating() {
        return () -> {
            General.sleep(200, 400);
            return Player.getAnimation() == -1;
        };
    }

    public static BooleanSupplier startedAnimating() {
        return () -> {
            General.sleep(200, 400);
            return Player.getAnimation() != -1;
        };
    }

    public static BooleanSupplier startedAnimation(int animationID) {
        return () -> {
            General.sleep(200, 400);
            return Player.getAnimation() == animationID;
        };
    }

    public static BooleanSupplier withdrewItem(int itemID, int startingCount) {
        return () -> {
            General.sleep(200, 400);
            return Inventory.getCount(itemID) > startingCount;
        };
    }

    public static BooleanSupplier withdrewItem(String itemName, int startingCount) {
        return () -> {
            General.sleep(200, 400);
            return Inventory.getCount(itemName) > startingCount;
        };
    }

    public static BooleanSupplier nearTile(int maxDistance, RSTile tile) {
        return () -> {
            General.sleep(200, 400);
            return Player.getPosition().distanceTo(tile) <= maxDistance;
        };
    }

    public static BooleanSupplier inventoryCountChanged(int oldInventoryCount) {
        return () -> {
            General.sleep(200, 400);
            return Inventory.getAll().length != oldInventoryCount;
        };
    }
    public static BooleanSupplier optionsClosed() {
        return () -> {
            General.sleep(200, 400);
            return Interfaces.isInterfaceSubstantiated(372) //Interface that shows name
                    || Interfaces.isInterfaceSubstantiated(231) //Interface that shows click continue for npc
                    || Interfaces.isInterfaceSubstantiated(217); //Interface that shows click continue for npc
        };
    }

    public static BooleanSupplier tabOpened(GameTab.TABS tab) {
        return () -> {
            General.sleep(200, 400);
            return GameTab.getOpen().equals(tab);
        };
    }

    public static BooleanSupplier inventoryIsEmpty() {
        return () -> {
            General.sleep(200, 400);
            return Inventory.getAll().length < 1;
        };
    }

    public static BooleanSupplier stoppedMoving() {
        return () -> {
            General.sleep(200, 400);
            return !Player.isMoving() && Game.getDestination() == null;
        };
    }

    public static BooleanSupplier isMoving() {
        return () -> {
            General.sleep(200, 400);
            return Player.isMoving();
        };
    }

    public static BooleanSupplier changedPlane(int startingPlane) {
        return () -> {
            General.sleep(200, 400);
            return Player.getPosition().getPlane() != startingPlane;
        };
    }

    public static BooleanSupplier changedTile(RSTile startingTile) {
        return () -> {
            General.sleep(200, 400);
            return !Player.getPosition().equals(startingTile);
        };
    }

    public static BooleanSupplier bankOpened() {
        return () -> {
            General.sleep(200, 400);
            return Banking.isBankScreenOpen();
        };
    }

    public static BooleanSupplier bankClosed() {
        return () -> {
            General.sleep(200, 400);
            return !Banking.isBankScreenOpen();
        };
    }

    public static BooleanSupplier depositBoxOpened() {
        return () -> {
            General.sleep(200, 400);
            return Banking.isDepositBoxOpen();
        };
    }

    public static BooleanSupplier nothingEquipped() {
        return () -> {
            General.sleep(200, 400);
            return Equipment.getItems().length == 0;
        };
    }

    public static BooleanSupplier itemEquipped(RSItem item, Equipment.SLOTS slot) {
        return () -> {
            General.sleep(200, 400);
            RSItem newItem = slot != null ? slot.getItem() : null;
            return newItem != null && (newItem.getID() == item.getID());
        };
    }
}
