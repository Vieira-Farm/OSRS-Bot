package scripting.swingcomponents.inputs;

import scripting.swingcomponents.constants.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class FxTextField extends JTextField {

    private Shape shape;
    private int borderRadius;

    public FxTextField() {
        this(5);
    }

    public FxTextField(int borderRadius) {
        this(borderRadius, 20);
    }

    public FxTextField(int borderRadius, int numberColumns) {
        super(numberColumns);
        setOpaque(false);
        this.borderRadius = borderRadius;
        this.setBorder(new EmptyBorder(5, 10, 5, 10));
        this.setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
        this.setFont(new Font("Roboto", Font.PLAIN, 14));
        this.setForeground(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
        this.setCaretColor(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        }
        return shape.contains(x, y);
    }
}
