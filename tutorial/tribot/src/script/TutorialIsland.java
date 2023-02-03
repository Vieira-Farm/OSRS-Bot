package script;

import client.clientextensions.Game;
import data.Variables;
import missions.bank.BankMission;
import missions.charactersetup.StyleMission;
import missions.combat.CombatMission;
import missions.cook.CookMission;
import missions.gettingstarted.GettingStartedMission;
import missions.mage.MageMission;
import missions.mining.MiningMission;
import missions.prayer.PrayerMission;
import missions.quest.QuestMission;
import missions.survival.SurvivalMission;
import scripting.frameworks.mission.missiontypes.Mission;
import scripting.frameworks.mission.missiontypes.MissionManager;
import scripting.frameworks.mission.missiontypes.TaskFinish;
import scripting.listeners.clickContinueListener.ClickContinueListener;

import java.util.LinkedList;

public class TutorialIsland implements MissionManager {

    public static final int QUEST_SETTING = 281;
    private double chatSleepModifier;
    private Variables variables;
    private LinkedList<Mission> missionList;
    private ClickContinueListener clickContinueRunnable;

    public TutorialIsland(String username) {
        this(1.0, username);
    }

    public TutorialIsland(double chatSleepModifier, String username) {
        try {
            this.variables = new Variables(this.getClass());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.chatSleepModifier = chatSleepModifier;
        this.variables.setUsername(username);
        this.missionList = new LinkedList<>();
    }

    @Override
    public LinkedList<Mission> getMissions() {
        return missionList;
    }

    @Override
    public boolean shouldLoopMissions() {
        return false;
    }

    @Override
    public void initializeMissionList() {
        if (!missionList.isEmpty()) {
            missionList.clear();
        }
        missionList.add(new StyleMission(variables));
        missionList.add(new GettingStartedMission());
        missionList.add(new SurvivalMission());
        missionList.add(new CookMission());
        missionList.add(new QuestMission());
        missionList.add(new MiningMission());
        missionList.add(new CombatMission());
        missionList.add(new BankMission());
        missionList.add(new PrayerMission());
        missionList.add(new MageMission());
    }

    @Override
    public String getMissionName() {
        return "Tutorial Island";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(QUEST_SETTING) < 1000;
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(QUEST_SETTING) >= 1000;
    }

    @Override
    public boolean shouldStopExecution(TaskFinish childTaskFinish) {
        if (childTaskFinish == StyleMission.MissionFinishes.NAME_UNAVAILABLE && variables.getUsername().contains("[")) {
            return false;
        }
        return MissionManager.super.shouldStopExecution(childTaskFinish);
    }

    @Override
    public void preMission() {
        clickContinueRunnable = new ClickContinueListener.ClickContinueBuilder().withPersisntentOnly().build();
        clickContinueRunnable.start();
    }

    @Override
    public void postMission() {
        if (clickContinueRunnable != null) {
            clickContinueRunnable.terminate();
        }
    }
}
