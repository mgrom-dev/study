package sample;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class Docker extends SplitMenuFactory {
    private static final KeyCode KEY_CODE = KeyCode.F3; //Клавиша для доступа к пунктам меню докера
    private static boolean isReady = false;

    private Docker(){
        //добавляем пункты меню
        menuButton.setText("[F3] - Docker");
        menuButton.getItems().addAll(
                //список запущенных контейнеров
                new MenuItem("show running containers"){{
                    setOnAction((e) ->
                            ((Text) Controller.getById("info")).setText(executeDockerCommand("docker ps"))
                    );
                }},

                //список всех контейнеров
                new MenuItem("show all containers"){{
                    setOnAction((e) ->
                            ((Text) Controller.getById("info")).setText(executeDockerCommand("docker ps -a"))
                    );
                }},

                //остановить все запущенные контейнеры
                new MenuItem("stop containers"){{
                    setOnAction((e) ->
                            ((Text) Controller.getById("info")).setText(executeDockerCommand("docker ps -q"))
                    );
                }}
        );

        //задаем горячую клавишу
        command.KEY = KEY_CODE;
    }

    public static void init(){
        if (!isReady) {
            new Docker();
            isReady = true;
        }
    }

    /**
     * Выполнить команду докера
     */
    private static String executeDockerCommand(String command){
        String response = Commands.executeCmd(command);
        String result;
        if (response.lastIndexOf("the docker client must be run") != -1) {
            result = "Docker not runner";
        } else {
            result = response;
        }
        return result;
    }
}
