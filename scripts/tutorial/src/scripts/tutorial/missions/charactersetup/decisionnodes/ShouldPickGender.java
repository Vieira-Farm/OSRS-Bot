package scripts.tutorial.missions.charactersetup.decisionnodes;

import scripts.scripting.frameworks.modulardecisiontree.nodes.FactoryDecisionNode;
import scripts.tutorial.data.Variables;
import scripts.tutorial.missions.charactersetup.processnodes.PickGender;
import scripts.tutorial.missions.charactersetup.processnodes.StyleCharacter;

public class ShouldPickGender extends FactoryDecisionNode {

    private Variables variables;

    @Override
    public boolean isValid() {
        return !variables.hasPickedGender();
    }

    @Override
    public void initializeNode() {
        setTrueNode(new PickGender(variables));
        setFalseNode(new StyleCharacter());
    }

    public static ShouldPickGender create(Variables variables) {
        ShouldPickGender decisionNode = new ShouldPickGender();
        decisionNode.variables = variables;
        decisionNode.initializeNode();
        return decisionNode;

    }
}
