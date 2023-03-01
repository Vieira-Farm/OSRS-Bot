package scripts.romeoandjuliet.missions;

import org.tribot.api2007.Login;
import scripts.client.clientextensions.Game;
import scripts.data.structures.bag.BagIds;
import scripts.romeoandjuliet.missions.leavejuliet.LeaveJulietMission;
import scripts.romeoandjuliet.missions.pickberries.PickBerriesMission;
import scripts.romeoandjuliet.missions.talkapothecary.TalkApothecaryMission;
import scripts.romeoandjuliet.missions.talkfather.TalkFatherMission;
import scripts.romeoandjuliet.missions.talkjuliet.TalkJulietMission;
import scripts.romeoandjuliet.missions.talkromeo.TalkRomeoMission;
import scripts.scripting.frameworks.mission.missiontypes.Mission;
import scripts.scripting.frameworks.mission.missiontypes.MissionManager;

import java.util.LinkedList;

public class RomeoAndJuliet implements MissionManager {

    public static int QUEST_SETTING = 144;
    private final LinkedList<Mission> missionList = new LinkedList<>();

    @Override
    public LinkedList<Mission> getMissions() {
        return missionList;
    }

    @Override
    public void initializeMissionList() {
        System.out.println("Tutorial Island starting");
        if (!missionList.isEmpty()) {
            missionList.clear();
        }
        missionList.add(new TalkJulietMission());
        missionList.add(new LeaveJulietMission());
        missionList.add(new TalkRomeoMission());
        missionList.add(new TalkFatherMission());
        missionList.add(new TalkApothecaryMission());
        missionList.add(new PickBerriesMission());
    }

    @Override
    public boolean shouldLoopMissions() {
        return true;
    }

    @Override
    public String getMissionName() {
        return "Romeo and Juliet";
    }

    public String getBagId() {
        return BagIds.ROMEO_AND_JULIET.getId();
    }

    @Override
    public boolean isMissionValid() {
        return Login.getLoginState() == Login.STATE.INGAME && Game.getSetting(QUEST_SETTING) <= 100;
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(QUEST_SETTING) >= 100;
    }
}