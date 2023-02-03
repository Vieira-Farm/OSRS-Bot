package scripting.frameworks.mission.missiontypes.progressive;

import data.progression.StopCondition;
import scripting.frameworks.mission.missiontypes.Mission;
import scripting.listeners.inventoryListener.InventoryListener;
import scripting.listeners.inventoryListener.InventoryObserver;

public interface ProgressiveMission extends Mission {

    StopCondition getStoppingCondition();

    @Override
    default boolean isMissionCompleted() {
        return getStoppingCondition() != null && getStoppingCondition().isConditionReached();
    }

    @Override
    default void preMission() {
        if (getStoppingCondition() instanceof StopCondition.ResourceCondition) {
            InventoryObserver.addListener(new InventoryListener() {
                @Override
                public void inventoryItemGained(int id, int count) {
                    if (getStoppingCondition() != null &&
                            id == ((StopCondition.ResourceCondition) getStoppingCondition()).getResource().getId()) {
                        ((StopCondition.ResourceCondition) getStoppingCondition()).incrementCurrentResourceCount(count);
                    }
                }

                @Override
                public void inventoryItemLost(int id, int count) {}
            });
        }
    }
}
