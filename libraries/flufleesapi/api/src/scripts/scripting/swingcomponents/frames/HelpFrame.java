package scripts.scripting.swingcomponents.frames;

import scripts.scripting.swingcomponents.constants.Colors;
import scripts.scripting.swingcomponents.panels.FxScrollPane;
import scripts.scripting.swingcomponents.panels.header.HeaderPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HelpFrame extends BaseGuiFrame {

    private JPanel contentPane;
    private FxScrollPane scrollPane;
    private JTextPane htmlTextPane;
    private String htmlText = "";
    private String htmlTextPrefix =
            "<html>\n" +
            "<head>\n" +
            "  <style>\n" +
            "    p, h1, h2, h3, span{\n" +
            "       color: #F2F2F2;\n" +
            "       font: \"Roboto\";\n" +
            "       margin: 0px;\n" +
            "       padding-top: 0px;\n" +
            "       padding-bottom: 0px;\n" +
            "     }\n" +
            "     p {\n" +
            "       padding-top: 5px\n" +
            "     }\n" +
            "     h2 {\n" +
            "       font-size: 14px;\n" +
            "       padding-top: 10px\n" +
            "     }\n" +
            "     h1 {\n" +
            "       font-size: 16px;\n" +
            "       padding-top: 10px\n" +
            "     }\n" +
            "  </style>\n" +
            "</head>";

    public HelpFrame(String titleText) {
        super(titleText, 400, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 2, 2, 2));
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBackground(Colors.DarkTheme.BACKGROUND.getColor());
        setContentPane(contentPane);

        HeaderPanel headerPanel = new HeaderPanel("Help/FAQ");
        headerPanel.setHelpVisible(false);
        contentPane.add(headerPanel, BorderLayout.NORTH);

        scrollPane = new FxScrollPane();
        scrollPane.setBorder(null);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        htmlTextPane = new JTextPane();
        htmlTextPane.setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
        htmlTextPane.setContentType("text/html");
        htmlTextPane.setForeground(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
        htmlTextPane.setText(htmlTextPrefix + htmlText + "</html>");
        htmlTextPane.setEditable(false);
        scrollPane.setViewportView(htmlTextPane);
    }

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlTextPrefix + htmlText + "</html>";
        htmlTextPane.setText(this.htmlText);
        htmlTextPane.validate();
    }

    @Override
    public void paintComponents(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponents(g);
    }
}
