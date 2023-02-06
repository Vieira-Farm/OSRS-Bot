package scripts.scripting.swingcomponents.inputs.buttons;

import javax.swing.*;
import java.awt.*;


public class SpinnerDownButton extends JButton {
	
	private int radius;
	
	public SpinnerDownButton(int radius) {
		this.radius = radius;
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRoundRect(0, 0, getWidth()-2, getHeight()-1, radius, 0);
        g2d.fillRect(getWidth()-3, 0, 2, getHeight()-2);
        super.paintComponent(g);
    }

}
