package scripts.scripting.swingcomponents.inputs;

import javax.swing.*;
import java.awt.*;

public class ClearFxLabel extends FxLabel {
    public ClearFxLabel(Icon icon) {
        super(icon);
    }

    public ClearFxLabel(String text) {
        super(text);
    }

    public ClearFxLabel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
