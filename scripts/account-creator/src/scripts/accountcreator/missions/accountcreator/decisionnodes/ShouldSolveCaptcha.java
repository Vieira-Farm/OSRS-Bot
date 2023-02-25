package scripts.accountcreator.missions.accountcreator.decisionnodes;

import scripts.accountcreator.data.Variables;
import scripts.accountcreator.missions.accountcreator.processnodes.CreateAccount;
import scripts.accountcreator.missions.accountcreator.processnodes.SolveCaptcha;
import scripts.scripting.frameworks.modulardecisiontree.nodes.FactoryDecisionNode;

public class ShouldSolveCaptcha extends FactoryDecisionNode {

    private Variables variables;

    private ShouldSolveCaptcha() {}

    @Override
    public boolean isValid() {
        return !variables.captchaSolver.isCaptchaSolved();
    }

    @Override
    public void initializeNode() {
        setTrueNode(new SolveCaptcha(variables.captchaSolver));
        setFalseNode(new CreateAccount(variables));
    }

    public static ShouldSolveCaptcha create(Variables variables) {
        ShouldSolveCaptcha shouldSolveCaptcha = new ShouldSolveCaptcha();
        shouldSolveCaptcha.variables = variables;
        shouldSolveCaptcha.initializeNode();
        return shouldSolveCaptcha;
    }
}
