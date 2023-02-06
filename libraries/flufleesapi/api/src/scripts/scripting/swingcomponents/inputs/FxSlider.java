package scripts.scripting.swingcomponents.inputs;

import scripts.scripting.swingcomponents.constants.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class FxSlider extends JSlider {

    private Color sliderColor;

    public FxSlider(Color sliderColor, int minValue, int maxValue) {
        super(minValue, maxValue);
        this.sliderColor = sliderColor;
        this.setFont(new Font("Roboto", Font.PLAIN, 14));
        this.setMajorTickSpacing(25);
        this.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
        this.setPaintLabels(true);
        this.setUI(new LightSliderUI(this, sliderColor));
        this.setValue(50);
        MouseAdapter ma = new SliderPopupListener();
        this.addMouseMotionListener(ma);
        this.addMouseListener(ma);
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g);
    }

    public class CustomWindow extends JWindow {

        public CustomWindow() {
            this.setShape(new RoundRectangle2D.Double(0, 0, 35, 35, 8, 8));
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setBackground(Color.black);
            g2.setColor(Color.black);
            super.paint(g);
        }

    }

    public class CustomLabel extends JLabel {

        public CustomLabel() {
            this.setBackground(sliderColor);
            this.setOpaque(true);
            this.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
            this.setFont(new Font("Roboto", Font.PLAIN, 18));
            this.setText("");
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            super.paint(g);
        }
    }

    class SliderPopupListener extends MouseAdapter {
        private final CustomWindow toolTip = new CustomWindow();
        private final CustomLabel label = new CustomLabel();
        private final Dimension size = new Dimension(35, 35);
        private int prevValue = -1;

        protected SliderPopupListener() {
            super();
            label.setBackground(sliderColor);

            toolTip.add(label);
            toolTip.setSize(size);
        }

        protected void updateToolTip(MouseEvent e) {
            JSlider slider = (JSlider) e.getComponent();
            int intValue = slider.getValue();
            if (prevValue != intValue) {
                label.setText(String.format("%2d", slider.getValue()));
                Point pt = e.getPoint();
                pt.y = -size.height - 5;
                SwingUtilities.convertPointToScreen(pt, e.getComponent());
                pt.translate(-size.width / 2, 0);
                toolTip.setLocation(pt);
            }
            prevValue = intValue;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            toolTip.setVisible(true);
            updateToolTip(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            toolTip.setVisible(true);
            updateToolTip(e);

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            toolTip.setVisible(false);
        }
    }
}