package scripts.missions.mage.processNodes

import scripts.client.clientextensions.Interfaces
import scripts.scripting.antiban.AntiBanSingleton
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode

class SelectedMainWorlds : SuccessProcessNode() {
    companion object {
        const val SELECT_MAIN_WORLD_INTERFACE_CHILD = 43
        const val SELECT_CHOOSE_BEGINNING_INTERFACE = 788
        const val CONFIRM_MAIN_WORLD_CHILD = 15
        const val CONFIRM_MAIN_WORLD_COMPONENT = 11
    }

    override fun getStatus(): String {
        return "Selecting Main Worlds"
    }

    override fun successExecute() {
        val startTime = System.currentTimeMillis()
        val worldInterfaceChild = Interfaces.get(SELECT_CHOOSE_BEGINNING_INTERFACE, SELECT_MAIN_WORLD_INTERFACE_CHILD)
        worldInterfaceChild?.click()

        AntiBanSingleton.get().lastReactionTime =
            AntiBanSingleton.get().generateReactionTime((System.currentTimeMillis() - startTime).toInt(), false)
        AntiBanSingleton.get().generateSupportingTrackerInfo((System.currentTimeMillis() - startTime).toInt(), false)
        AntiBanSingleton.get().sleepReactionTime()
    }
}