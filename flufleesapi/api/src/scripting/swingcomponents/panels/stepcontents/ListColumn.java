package scripting.swingcomponents.panels.stepcontents;

import scripting.swingcomponents.inputs.FxList;
import scripting.swingcomponents.panels.FxListPanel;

public class ListColumn extends Column {

    private FxListPanel listPanel;

    public void setList(FxList list) {
        setListPanel(new FxListPanel(list));
    }

    public void setListPanel(FxListPanel listPanel) {
        this.listPanel = listPanel;
        this.setViewportView(this.listPanel);
    }
}
