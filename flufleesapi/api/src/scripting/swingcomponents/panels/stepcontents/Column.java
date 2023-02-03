package scripting.swingcomponents.panels.stepcontents;

import scripting.swingcomponents.panels.FxScrollPane;

import javax.swing.*;
import java.awt.*;

public class Column extends FxScrollPane {

    protected JPanel contentsPane, temp;
    protected int columnWidth;
    protected GridBagLayout contentsPaneLayout;

    public Column() {
        columnWidth = 0;
        contentsPane = new JPanel();
        contentsPane.setOpaque(false);
        contentsPaneLayout = new GridBagLayout();
        contentsPaneLayout.rowHeights = new int[]{0};
        contentsPaneLayout.rowWeights = new double[]{1.0};
        contentsPaneLayout.columnWeights = new double[]{1.0};
        contentsPane.setLayout(contentsPaneLayout);

        this.setViewportView(contentsPane);
        this.getViewport().setOpaque(false);
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }
}
