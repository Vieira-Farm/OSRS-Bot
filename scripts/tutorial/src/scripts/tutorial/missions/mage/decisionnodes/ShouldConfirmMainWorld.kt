package scripts.tutorial.missions.mage.decisionnodes

import org.tribot.api2007.Game
import scripts.client.clientextensions.Interfaces
import scripts.tutorial.data.Constants
import scripts.tutorial.missions.mage.processNodes.ConfirmMainWorld
import scripts.tutorial.missions.mage.processNodes.SelectedMainWorlds
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode

class ShouldConfirmMainWorld : ConstructorDecisionNode() {
    override fun isValid(): Boolean {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 670 &&
                Interfaces.isInterfaceSubstantiated(SelectedMainWorlds.SELECT_CHOOSE_BEGINNING_INTERFACE) &&
                !(Interfaces.get(
                    SelectedMainWorlds.SELECT_CHOOSE_BEGINNING_INTERFACE,
                    SelectedMainWorlds.CONFIRM_MAIN_WORLD_CHILD,
                    SelectedMainWorlds.CONFIRM_MAIN_WORLD_COMPONENT
                )?.isHidden ?: true)

    }

    override fun initializeNode() {
        setTrueNode(ConfirmMainWorld())
        setFalseNode(ShouldOpenMagic())
    }
}
