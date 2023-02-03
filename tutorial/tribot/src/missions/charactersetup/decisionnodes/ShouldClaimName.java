package missions.charactersetup.decisionnodes;

import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSVarBit;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import data.Constants;
import data.Variables;
import missions.charactersetup.processnodes.ClaimName;

public class ShouldClaimName extends ConstructorDecisionNode {

    private Variables variables;

    @Override
    public boolean isValid() {
        return Player.getRSPlayer().getName().contains("#") && RSVarBit.get(Constants.NAME_SET_VARBIT).getValue() == 0;
    }

    @Override
    public void initializeNode() {
        setTrueNode(new ClaimName());
        setFalseNode(ShouldPickGender.create(variables));
    }

    public static ShouldClaimName create(Variables variables) {
        ShouldClaimName node = new ShouldClaimName();
        node.variables = variables;
        node.initializeNode();
        return node;
    }

}
