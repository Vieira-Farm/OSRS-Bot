package scripts.scripting.reusable.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;
import scripts.scripting.entityselector.finders.prefabs.InterfaceEntity;
import scripts.scripting.frameworks.task.Priority;
import scripts.scripting.frameworks.task.tasktypes.SuccessTask;
import scripts.utils.ConditionUtilities;

public class CloseQuestCompleted extends SuccessTask {
    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean isValid() {
        return Interfaces.isInterfaceValid(277);
    }

    @Override
    public void successExecute() {
        RSInterface closeButton =  new InterfaceEntity().inMaster(277).textureIdEquals(538).getFirstResult();
        if (closeButton != null && Clicking.click(closeButton)) {
            Timing.waitCondition(ConditionUtilities.interfaceNotSubstantiated(closeButton), General.random(3000, 5000));
        }
    }

    @Override
    public String getStatus() {
        return "Closing quest.";
    }
}
