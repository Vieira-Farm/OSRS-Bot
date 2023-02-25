package scripts.tutorial.missions.bank.decisionnodes;

import scripts.tutorial.missions.bank.processnodes.CloseBank;
import org.tribot.api2007.Banking;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

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
