package sample;

import javafx.application.Platform;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public abstract class SplitMenuFactory {
    public SplitMenuButton menuButton; //кнопка с меню
    public Command command; //команда которая будет вызывать меню
    private ArrayList<Command> tempCommands = new ArrayList<>(); //для временного хранения старых комманд

    SplitMenuFactory(){
        //создаем кнопку
        menuButton = new SplitMenuButton(){{
            setFocusTraversable(false);
            setOnHidden((e) -> returnCommands());
            setOnShown((e) -> removeCommands());
        }};
        ((FlowPane) Controller.getById("commandBlock")).getChildren().add(menuButton);

        //добавляем команду
        command = new Command(null, KeyEvent.KEY_RELEASED, () -> {
            if (menuButton.isShowing()) {
                menuButton.hide();
            } else {
                menuButton.show();
            }
        });
        Commands.add(command);
    }

    /**
     * Блокируем лишние комманды
     */
    private void removeCommands(){
        tempCommands.addAll(Commands.COMMANDS);
        Commands.COMMANDS.removeIf(c -> c != command);
    }

    /**
     * Возвращаем команды
     */
    private void returnCommands(){
        new Thread(() -> {
            try {
                Thread.sleep(300);
                Commands.COMMANDS.clear();
                Commands.COMMANDS.addAll(tempCommands);
                tempCommands.clear();
                Platform.runLater(() -> Controller.getById("content").requestFocus());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
