package scripting.swingcomponents.constants;

import java.awt.*;

public class Colors {

	public static enum DarkTheme {
		BACKGROUND(new Color(47, 52, 63)),
		TEXT_COLOR(new Color(217, 219, 229)),
		FIELD_TEXT_COLOR(new Color(146, 153, 164)),
		FIELD_BACKGROUND(new Color(64, 69, 82)),
		BUTTON_COLOR(new Color(70,75,88)),
		SCROLL_BAR_HOVER_COLOR(new Color(209,217,226)),
		SCROLL_BAR_BORDER_COLOR(new Color(38, 38, 38)),
		;
		
		private Color color;
		
		DarkTheme(Color color) {
			this.color = color;
		}
		
		public Color getColor() {
			return this.color;
		}
		
	}
}
