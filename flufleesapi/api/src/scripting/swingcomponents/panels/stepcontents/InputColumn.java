package scripting.swingcomponents.panels.stepcontents;

import scripting.swingcomponents.panels.InputPanel;

import java.awt.*;

public class InputColumn extends Column {

    public void addInputPanel(InputPanel inputPanel) {
        int[] rowHeights = new int[contentsPaneLayout.rowHeights.length + 1];
        double[] rowWeights = new double[contentsPaneLayout.rowWeights.length + 1];
        for (int i = 0; i < rowHeights.length-1; i++) {
            rowHeights[i] = 0;
            rowWeights[i] = 0.0;
        }
        rowHeights[rowHeights.length-1] = 0;
        rowWeights[rowWeights.length-1] = 1.0;
        contentsPaneLayout.rowHeights = rowHeights;
        contentsPaneLayout.rowWeights = rowWeights;

        GridBagConstraints inputConstraints = new GridBagConstraints();
        inputConstraints.fill = GridBagConstraints.HORIZONTAL;
        inputConstraints.insets = new Insets(0, 0, 5, 0);
        inputConstraints.gridx = 0;
        inputConstraints.gridy = contentsPaneLayout.rowHeights.length - 2;

        //Remove 20 from the width to account for the potential vertical scroll bar.
        inputPanel.setPreferredSize(new Dimension(columnWidth - 20, (32 + 32))); //32 is the label height
        contentsPane.add(inputPanel, inputConstraints);
    }
}
