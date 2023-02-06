package scripts.scripting.swingcomponents.gui.standard;

import scripts.scripting.swingcomponents.frames.BaseGuiFrame;
import scripts.scripting.swingcomponents.panels.header.HeaderPanel;

import java.awt.*;

public abstract class AbstractGui extends BaseGuiFrame {

    protected HeaderPanel headerPanel;

    public AbstractGui(String frameTitle, String description) {
        super(frameTitle);

        headerPanel = new HeaderPanel(frameTitle, description, 10);
        this.getContentPane().add(headerPanel, BorderLayout.NORTH);
    }

//    public void setHelpFrame(HelpFrame helpFrame) {
//        this.headerPanel.setHelpFrame(helpFrame);
//    }
//
//    public HelpFrame getHelpframe() {
//        return this.headerPanel.getHelpFrame();
//    }

    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }
}
