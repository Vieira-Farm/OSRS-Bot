package scripts.tutorial.missions.login.decisionnodes

import org.tribot.api2007.Login
import scripts.data.accounts.AccountDetails
import scripts.tutorial.missions.login.processnodes.ClickHereToPlay
import scripts.tutorial.missions.login.processnodes.LoginNode

import scripts.scripting.frameworks.modulardecisiontree.nodes.FactoryDecisionNode


class ShouldLogin(private var accountDetails: AccountDetails) : FactoryDecisionNode() {

    override fun isValid(): Boolean {
        return Login.getLoginState() !== Login.STATE.INGAME
    }

    override fun initializeNode() {
        setTrueNode(LoginNode(accountDetails))
        setFalseNode(ClickHereToPlay())
    }

    companion object {
        const val LOBBY_INTERFACE_MASTER = 378
        fun create(accountDetails: AccountDetails): ShouldLogin {
            val shouldLogin = ShouldLogin(accountDetails)
            shouldLogin.initializeNode()
            return shouldLogin
        }
    }
}