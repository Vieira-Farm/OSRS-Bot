package scripts;

import org.tribot.api.General;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import scripts.data.accounts.AccountDetails;
import scripts.accountcreator.script.AccountCreatorScript;
import scripts.tutorial.script.TutorialIslandScript;

@ScriptManifest(name = "AllInOne", authors = {"Letsmakemoneybitch"}, category = "Vieira's Bot Farm")
public class AllInOne extends Script {
    @Override
    public void run() {
        General.println("Vieira's Bot Farm started.");

//        AccountCreatorScript accountCreatorScript = new AccountCreatorScript(
//                "vieirabrabo_54@gmail.com",
//                "newVieira8",
//                "5.153.235.98",
//                "41003",
//                "igordmv1995",
//                "E7BDD74B1081229DC9E9854B5D1854F7",
//                "AntiCaptcha",
//                "49be110ab3e14e5db8d380c702b347fc"
//        );
//
//        accountCreatorScript.run();
//
//        AccountDetails accountDetails = new AccountDetails(
//                "vieirastest431@gmail.com",
//                "newVieira8",
//                "VieiraBrabo34",
//                true);
//
//        TutorialIslandScript tutorialScript = new TutorialIslandScript(accountDetails);
//        tutorialScript.run();
    }
}
