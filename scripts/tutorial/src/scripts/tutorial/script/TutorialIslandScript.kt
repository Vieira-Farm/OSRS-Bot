package scripts.tutorial.script

import org.tribot.api.General
import org.tribot.script.ScriptManifest
import org.tribot.script.interfaces.Arguments
import org.tribot.script.interfaces.Painting
import org.tribot.script.interfaces.Starting
import scripts.data.accounts.AccountDetails
import scripts.tutorial.missions.login.LoginMission
import scripts.scripting.frameworks.mission.missiontypes.Mission
import scripts.scripting.frameworks.mission.missiontypes.Mission.EarlyEndMission
import scripts.scripting.frameworks.mission.scriptTypes.MissionScript
import scripts.scripting.painting.scriptpaint.ScriptPaint
import scripts.utils.ArgumentUtilities

@ScriptManifest(
    authors = ["Letsmakemoneybitch"],
    category = "Vieira's Bot Farm",
    name = "Tutorial Island",
    version = 1.0,
    description = "Finishes the Tutorial Island.",
    gameMode = 1
)
class TutorialIslandScript(
    private val accountDetails: AccountDetails
): MissionScript(), Painting, Starting {
    override fun getMission(): Mission {
        General.println("username: ${accountDetails.username}")
        return LoginMission(accountDetails)
    }
    override fun onStart() {
        this.loginBotState = true
    }

    override fun preScriptTasks() {
        General.println("TutorialIslandScript: preScriptTasks")
        setScriptPaint(
            ScriptPaint.Builder(ScriptPaint.hex2Rgb("#ff0054"), "Tutorial Island")
                .addField("Version", 1.00.toString())
                .build()
        )
    }
}