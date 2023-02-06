package scripts.scripting.painting.scriptpaint;

public class SkillXP {

    private int startingXP, xpHour, xpGained;

    private SkillXP(){};

    public static class SkillXPBuilder {

        private int startingXP, xpHour = 0, xpGained = 0;

        public SkillXPBuilder(){};

        public SkillXPBuilder setStartingXP(int startingXP) {
            this.startingXP = startingXP;
            return this;
        }

        public SkillXPBuilder setXPHour(int xpHour) {
            this.xpHour = xpHour;
            return this;
        }

        public SkillXPBuilder setXPGained(int xpGained) {
            this.xpGained = xpGained;
            return this;
        }

        public SkillXP build() {
            SkillXP skillXP = new SkillXP();
            skillXP.startingXP = this.startingXP;
            skillXP.xpHour = xpHour;
            skillXP.xpGained = xpGained;
            return skillXP;
        }
    }

    public int getStartingXP() {
        return startingXP;
    }

    public int getXpHour() {
        return xpHour;
    }

    public int getXpGained() {
        return xpGained;
    }

    public void setXpHour(int xpHour) {
        this.xpHour = xpHour;
    }

    public void setXpGained(int xpGained) {
        this.xpGained = xpGained;
    }
}
