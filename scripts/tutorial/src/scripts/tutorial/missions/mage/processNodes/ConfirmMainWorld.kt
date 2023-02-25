package scripts.tutorial.missions.mage.processNodes

import scripts.client.clientextensions.Interfaces
import scripts.scripting.antiban.AntiBanSingleton
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode

class ConfirmMainWorld : SuccessProcessNode() {
    override fun getStatus(): String {
        return "Confirming Main World"
    }

    override fun successExecute() {
        val startTime = System.currentTimeMillis()
        Interfaces.get(
            SelectedMainWorlds.SELECT_CHOOSE_BEGINNING_INTERFACE,
            SelectedMainWorlds.CONFIRM_MAIN_WORLD_CHILD,
            SelectedMainWorlds.CONFIRM_MAIN_WORLD_COMPONENT
        )?.click()

        AntiBanSingleton.get().lastReactionTime =
            AntiBanSingleton.get().generateReactionTime((System.currentTimeMillis() - startTime).toInt(), false)
        AntiBanSingleton.get().generateSupportingTrackerInfo((System.currentTimeMillis() - startTime).toInt(), false)
        AntiBanSingleton.get().sleepReactionTime()
    }
}