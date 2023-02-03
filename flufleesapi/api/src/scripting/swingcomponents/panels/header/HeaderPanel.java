package scripting.swingcomponents.panels.header;

import scripting.swingcomponents.constants.Colors;
import scripting.swingcomponents.inputs.ClearFxLabel;
import scripting.swingcomponents.inputs.FxLabel;
import scripting.swingcomponents.jiconfont.icons.FontAwesome;
import scripting.swingcomponents.jiconfont.swing.IconFontSwing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HeaderPanel extends JPanel {
	
	private TitlePanel titlePanel;
	private SubTitlePanel subTitlePanel;
//	private HelpFrame helpFrame;
	private int topPadding;

	public HeaderPanel(String title) {
		this(Colors.DarkTheme.BACKGROUND.getColor(), title, "", 0);
	}
	
	public HeaderPanel(String title, String subTitle) {
		this(Colors.DarkTheme.BACKGROUND.getColor(), title, subTitle, 0);
	}
	
	public HeaderPanel(String title, String subTitle, int topPadding) {
		this(Colors.DarkTheme.BACKGROUND.getColor(), title, subTitle, 0);
	}

	public HeaderPanel(Color background, String title) {
		this(background, title, "", 0);
	}
	
	public HeaderPanel(Color background, String title, String subTitle) {
		this(background, title, subTitle, 0);
	}
	
	public HeaderPanel(Color background, String title, String subTitle, int topPadding) {
		super();
//		this.helpFrame = null;
		this.setOpaque(false);
		this.setBackground(background);
		this.setLayout(new BorderLayout(0, 0));
		this.titlePanel = new TitlePanel(title);
		this.add(titlePanel, BorderLayout.NORTH);
		if (!subTitle.isEmpty()) {
			this.subTitlePanel = new SubTitlePanel(subTitle);
			this.add(this.subTitlePanel, BorderLayout.CENTER);
		}
	}

	public void setHelpVisible(boolean visible) {
		this.titlePanel.setHelpVisible(visible);
	}

	public void setMinimizeVisible(boolean visible) {
		this.titlePanel.setMinimizeVisible(visible);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		super.paintComponent(g);
	}

//	public void setHelpFrame(HelpFrame helpFrame) {
//		this.helpFrame = helpFrame;
//	}
//
//	public HelpFrame getHelpFrame() {
//		return this.helpFrame;
//	}


	public class TitlePanel extends JPanel {
		private FxLabel minimize, close, help;
		private TitleLabel titleLabel;
		
		public TitlePanel(String title) {
			super();
			this.setBorder(new EmptyBorder(0, 5, 0, 5));
			setOpaque(false);
			GridBagLayout titleLayout = new GridBagLayout();
			titleLayout.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
			titleLayout.rowHeights = new int[] {0};
			titleLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
			titleLayout.rowWeights = new double[]{0.0};
			this.setLayout(titleLayout);

			ClearFxLabel lblHiddenMinimize = new ClearFxLabel(
					IconFontSwing.buildIcon(
							FontAwesome.WINDOW_MINIMIZE, 20
					)
			);
			GridBagConstraints hidenMinimizeConstraints = new GridBagConstraints();
			hidenMinimizeConstraints.gridx = 0;
			hidenMinimizeConstraints.anchor = 12;
			hidenMinimizeConstraints.insets = new Insets(3, 3, 0, 3);
			this.add(lblHiddenMinimize, hidenMinimizeConstraints);

			ClearFxLabel lblHiddenClose = new ClearFxLabel(
					IconFontSwing.buildIcon(
							FontAwesome.TIMES, 24
					)
			);
			GridBagConstraints hiddenCloseConstraints = new GridBagConstraints();
			hiddenCloseConstraints.gridx = 1;
			hiddenCloseConstraints.anchor = 12;
			hiddenCloseConstraints.insets = new Insets(3, 3, 0, 3);
			this.add(lblHiddenClose, hiddenCloseConstraints);

			ClearFxLabel lblHiddenHelp = new ClearFxLabel(
					IconFontSwing.buildIcon(
							FontAwesome.QUESTION, 24
					)
			);
			GridBagConstraints hiddenHelpConstraints = new GridBagConstraints();
			hiddenHelpConstraints.gridx = 2;
			hiddenHelpConstraints.anchor = 12;
			hiddenHelpConstraints.insets = new Insets(3, 3, 0, 5);
			this.add(lblHiddenHelp, hiddenHelpConstraints);
			
			this.titleLabel = new TitleLabel(title);
			GridBagConstraints titleLabelConstraints = new GridBagConstraints();
			titleLabelConstraints.gridx = 3;
			titleLabelConstraints.insets = new Insets(topPadding, 0, 0, 0);
			titleLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
			this.titleLabel.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
			this.titleLabel.setForeground(Color.WHITE);
			this.add(this.titleLabel, titleLabelConstraints);

			this.help = new FxLabel();
			this.help.setText(Character.toString(FontAwesome.QUESTION.getUnicode()));
			this.help.setFont(new Font("FontAwesome", Font.PLAIN, 24));
			this.help.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
			GridBagConstraints helpConstraints = new GridBagConstraints();
			helpConstraints.gridx = 4;
			helpConstraints.anchor = 12;
			helpConstraints.insets = new Insets(3, 3, 0, 5);
			this.help.addMouseListener(new DecorationMouseListener(DecorationTypes.HELP));
			this.add(this.help, helpConstraints);

			this.minimize = new FxLabel();
			this.minimize.setText(Character.toString(FontAwesome.WINDOW_MINIMIZE.getUnicode()));
			this.minimize.setFont(new Font("FontAwesome", Font.PLAIN, 20));
			this.minimize.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
			GridBagConstraints minimizeConstraints = new GridBagConstraints();
			minimizeConstraints.gridx = 5;
			minimizeConstraints.anchor = 12;
			minimizeConstraints.insets = new Insets(3, 3, 0, 3);
			this.minimize.addMouseListener(new DecorationMouseListener(DecorationTypes.MINIMIZE));
			this.add(this.minimize, minimizeConstraints);
			
			this.close = new FxLabel();
			this.close.setFont(new Font("FontAwesome", Font.PLAIN, 24));
			this.close.setText(Character.toString(FontAwesome.TIMES_CIRCLE.getUnicode()));
			this.close.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
			this.close.addMouseListener(new DecorationMouseListener(DecorationTypes.CLOSE));
			GridBagConstraints closeConstraints = new GridBagConstraints();
			closeConstraints.gridx = 6;
			closeConstraints.anchor = 12;
			closeConstraints.insets = new Insets(3, 3, 0, 3);
			this.add(this.close, closeConstraints);
			
		}

		public void setHelpVisible(boolean visible) {
			help.setForeground(visible ? Colors.DarkTheme.TEXT_COLOR.getColor() : new Color(0,0,0,0));
		}

		public void setMinimizeVisible(boolean visible) {
			minimize.setForeground(visible ? Colors.DarkTheme.TEXT_COLOR.getColor() : new Color(0,0,0,0));
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D graphics2D = (Graphics2D) g;
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			super.paintComponent(g);
		}
	}
	
	public class SubTitlePanel extends JPanel {
		
		private int verticalPadding;
		private SubTitleLabel subTitleLabel;
		
		public SubTitlePanel(String subTitle) {
			this(subTitle, 5);
		}
		
		public SubTitlePanel(String subTitle, int verticalPadding) {
			super();
			this.verticalPadding = verticalPadding;
			this.setBorder(new EmptyBorder(this.verticalPadding, 0, this.verticalPadding, 0));
			this.setOpaque(false);
			this.subTitleLabel = new SubTitleLabel(subTitle);
			this.subTitleLabel.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
			this.add(subTitleLabel, BorderLayout.NORTH);
		}
	}
	
	public class TitleLabel extends FxLabel {
		
		public TitleLabel(String title) {
			super(title);
			this.setHorizontalAlignment(SwingConstants.CENTER);
			this.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
			this.setFont(new Font("Roboto", Font.PLAIN, 18));
		}
	}
	
	public class SubTitleLabel extends FxLabel {
		
		public SubTitleLabel(String title) {
			super(title);
			this.setHorizontalAlignment(SwingConstants.CENTER);
			this.setFont(new Font("Roboto", Font.PLAIN, 14));
		}
	}

	public class DecorationMouseListener implements MouseListener {

		private DecorationTypes type;

		public DecorationMouseListener(DecorationTypes type) {
			this.type = type;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			((JLabel) e.getSource()).setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
			((JLabel) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			switch (type) {
				case HELP:
//					if (helpFrame != null) {
//						helpFrame.setVisible(true);
//					}
					break;
				case CLOSE:
					SwingUtilities.getWindowAncestor(e.getComponent()).dispose();
					break;
				case MINIMIZE:
					((JFrame) SwingUtilities.getWindowAncestor(e.getComponent())).setState(Frame.ICONIFIED);
					break;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			((JLabel) e.getSource()).setForeground(Colors.DarkTheme.TEXT_COLOR.getColor().darker());
			((JLabel) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((JLabel) e.getSource()).setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
			((JLabel) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public enum DecorationTypes {
		CLOSE,
		MINIMIZE,
		HELP;
	}
}
