package scripting.swingcomponents.panels.header;

import scripting.swingcomponents.constants.Colors;
import scripting.swingcomponents.swingmaterialdesign.MaterialButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CurrentStepPane extends JPanel {

	private int numberSteps, activeButtonIndex = 0;
	private static int buttonHeight = 50;
	private GridLayout layout;
	private static Dimension paneDimensions = new Dimension(800, buttonHeight);
	private Color activeButtonBackground, inactiveButtonBackground;
	private HeaderButton[] headerButtons;

	public CurrentStepPane() {
		super();
	}

	public static CurrentStepPane createHiddenPane() {
		CurrentStepPane currentStepPane = new CurrentStepPane();
		currentStepPane.numberSteps = 0;
		currentStepPane.activeButtonBackground = Colors.DarkTheme.BACKGROUND.getColor();
		currentStepPane.inactiveButtonBackground = Colors.DarkTheme.FIELD_BACKGROUND.getColor();
		currentStepPane.setPreferredSize(paneDimensions);
		currentStepPane.setOpaque(false);
		currentStepPane.setBorder(new EmptyBorder(0,0,0,0));
		return currentStepPane;
	}

	public CurrentStepPane(int numberSteps, Color activeButtonBackground, String... buttonText) {
		this(numberSteps, activeButtonBackground, Colors.DarkTheme.FIELD_BACKGROUND.getColor(), buttonText);
	}

	public CurrentStepPane(int numberSteps, Color activeButtonBackground, Color inactiveButtonBackground, String... buttonText) {
		super();
		this.numberSteps = numberSteps;
		this.activeButtonBackground = activeButtonBackground;
		this.inactiveButtonBackground = inactiveButtonBackground;
		this.layout = new GridLayout(0, (numberSteps > 3 ? 3 : numberSteps), 0, 0);
		this.setLayout(this.layout);
		this.setPreferredSize(paneDimensions);
		this.setOpaque(false);
		this.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
		this.headerButtons = new HeaderButton[numberSteps];
		for (int i = 0; i < numberSteps; i++) {
			this.headerButtons[i] = new HeaderButton(buttonText[i], this.activeButtonBackground,
					this.inactiveButtonBackground, i == activeButtonIndex, i);
			this.headerButtons[i].addActionListener(e -> {
				if (!(e.getSource() instanceof HeaderButton) ||
						((HeaderButton) e.getSource()).getButtonIndex() == activeButtonIndex) {
					return;
				}
				HeaderButton targetButton = (HeaderButton) e.getSource();
				if (numberSteps > 3 && (targetButton.getButtonIndex() > 0 &&
						targetButton.getButtonIndex() < numberSteps - 1)) {
					this.removeAll();
					this.add(this.headerButtons[targetButton.getButtonIndex() - 1]);
					this.add(this.headerButtons[targetButton.getButtonIndex()]);
					this.add(this.headerButtons[targetButton.getButtonIndex() + 1]);
					this.revalidate();
				}
				headerButtons[activeButtonIndex].setActive(false);
				targetButton.setActive(true);
				activeButtonIndex = targetButton.getButtonIndex();
			});
			if (i < 3) {
				this.add(this.headerButtons[i]);
			}
		}
	}

	public int getNumberSteps() {
		return numberSteps;
	}

	public void setNumberSteps(int numberSteps) {
		this.numberSteps = numberSteps;
		this.layout = new GridLayout(0, (numberSteps > 3 ? 3 : numberSteps), 0, 0);
		this.setLayout(this.layout);
	}

	public void setActiveButtonBackground(Color color) {
		this.activeButtonBackground = color;
	}

	public void setInactiveButtonBackground(Color inactiveButtonBackground) {
		this.inactiveButtonBackground = inactiveButtonBackground;
	}

	public void setHeaderButtons(String... buttonText) {
		this.headerButtons = new HeaderButton[numberSteps];
		for (int i = 0; i < numberSteps; i++) {
			this.headerButtons[i] = new HeaderButton(buttonText[i], this.activeButtonBackground,
					this.inactiveButtonBackground, i == activeButtonIndex, i);
			this.headerButtons[i].addActionListener(e -> {
				if (!(e.getSource() instanceof HeaderButton) ||
						((HeaderButton) e.getSource()).getButtonIndex() == activeButtonIndex) {
					return;
				}
				setActiveButton((HeaderButton) e.getSource());
			});
			if (i < 3) {
				this.add(this.headerButtons[i]);
			}
		}
	}

	private void setActiveButton(HeaderButton targetButton) {
		if (numberSteps > 3 && (targetButton.getButtonIndex() > 0 &&
				targetButton.getButtonIndex() < numberSteps - 1)) {
			this.removeAll();
			this.add(this.headerButtons[targetButton.getButtonIndex() - 1]);
			this.add(this.headerButtons[targetButton.getButtonIndex()]);
			this.add(this.headerButtons[targetButton.getButtonIndex() + 1]);
			this.revalidate();
		}
		headerButtons[activeButtonIndex].setActive(false);
		targetButton.setActive(true);
		activeButtonIndex = targetButton.getButtonIndex();
	}

	public int getButtonHeight() {
		return buttonHeight;
	}

	public void setButtonHeight(int buttonHeight) {
		this.buttonHeight = buttonHeight;
		this.paneDimensions.setSize(this.paneDimensions.getWidth(), this.buttonHeight);
	}

	public void setActiveButtonIndex(int buttonIndex) {
		setActiveButton(this.headerButtons[buttonIndex]);
	}

	public void addButtonActionListener(int buttonIndex, ActionListener listener) {
		addButtonActionListener(this.headerButtons[buttonIndex], listener);
	}

	public void addButtonActionListener(HeaderButton targetButton, ActionListener listener) {
		targetButton.addActionListener(listener);
	}

	public static class HeaderButton extends MaterialButton {

		private static final long serialVersionUID = 828456397674665759L;
		private Color activeBackgroundColor, inactiveBackgroundColor;
		private boolean isActive;
		private final int BORDER_RADIUS = 5; //This determines how round the button will look. 5 is the number I think looks the best
		private final Color TEXT_COLOR = Color.WHITE;
		private int buttonIndex;

		public HeaderButton(String text, Color activeBackgroundColor, Color inactiveBackgroundColor, boolean active, int buttonIndex) {
			super(text.toUpperCase());
			this.activeBackgroundColor = activeBackgroundColor;
			this.inactiveBackgroundColor = inactiveBackgroundColor;
			this.isActive = active;
			this.buttonIndex = buttonIndex;
			setBorderRadius(this.isActive ? BORDER_RADIUS : 0);
			setForeground(TEXT_COLOR);
			setBackground(this.isActive ? this.activeBackgroundColor : this.inactiveBackgroundColor);
			setOffset_td(0);
			setOffset_lr(0);
			setType(MaterialButton.ButtonType.FLAT);
			setFont(new Font("Roboto", Font.PLAIN, 14));
			setHorizontalAlignment(SwingConstants.CENTER);
			addActionListener(e -> {
				if (e.getSource() != null && !isActive) {
					setActive(!isActive);
				}
			});
		}

		public Color getActiveBackgroundColor() {
			return activeBackgroundColor;
		}

		public void setActiveBackgroundColor(Color activeBackgroundColor) {
			this.activeBackgroundColor = activeBackgroundColor;
		}

		public Color getInactiveBackgroundColor() {
			return inactiveBackgroundColor;
		}

		public void setInactiveBackgroundColor(Color inactiveBackgroundColor) {
			this.inactiveBackgroundColor = inactiveBackgroundColor;
		}

		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
			setBackground(this.isActive ? this.activeBackgroundColor : this.inactiveBackgroundColor);
			setBorderRadius(this.isActive ? BORDER_RADIUS : 0);
		}

		public int getButtonIndex() {
			return buttonIndex;
		}
	}
	
}
