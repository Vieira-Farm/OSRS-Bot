package scripts.scripting.swingcomponents.panels.wizard;

import scripts.scripting.swingcomponents.constants.Colors;
import scripts.scripting.swingcomponents.inputs.FxLabel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StepPanel extends AnimationPanel {

	private FxLabel titleLabel;
	private JComponent panelContents;

	public StepPanel(String title) {
		super();
		this.titleLabel = new FxLabel(title);
		this.titleLabel.setBorder(new EmptyBorder(5, 0, 10, 0));
		this.titleLabel.setFont(new Font("Roboto", Font.PLAIN, 20));
		this.titleLabel.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
		this.titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);

		this.setLayout(new BorderLayout());
		this.setBackground(Colors.DarkTheme.BACKGROUND.getColor());
		if (!titleLabel.getText().isEmpty()) {
			super.add(titleLabel, BorderLayout.NORTH);
		}
	}

	public void add(Component comp, Object constraints) {}

	public void setPanelContents(JComponent panelContents) {
		if (panelContents instanceof JScrollPane || panelContents instanceof JPanel) {
			this.panelContents = panelContents;
			super.add(this.panelContents, BorderLayout.CENTER);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		super.paintComponent(g);
		g.setColor(Colors.DarkTheme.BACKGROUND.getColor());
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public void onAnimationInFinished(int currentPanelIndex) {

	}
}
