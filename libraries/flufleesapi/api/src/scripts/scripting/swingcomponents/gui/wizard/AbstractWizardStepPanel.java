package scripts.scripting.swingcomponents.gui.wizard;

import scripts.scripting.swingcomponents.constants.Colors;
import scripts.scripting.swingcomponents.events.SlideEvent;
import scripts.scripting.swingcomponents.inputs.FxLabel;
import scripts.scripting.swingcomponents.panels.wizard.AnimationPanel;
import scripts.scripting.swingcomponents.panels.wizard.BottomButtonPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class AbstractWizardStepPanel extends AnimationPanel {

    private BottomButtonPane pnlBottomButtons;
    private FxLabel titleLabel;

    public AbstractWizardStepPanel(String stepTitle) {
        super();

        this.setOpaque(false);
        this.titleLabel = new FxLabel(stepTitle);
        this.titleLabel.setBorder(new EmptyBorder(5, 0, 10, 0));
        this.titleLabel.setFont(new Font("Roboto", Font.PLAIN, 20));
        this.titleLabel.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
        this.titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        this.pnlBottomButtons = new BottomButtonPane();

        this.setLayout(new BorderLayout());

        if (!titleLabel.getText().isEmpty()) {
            super.add(titleLabel, BorderLayout.NORTH);
        }

        super.add(pnlBottomButtons, BorderLayout.SOUTH);
    }

    protected abstract void setupPane();

    protected void onStepCompletion() {

    };

    protected boolean isStepComplete() {
        return true;
    }

    protected String getLeftButtonText() {
        return "Previous";
    }

    protected String getRightButtonText() {
        return "Next";
    }

    protected boolean getLeftButtonVisible() {
        return true;
    }

    protected boolean getRightButtonVisible() {
        return true;
    }

    protected boolean getButtonPanelVisible() {
        return true;
    }

    protected void onLeftButtonClick(ActionEvent event) {
        Component parent = (Component) event.getSource();
        while (parent.getParent() != null) {
            parent = parent.getParent();
        }
        if (this.isStepComplete()) {
            onStepCompletion();
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                    new SlideEvent(parent, SlideEvent.Slides.RIGHT)
            );
        }
    }

    protected void onRightButtonClick(ActionEvent event) {
        Component parent = (Component) event.getSource();
        while (parent.getParent() != null) {
            parent = parent.getParent();
        }
        if (this.isStepComplete()) {
            onStepCompletion();
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                    new SlideEvent(parent, SlideEvent.Slides.LEFT)
            );
        }
    }

    public void add(Component comp, Object constraints) {}

    public void setPanelContents(JScrollPane panelContents) {
        super.add(panelContents, BorderLayout.CENTER);
    }

    public void setPanelContents(JPanel panelContents) {
        super.add(panelContents, BorderLayout.CENTER);
    }

    @Override
    public void onAnimationInFinished(int currentPanelIndex) {
        this.setupBottomButtonPanel();
    }

    public void setupBottomButtonPanel() {
        pnlBottomButtons.setVisible(getButtonPanelVisible());
        pnlBottomButtons.setLeftButton(getLeftButtonText());
        pnlBottomButtons.setRightButton(getRightButtonText());

        pnlBottomButtons.setLeftButtonVisible(getLeftButtonVisible());
        pnlBottomButtons.setRightButtonVisible(getRightButtonVisible());

        pnlBottomButtons.setRightButtonActionListener(e -> {
            onRightButtonClick(e);
        });

        pnlBottomButtons.setLeftButtonActionListener(e -> {
            onLeftButtonClick(e);
        });
    }
}
