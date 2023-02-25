package scripts.tutorial.missions.bank.decisionnodes;

import scripts.client.clientextensions.Inventory;
import scripts.tutorial.data.Constants;
import scripts.data.structures.ScriptVariables;
import scripts.tutorial.missions.bank.processnodes.DepositItems;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import scripts.scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;

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