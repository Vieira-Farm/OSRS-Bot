package scripts.restlessghost.missions

import scripts.client.clientextensions.Game
import scripts.restlessghost.data.Constants.QUEST_SETTING
import scripts.restlessghost.missions.entertower.EnterTowerMission
import scripts.restlessghost.missions.exittower.ExitTowerMission
import scripts.restlessghost.missions.graveyardskull.GraveyardSkullMission
import scripts.restlessghost.missions.graveyardtalk.GraveyardMission
import scripts.restlessghost.missions.skull.SkullMission
import scripts.restlessghost.missions.talkaereck.TalkAereckMission
import scripts.restlessghost.missions.urhney.UrhneyMission
import scripts.scripting.frameworks.mission.missiontypes.Mission
import scripts.scripting.frameworks.mission.missiontypes.MissionManager
import java.util.*

class RestlessGhost : MissionManager {
    private val missionList: LinkedList<Mission> = LinkedList()

    override fun getMissionName(): String {
       return "Restless Ghost"
    }

    override fun isMissionValid(): Boolean {
        return Game.getSetting(QUEST_SETTING) < 5
    }

    override fun isMissionCompleted(): Boolean {
        return Game.getSetting(QUEST_SETTING) >= 5 && Game.getVarBitValue(6719) == 0
    }

    override fun getMissions(): LinkedList<Mission> {
        return missionList
    }

    override fun initializeMissionList() {

        println("Tutorial Island starting")
        if (!missionList.isEmpty()) {
            missionList.clear()
        }
        missionList.add(GraveyardMission())
        missionList.add(UrhneyMission())
        missionList.add(TalkAereckMission())
        missionList.add(SkullMission())
        missionList.add(GraveyardSkullMission())
        missionList.add(ExitTowerMission())
        missionList.add(EnterTowerMission())
    }

    override fun shouldLoopMissions(): Boolean {
        return true
    }
}