package scripts.tutorial.missions;

import org.tribot.api.General;
import scripts.client.clientextensions.Game;
import scripts.tutorial.data.Variables;
import scripts.tutorial.missions.bank.BankMission;
import scripts.tutorial.missions.charactersetup.StyleMission;
import scripts.tutorial.missions.combat.CombatMission;
import scripts.tutorial.missions.cook.CookMission;
import scripts.tutorial.missions.gettingstarted.GettingStartedMission;
import scripts.tutorial.missions.mage.MageMission;
import scripts.tutorial.missions.mining.MiningMission;
import scripts.tutorial.missions.prayer.PrayerMission;
import scripts.tutorial.missions.quest.QuestMission;
import scripts.tutorial.missions.survival.SurvivalMission;
import scripts.scripting.frameworks.mission.missiontypes.Mission;
import scripts.scripting.frameworks.mission.missiontypes.MissionManager;
import scripts.scripting.frameworks.mission.missiontypes.TaskFinish;
import scripts.scripting.listeners.clickContinueListener.ClickContinueListener;

import java.util.LinkedList;

public class TutorialIsland implements MissionManager {

    public static final int QUEST_SETTING = 281;
    private double chatSleepModifier;
    private Variables variables;
    private LinkedList<Mission> missionList;
    private ClickContinueListener clickContinueRunnable;

    public TutorialIsland(String username) {
        this(1.0, username);
        General.println("TutorialIsland: constructor");
    }

    public TutorialIsland(double chatSleepModifier, String username) {
        try {
            this.variables = new Variables(this.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.chatSleepModifier = chatSleepModifier;
        this.variables.username = username;
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
        System.out.println("Tutorial Island starting");
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
        if (childTaskFinish == StyleMission.MissionFinishes.NAME_UNAVAILABLE && variables.username.contains("[")) {
            return false;
        }
        return MissionManager.super.shouldStopExecution(childTaskFinish);
    }

    @Override
    public void preMission() {
        General.println("Tutorial Island: preMission");
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
