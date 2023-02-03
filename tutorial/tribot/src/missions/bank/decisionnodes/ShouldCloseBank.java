package missions.bank.decisionnodes;

import missions.bank.processnodes.CloseBank;
import org.tribot.api2007.Banking;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldCloseBank extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Banking.isBankScreenOpen();
    }

    @Override
    public void initializeNode() {
        setTrueNode(new CloseBank());
        setFalseNode(new ShouldOpenPoll());
    }

}
