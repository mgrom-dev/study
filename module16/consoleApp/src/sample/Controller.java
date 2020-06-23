package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class Controller {
    @FXML
    public GridPane mainForm;

    //Для доступа по ид элемента
    private static HashMap<String, Node> nodes = new HashMap<>();

    /**
     * возвращает ноду по ид
     */
    public static Node getById(String key){
        return nodes.get(key);
    }

    /**
     * добавляем возможность обращения по ид элементу
     */
    private static void initNodesId(Node node){
        String id = node.getId();
        if (id != null) {
            nodes.put(id, node);
        }
        if (node instanceof Pane) {
            ((Pane) node).getChildren().forEach(Controller::initNodesId);
        } else if (node instanceof TabPane) {
            ((TabPane) node).getTabs().forEach(children -> initNodesId(children.getContent()));
        } else if (node instanceof ScrollPane) {
            initNodesId(((ScrollPane) node).getContent());
        }
    }

    public void initialize() {
        initNodesId(mainForm);
        mainForm.addEventHandler(KeyEvent.ANY, k -> {
            Command command = Commands.getByKey(k.getCode() == KeyCode.UNDEFINED ? (k.getCharacter().equals("\r") ? KeyCode.ENTER : KeyCode.UNDEFINED) : k.getCode());
            if (command != null && command.EVENT_TYPE == k.getEventType()) {
                command.ACTION.run();
            }
        });
    }
}
