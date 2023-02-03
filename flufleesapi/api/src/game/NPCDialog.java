package game;

import org.tribot.api.General;
import client.clientextensions.NPCChat;
import utils.npc.NPCInteraction;

import java.util.Random;

public class NPCDialog {

    public static class ChatOptions {
        private boolean holdSpacebar, tapSpacebar, optionNumbers;
        private double sleepMultiplier;

        public ChatOptions(boolean holdSpacebar, boolean tapSpacebar, boolean optionNumbers, double sleepMultiplier) {
            this.holdSpacebar = holdSpacebar;
            this.tapSpacebar = tapSpacebar;
            this.optionNumbers = optionNumbers;
            this.sleepMultiplier = sleepMultiplier;
        }

        public static ChatOptions generateRandom(String playerName) {
            Random random = new Random(playerName.hashCode());
            boolean holdSpacebar = random.nextBoolean();
            return new ChatOptions(holdSpacebar,
                    holdSpacebar ? false : random.nextBoolean(),
                    random.nextBoolean(),
                    random.nextDouble());
        }

        public boolean isHoldSpacebar() {
            return holdSpacebar;
        }

        public boolean isTapSpacebar() {
            return tapSpacebar;
        }

        public boolean isOptionNumbers() {
            return optionNumbers;
        }

        public double getSleepMultiplier() {
            return sleepMultiplier;
        }

    }

    public static double sleepMultiplier = 1.0;
    private static final String DEFAULT_INTERACTION = "Talk-to";

    public static boolean handleNPCChat(String npcName, ChatOptions chatOptions) {
        return handleNPCChat(npcName, chatOptions, false);
    }

    public static boolean handleNPCChat(String npcName, ChatOptions chatOptions, boolean ranged) {
        return handleNPCChat(npcName, chatOptions, DEFAULT_INTERACTION, ranged);
    }

    public static boolean handleNPCChat(String npcName, ChatOptions chatOptions, String interactionOption,
                                        boolean ranged) {
        if (!NPCChat.inChat(3)) {
            NPCInteraction.interactABC2(interactionOption, ranged, npcName);
        }

        while (NPCChat.inChat(3)) {
            if (!chatOptions.isHoldSpacebar()) {
                General.sleep((long) (NPCChat.getReadWaitTime(NPCChat.getMessage()) * chatOptions.getSleepMultiplier()));
                NPCChat.clickContinue(true, chatOptions.isTapSpacebar());
            } else {
                NPCChat.holdSpaceForChat();
                General.sleep(General.randomSD(500, 10000, 1000));
            }
        }
        return !NPCChat.inChat(3);
    }

    public static boolean handleNPCChat(String npcName, ChatOptions chatOptions, String... options) {
        return handleNPCChat(npcName, chatOptions, false, options);
    }

    public static boolean handleNPCChat(String npcName, ChatOptions chatOptions, boolean ranged, String... options) {
        return handleNPCChat(npcName, chatOptions, DEFAULT_INTERACTION, ranged, options);
    }

    public static boolean handleNPCChat(String npcName, ChatOptions chatOptions, String interactionOption,
                                        boolean ranged, String... options) {
        if (!NPCChat.inChat(3)) {
            NPCInteraction.interactABC2(interactionOption, ranged, npcName);
        }

        while (NPCChat.inChat(3)) {
            if (NPCChat.clickContinuePresent()) {
                if (!chatOptions.isHoldSpacebar()) {
                    General.sleep((long) (NPCChat.getReadWaitTime(NPCChat.getMessage()) * chatOptions.getSleepMultiplier()));
                    NPCChat.clickContinue(true, chatOptions.isTapSpacebar());
                } else {
                    NPCChat.holdSpaceForChat();
                    General.sleep(General.randomSD(500, 10000, 1000));
                }
            } else {
                General.sleep((long) (NPCChat.getReadWaitTime(NPCChat.getOptions()) * sleepMultiplier));
                NPCChat.selectOption(true, chatOptions.isOptionNumbers(), options);
            }
        }
        return NPCChat.inChat(3);
    }
}