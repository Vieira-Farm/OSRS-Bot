package scripting.swingcomponents.panels.wizard;

import scripting.swingcomponents.constants.Colors;
import scripting.swingcomponents.inputs.buttons.FxButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class BottomButtonPane extends JPanel {

    private FxButton leftButton, rightButton;
    private ActionListener leftActionListener, rightActionListener;

    public BottomButtonPane() {
        this.setOpaque(false);
        this.setLayout(new BorderLayout(0, 0));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    public BottomButtonPane(String leftButtonText, String rightButtonText) {
        this();
        setLeftButton(leftButtonText);
        setRightButton(rightButtonText);
    }

    public BottomButtonPane(FxButton leftButton, FxButton rightButton) {
        this();
        setLeftButton(leftButton);
        setRightButton(rightButton);
    }

    public void setLeftButton(String buttonText) {
        setLeftButton(new FxButton(buttonText));
    }

    public void setLeftButton(FxButton leftButton) {
        if (this.leftButton != null) {
            this.remove(this.leftButton);
        }
        this.leftButton = leftButton;
        this.add(this.leftButton, BorderLayout.WEST);
    }

    public void setRightButton(String buttonText) {
        setRightButton(new FxButton(buttonText));
    }

    public void setRightButton(FxButton rightButton) {
        if (this.rightButton != null) {
            this.remove(this.rightButton);
        }
        this.rightButton = rightButton;
        this.add(this.rightButton, BorderLayout.EAST);
    }

    public void setLeftButtonText(String buttonText) {
        if (this.leftButton == null) {
            setLeftButton(buttonText);
        } else {
            this.leftButton.setText(buttonText);
        }
    }

    public void setRightButtonText(String buttonText) {
        if (this.rightButton == null) {
            setRightButton(buttonText);
        } else {
            this.rightButton.setText(buttonText);
        }
    }

    public void setLeftButtonVisible(boolean visible) {
        setButtonVisible(visible, this.leftButton);
    }

    public void setRightButtonVisible(boolean visible) {
        setButtonVisible(visible, this.rightButton);
    }

    private void setButtonVisible(boolean visible, FxButton button) {
        if (!visible) {
            button.setBackground(Colors.DarkTheme.BACKGROUND.getColor());
            button.setForeground(Colors.DarkTheme.BACKGROUND.getColor());
            button.setEnabled(false);
        } else {
            button.setBackground(Colors.DarkTheme.BUTTON_COLOR.getColor());
            button.setEnabled(true);
        }
    }

    public void setLeftButtonActionListener(ActionListener listener) {
        if (this.leftActionListener != null) {
            this.leftButton.removeActionListener(this.leftActionListener);
        }

        this.leftActionListener = listener;
        this.leftButton.addActionListener(listener);
    }

    public void setRightButtonActionListener(ActionListener listener) {
        if (this.rightActionListener != null) {
            this.rightButton.removeActionListener(this.rightActionListener);
        }
        this.rightActionListener = listener;
        this.rightButton.addActionListener(listener);
    }
}