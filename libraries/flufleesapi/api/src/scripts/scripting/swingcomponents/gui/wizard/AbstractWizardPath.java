package scripts.scripting.swingcomponents.gui.wizard;

import java.awt.*;

public abstract class AbstractWizardPath {

    public AbstractWizardPath() {

    }

    protected abstract int getNumberSteps();
    protected abstract String[] getStepNames();
    protected abstract Color getStepButtonBackground();
    protected abstract AbstractWizardStepPanel[] getSteps();
}
