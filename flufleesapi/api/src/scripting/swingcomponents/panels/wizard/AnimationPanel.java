package scripting.swingcomponents.panels.wizard;

import javax.swing.*;

public abstract class AnimationPanel extends JPanel {

    public void onAnimationOutFinished() {

    }

    public abstract void onAnimationInFinished(int currentPanelIndex);
}
