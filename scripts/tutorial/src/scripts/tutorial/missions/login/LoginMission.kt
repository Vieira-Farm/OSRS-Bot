package scripts.tutorial.missions.login

import org.tribot.api2007.Login
import scripts.data.accounts.AccountDetails
import scripts.tutorial.missions.login.decisionnodes.ShouldLogin
import scripts.scripting.frameworks.mission.missiontypes.Mission.MissionGameState
import scripts.scripting.frameworks.mission.missiontypes.TreeMission
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode


class LoginMission(private var accountDetails: AccountDetails) : TreeMission {

    override fun getMissionName(): String? {
        return "LoginNode"
    }

    override fun isMissionValid(): Boolean {
        return Login.getLoginState() != Login.STATE.INGAME
    }

    override fun isMissionCompleted(): Boolean {
        return !isMissionValid
    }

    override fun getTreeRoot(): BaseDecisionNode? {
        return ShouldLogin.create(accountDetails)
    }

    override fun getMissionGameState(): MissionGameState {
        return MissionGameState.OUT_OF_GAME
    }
}
