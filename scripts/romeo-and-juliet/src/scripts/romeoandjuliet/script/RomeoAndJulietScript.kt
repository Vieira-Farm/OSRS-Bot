package scripts.romeoandjuliet.script

import org.tribot.api.General
import org.tribot.script.ScriptManifest
import org.tribot.script.interfaces.Painting
import org.tribot.script.interfaces.Starting
import scripts.romeoandjuliet.missions.RomeoAndJuliet
import scripts.scripting.frameworks.mission.missiontypes.Mission
import scripts.scripting.frameworks.mission.scriptTypes.MissionScript
import scripts.scripting.painting.scriptpaint.ScriptPaint

@ScriptManifest(
    authors = ["Letsmakemoneybitch"],
    category = "Vieira's Bot Farm",
    name = "[Quest] - Romeo And Juliet",
    version = 1.0,
    description = "Finishes the Romeo and Juliet quest.",
    gameMode = 1
)
class RomeoAndJulietScript : MissionScript(), Painting, Starting {

    override fun onStart() {
        this.loginBotState = true
    }

    override fun preScriptTasks() {
        General.println("RomeoAndJulietScript: preScriptTasks")
        setScriptPaint(
            ScriptPaint.Builder(ScriptPaint.hex2Rgb("#ff0054"), "Romeo And Juliet")
                .addField("Version", 1.00.toString())
                .build()
        )
    }
    override fun getMission(): Mission {
        return RomeoAndJuliet()
    }

}
