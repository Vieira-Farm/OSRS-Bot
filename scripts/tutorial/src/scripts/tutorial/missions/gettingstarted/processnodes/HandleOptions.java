package scripts.tutorial.missions.gettingstarted.processnodes;

import scripts.client.clientextensions.Options;
import scripts.data.structures.ScriptVariables;
import scripts.scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;
import scripts.utils.numbers.LFSR;

public class HandleOptions extends SuccessProcessNode {

    @Override
    public String getStatus() {
        return "Handling Necessary Options";
    }

    @Override
    public void successExecute() {
        int seed = ScriptVariables.getInstance().getRandomNumber(3) + 1; //Convert number that is in the range of [0,2] to [1,3]
        LFSR register = new LFSR(seed, 1, 2);
        for (int i = 0; i < 3; i++) {
            switch (register.getNextInt()) { //[Generate random int from 1 -> 3 with no repeats
                case 3:
                    Options.setShiftClickDropEnabled(ScriptVariables.getInstance().getRandomBoolean());
                    break;
                case 2:
                    Options.setEscapeClose(ScriptVariables.getInstance().getRandomBoolean());
                    break;
                case 1:
                    if (ScriptVariables.getInstance().getRandomBoolean()) {
                        Options.muteSounds();
                    }
                    break;
            }
        }
    }
}