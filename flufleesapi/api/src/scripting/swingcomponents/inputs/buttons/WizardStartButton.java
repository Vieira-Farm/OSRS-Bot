package scripting.swingcomponents.inputs.buttons;

import scripting.swingcomponents.jiconfont.IconCode;
import scripting.swingcomponents.jiconfont.swing.IconFontSwing;
import scripting.swingcomponents.panels.wizard.StartButtonPane.ButtonLayout;
import scripting.swingcomponents.swingmaterialdesign.MaterialButton;
import scripting.swingcomponents.swingmaterialdesign.MaterialShadow;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class WizardStartButton extends MaterialButton {

	private static final long serialVersionUID = 9146242376506217498L;

	public WizardStartButton(String text, Color backgroundColor, ButtonLayout buttonLayout) {
		this(text, backgroundColor, buttonLayout, null);
	}
	
	public WizardStartButton(String text, Color backgroundColor, ButtonLayout buttonLayout, IconCode iconCode) {
		super(text, 600);
		setBackground(backgroundColor);
		setFont(new Font("Roboto", Font.PLAIN, buttonLayout.getFontSize()));
		setBorderRadius(10);
		setPreferredSize(buttonLayout.getButtonSize());
		setDisableElevation(true);
        setForeground(Color.WHITE);
		if (iconCode != null) {
			setIcon(IconFontSwing.buildIcon(iconCode, buttonLayout.getIconSize(), getForeground()));
		}
	}

	@Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (isEnabled()) {
        	elevationEffect.paint(g);
            g2.translate(MaterialShadow.OFFSET_LEFT, MaterialShadow.OFFSET_TOP);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - offsetHorizontal,
                    getHeight() - offsetVertical, borderRadius, borderRadius));
            g2.setColor(new Color(rippleColor.getRed() / 255f, rippleColor.getBlue() / 255f,
                    rippleColor.getBlue() / 255f, 0.12f));
            if (buttonType == ButtonType.FLAT) {
                elevationEffect.paint(g);
            }
        } else {
            Color bg = getBackground();
            g2.setColor(new Color(bg.getRed() / 255f, bg.getGreen() / 255f, bg.getBlue() / 255f, 0.6f));
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - offsetHorizontal,
                    getHeight() - offsetVertical, borderRadius * 2, borderRadius * 2));
        }

        FontMetrics metrics = g.getFontMetrics(getFont());
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent() +
                (getIcon() != null ? getIcon().getIconHeight() - 20 : 0);
        g2.setFont(getFont());
        if (isEnabled()) {
            g2.setColor(getForeground());
        } else {
            Color fg = getForeground();
            g2.setColor(new Color(fg.getRed() / 255f, fg.getGreen() / 255f, fg.getBlue() / 255f, 0.6f));
        }
        if (getIcon() != null) {
        	g.setColor(g2.getColor());
        	getIcon().paintIcon(this, g, (getWidth() - getIcon().getIconWidth())/2,
                    (getHeight() - getIcon().getIconHeight())/2 - 10);
        }
        
        g2.drawString(getText(), x, y);

        if (isEnabled() && !disableRipple) {
            g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth() - offsetHorizontal,
                    getHeight() - offsetVertical, Math.max(borderRadius * 2 - 4, 0), Math.max(borderRadius * 2 - 4, 0)));
            g2.setColor(rippleColor);
            rippleEffect.paint(g2);
        }
    }
	
}
