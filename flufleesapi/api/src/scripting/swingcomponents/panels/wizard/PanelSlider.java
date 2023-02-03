package scripting.swingcomponents.panels.wizard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class PanelSlider<ParentType extends Container> {

    private static final int RIGHT = 0x01;
    private static final int LEFT = 0x02;
    private static final int TOP = 0x03;
    private static final int BOTTOM = 0x04;
    private final JPanel basePanel = new JPanel();
    private final ParentType parent;
    private final Object lock = new Object();
    private final ArrayList<Component> panels = new ArrayList<>();
    private final boolean useSlideButton = true;
    private boolean isSlideInProgress = false;
    private int currentPanelIndex = 0;

    private final JPanel glassPane;

    {
        glassPane = new JPanel();
        glassPane.setOpaque(false);
        glassPane.addMouseListener(new MouseAdapter() {});
        glassPane.addMouseMotionListener(new MouseMotionAdapter() {});
        glassPane.addKeyListener(new KeyAdapter() {});
    }

    public PanelSlider(final ParentType parent) {
        if (parent == null) {
            throw new RuntimeException("ProgramCheck: Parent can not be null.");
        } else if (
                !(parent instanceof JFrame) &&
                !(parent instanceof JDialog) &&
                !(parent instanceof JWindow) &&
                !(parent instanceof JPanel)
        ) {
            throw new RuntimeException("ProgramCheck: Parent type not supported. " + parent.getClass().getSimpleName());
        }
        this.parent = parent;
        basePanel.setSize(parent.getSize());
        basePanel.setLayout(new BorderLayout());
        basePanel.setOpaque(false);
    }

    public JPanel getBasePanel() {
        return basePanel;
    }

    public void addComponent(final Component component) {
        if (!panels.contains(component)) {
            panels.add(component);
            if (panels.size() == 1) {
                basePanel.add(component);
            }
            component.setSize(basePanel.getSize());
            component.setLocation(0, 0);
        }
    }

    public void removeComponent(final Component component) {
        if (panels.contains(component)) {
            panels.remove(component);
        }
    }

    public void slideLeft() {
        slideLeft(currentPanelIndex + 1 < panels.size() ? currentPanelIndex + 1 : 0);
    }

    public void slideLeft(int targetPanelIndex) {
        slide(LEFT, targetPanelIndex);
    }

    public void slideRight() {
        slideRight(currentPanelIndex - 1 >= 0 ? currentPanelIndex - 1 : panels.size() - 1);
    }

    public void slideRight(int targetPanelIndex) {
        slide(RIGHT, targetPanelIndex);
    }

    public void slideTop() {
        slideTop(currentPanelIndex + 1 < panels.size() ? currentPanelIndex + 1 : 0);
    }

    public void slideTop(int targetPanelIndex) {
        slide(TOP, targetPanelIndex);
    }

    public void slideBottom() {
        slideBottom(currentPanelIndex - 1 >= 0 ? currentPanelIndex - 1: panels.size() - 1);
    }

    public void slideBottom(int targetPanelIndex) {
        slide(BOTTOM, targetPanelIndex);
    }

    private void enableUserInput(final ParentType w) {
        if (w instanceof JFrame) {
            ((JFrame) w).getGlassPane().setVisible(false);
        }
        if (w instanceof JDialog) {
            ((JDialog) w).getGlassPane().setVisible(false);
        }
        if (w instanceof JWindow) {
            ((JWindow) w).getGlassPane().setVisible(false);
        }
    }

    private void disableUserInput(final ParentType w) {
        if (w instanceof JFrame) {
            ((JFrame) w).setGlassPane(glassPane);
        }
        if (w instanceof JDialog) {
            ((JDialog) w).setGlassPane(glassPane);
        }
        if (w instanceof JWindow) {
            ((JWindow) w).setGlassPane(glassPane);
        }
        glassPane.setVisible(true);
    }

    private void enableTransparentOverlay() {
        if (parent instanceof JFrame) {
            ((JFrame) parent).getContentPane().setBackground(panels.get(0).getBackground());
            parent.remove(basePanel);
            parent.validate();
        }
        if (parent instanceof JDialog) {
            ((JDialog) parent).getContentPane().setBackground(panels.get(0).getBackground());
            parent.remove(basePanel);
            parent.validate();
        }
        if (parent instanceof JWindow) {
            ((JWindow) parent).getContentPane().setBackground(panels.get(0).getBackground());
            parent.remove(basePanel);
            parent.validate();
        }
    }

    private void slide(final int slideType, int targetPanelIndex) {
        if (!isSlideInProgress) {
            isSlideInProgress = true;
            final Thread animationThread = new Thread(() -> {
                parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                disableUserInput(parent);
                performSlide(slideType, targetPanelIndex);
                enableUserInput(parent);
                parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                isSlideInProgress = false;
            });
            animationThread.setDaemon(true);
            animationThread.start();
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private void performSlide(int slideType, int targetPanelIndex) {
        if (panels.size() < 2) {
            System.err.println("Not enough panels");
            return;
        }
        synchronized (lock) {
            int multiplier = (slideType == LEFT) || (slideType == TOP) ? 1 : -1;
            if (currentPanelIndex == 0 && multiplier == -1) {
                currentPanelIndex = panels.size()-1;
            } else if (currentPanelIndex == panels.size()-1 && multiplier == 1) {
                currentPanelIndex = 0;
            }
            Component componentOld = panels.get(currentPanelIndex);
            Component componentNew = panels.get(targetPanelIndex);
            setCurrentPanelIndex(targetPanelIndex);
            boolean isHorizontalSlide = (slideType == LEFT) || (slideType == RIGHT);

            if (isHorizontalSlide) {
                componentNew.setLocation(new Point(componentOld.getWidth() * multiplier, 0));
            } else {
                componentNew.setLocation(new Point(0, componentOld.getHeight() * multiplier));
            }

            int stepSize, maxX, maxY;
            if (isHorizontalSlide) {
                stepSize = (int) (((float) parent.getWidth() / (float) Toolkit.getDefaultToolkit().getScreenSize().width) * 40.f);
                maxX = componentOld.getWidth();
                maxY = 0;
            } else {
                stepSize = (int) (((float) parent.getHeight() / (float) Toolkit.getDefaultToolkit().getScreenSize().height) * 20.f);
                maxX = 0;
                maxY = componentOld.getHeight();
            }

            stepSize = stepSize < 5 ? 5 : stepSize;
            int stepCount = maxX == 0 ? maxY/stepSize + 1: maxX/stepSize + 1;
            basePanel.add(componentNew);
            basePanel.revalidate();

            for (int i = 1; i < stepCount; i++) {
                if (isHorizontalSlide) {
                    componentOld.setLocation((0 - i * stepSize) * multiplier, maxY);
                    componentNew.setLocation((maxX - i * stepSize) * multiplier, maxY);
                } else {
                    componentOld.setLocation(maxX, (0 - i * stepSize) * multiplier);
                    componentNew.setLocation(maxX, (maxY - i * stepSize) * multiplier);
                }
                try {
                    Thread.sleep(300 / (stepCount));
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            componentOld.setLocation(-10000, -10000);
            componentNew.setLocation(0, 0);
            basePanel.remove(componentOld);
            basePanel.revalidate();

            if (componentOld instanceof AnimationPanel) {
                ((AnimationPanel) componentOld).onAnimationOutFinished();
            }
            if (componentNew instanceof AnimationPanel) {
                ((AnimationPanel) componentNew).onAnimationInFinished(currentPanelIndex);
            }
        }
    }

    public int getCurrentPanelIndex() {
        return currentPanelIndex;
    }

    public void setCurrentPanelIndex(int panelIndex) {
        this.currentPanelIndex = panelIndex;
    }

    public ArrayList<Component> getPanels() {
        return this.panels;
    }
}

