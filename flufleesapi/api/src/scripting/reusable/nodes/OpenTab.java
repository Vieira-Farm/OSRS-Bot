package scripting.reusable.nodes;

import org.tribot.api2007.GameTab;
import scripting.frameworks.modulardecisiontree.nodes.SuccessProcessNode;

public class OpenTab extends SuccessProcessNode {

    private String status;
    private GameTab.TABS tab;

    public OpenTab(String status, GameTab.TABS tab) {
        this.status = status;
        this.tab = tab;
    }

    public OpenTab(GameTab.TABS tab) {
        this.tab = tab;
        this.status = "Opening " + tab.name + " tab";
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void successExecute() {
        GameTab.open(tab);
    }
}