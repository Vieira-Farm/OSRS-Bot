package scripts.scripting.swingcomponents.inputs.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IntegerInputValidator extends KeyAdapter {

    @Override
    public void keyTyped(KeyEvent e) {
        char inputChar = e.getKeyChar();
        if ((inputChar < '0' || inputChar > '9') && inputChar != '\b' && inputChar != '\r' && inputChar != '\n') {
            e.consume();
        }
    }
}
