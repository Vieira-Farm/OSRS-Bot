package scripting.swingcomponents.panels.stepcontents;

import javax.swing.*;
import java.awt.*;

public class ColumnPanel extends JPanel {

    final int columnWidth = 340, spacerColumnWidth = 30;
    GridBagLayout columnLayout;

    public ColumnPanel() {
        super();

        columnLayout = new GridBagLayout();
        columnLayout.rowHeights = new int[]{0};
        columnLayout.rowWeights = new double[]{Double.MIN_VALUE};
        columnLayout.columnWidths = new int[]{0, 0};
        columnLayout.columnWeights = new double[]{1.0, 1.0};

        this.setLayout(columnLayout);
        this.setOpaque(false);
    }

    /**
     * Gets the constraints for the column panel. These are always the same.
     *
     * @return GridBagConstraints object representing the constraints for the column panel.
     */
    public static GridBagConstraints getColumnPanelConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        return constraints;
    }

    /**
     * Adds a column to the column layout
     */
    public void addColumn(Column column) {
        int columnsToAdd = columnLayout.columnWidths.length > 2 ? 2 : 1;
        int[] newColumnWidths = new int[columnLayout.columnWidths.length + columnsToAdd];
        double[] newColumnWeights = new double[columnLayout.columnWeights.length + columnsToAdd];
        //We want to copy all old widths/weights except the last (and the last values are constants so it's safe).
        //We do -3 as our new array is actually 2 bigger than the old array. Meaning that new.length-3 is the index
        //of old.length-1.
        for (int i = 0; i < newColumnWidths.length - (columnsToAdd + 1); i++) {
            newColumnWidths[i] = columnLayout.columnWidths[i];
            newColumnWeights[i] = columnLayout.columnWeights[i];
        }
        if (columnsToAdd == 2) {
            newColumnWidths[newColumnWidths.length-3] = spacerColumnWidth; //Adds a spacer column to space out columns
            newColumnWeights[newColumnWeights.length-3] = 0;
        }
        //Adds 15 to the width to account for the potential vertical scroll bar.
        newColumnWidths[newColumnWidths.length-2] = columnWidth + 15;
        newColumnWidths[newColumnWidths.length-1] = 0;
        newColumnWeights[newColumnWeights.length-2] = 0.0; //This is the weight of the new column.
        newColumnWeights[newColumnWeights.length-1] = 1.0;

        columnLayout.columnWidths = newColumnWidths;
        columnLayout.columnWeights = newColumnWeights;

        GridBagConstraints newColumnConstraints = new GridBagConstraints();
        newColumnConstraints.fill = GridBagConstraints.BOTH;
        newColumnConstraints.gridy = 0; //This is always 0 as we want to put our column in the first row.
        newColumnConstraints.gridx = newColumnWidths.length-2;
        column.setColumnWidth(columnWidth);
        this.add(column, newColumnConstraints);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
