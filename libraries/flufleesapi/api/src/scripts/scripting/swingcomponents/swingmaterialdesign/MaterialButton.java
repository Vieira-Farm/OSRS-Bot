package scripts.scripting.swingcomponents.swingmaterialdesign;

import scripts.scripting.swingcomponents.swingmaterialdesign.resource.MaterialColor;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * A Material Design button.
 *
 * @author bilux (i.bilux@gmail.com), modified by Fluffee
 */
public class MaterialButton extends JButton {

	protected RippleEffect rippleEffect;
    protected ElevationEffect elevationEffect;
    protected ButtonType buttonType = ButtonType.DEFAULT;
    protected boolean isMousePressed = false, isMouseOver = false, disableClicking = false, disableRipple = false, disableElevation = false;
    protected Color rippleColor = Color.WHITE;
    protected int borderRadius = 2, offsetVertical = 0, offsetHorizontal = 0;

    public MaterialButton(String buttonText) {
    	this();
    	setText(buttonText);
    }

    public MaterialButton(String buttonText, int rippleDuration) {
        this();
        setText(buttonText);
        rippleEffect.getAnimation().setDuration(rippleDuration);
    }
    
    /**
     * Creates a new button.
     */
    public MaterialButton() {
        rippleEffect = RippleEffect.applyTo(this);
        elevationEffect = ElevationEffect.applyTo(this, 0);
        setOpaque(false);
        offsetVertical = buttonType == ButtonType.FLAT ? 0 : MaterialShadow.OFFSET_TOP + MaterialShadow.OFFSET_BOTTOM;
        offsetHorizontal = buttonType == ButtonType.FLAT ? 0 : MaterialShadow.OFFSET_LEFT + MaterialShadow.OFFSET_RIGHT;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            	if (disableClicking) {
            		return;
            	}
                isMousePressed = true;
            	if (!disableElevation) {
                    elevationEffect.setLevel(getElevation());
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            	if (disableClicking) {
            		return;
            	}
                isMousePressed = false;
                if (!disableElevation) {
                    elevationEffect.setLevel(getElevation());
                }
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isMouseOver = true;
                if (!disableElevation) {
                    elevationEffect.setLevel(getElevation());
                }
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isMouseOver = false;
                if (!disableElevation) {
                    elevationEffect.setLevel(getElevation());
                }
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });

        setUI(new BasicButtonUI() {
            @Override
            public boolean contains(JComponent c, int x, int y) {
                return x > MaterialShadow.OFFSET_LEFT && y > MaterialShadow.OFFSET_TOP
                        && x < getWidth() - MaterialShadow.OFFSET_RIGHT && y < getHeight() - MaterialShadow.OFFSET_BOTTOM;
            }
        });
    }

    /**
     * Gets the type of this button.
     *
     * @return the type of this button
     * @see ButtonType
     */
    public ButtonType getButtonType() {
        return buttonType;
    }

    /**
     * Sets the type of this button.
     *
     * @param buttonType the type of this button
     * @see ButtonType
     */
    public void setType(ButtonType buttonType) {
        this.buttonType = buttonType;
        repaint();
    }

    /**
     * Sets the background color of this button.
     * <p>
     * Keep on mind that setting a background color in a Material component like
     * this will also set the foreground color to either white or black and the
     * ripple color to a brighter or darker shade of the color, depending of how
     * bright or dark is the chosen background color. If you want to use a
     * custom foreground color and ripple color, ensure the background color has
     * been set first.
     * <p>
     * <b>NOTE:</b> It is up to the look and feel to honor this property, some
     * may choose to ignore it. To avoid any conflicts, using the
     * <a href="https://docs.oracle.com/javase/7/docs/api/javax/swing/plaf/metal/package-summary.html">
     * Metal Look and Feel</a> is recommended.
     *
     * @param bg
     */
    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        setForeground(MaterialUtils.isDark(bg) ? MaterialColor.WHITE : MaterialColor.BLACK);
        setRippleColor(MaterialUtils.isDark(bg) ? MaterialColor.WHITE : MaterialUtils.darken(MaterialUtils.darken(bg)));
    }

    /**
     * Gets the ripple color.
     *
     * @return the ripple color
     */
    public Color getRippleColor() {
        return rippleColor;
    }

    /**
     * Sets the ripple color. You should only do this for flat buttons.
     *
     * @param rippleColor the ripple color
     */
    public void setRippleColor(Color rippleColor) {
        this.rippleColor = rippleColor;
    }

    /**
     * Gets the current border radius of this button.
     *
     * @return the current border radius of this button, in pixels.
     */
    public int getBorderRadius() {
        return borderRadius;
    }

    /**
     * Sets the border radius of this button. You can define a custom radius in
     * order to get some rounded corners in your button, making it look like a
     * pill or even a circular action button.
     *
     * @param borderRadius the new border radius of this button, in pixels.
     */
    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        elevationEffect.setBorderRadius(borderRadius);
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        super.setCursor(b ? super.getCursor() : Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
    public void setDisableClicking(boolean disable) {
    	this.disableClicking = disable;
    }
    
    public void setDisableRipple(boolean disable) {
    	this.disableRipple = disable;
    }

    public void setDisableElevation(boolean disable) {
        this.disableElevation = disable;
    }
    

    public int getOffset_td() {
		return offsetVertical;
	}

	public void setOffset_td(int offset_td) {
		this.offsetVertical = offset_td;
	}

	public int getOffset_lr() {
		return offsetHorizontal;
	}

	public void setOffset_lr(int offset_lr) {
		this.offsetHorizontal = offset_lr;
	}

	@Override
    protected void processFocusEvent(FocusEvent focusEvent) {
        super.processFocusEvent(focusEvent);
    }

    @Override
    protected void processMouseEvent(MouseEvent mouseEvent) {
        super.processMouseEvent(mouseEvent);
    }

    private int getElevation() {
        if (isMousePressed) {
            return 2;
        } else if (buttonType == ButtonType.RAISED || isMouseOver) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if ((buttonType != ButtonType.FLAT) && isEnabled()) {
            elevationEffect.paint(g);
            g2.translate(MaterialShadow.OFFSET_LEFT, MaterialShadow.OFFSET_TOP);
        }

        if (isEnabled()) {
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - offsetHorizontal, getHeight() - offsetVertical, borderRadius, borderRadius));
            g2.setColor(new Color(rippleColor.getRed() / 255f, rippleColor.getBlue() / 255f, rippleColor.getBlue() / 255f, 0.12f));
            if (buttonType == ButtonType.FLAT) {
                elevationEffect.paint(g);
            }
        } else {
            Color bg = getBackground();
            g2.setColor(new Color(bg.getRed() / 255f, bg.getGreen() / 255f, bg.getBlue() / 255f, 0.6f));
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - offsetHorizontal, getHeight() - offsetVertical, borderRadius, borderRadius));
        }

        FontMetrics metrics = g.getFontMetrics(getFont());
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        g2.setFont(getFont());
        if (isEnabled()) {
            g2.setColor(getForeground());
        } else {
            Color fg = getForeground();
            g2.setColor(new Color(fg.getRed() / 255f, fg.getGreen() / 255f, fg.getBlue() / 255f, 0.6f));
        }
        g2.drawString(getText(), x, y);

        if (isEnabled() && !disableRipple) {
            g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth() - offsetHorizontal, getHeight() - offsetVertical, Math.max(borderRadius * 2 - 4, 0), Math.max(borderRadius * 2 - 4, 0)));
            g2.setColor(rippleColor);
            rippleEffect.paint(g2);
        }
        
        if (getIcon() != null) {
        	getIcon().paintIcon(this, g, 0, 0);
        }
    }

    @Override
    protected void paintBorder(Graphics g) {}

    /**
     * Button types.
     */
    public enum ButtonType {
        /**
         * A default button.
         */
        DEFAULT,
        /**
         * A raised button. Raised buttons have a shadow even if they are not
         * focused.
         */
        RAISED,
        /**
         * A flat button. Flat buttons don't have shadows and are typically
         * transparent.
         */
        FLAT
    }
}
