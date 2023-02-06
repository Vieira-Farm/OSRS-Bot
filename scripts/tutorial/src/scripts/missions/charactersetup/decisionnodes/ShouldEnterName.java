package scripts.missions.charactersetup.decisionnodes;

import scripts.data.Constants;
import scripts.data.Variables;
import scripts.missions.charactersetup.processnodes.EnterName;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSVarBit;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;

public class ShouldEnterName extends BaseDecisionNode {

    //name pick master = 558
    //name pick box to click = 11 if it has text we've looked up a name, otherwise we haven't
    //varbit 5607 == 1 when name picked, 0 when not picked
    //setting 281 = 2 when style done
    //varbit 5605 is status of checking, 1 = name taken, or no name entered, 2 = waiting on response from server, 4 = available, 5 = waiting to claim

    private Variables variables;

    @Override
    public boolean isValid() {
        RSPlayer rsPlayer = Player.getRSPlayer();
        String playerName = (rsPlayer != null && rsPlayer.getName() != null) ? rsPlayer.getName() : "#";
        return rsPlayer == null || playerName.contains("#") &&
                (RSVarBit.get(Constants.NAME_AVAILABILITY_VARBIT).getValue() == 1 ||
                        RSVarBit.get(Constants.NAME_AVAILABILITY_VARBIT).getValue() == 0);
    }

    @Override
    public void initializeNode() {
        setTrueNode(new EnterName(variables.getUsername()));
        setFalseNode(ShouldClaimName.create(variables));
    }

    public static ShouldEnterName create(Variables variables) {
        ShouldEnterName node = new ShouldEnterName();
        node.variables = variables;
        node.initializeNode();
        return node;
    }
}
