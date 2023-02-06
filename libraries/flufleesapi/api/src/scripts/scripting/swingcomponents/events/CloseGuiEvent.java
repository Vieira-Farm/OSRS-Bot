package scripts.scripting.swingcomponents.events;

import java.awt.*;
import java.awt.event.ComponentEvent;

public class CloseGuiEvent extends ComponentEvent {

    private static final int EVENT_ID = AWTEvent.RESERVED_ID_MAX + 201;

    public CloseGuiEvent(Component source) {
        super(source, EVENT_ID);
    }
}
