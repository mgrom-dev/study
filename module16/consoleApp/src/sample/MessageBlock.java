package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MessageBlock {
    private static ArrayList<Command> oldCommands;

    public static void hide(){
        Controller.getById("messageForm").setVisible(false);
    }

    private static void abort(){
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Commands.clear();
            Commands.addAll(oldCommands);
            Platform.runLater(() -> {
                Controller.getById("messageForm").setVisible(false);
                Controller.getById("content").requestFocus();
            });
        }).start();
    }

    public static void messageInput(String message, Runnable okAction){
        messageYesNo(message, okAction);
        ((TextField) Controller.getById("inputText")).setText("");
        Controller.getById("inputText").setVisible(true);
        Controller.getById("inputText").setStyle("-fx-text-fill: green;");
        Controller.getById("inputText").requestFocus();
        Controller.getById("buttonCancel").setVisible(false);
        Controller.getById("buttonOk").getProperties().put("gridpane-margin", new Insets(0,0,0,0));
        Commands.remove(Commands.getByKey(KeyCode.ENTER));
        Commands.add(new Command(KeyCode.ENTER, KeyEvent.KEY_RELEASED, () -> {
            if (((TextField) Controller.getById("inputText")).getText().length() > 0) {
                okAction.run();
            }
            abort();
        }));
        Commands.remove(Commands.getByKey(KeyCode.LEFT));
        Commands.remove(Commands.getByKey(KeyCode.RIGHT));
    }

    public static void messageYesNo(String message, Runnable okAction){
        ((Text) Controller.getById("message")).setText(message);
        Controller.getById("messageForm").setVisible(true);
        Controller.getById("inputText").setVisible(false);
        Controller.getById("buttonCancel").getProperties().put("gridpane-margin", new Insets(0,50,0,0));
        Controller.getById("buttonCancel").setOnMouseClicked((b) -> abort());
        Controller.getById("buttonCancel").setVisible(true);
        Controller.getById("buttonOk").getProperties().put("gridpane-margin", new Insets(0,0,0,50));
        Controller.getById("buttonOk").setOnMouseClicked((b) -> {
            okAction.run();
            abort();
        });
        Controller.getById("buttonOk").requestFocus();
        oldCommands = Commands.copy();
        Commands.clear();
        Runnable switchButton = () -> {
            if (Controller.getById("buttonOk").isFocused()) {
                Controller.getById("buttonCancel").requestFocus();
                Controller.getById("buttonCancel").setStyle("-fx-border-color: YELLOW; -fx-border-width: 3;");
                Controller.getById("buttonOk").setStyle("");
            } else {
                Controller.getById("buttonOk").requestFocus();
                Controller.getById("buttonOk").setStyle("-fx-border-color: YELLOW; -fx-border-width: 3;");
                Controller.getById("buttonCancel").setStyle("");
            }
        };
        switchButton.run();
        Commands.add(new Command(KeyCode.LEFT, KeyEvent.KEY_RELEASED, switchButton));
        Commands.add(new Command(KeyCode.RIGHT, KeyEvent.KEY_RELEASED, switchButton));
        Commands.add(new Command(KeyCode.ENTER, KeyEvent.KEY_TYPED, () -> {
            if (Controller.getById("buttonOk").isFocused()) {
                okAction.run();
            }
            abort();
        }));
    }
}
