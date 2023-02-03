package missions.charactersetup.decisionnodes;

import scripting.frameworks.modulardecisiontree.nodes.FactoryDecisionNode;
import data.Variables;
import missions.charactersetup.processnodes.PickGender;
import missions.charactersetup.processnodes.StyleCharacter;

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
