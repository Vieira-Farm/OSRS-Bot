package scripts.script;

import org.tribot.api.General;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import scripts.scripting.frameworks.mission.missiontypes.Mission;
import scripts.scripting.frameworks.mission.scriptTypes.MissionScript;
import scripts.scripting.painting.scriptpaint.ScriptPaint;

public class TutorialIslandScript extends MissionScript implements Painting, Starting {

    @Override
    public Mission getMission() {
        return new TutorialIsland("1nce9gag");
    }

    @Override
    public void preScriptTasks() {
        General.println("TutorialIslandScript: preScriptTasks");
        this.setScriptPaint(
                new ScriptPaint.Builder(ScriptPaint.hex2Rgb("#ff0054"), "Tutorial Island")
                        .addField("Version", Double.toString(1.00))
                        .build()
        );
    }
}
