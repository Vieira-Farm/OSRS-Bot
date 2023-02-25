package scripts.tutorial.missions.login.processnodes

import org.tribot.api.Clicking
import org.tribot.api.General
import org.tribot.api2007.types.RSInterfaceChild
import scripts.client.clientextensions.Interfaces
import scripts.client.clientextensions.Timing
import scripts.tutorial.missions.login.decisionnodes.ShouldLogin
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode
import scripts.utils.ConditionUtilities

class ClickHereToPlay : SuccessProcessNode() {

    override fun getStatus(): String {
        return "Clicking Here to Play"
    }

    override fun successExecute() {
        val clickHereToPlay: RSInterfaceChild = Interfaces.get(ShouldLogin.LOBBY_INTERFACE_MASTER, CLICK_HERE_BUTTON)
        if (Interfaces.isInterfaceSubstantiated(clickHereToPlay)) {
            Clicking.click("", clickHereToPlay)
            Timing.waitCondition(
                ConditionUtilities.interfaceNotSubstantiated(clickHereToPlay),
                General.random(3000, 5000).toLong()
            )
        }
    }

    private companion object {
        private const val CLICK_HERE_BUTTON = 85

    }
}