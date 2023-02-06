package scripts.scripting.swingcomponents.events;

import java.awt.*;
import java.awt.event.ComponentEvent;

public class SlideEvent extends ComponentEvent {

    private Slides slide;
    private static final int ID = AWTEvent.RESERVED_ID_MAX + 101;

    public SlideEvent(Component source, Slides slide) {
        super(source, ID);
        this.slide = slide;
    }

    public Slides getSlide() {
        return slide;
    }

    public enum Slides {
        LEFT,
        RIGHT,
        ;
    }
}
