package data.progression;

import org.tribot.api2007.Skills;
import data.interactables.BaseInteractable;
import data.interactables.Interactable;

public abstract class StopCondition {

    Conditions condition;
    Interactable resource;
    int finalCount;

    public StopCondition(Conditions condition, Interactable resource, int finalCount) {
        this.condition = condition;
        this.resource = resource;
        this.finalCount = finalCount;
    }

    public StopCondition(Conditions condition, int finalCount) {
        this.condition = condition;
        this.finalCount = finalCount;
    }

    public abstract boolean isConditionReached();

    public enum Conditions {
        LEVEL_REACHED,
        RESOURCES_GATHERED,
        RESOURCES_USED,
    }

    public static class LevelReachedCondition extends StopCondition {

        private Skills.SKILLS skill;

        public LevelReachedCondition(int levelGoal, Skills.SKILLS skill) {
            this(Conditions.LEVEL_REACHED, levelGoal);
            this.skill = skill;
        }

        private LevelReachedCondition(Conditions condition, int finalCount) {
            super(condition, finalCount);
        }

        @Override
        public boolean isConditionReached() {
            return skill.getActualLevel() >= this.finalCount;
        }
    }

    public static abstract class ResourceCondition extends StopCondition {
        int currentResourceCount = 0;

        public ResourceCondition(Conditions condition, int resourceCount, Interactable resource) {
            super(Conditions.RESOURCES_GATHERED, resource, resourceCount);
        }

        public ResourceCondition(Conditions condition, int resourceCount, String resource) {
            super(Conditions.RESOURCES_GATHERED, new Interactable(resource, "Use", BaseInteractable.DEFAULT_ID),
                    resourceCount);
        }

        public ResourceCondition(Conditions condition, int resourceCount, int resourceId) {
            super(Conditions.RESOURCES_GATHERED, new Interactable("", "Use", resourceId),
                    resourceCount);
        }

        public void incrementCurrentResourceCount(int incrementValue) {
            this.currentResourceCount += incrementValue;
        }

        public void incremementCurrentResourceCount() {
            this.currentResourceCount++;
        }

        public void setCurrentResourceCount(int currentResourceCount) {
            this.currentResourceCount = currentResourceCount;
        }

        public int getCurrentResourceCount() {
            return currentResourceCount;
        }

        public Interactable getResource() {
            return resource;
        }

        @Override
        public boolean isConditionReached() {
            return currentResourceCount >= finalCount;
        }
    }

    public static class ResourceGatherCondition extends ResourceCondition {
        public ResourceGatherCondition(int resourceCount, Interactable resource) {
            super(Conditions.RESOURCES_GATHERED, resourceCount, resource);
        }

        public ResourceGatherCondition(int resourceCount, int id) {
            super(Conditions.RESOURCES_GATHERED, resourceCount, id);
        }

        public ResourceGatherCondition(int resourceCount, String resource, int id) {
            super(Conditions.RESOURCES_GATHERED, resourceCount, resource);
        }
    }

    public static class ResourceUsedCondition extends ResourceCondition {
        public ResourceUsedCondition(int resourceCount, Interactable resource) {
            super(Conditions.RESOURCES_USED, resourceCount, resource);
        }

        public ResourceUsedCondition(int resourceCount, String resource) {
            super(Conditions.RESOURCES_USED, resourceCount, resource);
        }
    }
}
