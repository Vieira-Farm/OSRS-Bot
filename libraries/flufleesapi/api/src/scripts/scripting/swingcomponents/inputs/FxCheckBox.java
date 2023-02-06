package scripts.scripting.swingcomponents.inputs;

import scripts.scripting.swingcomponents.constants.Colors;
import scripts.scripting.swingcomponents.jiconfont.icons.FontAwesome;
import scripts.scripting.swingcomponents.jiconfont.swing.IconFontSwing;

import javax.swing.*;
import java.awt.*;

public class FxCheckBox extends JCheckBox {
	
	private final Color CHECK_GREEN = new Color(23, 163, 101);
	
	public FxCheckBox (String text) {
		super(text);
		this.setFont(new Font("Roboto", Font.PLAIN, 16));
		this.setForeground(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
		this.setIcon(IconFontSwing.buildIcon(FontAwesome.SQUARE_O, 32, getForeground()));
		this.setRolloverSelectedIcon(IconFontSwing.buildIcon(FontAwesome.CHECK_SQUARE, 30, CHECK_GREEN.brighter()));
		this.setSelectedIcon(IconFontSwing.buildIcon(FontAwesome.CHECK_SQUARE, 30, CHECK_GREEN));
		this.setFocusPainted(false);
		this.setEnabled(true);
		this.setOpaque(false);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g); 
        if (this.isSelected()) {
            this.setBackground(Color.white);
        }
    }


}
