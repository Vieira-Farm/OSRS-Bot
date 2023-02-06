package scripts.scripting.swingcomponents.panels;

import scripts.scripting.swingcomponents.constants.Colors;
import scripts.scripting.swingcomponents.inputs.FxList;

import javax.swing.*;
import java.awt.*;

public class FxListPanel extends JPanel {

    private FxList fxList;
    private JScrollPane listScrollPanel;

    public FxListPanel(FxList fxList) {
        this.fxList = fxList;
        this.listScrollPanel = new FxScrollPane();
        this.listScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.listScrollPanel.setViewportView(this.fxList);

        this.setOpaque(false);
        this.setLayout(new BorderLayout());
        this.add(listScrollPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Colors.DarkTheme.BACKGROUND.getColor());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}
