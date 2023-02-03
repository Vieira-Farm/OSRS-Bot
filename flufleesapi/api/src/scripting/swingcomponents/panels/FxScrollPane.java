package scripting.swingcomponents.panels;

import scripting.swingcomponents.constants.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class FxScrollPane extends JScrollPane {

    protected int verticalScrollBarWidth = 0, horizontalScrollBarHeight = 0;;
    private ChangeListener viewportChangeListener;

    public FxScrollPane() {
        this.setOpaque(false);
        this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setWheelScrollingEnabled(true);
        this.setBorder(new EmptyBorder(0, 10, 0, 0));
        this.getVerticalScrollBar().setUI(new FxScrollbarUI());
        this.getHorizontalScrollBar().setUI(new FxScrollbarUI());
        this.setViewportChangeListener(e -> { //Used to center the viewport contents if the scrollbar becomes visible.
            if (getVerticalScrollBar().getWidth() != this.getVerticalScrollBarWidth()) {
                this.setVerticalScrollBarWidth(getVerticalScrollBar().getWidth());
                setBorder(new EmptyBorder(0, verticalScrollBarWidth, 0, 0));
            }
            if (getHorizontalScrollBar().getHeight() != this.getHorizontalScrollBarHeight()) {
                this.setHorizontalScrollBarHeight(getHorizontalScrollBar().getHeight());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g);
    }

    public int getVerticalScrollBarWidth() {
        return verticalScrollBarWidth;
    }

    public void setVerticalScrollBarWidth(int verticalScrollBarWidth) {
        this.verticalScrollBarWidth = verticalScrollBarWidth;
    }

    public int getHorizontalScrollBarHeight() {
        return horizontalScrollBarHeight;
    }

    public void setHorizontalScrollBarHeight(int horizontalScrollBarHeight) {
        this.horizontalScrollBarHeight = horizontalScrollBarHeight;
    }

    public ChangeListener getViewportChangeListener() {
        return viewportChangeListener;
    }

    public void setViewportChangeListener(ChangeListener viewportChangeListener) {
        this.getViewport().removeChangeListener(this.getViewportChangeListener());
        this.viewportChangeListener = viewportChangeListener;
        this.getViewport().addChangeListener(this.getViewportChangeListener());
    }
}

class FxScrollbarUI extends BasicScrollBarUI {
    private final Dimension noButtonDimension = new Dimension();

    public FxScrollbarUI() {
        super();
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return noButtonDimension;
            }
        };
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return noButtonDimension;
            }
        };
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        int width = r.width > r.height ? r.width + 100 : r.width;
        int height = r.height > r.width ? r.height + 100 : r.height;
        g.clearRect(r.x, r.y, width, height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = null;
        JScrollBar sb = (JScrollBar) c;
        if (!sb.isEnabled()) {
            return;
        } else if (isThumbRollover()) {
            color = Colors.DarkTheme.SCROLL_BAR_HOVER_COLOR.getColor();
        } else {
            color = Colors.DarkTheme.FIELD_TEXT_COLOR.getColor();
        }
        g2.setPaint(color);
        if (r.width > r.height) { //Horizontal scrollbar
            g2.fillRoundRect(r.x + 6, r.y + r.height / 4, r.width - 12, r.height/2, 10, 10);
            g2.setPaint(Colors.DarkTheme.SCROLL_BAR_BORDER_COLOR.getColor());
            g2.drawRoundRect(r.x + 6, r.y + r.height / 4, r.width - 12, r.height/2, 10, 10);
        } else { //Vertical scrollbar
            g2.fillRoundRect(r.x + r.width / 4, r.y + 6, r.width / 2, r.height - 12, 10, 10);
            g2.setPaint(Colors.DarkTheme.SCROLL_BAR_BORDER_COLOR.getColor());
            g2.drawRoundRect(r.x + r.width / 4, r.y + 6, r.width / 2, r.height - 12, 10, 10);
        }
        g2.dispose();
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
    }
}
