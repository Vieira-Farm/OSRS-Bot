package scripting.swingcomponents.inputs.buttons;

import scripting.swingcomponents.constants.Colors;
import scripting.swingcomponents.swingmaterialdesign.MaterialButton;

import java.awt.*;

public class FxButton extends MaterialButton {

    public FxButton(String buttonText) {
        super(buttonText);
        this.setBackground(Colors.DarkTheme.BUTTON_COLOR.getColor());
        this.setFont(new Font("Roboto", Font.PLAIN, 14));
        this.setBorderRadius(8);
        this.setDisableElevation(true);
        this.setPreferredSize(new Dimension(100, 50));
    }
}
