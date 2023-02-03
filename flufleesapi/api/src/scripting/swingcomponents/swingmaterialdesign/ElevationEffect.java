package scripting.swingcomponents.swingmaterialdesign;

import scripting.swingcomponents.swingmaterialdesign.animation.Animation;
import scripting.swingcomponents.swingmaterialdesign.animation.AnimationListener;
import scripting.swingcomponents.swingmaterialdesign.animation.Animator;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * An elevation effect.
 *
 * @author bilux (i.bilux@gmail.com)
 */
public class ElevationEffect extends Animation {

    protected JComponent target;
    protected MaterialShadow shadow;
    protected double level;
    protected int borderRadius;
    private Animator animator;
    private double startLevel, targetLevel;

    private ElevationEffect(final JComponent component, int elevationLevel) {
        this(component, elevationLevel, 300);
    }

    private ElevationEffect(final JComponent component, int elevationLevel, int duration) {
    	this.setDuration(duration);
    	target = component;
        level = elevationLevel;
        targetLevel = elevationLevel;
        shadow = new MaterialShadow();
        animator = new Animator(new AnimationListener() {
            @Override
            public void onStart() {}

            @Override
            public void onAnimation(double percent) {
                level = startLevel + ((targetLevel - startLevel) * percent);
                target.repaint();
            }

            @Override
            public void onEnd() {
                level = targetLevel;
                target.repaint();
            }

            @Override
            public void onStop() {
                level = targetLevel;
                target.repaint();
            }
        });
        animator.setDelay(0);
        animator.setDuration(this.getDuration());
    	
    }
    
    /**
     * Gets the elevation level.
     *
     * @return Elevation level [0~5] as a double
     */
    public double getLevel() {
        return level;
    }

    /**
     * Sets the elevation newLevel.
     *
     * @param elevationLevel int representing the new elevation level [0~5]
     */
    public void setLevel(int elevationLevel) {
    	if (!target.isShowing()) {
    		this.level = elevationLevel;
    	} else if (elevationLevel != this.level) {
    		animator.stop();
            startLevel = this.level;
            targetLevel = elevationLevel;
            animator.start();
    	}
    }

    /**
     * Gets the current border radius of the component casting a shadow. This
     * should be updated by the target component if such a property exists for
     * it and is modified.
     *
     * @return the current border radius casted on the shadow, in pixels
     */
    public int getBorderRadius() {
        return borderRadius;
    }

    /**
     * Sets the current border radius of the component casting a shadow. This
     * should be updated by the target component if such a property exists for
     * it and is modified.
     *
     * @param borderRadius the new border radius casted on the shadow, in
     * pixels.
     */
    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
    }

    /**
     * Paints this effect.
     *
     * @param g canvas
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(target.getParent().getBackground());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        if (target instanceof MaterialButton &&
                (((MaterialButton) target).getButtonType() == MaterialButton.ButtonType.FLAT)) {
            g2.setColor(MaterialUtils.brighten(target.getBackground(),
                    (int) (((66.0 / (1 + Math.exp(-2.0 * level))) - 33.0))));
            g2.fill(new RoundRectangle2D.Float(0, 0, target.getWidth(), target.getHeight(),
                    borderRadius, borderRadius));
        } else {
            g.drawImage(shadow.render(target.getWidth(), target.getHeight(), borderRadius, level)
                    , 0, 0, null);
        }
    }

    /**
     * Creates an elevation effect for the given component. Each component is
     * responsible of calling {@link #paint(Graphics)} in order to display the
     * effect. For this to work, there should be an offset between the contents
     * of the component and its actual bounds, these values can be found in
     * {@link MaterialShadow}.
     *
     * @param target the target of the resulting {@code ElevationEffect}
     * @param level the initial elevation level [0~5]
     * @return an {@code ElevationEffect} object providing support for painting
     * ripples
     * @see MaterialShadow#OFFSET_TOP
     * @see MaterialShadow#OFFSET_BOTTOM
     * @see MaterialShadow#OFFSET_LEFT
     * @see MaterialShadow#OFFSET_RIGHT
     */
    public static ElevationEffect applyTo(JComponent target, int level) {
        return new ElevationEffect(target, level);
    }
    
    /**
     * Creates an elevation effect for the given component. Each component is
     * responsible of calling {@link #paint(Graphics)} in order to display the
     * effect. For this to work, there should be an offset between the contents
     * of the component and its actual bounds, these values can be found in
     * {@link MaterialShadow}.
     *
     * @param target the target of the resulting {@code ElevationEffect}
     * @param level the initial elevation level [0~5]
     * @param duration Duration of the animation in milliseconds
     * @return an {@code ElevationEffect} object providing support for painting
     * ripples
     * @see MaterialShadow#OFFSET_TOP
     * @see MaterialShadow#OFFSET_BOTTOM
     * @see MaterialShadow#OFFSET_LEFT
     * @see MaterialShadow#OFFSET_RIGHT
     */
    public static ElevationEffect applyTo(JComponent target, int level, int duration) {
        return new ElevationEffect(target, level, duration);
    }
}
