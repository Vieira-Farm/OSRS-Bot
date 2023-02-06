package scripts.scripting.swingcomponents.inputs;

import javax.swing.*;
import java.awt.*;

public class FxLabel extends JLabel {

    public FxLabel(Icon icon) {
        super(icon);
    }

    public FxLabel(String text) {
        super(text);
    }

    public FxLabel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g);
    }
}
