package scripting.swingcomponents.frames;

import scripting.swingcomponents.ComponentMover;
import scripting.swingcomponents.constants.Colors;
import scripting.swingcomponents.inputs.FxLabel;
import scripting.swingcomponents.inputs.buttons.FxButton;
import scripting.swingcomponents.jiconfont.icons.FontAwesome;
import scripting.swingcomponents.panels.header.HeaderPanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class FxDialog extends JDialog {

    private HeaderPanel pnlHeader;
    private FxLabel lblDialogText, lblDialogIcon;
    private JPanel pnlText, pnlButtons, pnlInner;
    private int maxWidth = 0;

    public FxDialog(Frame owner, String title, String contents) {
        this(owner, title, contents, true);
    }

    public FxDialog(Frame owner, String title, String contents, boolean modal) {
        this(owner, title, contents, modal, null);
    }

    public FxDialog (Frame owner, String title, String contents, boolean modal, FontAwesome icon) {
        this(owner, title, contents, modal, icon, (FxButton) null);
    }

    public FxDialog(Frame owner, String title, String contents, boolean modal, FontAwesome icon, FxButton... buttons) {
        super(owner, title, modal);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        this.setSize(500, 200);
        this.setShape(new RoundRectangle2D.Double(0, 0, this.getSize().getWidth(), this.getSize().getHeight(), 20, 20));
        this.getContentPane().setBackground(Colors.DarkTheme.BACKGROUND.getColor());
        this.setBackground(Colors.DarkTheme.BACKGROUND.getColor());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ComponentMover componentMover = new ComponentMover();
        componentMover.registerComponent(this);

        pnlHeader = new HeaderPanel(title, "", 10);
        pnlHeader.setHelpVisible(false);
        pnlHeader.setMinimizeVisible(false);
        pnlHeader.setBorder(new CompoundBorder(new EmptyBorder(0, 3, 0, 3),
                new MatteBorder(0,0,1,0, Colors.DarkTheme.FIELD_TEXT_COLOR.getColor())));
        this.getContentPane().add(pnlHeader, BorderLayout.NORTH);

        pnlInner = new JPanel();
        pnlInner.setOpaque(false);
        pnlInner.setBorder(new EmptyBorder(5, 10, 0, 10));
        pnlInner.setLayout(new BorderLayout());

        pnlText = new JPanel();
        pnlText.setOpaque(false);
        pnlText.setBorder(new EmptyBorder(5, icon != null ? 10 : 5, 5, 5));
        pnlText.setLayout(new BorderLayout());
        pnlText.setPreferredSize(new Dimension(500, 200));

        lblDialogText = new FxLabel("<html>" + contents + "</html>");
        lblDialogText.setForeground(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
        lblDialogText.setFont(new Font("Roboto", Font.PLAIN, 16));
        pnlText.add(lblDialogText, BorderLayout.CENTER);
        if (pnlText.getPreferredSize().getHeight() > 80) {
            this.setSize(500, 200 + ((int) pnlText.getPreferredSize().getHeight() - 140));
            this.setShape(new RoundRectangle2D.Double(0, 0, this.getSize().getWidth(), this.getSize().getHeight(), 20, 20));
        }
        pnlInner.add(pnlText, BorderLayout.CENTER);

        if (icon != null) {
            lblDialogIcon = new FxLabel();
            lblDialogIcon.setText(Character.toString(icon.getUnicode()));
            lblDialogIcon.setFont(new Font("FontAwesome", Font.PLAIN, 40));
            lblDialogIcon.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
            pnlInner.add(lblDialogIcon, BorderLayout.WEST);
        }

        if (buttons != null && buttons.length > 0) {
            pnlButtons = new JPanel();
            pnlButtons.setOpaque(false);
            pnlButtons.setLayout(new FlowLayout());
            for (FxButton button : buttons) {
                pnlButtons.add(button);
            }
            this.getContentPane().add(pnlButtons, BorderLayout.SOUTH);
        }
        this.getContentPane().add(pnlInner, BorderLayout.CENTER);
        this.setLocationRelativeTo(owner);
        this.setVisible(true);
    }

    public static FxDialog errorDialogFactory(Frame owner, String title, String message) {
        FxButton btnOkay = new FxButton("Okay");
        FxDialog errorDialog = new FxDialog(owner, title, message, false, FontAwesome.EXCLAMATION_TRIANGLE, btnOkay);
        btnOkay.addActionListener(e -> errorDialog.dispose());
        return errorDialog;
    }

}
