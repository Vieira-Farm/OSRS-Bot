package scripts;

import org.tribot.api.General;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

@ScriptManifest(name = "MyMultiScript", authors = { "Wastedbro" }, category = "Template")
public class MyScript extends Script {

    @Override
    public void run() {
        General.println(SampleHelper.getHello());
    }
}
