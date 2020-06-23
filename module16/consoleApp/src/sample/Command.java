package sample;

import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Command {
    public KeyCode KEY;
    public final Runnable ACTION;
    public final EventType<KeyEvent> EVENT_TYPE;

    Command(KeyCode key, EventType<KeyEvent> eventType, Runnable action){
        KEY = key;
        ACTION = action;
        EVENT_TYPE = eventType;
    }
}
