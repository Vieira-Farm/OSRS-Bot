package scripting.swingcomponents.inputs;

import scripting.swingcomponents.constants.Colors;
import scripting.swingcomponents.inputs.buttons.SpinnerDownButton;
import scripting.swingcomponents.inputs.buttons.SpinnerUpButton;
import scripting.swingcomponents.jiconfont.icons.FontAwesome;
import scripting.swingcomponents.jiconfont.swing.IconFontSwing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FxSpinner extends JSpinner {
	private int borderRadius = 5;

	public FxSpinner() {
		this(new Color(100, 100, 100));
	}

	public FxSpinner(Color arrowColor) {
		super();
		this.setUI(new RoundedSpinnerUI(arrowColor));
		setupSpinner();
	}

	public FxSpinner(int initialValue, int minValue, int maxValue, int stepSize) {
		this(new Color(100, 100, 100), initialValue, minValue, maxValue, stepSize);
	}

	public FxSpinner(Color arrowColor, int initialValue, int minValue, int maxValue, int stepSize) {
		super();
		this.setModel(new SpinnerNumberModel(initialValue, minValue, maxValue, stepSize));
		this.setUI(new RoundedSpinnerUI(arrowColor));
		setupSpinner();
	}

	private void setupSpinner() {
		((DefaultEditor) this.getEditor()).getTextField().setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
		((DefaultEditor) this.getEditor()).getTextField().setForeground(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
		((DefaultEditor) this.getEditor()).getTextField().setColumns(20);
		((DefaultEditor) this.getEditor()).getTextField().setBorder(new EmptyBorder(0, 0, 0, 5));
		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), new EmptyBorder(3, 10, 2, 10)));
		this.setForeground(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
		this.setFont(new Font("Roboto", Font.PLAIN, 14));
		this.setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
		this.setEnabled(true);
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, borderRadius, borderRadius);
    }

    protected void paintBorder(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    	g2d.setColor(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
    	g2d.setStroke(new BasicStroke(1));
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, borderRadius, borderRadius);
    }

	private class RoundedSpinnerUI extends BasicSpinnerUI {
		
		private Color arrowColor;
		
		public RoundedSpinnerUI(Color arrowColor) {
			this.arrowColor = arrowColor;
		}
		
		@Override
		protected Component createNextButton() {
			SpinnerUpButton nextButton = new SpinnerUpButton(borderRadius);
			nextButton.setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
			nextButton.setIcon(IconFontSwing.buildIcon(FontAwesome.CARET_UP, 14, this.arrowColor));
            nextButton.setBorder(BorderFactory.createCompoundBorder(
            		BorderFactory.createMatteBorder(0, 0, 1, 0, arrowColor.brighter()),
					new EmptyBorder(5, 5, 5, 5)));
            nextButton.addMouseListener(new RoundedSpinnerMouseListener());
            this.installNextButtonListeners(nextButton);
			return nextButton;
		}
		
		@Override
		protected Component createPreviousButton() {
			
			SpinnerDownButton prevButton = new SpinnerDownButton(7);
			prevButton.setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
			prevButton.setIcon(IconFontSwing.buildIcon(FontAwesome.CARET_DOWN, 14, this.arrowColor));
			prevButton.setBorder(new EmptyBorder(5, 5, 5, 5));
			prevButton.addMouseListener(new RoundedSpinnerMouseListener());
			this.installPreviousButtonListeners(prevButton);
			return prevButton;
		}
	}
	
	private class RoundedSpinnerMouseListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			((JButton) e.getSource()).setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor().brighter());
			((JButton) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((JButton) e.getSource()).setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
			((JButton) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
}
