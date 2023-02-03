package missions.charactersetup.processnodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSInterfaceChild;
import data.structures.ScriptVariables;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import data.Constants;
import utils.ConditionUtilities;
import utils.Utilities;

import java.util.Random;

public class StyleCharacter extends SuccessProcessNode {
    @Override
    public String getStatus() {
        return "Style character";
    }

    @Override
    public void successExecute() {
        StyleOptions[] styleOptions = getStyleOptions();
        for (StyleOptions option : styleOptions) {
            int[] clicks = getClicks(option);
            RSInterfaceChild leftChild = Interfaces.get(Constants.STYLE_INTERFACE_MASTER, option.getLeftInteface());
            RSInterfaceChild rightChild = Interfaces.get(Constants.STYLE_INTERFACE_MASTER, option.getRightInterface());
            for (int click : clicks) {
                if (click == 0) { //Once we hit a zero, everything after that point is also a zero. So we're done clicking
                    break;
                } else if (click < 0) {
                    clickInterface(leftChild, -click);

                } else {
                    clickInterface(rightChild, click);
                }
            }
        }
        RSInterfaceChild acceptButton = Interfaces.get(Constants.STYLE_INTERFACE_MASTER, Constants.STYLE_ACCEPT_BUTTON);
        if (acceptButton != null && Clicking.click(acceptButton)) {
            Timing.waitCondition(ConditionUtilities.settingDoesNotEqual(22, 0), General.random(5000, 7000));
        }

    }

    public void clickInterface(RSInterface rsInterface, int numberClicks) {
        for (int i = 0; i < numberClicks; i++) {
            Clicking.click(rsInterface);
            General.sleep(General.randomSD(100, 1000, 50));
        }
    }

    public StyleOptions[] getStyleOptions() {
        int leftRightPercentage = ScriptVariables.getInstance().getRandomNumber(101);
        int upDownPercentage = ScriptVariables.getInstance().getRandomNumber(101-leftRightPercentage) + leftRightPercentage;
        int randomPercentage = ScriptVariables.getInstance().getRandomNumber(101-upDownPercentage) + upDownPercentage;
        int randomChoice = ScriptVariables.getInstance().getRandomNumber(101);
        if (randomChoice > randomPercentage) {
            StyleOptions[] styleOptions = StyleOptions.values();
            shuffleArray(styleOptions);
            return styleOptions;
        } else if (randomChoice > upDownPercentage) {
            return new StyleOptions[]{StyleOptions.HEAD, StyleOptions.FACIAL, StyleOptions.TORSO, StyleOptions.ARMS,
                    StyleOptions.HANDS, StyleOptions.LEGS, StyleOptions.FEET, StyleOptions.HAIR_COLOR,
                    StyleOptions.TORSO_COLOR, StyleOptions.LEGS_COLOR, StyleOptions.FEET_COLOR, StyleOptions.SKIN};
        } else {
            return new StyleOptions[]{StyleOptions.HEAD, StyleOptions.HAIR_COLOR, StyleOptions.FACIAL,
                            StyleOptions.TORSO, StyleOptions.TORSO_COLOR, StyleOptions.ARMS, StyleOptions.HANDS,
                            StyleOptions.LEGS, StyleOptions.LEGS_COLOR, StyleOptions.FEET, StyleOptions.FEET_COLOR,
                            StyleOptions.SKIN};
        }
    }

    public int[] getClicks(StyleOptions styleOption) {
        int maxCicks = (int) (styleOption.getMaxOptions() * 1.33);
        int clickCount = 0;
        int[] clickArray = new int[maxCicks];
        for (int i = 0; i < clickArray.length && clickCount < maxCicks; i++) {
            int tempClicks = Utilities.getRandomInteger(1, (maxCicks-clickCount));
            clickCount += tempClicks;
            clickArray[i] = ScriptVariables.getInstance().getRandomBoolean() ? tempClicks : -tempClicks;
        }
        return clickArray;
    }

    private static void shuffleArray(StyleOptions[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            StyleOptions temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public enum StyleOptions {
        HEAD(106, 113, 23),
        FACIAL(107, 114, 14),
        TORSO(108, 115, 13),
        ARMS(109, 116, 11),
        HANDS(110, 117, 1),
        LEGS(111, 118, 10),
        FEET(112, 119, 1),
        HAIR_COLOR(105, 121, 12),
        TORSO_COLOR(123, 127, 28),
        LEGS_COLOR(122, 129, 28),
        FEET_COLOR(124, 130, 6),
        SKIN(125, 131, 7);

        private final int leftInteface, rightInterface, maxOptions;

        StyleOptions(int leftInterface, int rightInterface, int maxOptions) {
            this.leftInteface = leftInterface;
            this.rightInterface = rightInterface;
            this.maxOptions = maxOptions;
        }

        public int getLeftInteface() {
            return leftInteface;
        }

        public int getRightInterface() {
            return rightInterface;
        }

        public int getMaxOptions() {
            return maxOptions;
        }
    }
}
