package missions.bank.processnodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import client.clientextensions.Equipment;
import client.clientextensions.Inventory;
import data.structures.ScriptVariables;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import utils.ConditionUtilities;

public class DepositItems extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Depositing Items";
    }

    @Override
    public void successExecute() {
        switch (ScriptVariables.getInstance().getRandomNumber(3)) {
            case 0:
                if (Inventory.getSize() > 0) {
                    Banking.depositAll();
                }
                Timing.waitCondition(ConditionUtilities.inventoryIsEmpty(), General.random(3000, 5000));
                break;
            case 1:
                if (Equipment.getItems().length > 0) {
                    Banking.depositEquipment();
                }
                Timing.waitCondition(ConditionUtilities.nothingEquipped(), General.random(3000, 5000));
                break;
            case 2:
                if (Inventory.getSize() > 0) {
                    Banking.depositAll();
                }
                if (Equipment.getItems().length > 0) {
                    Banking.depositEquipment();
                }
                Timing.waitCondition(ConditionUtilities.inventoryIsEmpty(), General.random(3000, 5000));
                Timing.waitCondition(ConditionUtilities.nothingEquipped(), General.random(3000, 5000));
                break;

        }
    }
}