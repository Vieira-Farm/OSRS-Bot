package script;

import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;
import scripting.frameworks.mission.missiontypes.Mission;
import scripting.frameworks.mission.scriptTypes.MissionScript;
import scripting.painting.scriptpaint.ScriptPaint;

public class TutorialIslandScript extends MissionScript implements Painting, Starting {

    @Override
    public Mission getMission() {
        return new TutorialIsland("BunnyEars15");
    }

    @Override
    public void preScriptTasks() {
        this.setScriptPaint(
                new ScriptPaint.Builder(ScriptPaint.hex2Rgb("#ff0054"), "Tutorial Island")
                        .addField("Version", Double.toString(1.00))
                        .build()
        );
    }
}
