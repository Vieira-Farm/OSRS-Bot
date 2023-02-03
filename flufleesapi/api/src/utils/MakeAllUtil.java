package utils;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import client.clientextensions.Timing;

import java.util.Arrays;

public class MakeAllUtil {

    public static boolean makeItems(String itemName, boolean waitToStart, MAKE_AMOUNT make_amount, int quantity) {
        if (!isMakeAllOpen())
            return false;
        if (make_amount == MAKE_AMOUNT.X) {
            if (setupCustomAmount(quantity)) {
                make_amount = MAKE_AMOUNT.CUSTOM;
            } else {
                return false;
            }
        }
        if (!selectAmountButton(make_amount))
            return false;

        RSInterface[] children = getMakeAllInterface().getChildren();

        if (children == null)
            return false;

        // based on observation that only the selectable boxes have component names
        RSInterface[] correctChildren = Arrays.stream(children)
                .filter(rsInterface -> rsInterface.getComponentName() != null && !rsInterface.getComponentName().isEmpty())
                .toArray(RSInterface[]::new);

        for (int i = 0; i < correctChildren.length; i++) {
            RSInterface child = correctChildren[i];

            if (child.getComponentName().contains(itemName)) {
                Keyboard.typeSend(Integer.toString(i + 1));

                return !waitToStart || Timing.waitCondition(() -> !isMakeAllOpen(), General.random(3500, 5000));
            }
        }

        return false;
    }

    public static boolean makeItems(String itemName){
        return makeItems(itemName, true, MAKE_AMOUNT.ALL, -1);
    }

    public static boolean isMakeAmountSelected(MAKE_AMOUNT amount) {
        RSInterface makeAllButton = getAmountButton(amount);

        return makeAllButton != null && makeAllButton.getActions() == null;
    }

    public static boolean selectAmountButton(MAKE_AMOUNT amount) {
        if (isMakeAmountSelected(amount))
            return true;

        RSInterface makeAllButton = getAmountButton(amount);
        return makeAllButton != null && makeAllButton.click();
    }

    public static boolean isMakeAllOpen(){
        return Interfaces.isInterfaceSubstantiated(270);
    }

    public static RSInterface getAmountButton(MAKE_AMOUNT amount){
        RSInterface makeAllInterface = getMakeAllInterface();
        if(makeAllInterface == null)
            return null;
        return makeAllInterface.getChild(amount.id);
    }

    public static RSInterface getMakeAllInterface(){
        return Interfaces.get(270);
    }

    public static boolean setupCustomAmount(int quantity) {
        selectAmountButton(MAKE_AMOUNT.X);
        if (!Timing.waitCondition(ConditionUtilities.interfaceNotSubstantiated(270), General.random(3000, 5000))) {
            return false;
        }
        Keyboard.typeSend(Integer.toString(quantity));
        return Interfaces.isInterfaceSubstantiated(270);
    }

    public enum MAKE_AMOUNT{
        // these are IDs of interfaces, might need to be changed due to updates
        ONE(7), FIVE(8), TEN(9), CUSTOM(10), X(11), ALL(12);

        public int id;
        MAKE_AMOUNT(int id){
            this.id = id;
        }
    }
}
