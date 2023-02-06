package scripts.utils.npc;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;
import scripts.client.clientextensions.NPCChat;
import scripts.scripting.antiban.AntiBanSingleton;

/**
 * Created by Fluffee on 03/05/2017.
 */
public class NPCInteraction {

    public static boolean interactABC2(String option, boolean ranged, int id) {
        RSNPC[] npcs = NPCs.findNearest(id);
        if (npcs.length > 0) {
            return interactABC2(option, ranged, npcs[0]);
        }
        return false;
    }

    public static boolean interactABC2(String option, boolean ranged, String name) {
        RSNPC[] npcs = NPCs.findNearest(name);
        if (npcs.length > 0) {
            return interactABC2(option, ranged, npcs[0]);
        }
        return false;
    }

    public static boolean interactABC2(String option, boolean ranged, RSNPC npc) {
        if (npc != null) {
            if (!npc.isOnScreen() || !npc.isClickable()) {
                if (!npc.adjustCameraTo()) {
                    Walking.blindWalkTo(npc);
                    Timing.waitCondition(() -> {
                        General.sleep(200, 400);
                        return !Player.isMoving();
                    }, General.random(6000, 7000));
                }
            }
            if (Clicking.click(option, npc)) {
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return Player.isMoving() || NPCChat.inChat(1);
                }, General.random(3000, 5000));
            } else {
                npc.adjustCameraTo();
            }
            if (Player.isMoving()) {
                AntiBanSingleton.get().resolveTimedActions();
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return NPCChat.getMessage() != null || NPCChat.getName() != null || npc.isInteractingWithMe();
                }, General.random(5000, 7000));
            }
            return NPCChat.getMessage() != null || NPCChat.getName() != null || npc.isInteractingWithMe();
        }
        return false;
    }
}
