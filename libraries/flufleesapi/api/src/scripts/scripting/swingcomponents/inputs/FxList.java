package scripts.scripting.swingcomponents.inputs;

import scripts.scripting.swingcomponents.constants.Colors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicMenuItemUI;
import java.awt.*;

public class FxList extends JList {

    private DefaultListModel listModel;

    public FxList() {
        this.setFont(new Font("Roboto", Font.PLAIN, 14));
        this.setFixedCellHeight(45);
        this.setCellRenderer(new FxCellRenderer());
        this.setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
        this.setVisibleRowCount(-1);

        listModel = new DefaultListModel();
        this.setModel(listModel);
        this.setComponentPopupMenu(new PopUpDemo());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g);
    }

    public void addItem(Object item) {
        listModel.addElement(item);
    }

    public void removeItem(Object item) {
        listModel.removeElement(item);
    }

    public void removeItem(int index) {
        listModel.remove(index);
    }

    public DefaultListModel getListModel() {
        return listModel;
    }
}

class FxCellRenderer implements ListCellRenderer{

    private final JLabel jlblCell = new JLabel(" ", JLabel.LEFT);
    private final Border emptyBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    @Override
    public Component getListCellRendererComponent(JList jList, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        jlblCell.setOpaque(true);
        jlblCell.setFont(new Font("Roboto", Font.PLAIN, 14));
        if (index % 2 == 0) {
            jlblCell.setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
        } else {
            jlblCell.setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor().brighter());
        }
        jlblCell.setForeground(isSelected ? Colors.DarkTheme.TEXT_COLOR.getColor() :
                Colors.DarkTheme.FIELD_TEXT_COLOR.getColor());
        jlblCell.setText(value.toString());
        jlblCell.setBorder(emptyBorder);

        return jlblCell;
    }
}

class PopUpDemo extends JPopupMenu {

    private DefaultListModel listModel;
    private int selectedIndex = -1;

    public PopUpDemo() {
        TestMenuItem moveUp = new TestMenuItem("Move up");
        moveUp.addActionListener((event) -> {
            if (selectedIndex < 1 || listModel == null) {
                return;
            }
            Object item = listModel.getElementAt(selectedIndex);
            listModel.removeElementAt(selectedIndex);
            listModel.insertElementAt(item, selectedIndex - 1);
        });
        add(moveUp);

        TestMenuItem moveDown = new TestMenuItem("Move down");
        moveDown.addActionListener((event) -> {
            if (selectedIndex < 0 || listModel == null || listModel.getSize() <= selectedIndex + 1) {
                return;
            }
            Object item = listModel.getElementAt(selectedIndex);
            listModel.removeElementAt(selectedIndex);
            listModel.insertElementAt(item, selectedIndex + 1);
        });
        add(moveDown);

        TestMenuItem remove = new TestMenuItem("Remove item");
        remove.addActionListener((event) -> {
            if (selectedIndex < 0 || listModel == null) {
                return;
            }
            listModel.removeElementAt(selectedIndex);
        });
        add(remove);

        this.setBackground(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
        this.setBorder(new CompoundBorder(new LineBorder(Colors.DarkTheme.FIELD_TEXT_COLOR.getColor(), 1), new EmptyBorder(3, 3, 3, 3)));
    }

    @Override
    public void show(Component invoker, int x, int y) {
        if (invoker instanceof JList) {
            int row = ((JList) invoker).locationToIndex(new Point(x, y));
            if (row != -1) {
                ((JList) invoker).setSelectedIndex(row);
            }
            selectedIndex = row;
            listModel = (DefaultListModel) ((JList) invoker).getModel();
        }
        super.show(invoker, x, y);
    }

    @Override
    public void hide() {
        selectedIndex = -1;
        listModel = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g);
    }
}

class TestUI extends BasicMenuItemUI {

    @Override
    protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
        Color oldColor = g.getColor();
        int menuWidth = menuItem.getWidth();
        int menuHeight = menuItem.getHeight();
        g.setColor(Colors.DarkTheme.FIELD_BACKGROUND.getColor());
        g.fillRect(0,0, menuWidth, menuHeight);
        g.setColor(oldColor);
    }

    @Override
    protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {
        FontMetrics fm = g.getFontMetrics();
        int mnemIndex = menuItem.getDisplayedMnemonicIndex();

        g.setColor((menuItem.isArmed() ? Colors.DarkTheme.TEXT_COLOR.getColor() :
                Colors.DarkTheme.FIELD_TEXT_COLOR.getColor()));
        BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,
                mnemIndex, textRect.x, textRect.y + fm.getAscent());
    }
}

class TestMenuItem extends JMenuItem{

    public TestMenuItem(Icon icon) {
        this(null, icon);
    }

    public TestMenuItem(String text) {
        this(text, (Icon)null);
    }

    public TestMenuItem(String text, Icon icon) {
        super(text, icon);
        this.setFont(new Font("Roboto", Font.PLAIN, 14));
        this.setForeground(Colors.DarkTheme.TEXT_COLOR.getColor());
        this.setUI(new TestUI());
        this.setBorderPainted(false);
    }
}



