package scripts.restlessghost.script

import org.tribot.api.General
import org.tribot.script.ScriptManifest
import org.tribot.script.interfaces.Painting
import org.tribot.script.interfaces.Starting
import scripts.restlessghost.missions.RestlessGhost
import scripts.scripting.frameworks.mission.missiontypes.Mission
import scripts.scripting.frameworks.mission.scriptTypes.MissionScript
import scripts.scripting.painting.scriptpaint.ScriptPaint

@ScriptManifest(
    authors = ["Letsmakemoneybitch"],
    category = "Vieira's Bot Farm",
    name = "[Quest] - Restless Ghost",
    version = 1.0,
    description = "Finishes the Restless Ghost quest.",
    gameMode = 1
)
class RestlessGhostScript : MissionScript(), Painting, Starting {
    val scriptPaint: ScriptPaint = ScriptPaint.Builder(
        ScriptPaint.hex2Rgb("#ff0054"), "Restless Ghost"
    )
        .addField("Version", 1.00.toString())
        .build()

    override fun onStart() {
        this.loginBotState = true
    }

    override fun preScriptTasks() {
        General.println("RestlessGhostScript: preScriptTasks")
        setScriptPaint(
            ScriptPaint.Builder(ScriptPaint.hex2Rgb("#ff0054"), "Restless Ghost")
                .addField("Version", 1.00.toString())
                .build()
        )
    }
    override fun getMission(): Mission {
        return RestlessGhost()
    }
}