package scripts.missions.quest.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.ConditionUtilities;
import scripts.utils.Utilities;

public class WalkToGuide extends SuccessProcessNode {

    private final RSTile QUEST_GUIDE_TILE = Utilities.getRandomizedTile(new RSTile(3086, 3128, 0), 3);

    @Override
    public String getStatus() {
        return "Walking to guide";
    }

    @Override
    public void successExecute() {
        if (Walking.blindWalkTo(QUEST_GUIDE_TILE)) {
            Timing.waitCondition(ConditionUtilities.nearTile(2, QUEST_GUIDE_TILE), General.random(10000, 15000));
        }
    }
}