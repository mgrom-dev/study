package sample;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class Exec {
    private static final KeyCode KEY_CODE = KeyCode.F2;
    private static Label text = null;

    public static void init(){
        if (text == null) {
            text = new Label("[F2] - Exec");
            ((FlowPane) Controller.getById("commandBlock")).getChildren().add(text);

            Commands.add(new Command(KEY_CODE, KeyEvent.KEY_RELEASED, () -> MessageBlock.messageInput("Enter the command", () -> {
                try {
                    Process process = Runtime.getRuntime().exec("cmd /c \"" + ((TextField) Controller.getById("inputText")).getText() + "\"");
                    String output = new String(process.getInputStream().readAllBytes(), "cp866");
                    String errorMessage = new String(process.getErrorStream().readAllBytes(), "cp866");
                    ((Text) Controller.getById("info")).setText(errorMessage.length() > 0 ? errorMessage : output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            })));
        }
    }
}
