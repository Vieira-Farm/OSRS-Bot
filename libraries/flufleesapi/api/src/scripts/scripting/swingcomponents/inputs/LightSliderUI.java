package scripts.scripting.swingcomponents.inputs;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

/**
 * Retrieved from: https://stackoverflow.com/questions/1196647/custom-slider-ui-swing
 */
public class LightSliderUI extends BasicSliderUI implements MouseMotionListener, MouseListener {

    private final BasicStroke stroke = new BasicStroke(2f);
    JPopupMenu pop = new JPopupMenu();
    JFrame parent;
    JLabel lblPop = new JLabel("", SwingConstants.CENTER);

    private boolean upperDragging;
    private Color sliderColor;

    public LightSliderUI(JSlider slider, Color sliderColor) {
        super(slider);
        this.sliderColor = sliderColor;
    }

    @Override
    protected void calculateThumbSize() {
        super.calculateThumbSize();
        thumbRect.setSize(thumbRect.width, thumbRect.height);
    }

    /**
     * Creates a listener to handle track events in the specified slider.
     */
    @Override
    protected TrackListener createTrackListener(JSlider slider) {
        return new RangeTrackListener();
    }


    @Override
    protected void calculateThumbLocation() {
        // Call superclass method for lower thumb location.
        super.calculateThumbLocation();

        // Adjust upper value to snap to ticks if necessary.
        if (slider.getSnapToTicks()) {
            int upperValue = slider.getValue() + slider.getExtent();
            int snappedValue = upperValue;
            int majorTickSpacing = slider.getMajorTickSpacing();
            int minorTickSpacing = slider.getMinorTickSpacing();
            int tickSpacing = 0;

            if (minorTickSpacing > 0) {
                tickSpacing = minorTickSpacing;
            } else if (majorTickSpacing > 0) {
                tickSpacing = majorTickSpacing;
            }

            if (tickSpacing != 0) {
                // If it's not on a tick, change the value
                if ((upperValue - slider.getMinimum()) % tickSpacing != 0) {
                    float temp = (float) (upperValue - slider.getMinimum()) / (float) tickSpacing;
                    int whichTick = Math.round(temp);
                    snappedValue = slider.getMinimum() + (whichTick * tickSpacing);
                }

                if (snappedValue != upperValue) {
                    slider.setExtent(snappedValue - slider.getValue());
                }
            }
        }

        // Calculate upper thumb location.  The thumb is centered over its
        // value on the track.
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            int upperPosition = xPositionForValue(slider.getValue() + slider.getExtent());
            thumbRect.x = upperPosition - (thumbRect.width / 2);
            thumbRect.y = trackRect.y;

        } else {
            int upperPosition = yPositionForValue(slider.getValue() + slider.getExtent());
            thumbRect.x = trackRect.x;
            thumbRect.y = upperPosition - (thumbRect.height / 2);
        }
        slider.repaint();
    }


    private Shape createThumbShape(int width, int height) {
        Ellipse2D shape = new Ellipse2D.Double(0, 0, width, height);
        return shape;
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        g2d.setStroke(stroke);
        g2d.setPaint(Color.GRAY);
        Color oldColor = Color.GRAY;
        Rectangle trackBounds = trackRect;
        if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
            g2d.drawLine(trackRect.x, trackRect.y + trackRect.height / 2,
                    trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
            int lowerX = thumbRect.width / 2;
            int upperX = thumbRect.x + (thumbRect.width / 2);
            int cy = (trackBounds.height / 2) - 2;
            g2d.translate(trackBounds.x, trackBounds.y + cy);
            g2d.setColor(sliderColor);
            g2d.drawLine(lowerX - trackBounds.x, 2, upperX - trackBounds.x, 2);
            g2d.translate(-trackBounds.x, -(trackBounds.y + cy));
            g2d.setColor(oldColor);
        }
        g2d.setStroke(old);
    }

    @Override
    public void paintFocus(Graphics g) {
        //Do nothing, to prevent border.
    }

    @Override
    public void paintThumb(Graphics g) {
        Rectangle knobBounds = thumbRect;
        int w = knobBounds.width;
        int h = knobBounds.height;
        Graphics2D g2d = (Graphics2D) g.create();
        Shape thumbShape = createThumbShape(h - 1, h - 1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(knobBounds.x - ((h - w) / 2), knobBounds.y);
        g2d.setColor(sliderColor);
        g2d.fill(thumbShape);

        g2d.setColor(sliderColor);
        g2d.draw(thumbShape);
        g2d.dispose();
    }

    /**
     * Listener to handle model change events.  This calculates the thumb
     * locations and repaints the slider if the value change is not caused by dragging a thumb.
     */
    public class ChangeHandler implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent arg0) {
            calculateThumbLocation();
            slider.repaint();
        }
    }

    /**
     * Listener to handle mouse movements in the slider track.
     */
    public class RangeTrackListener extends TrackListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!slider.isEnabled()) {
                return;
            }
            currentMouseX -= thumbRect.width / 2; // Because we want the mouse location correspond to middle of the "thumb", not left side of it.
            moveUpperThumb();
        }

        public void mousePressed(MouseEvent e) {
            if (!slider.isEnabled()) {
                return;
            }

            currentMouseX = e.getX();
            currentMouseY = e.getY();

            if (slider.isRequestFocusEnabled()) {
                slider.requestFocus();
            }

            boolean upperPressed = false;
            if (slider.getMinimum() == slider.getValue()) {
                if (thumbRect.contains(currentMouseX, currentMouseY)) {
                    upperPressed = true;
                }
            } else {
                if (thumbRect.contains(currentMouseX, currentMouseY)) {
                    upperPressed = true;
                }
            }

            if (upperPressed) {
                switch (slider.getOrientation()) {
                    case JSlider.VERTICAL:
                        offset = currentMouseY - thumbRect.y;
                        break;
                    case JSlider.HORIZONTAL:
                        offset = currentMouseX - thumbRect.x;
                        break;
                }
                //upperThumbSelected = true;
                upperDragging = true;
                return;
            }


            upperDragging = false;
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            upperDragging = false;
            slider.setValueIsAdjusting(false);
            super.mouseReleased(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!slider.isEnabled()) {
                return;
            }

            currentMouseX = e.getX();
            currentMouseY = e.getY();

            if (upperDragging) {
                slider.setValueIsAdjusting(true);
                moveUpperThumb();

            }
        }

        @Override
        public boolean shouldScroll(int direction) {
            return false;
        }

        /**
         * Moves the location of the upper thumb, and sets its corresponding  value in the slider.
         */
        public void moveUpperThumb() {
            int thumbMiddle = 0;
            switch (slider.getOrientation()) {

                case JSlider.HORIZONTAL:
                    int halfThumbWidth = thumbRect.width / 2;
                    int thumbLeft = currentMouseX - offset;
                    int trackLeft = trackRect.x;
                    int trackRight = trackRect.x + (trackRect.width - 1);
                    int hMax = xPositionForValue(slider.getMaximum() -
                            slider.getExtent());

                    if (drawInverted()) {
                        trackLeft = hMax;
                    } else {
                        trackRight = hMax;
                    }
                    thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
                    thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

                    setThumbLocation(thumbLeft, thumbRect.y);//setThumbLocation

                    thumbMiddle = thumbLeft + halfThumbWidth;
                    slider.setValue(valueForXPosition(thumbMiddle));
                    break;


                default:
                    return;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}