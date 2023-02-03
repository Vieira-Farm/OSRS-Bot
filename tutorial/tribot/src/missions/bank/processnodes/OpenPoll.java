package missions.bank.processnodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import client.clientextensions.NPCChat;
import data.structures.ScriptVariables;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;

public class OpenPoll extends SuccessProcessNode {

    private final RSTile POLL_BOOTH_TILE = new RSTile(3120, 3121, 0);

    public String getStatus() {
        return "Opening Poll";
    }

    public void successExecute() {
        if (ScriptVariables.getInstance().getRandomBoolean()) {
            openPollAfterMoving();
            RSObject[] pollBooths = Objects.find(3, "Poll booth");
            if (pollBooths.length < 1) {
                Walking.walkTo(POLL_BOOTH_TILE);
                return;
            } else {
                Clicking.click("Use Poll booth", pollBooths[0]);
            }
        } else {
            RSObject[] pollBooths = Objects.find(10, "Poll booth");
            if (pollBooths.length < 1)
                return;
            if (pollBooths[0].isOnScreen() && pollBooths[0].isClickable() && Clicking.click("Use Poll booth", pollBooths[0])) {
                Timing.waitCondition(ConditionUtilities.stoppedMoving(), General.random(3000, 5000));
            } else {
                pollBooths[0].adjustCameraTo();
            }
        }
    }

    private void openPollAfterMoving() {
        RSObject[] pollBooths = Objects.find(3, "Poll booth");
        if (pollBooths.length < 1) {
            Walking.walkTo(POLL_BOOTH_TILE);
        } else if (Clicking.click("Use Poll booth", pollBooths[0])) {
            Timing.waitCondition(() -> {
                General.random(200, 400);
                return NPCChat.inChat(1);
            }, General.random(3000, 5000));
        }
    }
}
