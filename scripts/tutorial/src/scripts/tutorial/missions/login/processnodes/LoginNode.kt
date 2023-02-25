package scripts.tutorial.missions.login.processnodes

import org.tribot.api2007.Login
import scripts.data.accounts.AccountDetails

import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode

class LoginNode(private val accountDetails: AccountDetails) : SuccessProcessNode() {
    override fun getStatus(): String {
        return "Logging In"
    }

    override fun successExecute() {
        Login.login(accountDetails.email, accountDetails.password)
    }
}
