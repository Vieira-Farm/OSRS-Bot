package scripts.missions.mage.decisionnodes

import org.tribot.api2007.Game
import scripts.client.clientextensions.Interfaces
import scripts.data.Constants
import scripts.missions.mage.processNodes.SelectedMainWorlds
import scripts.missions.mage.processNodes.SelectedMainWorlds.Companion.SELECT_CHOOSE_BEGINNING_INTERFACE
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode

class ShouldChooseBeginning : ConstructorDecisionNode() {
    override fun isValid(): Boolean {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) == 670 &&
                Interfaces.isInterfaceSubstantiated(SELECT_CHOOSE_BEGINNING_INTERFACE) &&
                Interfaces.get(
                    SELECT_CHOOSE_BEGINNING_INTERFACE,
                    SelectedMainWorlds.CONFIRM_MAIN_WORLD_CHILD, 11
                )?.isHidden ?: true

    }

    override fun initializeNode() {
        setTrueNode(SelectedMainWorlds())
        setFalseNode(ShouldConfirmMainWorld())
    }
}
