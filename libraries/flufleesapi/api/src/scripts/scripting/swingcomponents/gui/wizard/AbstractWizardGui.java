package scripts.scripting.swingcomponents.gui.wizard;

import scripts.scripting.swingcomponents.events.CloseGuiEvent;
import scripts.scripting.swingcomponents.events.SlideEvent;
import scripts.scripting.swingcomponents.gui.standard.AbstractGui;
import scripts.scripting.swingcomponents.panels.header.CurrentStepPane;
import scripts.scripting.swingcomponents.panels.wizard.PanelSlider;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractWizardGui extends AbstractGui {

    protected CurrentStepPane pnlStepTracker;
    protected AbstractWizardStepPanel[] steps;
    protected PanelSlider<JFrame> slider;
    protected int currentStep;

    public AbstractWizardGui(String frameTitle, String description) {
        super(frameTitle, description);

        slider = new PanelSlider<>(this);
        pnlStepTracker = CurrentStepPane.createHiddenPane();
        headerPanel.add(pnlStepTracker, BorderLayout.SOUTH);
        this.getContentPane().add(slider.getBasePanel(), BorderLayout.CENTER);
    }

    @Override
    protected void processEvent(AWTEvent e) {
        if (e instanceof SlideEvent) {
            SlideEvent slideEvent = (SlideEvent) e;
            if (slideEvent.getSlide() == SlideEvent.Slides.LEFT) {
                slider.slideLeft();
            } else {
                slider.slideRight();
            }
            pnlStepTracker.setActiveButtonIndex(slider.getCurrentPanelIndex());
        } else if (e instanceof CloseGuiEvent) {
            this.setVisible(false);
        } else {
            super.processEvent(e);
            return;
        }
    }

    public void setupStepTrackerListeners() {
        if (steps.length != pnlStepTracker.getNumberSteps()) {
            return;
        }
        for (int i = 0; i < steps.length; i++) {
            final int index = i;
            pnlStepTracker.addButtonActionListener(index, e -> {
                if (slider.getCurrentPanelIndex() == index || !steps[currentStep].isStepComplete()) {
                    e.setSource(null);
                    pnlStepTracker.setActiveButtonIndex(0);
                    return;
                } else if (slider.getCurrentPanelIndex() < index) {
                    steps[slider.getCurrentPanelIndex()].onStepCompletion();
                    slider.slideLeft(index);
                } else {
                    steps[slider.getCurrentPanelIndex()].onStepCompletion();
                    slider.slideRight(index);
                }
            });
        }
    }

    public void setGuiPath(AbstractWizardPath path) {
        pnlStepTracker.setNumberSteps(path.getNumberSteps());
        pnlStepTracker.setActiveButtonBackground(path.getStepButtonBackground());
        pnlStepTracker.setHeaderButtons(path.getStepNames());

        steps = path.getSteps();
        setupStepTrackerListeners();

        for (AbstractWizardStepPanel step : steps) {
            step.setupPane();
            slider.addComponent(step);
        }
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public CurrentStepPane getPnlStepTracker() {
        return pnlStepTracker;
    }

    public AbstractWizardStepPanel[] getSteps() {
        return steps;
    }

    public PanelSlider<JFrame> getSlider() {
        return slider;
    }

    protected abstract void setupStartButtonPane();

}
