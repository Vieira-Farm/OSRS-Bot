package scripts.scripting.swingcomponents.inputs.buttons;

import javax.swing.*;
import java.awt.*;


public class SpinnerUpButton extends JButton {
	
	private int radius;
	
	public SpinnerUpButton(int radius) {
		this.radius = radius;
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRoundRect(0, 1, getWidth()-2, getHeight(), radius, 0);
        g2d.fillRect(getWidth()-3, 2, 2, getHeight());
        super.paintComponent(g);
    }

}
