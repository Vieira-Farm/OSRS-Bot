package scripting.swingcomponents.panels.wizard;

import javax.swing.*;
import java.awt.*;

public class StartButtonPane extends AnimationPanel {
	
	private ButtonLayout buttonLayout;
	private JPanel outerPanel, innerPanel;
	private GridBagLayout outerPanelLayout = new GridBagLayout();
	private GridBagConstraints innerPaneConstraints = new GridBagConstraints();
	
	public StartButtonPane(ButtonLayout buttonLayout) {
//		this(buttonLayout, Colors.DarkTheme.BACKGROUND.getColor());
		this(buttonLayout, Color.WHITE);
	}
	
	public StartButtonPane(ButtonLayout buttonLayout, Color backgroundColor) {
		super();
		this.buttonLayout = buttonLayout;
		setLayout(new BorderLayout(0, 0));
//		setBackground(backgroundColor);
		setOpaque(false);
		setupOuterPanel();
		setupInnerPanel();
	}
	
	public void setupOuterPanel() {
		outerPanel = new JPanel();
		outerPanel.setOpaque(false);
		outerPanelLayout.columnWidths = new int[] {10, 0, 10};
		outerPanelLayout.rowHeights = new int[] {30, 280, 130};
		outerPanelLayout.columnWeights = new double[]{0.0, 1.0};
		outerPanelLayout.rowWeights = new double[]{1.0, 0.0, 0.0};
		outerPanel.setLayout(outerPanelLayout);
		add(outerPanel);
	}
	
	public void setupInnerPanel() {
		innerPanel = new JPanel();
		innerPanel.setOpaque(false);
		innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, this.buttonLayout.getHorizontalSpacing(), 0));
		innerPaneConstraints.insets = new Insets(0, 0, 5, 0);
		innerPaneConstraints.fill = GridBagConstraints.HORIZONTAL;
		innerPaneConstraints.gridx = 1;
		innerPaneConstraints.gridy = 1;
		outerPanel.add(innerPanel, innerPaneConstraints);
	}
	
	public void addButton(JComponent button) {
		innerPanel.add(button);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
//		g.setColor(Colors.DarkTheme.BACKGROUND.getColor());
//		g.setColor(Color.WHITE);
//		g.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public void onAnimationInFinished(int currentPanelIndex) {

	}

	@Override
	public void onAnimationOutFinished() {
//		slider.removeComponent(pnlWizardStart);
//		slider.setCurrentPanelIndex(0);
	}

	public enum ButtonLayout {
		TWO_BUTTONS(new Dimension(275, 275), 50, 0, 24, 80),
		THREE_BUTTONS(new Dimension(200, 200), 30, 0, 18, 60),
		FOUR_BUTTONS(new Dimension(180, 180), 10, 0, 18, 60),
		FOUR_BUTTONS_TWO_ROWS(new Dimension(180, 180), 10, 10, 18, 60);
		
		private Dimension buttonSize;
		private int horizontalSpacing, verticalSpacing, fontSize, iconSize;
		
		ButtonLayout(Dimension buttonSize, int horizontalSpacing, int verticalSpacing, int fontSize, int iconSize) {
			this.buttonSize = buttonSize;
			this.horizontalSpacing = horizontalSpacing;
			this.verticalSpacing = verticalSpacing;
			this.fontSize = fontSize;
			this.iconSize = iconSize;
		}

		public Dimension getButtonSize() {
			return buttonSize;
		}

		public int getHorizontalSpacing() {
			return horizontalSpacing;
		}

		public int getVerticalSpacing() {
			return verticalSpacing;
		}

		public int getFontSize() {
			return fontSize;
		}

		public int getIconSize() {
			return iconSize;
		}
		
	}

}
