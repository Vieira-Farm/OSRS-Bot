package scripting.swingcomponents.panels;

import scripting.swingcomponents.constants.Colors;
import scripting.swingcomponents.inputs.FxLabel;
import scripting.swingcomponents.inputs.buttons.FxButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputPanel extends JPanel {
	
	private FxLabel inputLabel;
	private JComponent input;
	
	public InputPanel(String labelText, JComponent input) {
		super();

		this.setLayout(new BorderLayout(0, 0));
		this.inputLabel = new FxLabel(labelText);
		this.inputLabel.setBorder(new EmptyBorder(3, 0, 3, 0));
		this.inputLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
		this.inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.inputLabel.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
		this.add(this.inputLabel, BorderLayout.NORTH);
		this.input = input;
		this.add(this.input);
		this.setOpaque(false);
	}
	
	@Override
	public Component add(Component comp) {
		if (comp instanceof JSlider) {
//			comp.setPreferredSize(new Dimension(270, 40));
		} else if (comp instanceof JCheckBox){
//			comp.setPreferredSize(new Dimension(260, 30));
			((JCheckBox) comp).setBorder(new EmptyBorder(0, 0, 0, 0));
			this.remove(this.inputLabel);
			super.add(comp, BorderLayout.SOUTH);
			return null;
		} else if (comp instanceof FxButton) {
//			comp.setPreferredSize(new Dimension(270, 50));
			this.remove(this.inputLabel);
			super.add(comp, BorderLayout.SOUTH);
			return null;
		} else {
//			comp.setPreferredSize(new Dimension(270, 30));
			this.inputLabel.setBorder(new EmptyBorder(3, 0, 10, 0));
		}
		
        return super.add(comp);
    }
}
