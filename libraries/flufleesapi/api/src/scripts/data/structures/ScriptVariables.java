package scripts.data.structures;

import scripts.utils.Utilities;

import java.util.Random;

public class ScriptVariables {

    private double chatSleepMultiplier;
    private Random randomGenerator;
    private String missionName, scriptStatus, randomSeed;

    private static final ScriptVariables instance = new ScriptVariables();

    private ScriptVariables() {
        chatSleepMultiplier = 1.0;
        missionName = "Loading...";
        scriptStatus = "Loading...";
        randomSeed = "";
        randomGenerator = new Random(Utilities.stringToSeed(randomSeed));
    }

    public static ScriptVariables getInstance() {
        return instance;
    }

    public double getChatSleepMultiplier() {
        return this.chatSleepMultiplier;
    }

    public void setChatSleepMultiplier(double chatSleepMultiplier) {
        this.chatSleepMultiplier = chatSleepMultiplier;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getScriptStatus() {
        return scriptStatus;
    }

    public void setScriptStatus(String scriptStatus) {
        this.scriptStatus = scriptStatus;
    }

    private Random getRandomGenerator() {
        return randomGenerator;
    }

    private void setRandomSeed(String seed) {
        this.randomSeed = seed;
        this.randomGenerator.setSeed(Utilities.stringToSeed(randomSeed));
    }

    public int getRandomNumber(int maxValue) {
        return getRandomGenerator().nextInt(maxValue);
    }

    public double getRandomDouble() {
        return getRandomGenerator().nextDouble();
    }

    public boolean getRandomBoolean() {
        return getRandomGenerator().nextBoolean();
    }
}
