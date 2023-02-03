package scripts;

import org.tribot.api.General;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import script.TutorialIsland;
import script.TutorialIslandScript;

@ScriptManifest(name = "AllInOne", authors = { "Letsmakemoneybitch" }, category = "MoneyMaking")
public class AllInOne extends Script {

    @Override
    public void run() {
        General.println("COMECOU");
        TutorialIslandScript script = new TutorialIslandScript();
        script.run();
    }
}
