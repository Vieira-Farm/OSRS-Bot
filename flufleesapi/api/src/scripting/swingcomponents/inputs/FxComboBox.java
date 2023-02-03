package scripting.swingcomponents.inputs;

import scripting.swingcomponents.constants.Colors;
import scripting.swingcomponents.jiconfont.icons.FontAwesome;
import scripting.swingcomponents.jiconfont.swing.IconFontSwing;
import scripting.swingcomponents.panels.FxScrollPane;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class FxComboBox<T> extends JComboBox<T> {

	private static final long serialVersionUID = -1612034981429888679L;

	private Color borderColor;

	public FxComboBox() {
	    this(
	            Colors.DarkTheme.FIELD_BACKGROUND.getColor(),
                Colors.DarkTheme.FIELD_TEXT_COLOR.getColor(),
                Colors.DarkTheme.FIELD_TEXT_COLOR.getColor(),
                Colors.DarkTheme.FIELD_BACKGROUND.getColor().darker()
        );
    }

    public FxComboBox(Color backgroundColor, Color textColor, Color borderColor, Color selectedColor) {
    	this.borderColor = borderColor;
    	this.setForeground(textColor);
        this.setRenderer(new CellRenderer<T>(backgroundColor, textColor, selectedColor));
        this.setEditor(new RoundedComboBoxEditor(backgroundColor, textColor));
        this.setUI(new RoundedComboBoxUI(textColor));
        this.setBackground(backgroundColor);
        this.setEditable(true);
        this.setFont(new Font("Roboto", Font.PLAIN, 14));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(borderColor);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 5, 5);
    }

    @Override
    public void setBackground(Color backgroundColor) {
        super.setBackground(backgroundColor);
        if (this.getEditor() != null && this.getEditor() instanceof FxComboBox.RoundedComboBoxEditor) {
            ((RoundedComboBoxEditor) this.getEditor()).setBackground(backgroundColor);
        }
        if (this.getRenderer() != null && this.getRenderer() instanceof FxComboBox.CellRenderer) {
            ((CellRenderer) this.getRenderer()).setBackground(backgroundColor);
        }
    }

    @Override
    public void setForeground(Color foregroundColor) {
        super.setForeground(foregroundColor);
        if (this.getRenderer() != null && this.getRenderer() instanceof FxComboBox.CellRenderer) {
            ((CellRenderer) this.getRenderer()).setForeground(foregroundColor);
        }
        if (this.getEditor() != null && this.getEditor() instanceof FxComboBox.RoundedComboBoxEditor) {
            ((RoundedComboBoxEditor) this.getEditor()).setForeground(foregroundColor);
        }
        if (this.getUI() != null && this.getUI() instanceof FxComboBox.RoundedComboBoxUI) {
            ((RoundedComboBoxUI) this.getUI()).setArrowColor(foregroundColor);
        }
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setSelectedColor(Color selectedColor) {
        if (this.getRenderer() != null && this.getBorder() instanceof FxComboBox.CellRenderer) {
        	((CellRenderer) this.getRenderer()).setSelectedColor(selectedColor);
        }
    }

    private class CellRenderer<T> extends FxLabel implements ListCellRenderer<T> {

		private static final long serialVersionUID = 3278535837792025759L;
		private Color selectedColor;
        private Color backgroundColor;

        public CellRenderer(Color backgroundColor, Color foregroundColor, Color selectedColor) {
            super();
            this.setOpaque(true);
            this.setFont(new Font("Roboto", Font.PLAIN, 14));
            this.backgroundColor = backgroundColor;
            this.selectedColor = selectedColor;
            this.setBackground(backgroundColor);
            this.setForeground(foregroundColor);
            this.setBorder(new EmptyBorder(5, 5, 5, 5));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            if (isSelected) {
                this.setBackground(this.selectedColor);
            } else {
                this.setBackground(this.backgroundColor);
            }
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            super.paintComponent(g);
        }

        public void setSelectedColor(Color selectedColor) {
            this.selectedColor = selectedColor;
        }
    }

    public class RoundedComboBoxEditor extends BasicComboBoxEditor {
        private FxLabel label = new FxLabel();
        private CustomPanel panel = new CustomPanel();
        private Object selectedItem;

        public RoundedComboBoxEditor(Color backgroundColor, Color textColor) {
            label.setFont(new Font("Roboto", Font.PLAIN, 14));
            label.setForeground(textColor);

            panel.setLayout(new BorderLayout(0,0));
            panel.setBorder(new EmptyBorder(0, 5, 0, 5));
            panel.add(label);
            panel.setOpaque(false);
            panel.setBackground(backgroundColor);
        }

        public Component getEditorComponent() {
            return this.panel;
        }

        public Object getItem() {
            return this.selectedItem;
        }

        public void setItem(Object item) {
            if (item != null) {
                this.selectedItem = item;
                label.setText(item.toString());
            }
        }

        public void setBackground(Color backgroundColor) {
            panel.setBackground(backgroundColor);
        }

        public void setForeground(Color foregroundColor) {
            label.setForeground(foregroundColor);
            panel.setForeground(foregroundColor);
        }
    }

    public class CustomPanel extends JPanel {
    	
    	protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            super.paintComponent(g);
        }
    }

    public class RoundedComboBoxUI extends BasicComboBoxUI {

        private Color arrowColor = Color.BLACK;

        public RoundedComboBoxUI(Color arrowColor) {
            this.arrowColor = arrowColor;
        }

        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popupBox = new BasicComboPopup(comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    FxScrollPane scrollPane = new FxScrollPane();
                    scrollPane.setViewportView(list);
                    scrollPane.setBorder(new EmptyBorder(0,0,0,0));
                    scrollPane.setViewportChangeListener(e -> {
                        if (scrollPane.getVerticalScrollBarWidth() != scrollPane.getVerticalScrollBar().getWidth()) {
                            scrollPane.setVerticalScrollBarWidth(scrollPane.getVerticalScrollBar().getWidth());
                            scrollPane.setPreferredSize(new Dimension(scrollPane.getSize().width +
                                    scrollPane.getVerticalScrollBarWidth(), (int) scrollPane.getSize().getHeight()));
                        }
                    });
                    return scrollPane;
                }
            };
            popupBox.getAccessibleContext().setAccessibleParent(comboBox);
            popupBox.setBorder(new EmptyBorder(0, 0, 0, 0));
            return popupBox;


        }

        @Override
        protected JButton createArrowButton() {
            IconFontSwing.register(FontAwesome.getIconFont());
            JButton dropdownButton = new JButton();
            dropdownButton.setBorderPainted(false);
            dropdownButton.setFocusPainted(false);
            dropdownButton.setContentAreaFilled(false);
            dropdownButton.setIcon(IconFontSwing.buildIcon(FontAwesome.ANGLE_DOWN, 18, this.arrowColor));
            dropdownButton.setBorder(new EmptyBorder(0, 0, 0, 0));
            return dropdownButton;
        }

        public void setArrowColor(Color arrowColor) {
            this.arrowColor = arrowColor;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            super.paint(g, c);
        }
    }
}
