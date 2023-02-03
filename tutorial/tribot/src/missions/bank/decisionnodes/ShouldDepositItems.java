package missions.bank.decisionnodes;

import client.clientextensions.Inventory;
import data.Constants;
import data.structures.ScriptVariables;
import missions.bank.processnodes.DepositItems;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

public class ShouldDepositItems extends ConstructorDecisionNode {

    @Override
    public boolean isValid() {
        return Banking.isBankScreenOpen() &&
                (ScriptVariables.getInstance().getRandomNumber(4) !=
                            Constants.DepositSettings.NONE.getDepositValue() &&
                        (Inventory.getSize() > 0 || Equipment.getItems().length > 0));
    }

    @Override
    public void initializeNode() {
        setTrueNode(new DepositItems());
        setFalseNode(new ShouldCloseBank());
    }

}