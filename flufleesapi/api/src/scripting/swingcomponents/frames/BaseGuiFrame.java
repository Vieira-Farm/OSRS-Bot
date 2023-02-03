package scripting.swingcomponents.frames;

import org.tribot.util.Util;
import scripting.swingcomponents.ComponentMover;
import scripting.swingcomponents.constants.Colors;
import scripting.swingcomponents.fonts.FontUtilities;
import scripting.swingcomponents.jiconfont.icons.FontAwesome;
import scripting.swingcomponents.jiconfont.swing.IconFontSwing;
import utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

public class BaseGuiFrame extends JFrame {
	
	private static final long serialVersionUID = -5132474799829962838L;

	public BaseGuiFrame(String titleText) {
		this(titleText, 800, 600);
	}

	public BaseGuiFrame (String titleText, int width, int height) {
		setupFonts();
		this.setUndecorated(true);
		this.setTitle(titleText);
		this.setBounds(0, 0, width, height);
		this.setLocationRelativeTo(null);

		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.setShape(new RoundRectangle2D.Double(0, 0, width, height, 20, 20));
		this.setBackground(Colors.DarkTheme.BACKGROUND.getColor()); //This is the actual call that should control everything, at least ideally
		this.getContentPane().setBackground(Colors.DarkTheme.BACKGROUND.getColor()); //This is the actual call that should control everything, at least ideally

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		ComponentMover componentMover = new ComponentMover();
		componentMover.registerComponent(this);
	}

	@Override
	public void paintComponents(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		super.paintComponents(g);
	}

	private void setupFonts() {
		File fontFile = new File(Util.getWorkingDirectory().getAbsolutePath() + File.separator +
				"FluffeeScripts" + File.separator + "gui" + File.separator + "fontawesome-webfont.ttf");
		if (!fontFile.exists()) {
			FontUtilities.createFontAwesomeFont("FluffeeScripts" + File.separator + "gui" + File.separator + "fontawesome-webfont.ttf");
		}

		fontFile = new File(Util.getWorkingDirectory().getAbsolutePath() + File.separator +
				"FluffeeScripts" + File.separator + "gui" + File.separator + "Roboto.otf");
		if (!fontFile.exists()) {
			FontUtilities.createRobotoFont("FluffeeScripts" + File.separator + "gui" + File.separator + "Roboto.otf");
		}

		IconFontSwing.register(FontAwesome.getIconFont());
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(
					Utilities.getFluffeeScriptsDirectory() + "gui" + File.separator + "fontawesome-webfont.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(
					Utilities.getFluffeeScriptsDirectory() + "gui" + File.separator + "Roboto.otf")));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
